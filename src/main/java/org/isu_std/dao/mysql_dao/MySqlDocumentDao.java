package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.folder_setup.FolderManager;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.models.Document;
import org.isu_std.models.ModelHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MySqlDocumentDao implements DocManageDao, DocumentDao{
    private final JDBCHelper jdbcHelper;
    private final MySqlDeletedDocumentDao mySqlDeletedDocumentDao;

    public MySqlDocumentDao(JDBCHelper jdbcHelper, MySqlDeletedDocumentDao mySqlDeletedDocumentDao){
        this.jdbcHelper = jdbcHelper;
        this.mySqlDeletedDocumentDao = mySqlDeletedDocumentDao;
    }

    @Override
    public boolean addDocument(int barangayId, Document document){
        String query = "INSERT INTO document(barangay_id, document_id, " +
                "document_name, price, requirements, doc_file_name, document_file) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try{
            int rowsAffected = jdbcHelper.executeUpdateWithFiles(
                    query,
                    barangayId,
                    getDocumentId(barangayId),
                    document.documentName(),
                    document.price(),
                    document.requirements(),
                    document.documentFile().getName(),
                    document.documentFile()
            );

            return rowsAffected == 1;
        }catch(SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private int getDocumentId(int barangayId) throws SQLException, IOException{
        // Checks first if there's a stored document id.
        Optional<Integer> storedDocumentId = mySqlDeletedDocumentDao.getAndDeleteStoredDocId(barangayId);

        // empty indicates that there's no stored id.
        return storedDocumentId.orElse(getCreatedDocumentId(barangayId));
    }

    private int getCreatedDocumentId(int barangayId) throws SQLException, IOException{
        // Default value = barangayId;
        var strDocIdBuilder = new StringBuilder();

        strDocIdBuilder
                .append(barangayId)
                .append(0);

        setStrBuilderCreatedDocId(barangayId, strDocIdBuilder);

        return Integer.parseInt(strDocIdBuilder.toString());
    }

    private void setStrBuilderCreatedDocId(int barangayId, StringBuilder strDocIdBuilder)
            throws SQLException, IOException{
        Optional<Integer> brgyDocIdCount = getBrgyDocIdCount(barangayId);

        // Document Exist : BarangayId + 0 + 1 + (brgyDocIdCount + 1) -> "100" + "0" + (1 + 1) = "10002"
        if(brgyDocIdCount.isPresent()) {
            strDocIdBuilder.append(brgyDocIdCount.get() + 1);
            return;
        }

        // No Document Exist : BarangayId + 0 + 1 -> "100" + "0" +  "1" = "10001"
        strDocIdBuilder.append(1);
    }

    private Optional<Integer> getBrgyDocIdCount(int barangayId) throws SQLException, IOException{
        String query = "SELECT COUNT(document_id) FROM document WHERE barangay_id = ?";

        return jdbcHelper.executeSingleSet(
                query,
                resultSet -> resultSet.getInt(1),
                barangayId
        );
    }

    @Override
    public boolean updateDocument(String chosenDetail, Document document, int barangayId, int documentId) {
        try{
            //If the document file is modify
            Object infoValue = ModelHelper.getFirstExistingValue(document.getValues());

            if(infoValue instanceof File documentFile){
                return updateDocumentFile(documentFile, barangayId, documentId);
            }

            //Rest of Information
            return updateDocumentInfo(chosenDetail, infoValue, barangayId, documentId);
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private boolean updateDocumentInfo(String chosenDetail, Object infoValue, int barangayId, int documentId)
            throws SQLException, IOException {
        String query = getUpdateDocInfoQuery(chosenDetail);

        int rowsAffected = jdbcHelper.executeUpdate(
                query,
                infoValue,
                barangayId,
                documentId
        );

        return rowsAffected == 1;
    }

    private String getUpdateDocInfoQuery(String chosenDetail){
        return "UPDATE document SET %s = ? WHERE barangay_id = ? AND document_id = ?"
                .formatted(chosenDetail);
    }



    private boolean updateDocumentFile(File docFile, int barangayId, int documentId) throws SQLException, IOException{
        var query = "UPDATE document SET doc_file_name = ?, document_file = ? " +
                "WHERE barangay_id = ? AND document_id = ?";

        int rowsAffected = jdbcHelper.executeUpdateWithFiles(
                query,
                docFile.getName(),
                docFile,
                barangayId,
                documentId
        );

        return rowsAffected == 1;
    }

    @Override
    public Map<Integer, Document> getDocumentMap(int barangayId){
        var query = "SELECT document_id, document_name, price, " +
                "requirements, doc_file_name, document_file FROM document " +
                "WHERE barangay_id = ? ORDER BY document_id ASC";

        try{
            Optional<Map<Integer, Document>> optionalDocumentMap = jdbcHelper.executeSingleSet(
                    query,
                    this::getResultSetDocumentMap,
                    barangayId
            );

            return optionalDocumentMap.orElse(new HashMap<>());
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private @NotNull Map<Integer, Document> getResultSetDocumentMap(ResultSet resultSet)
            throws SQLException, IOException{
        Map<Integer, Document> documentMap = new HashMap<>();

        do {
            int documentId = resultSet.getInt(1);
            Document document = new Document(
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    getResultedFile(
                            resultSet.getString(5),
                            resultSet.getBinaryStream(6)
                    )
            );

            documentMap.put(documentId, document);
        }while (resultSet.next());

        return documentMap;
    }

    private File getResultedFile(String fileName, InputStream inputStream) throws IOException{
        FolderConfig documentPath = FolderConfig.DOC_DOCUMENT_DIRECTORY;
        FolderManager.setFileDirectory(documentPath);

        File file = new File(documentPath.getDirectory() + File.separator + fileName);

        if(file.exists()){
            return file;
        }

        return getOptionalResultedFile(file, inputStream).orElseThrow();
    }

    private Optional<File> getOptionalResultedFile(File file, InputStream inputStream) throws IOException{
        try(OutputStream outputStream = new FileOutputStream(file)){
            byte[] bytes = new byte[8192];
            int reader;

            while((reader = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, reader);
            }

            return Optional.of(file);
        }
    }

    @Override
    public Optional<String> findDocumentName(int barangayId, int documentId){
        var query = "SELECT document_name FROM document " +
                "WHERE barangay_id = ? AND document_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getString(1),
                    barangayId,
                    documentId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteDocument(int barangayId, int documentId){
        String query = "DELETE FROM document WHERE document_id = ? AND barangay_id = ?";

        try{
            if(!mySqlDeletedDocumentDao.storeDocumentId(barangayId, documentId)){
                SystemLogger.logWarning(MySqlDocumentDao.class, "Failed to store the documentID");

                return false;
            }

            int rowsAffected = jdbcHelper.executeUpdate(query, documentId, barangayId);

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException("Failed to delete document by document_id : " + documentId, e);
        }
    }

    @Override
    public Optional<Document> getOptionalDocument(int barangayId, int documentId) {
        var query = "SELECT document_name, price, requirements, doc_file_name, document_file " +
                "FROM document WHERE barangay_id = ? AND document_id = ? LIMIT 1";
        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    this::getResultDocument,
                    barangayId,
                    documentId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private Document getResultDocument(ResultSet resultSet) throws SQLException, IOException{
        return new Document(
                resultSet.getString(1),
                resultSet.getDouble(2),
                resultSet.getString(3),
                getResultedFile(
                        resultSet.getString(4),
                        resultSet.getBinaryStream(5)
                )
        );
    }

    public Optional<Double> findDocumentPrice(int barangayId, int documentId){
        var query = "SELECT price FROM document " +
                "WHERE barangay_id = ? AND document_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getDouble(1),
                    barangayId,
                    documentId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> findDocumentFileName(int barangayId, int documentId){
        var query = "SELECT doc_file_name FROM document " +
                "WHERE barangay_id = ? AND document_id = ?";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getString(1),
                    barangayId,
                    documentId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

}

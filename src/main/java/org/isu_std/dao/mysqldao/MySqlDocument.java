package org.isu_std.dao.mysqldao;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyDocManager;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.database.MySQLDBConnection;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.folder_setup.Folder;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.models.Document;
import org.isu_std.models.modelbuilders.DocumentBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MySqlDocument implements DocManageDao, DocumentDao{
    @Override
    public boolean add(int barangayId, Document document){
        String query = "INSERT INTO document(barangay_id, document_id, " +
                "document_name, price, requirements, doc_file_name, document_file) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
            FileInputStream fileInputStream = new FileInputStream(document.documentFile());
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, getDocumentId(barangayId));
            preStatement.setString(3, document.documentName());
            preStatement.setDouble(4, document.price());
            preStatement.setString(5, document.requirements());
            preStatement.setString(6, document.documentFile().getName());
            preStatement.setBinaryStream(
                    7, fileInputStream, document.documentFile().length()
            );

            return preStatement.executeUpdate() == 1;
        }catch(SQLException | IOException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }

    private int getDocumentId(int barangayId){
        // Checks first if there's a stored document id.
        int storedDocumentId = MySqlDeletedDocument.getStoredDocumentId(barangayId);

        // 0 indicates that there's no stored id.
        if(storedDocumentId != 0){
            return storedDocumentId;
        }

        // Default value = barangayId;
        StringBuilder strDocIdBuilder = new StringBuilder();
        strDocIdBuilder
                .append(barangayId)
                .append(0);

        setStrBuilderCreatedDocId(getBrgyDocIdCount(barangayId), strDocIdBuilder);

        return Integer.parseInt(strDocIdBuilder.toString());
    }

    private void setStrBuilderCreatedDocId(int brgyDocIdCount, StringBuilder strDocIdBuilder){
        // No Document Exist : BarangayId + 0 + 1 -> "100" + "0" +  "1" = "10001"
        if(brgyDocIdCount == 0){
            strDocIdBuilder.append(1);
            return;
        }

        // Document Exist : BarangayId + 1 + (brgyDocIdCount + 1) -> "100" + (1 + 1) = "1002"
        strDocIdBuilder.append(brgyDocIdCount + 1);
    }

    private int getBrgyDocIdCount(int barangayId){
        String query = "SELECT COUNT(document_id) FROM document WHERE barangay_id = ?";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return 0;
    }

    @Override
    public boolean modify(ModifyDocManager modifyDocManager) {
        try(Connection connection = MySQLDBConnection.getConnection()) {
            if(modifyDocManager.getDocumentBuilder().getDocumentFile() != null){
                return modifyDocumentFile(connection, modifyDocManager);
            }

            return modifyDocumentInfo(connection, modifyDocManager);
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return false;
    }

    private boolean modifyDocumentInfo(Connection connection, ModifyDocManager modifyDocManager)
            throws SQLException, IllegalStateException
    {
        String query = "UPDATE document SET %s = ? WHERE barangay_id = ? AND document_id = ?"
                .formatted(modifyDocManager.getDocumentDetail());

        try(PreparedStatement preStatement = connection.prepareStatement(query)){
            setModifyPreStatement(1, preStatement, modifyDocManager);
            preStatement.setInt(2, modifyDocManager.getBarangayId());
            preStatement.setInt(3, modifyDocManager.getDocumentId());

            return preStatement.executeUpdate() == 1;
        }
    }

    private void setModifyPreStatement(
            int parameterIndex, PreparedStatement preStatement, ModifyDocManager modifyDocManager
    ) throws SQLException {
        Document document = modifyDocManager.getDocumentBuilder().build();

        if(document.documentName() != null){
            preStatement.setString(parameterIndex, document.documentName());
            return;
        }

        if(document.price() != 0){
            preStatement.setDouble(parameterIndex, document.price());
            return;
        }

        if(document.requirements() != null){
            preStatement.setString(parameterIndex, document.requirements());
            return;
        }

        throw new IllegalStateException("Unexpected error -> The document argument contains no value.");
    }

    private boolean modifyDocumentFile(Connection connection, ModifyDocManager modifyDocManager) throws SQLException{
        String query = "UPDATE document SET doc_file_name = ?, document_file = ? " +
                "WHERE barangay_id = ? AND document_id = ?";

        File docFile = modifyDocManager.getDocumentBuilder().getDocumentFile();

        try(PreparedStatement preStatement = connection.prepareStatement(query);
            FileInputStream fileInputStream = new FileInputStream(docFile);
        ){
            preStatement.setString(1, docFile.getName());
            preStatement.setBinaryStream(2, fileInputStream, docFile.length());
            preStatement.setInt(3, modifyDocManager.getBarangayId());
            preStatement.setInt(4, modifyDocManager.getDocumentId());

            return preStatement.executeUpdate() == 1;
        }catch (IOException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return false;
    }

    @Override
    public Map<Integer, Document> getDocumentMap(int barangayId){
        String query = "SELECT document_id, document_name, price, " +
                "requirements, doc_file_name, document_file FROM document " +
                "WHERE barangay_id = ? ORDER BY document_id ASC";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, barangayId);

            ResultSet resultSet = preStatement.executeQuery();
            return getDocumentMap(resultSet);
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return new HashMap<>();
    }

    private @NotNull Map<Integer, Document> getDocumentMap(ResultSet resultSet) throws SQLException{
        Map<Integer, Document> documentMap = new HashMap<>();

        while(resultSet.next()){
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
        }

        return documentMap;
    }

    private File getResultedFile(String fileName, InputStream inputStream){
        String path = FolderConfig.DOC_DOCUMENT_PATH.getPath();
        Folder.setFileFolder(path);

        File file = new File(path + File.separator + fileName);
        if(file.exists()){
            return file;
        }

        return getOptionalResultedFile(file, inputStream).orElseThrow();
    }

    private Optional<File> getOptionalResultedFile(File file, InputStream inputStream){
        try(OutputStream outputStream = new FileOutputStream(file)){
            byte[] bytes = new byte[8192];
            int reader;

            while((reader = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, reader);
            }

            return Optional.of(file);
        }catch (IOException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> getDocumentName(int barangayId, int documentId){
        String query = "SELECT document_name FROM document WHERE barangay_id = ? AND document_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(resultSet.getString(1));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean isDeleteSuccess(int barangayId, int documentId){
        if(!MySqlDeletedDocument.storeDocumentId(barangayId, documentId)){
            SystemLogger.logWarning(MySqlDocument.class, "Failed to store the documentID");
            return false;
        }

        String query = "DELETE FROM document WHERE document_id = ? AND barangay_id = ?";
        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, documentId);
            preStatement.setInt(2, barangayId);

            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return false;
    }

    @Override
    public Optional<Document> getOptionalDocument(int barangayId, int documentId) {
        String query = "SELECT document_name, price, requirements, doc_file_name, document_file " +
                "FROM document WHERE barangay_id = ? AND document_id = ? LIMIT 1";
        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(getResultDocument(resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return Optional.empty();
    }

    private Document getResultDocument(ResultSet resultSet) throws SQLException{
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

    @Override
    public Optional<Document> getOptionalDocDetail(int barangayId, int documentId) {
        String query = "SELECT document_name, price, requirements " +
                "FROM document WHERE barangay_id = ? AND document_id = ?";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                Document documentDetail = buildDocumentDetail(resultSet);
                return Optional.of(documentDetail);
            }
        }catch(SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return Optional.empty();
    }

    private Document buildDocumentDetail(ResultSet resultSet) throws SQLException{
        DocumentBuilder documentBuilder = new DocumentBuilder();

        documentBuilder
                .documentName(resultSet.getString(1))
                .price(resultSet.getDouble(2))
                .requirements(resultSet.getString(3));

        return documentBuilder.build();
    }

    public double getDocumentPrice(int barangayId, int documentId){
        String query = "SELECT price FROM document WHERE barangay_id = ? AND document_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocument.class, e.getMessage());
        }

        return 0;
    }
}

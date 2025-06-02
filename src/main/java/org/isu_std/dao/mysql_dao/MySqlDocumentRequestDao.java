package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.folder_setup.FolderManager;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.io.SystemLogger;
import org.isu_std.models.DocumentRequest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MySqlDocumentRequestDao implements DocumentRequestDao {
    private final JDBCHelper jdbcHelper;

    public MySqlDocumentRequestDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    @Override
    public boolean addDocRequest(DocumentRequest documentRequest) {
        var query = "INSERT INTO document_request(reference_id, user_id, barangay_id, document_id) " +
                "values(?, ?, ?, ?)";

        try{
            int affectedRows = jdbcHelper.executeUpdate(
                    query,
                    documentRequest.referenceId(),
                    documentRequest.userId(),
                    documentRequest.barangayId(),
                    documentRequest.documentId()
            );

            if(affectedRows != 1){
                return false;
            }

            return addDocRequirementRequest(
                    documentRequest.referenceId(),
                    documentRequest.requirementDocList()
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private boolean addDocRequirementRequest(String referenceId, List<File> requirementDocList)
            throws SQLException, IOException{
        for(File file : requirementDocList){
            if(!addDocRequirementProcess(referenceId, file)){
                return false;
            }
        }

        return true;
    }

    private boolean addDocRequirementProcess(String referenceId, File file) throws SQLException, IOException{
        var query = "INSERT INTO doc_requirement_request" +
                "(reference_id, doc_requirement_name, doc_requirement_file) " +
                "values(?, ?, ?)";

        int rowsAffected = jdbcHelper.executeUpdateWithFiles(
                query, referenceId, file.getName(), file
        );

        return rowsAffected == 1;
    }

    @Override
    public List<DocumentRequest> getBrgyDocReqPendingList(int barangayId) {
        String query = "SELECT reference_id, user_id, barangay_id, document_id " +
                "FROM document_request WHERE barangay_id = ? AND is_approve = FALSE";

        return getDocumentRequestList(query, barangayId);
    }

    @Override
    public List<DocumentRequest> getApprovedDocList(int barangayId){
        var query = "SELECT reference_id, user_id, barangay_id, document_id " +
                "FROM document_request WHERE barangay_id = ? AND is_approve = TRUE";

        return getDocumentRequestList(query, barangayId);
    }

    private List<DocumentRequest> getDocumentRequestList(String query, int barangayId){
        try{
            return jdbcHelper.executeQuery(
                    query,
                    this::getDocumentRequest,
                    barangayId
            );
        }catch (SQLException | IOException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private DocumentRequest getDocumentRequest(ResultSet resultSet) throws SQLException, IOException{
        String referenceId =  resultSet.getString(1);

        return new DocumentRequest(
                referenceId,
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                getRequirementFileList(referenceId)
        );
    }

    private List<File> getRequirementFileList(String referenceId) throws SQLException, IOException {
        String query = "SELECT doc_requirement_name, doc_requirement_file " +
                "FROM doc_requirement_request WHERE reference_id = ?";

        FolderConfig folderPath = FolderConfig.DOC_REQUEST_DIRECTORY;

        // Setting the folder that stores the requirement files.
        FolderManager.setFileDirectory(folderPath);

        return jdbcHelper.executeQuery(
                query,
                resultSet -> getResultRequirmentFile(resultSet, folderPath),
                referenceId
        );
    }

    private File getResultRequirmentFile(ResultSet resultSet, FolderConfig folderPath)
            throws SQLException, IOException{
        String fileName = resultSet.getString(1);
        File file = new File(
                folderPath.getDirectory() + File.separator + fileName
        );

        if(file.exists()){
            return file;
        }

        writeNewReqFile(resultSet, file);

        return file;
    }

    private void writeNewReqFile(ResultSet resultSet, File file) throws SQLException, IOException{
        try(InputStream inputStream = resultSet.getBinaryStream(2);
            OutputStream outputStream = new FileOutputStream(file)
        ){
            byte[] bytes = new byte[8192];
            int reader;

            while ((reader = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, reader);
            }
        }
    }

    private boolean requestQueryPerform(String query, String operationType, String referenceId){
        try{
            return jdbcHelper.executeUpdate(query, referenceId) == 1;
        }catch (SQLException e){
            throw new DataAccessException(
                    "Failed to %s by reference_id : %s".formatted(operationType, referenceId), e
            );
        }
    }

    @Override
    public boolean requestApprove(String referenceId){
        var query = "UPDATE document_request SET is_Approve = TRUE WHERE reference_id = ? ";

        return requestQueryPerform(query, "approve request", referenceId);
    }

    @Override
    public boolean deleteDocRequest(String referenceId){
        String query = "DELETE FROM document_request WHERE reference_id = ?";

        return requestQueryPerform(query, "delete request", referenceId);
    }

    @Override
    public Optional<Integer> getUserDocRequestCount(DocumentRequest documentRequest) {
        var query = "SELECT COUNT(user_id) FROM document_request " +
                "WHERE user_id = ? AND barangay_id = ? AND document_id = ?";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getInt(1),
                    documentRequest.userId(),
                    documentRequest.barangayId(),
                    documentRequest.documentId()
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Boolean> isRequestApproved(String referenceId){
        var query = "SELECT is_approve FROM document_request WHERE reference_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getBoolean(1),
                    referenceId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentRequest> getUserReqDocList(int userId, int barangayId) {
        var query = "SELECT reference_id, document_id " +
                "FROM document_request WHERE user_id = ? AND barangay_id = ?";

        try{
            return jdbcHelper.executeQuery(
                    query,
                    resultSet -> getUserRequest(resultSet, userId, barangayId),
                    userId, barangayId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public DocumentRequest getUserRequest(ResultSet resultSet, int userId, int barangayId)
            throws SQLException, IOException{
        String referenceId = resultSet.getString(1);
        int documentId = resultSet.getInt(2);

        return new DocumentRequest(
                referenceId,
                userId,
                barangayId,
                documentId,
                getRequirementFileList(referenceId)
        );
    }
}

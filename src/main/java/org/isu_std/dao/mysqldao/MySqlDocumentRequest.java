package org.isu_std.dao.mysqldao;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.database.MySQLDBConnection;
import org.isu_std.io.folder_setup.Folder;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.io.SystemLogger;
import org.isu_std.models.DocumentRequest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlDocumentRequest implements DocumentRequestDao {
    @Override
    public boolean addDocRequest(DocumentRequest documentRequest) {
        String query = "INSERT INTO document_request(reference_id, user_id, barangay_id, document_id) " +
                "values(?, ?, ?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setString(1, documentRequest.referenceId());
            preStatement.setInt(2, documentRequest.userId());
            preStatement.setInt(3, documentRequest.barangayId());
            preStatement.setInt(4, documentRequest.documentId());

            if(preStatement.executeUpdate() != 1){
                return false;
            }

            return addDocRequirementRequest(
                    connection,
                    documentRequest.referenceId(),
                    documentRequest.requirementDocList()
            );
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }

    private boolean addDocRequirementRequest(Connection connection, String referenceId, List<File> requirementDocList) throws SQLException{
        for(File file : requirementDocList){
            if(!addDocRequirementProcess(connection, referenceId, file)){
                return false;
            }
        }

        return true;
    }

    private boolean addDocRequirementProcess(Connection connection, String referenceId, File file) throws SQLException{
        String query = "INSERT INTO doc_requirement_request" +
                "(reference_id, doc_requirement_name, doc_requirement_file) " +
                "values(?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query);
            FileInputStream fileInputStream = new FileInputStream(file)
        ){
            preparedStatement.setString(1, referenceId);
            preparedStatement.setString(2, file.getName());
            preparedStatement.setBinaryStream(3, fileInputStream, file.length());

            return preparedStatement.executeUpdate() == 1;
        }catch (IOException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }

    @Override
    public List<DocumentRequest> getDocRequestPendingList(int barangayId) {
        String query = "SELECT reference_id, user_id, barangay_id, document_id " +
                "FROM document_request WHERE barangay_id = ? AND is_approve = FALSE";

        List<DocumentRequest> docReqList = new ArrayList<>();

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);

            ResultSet resultSet = preStatement.executeQuery();
            while(resultSet.next()) {
                docReqList.add(getDocumentRequest(connection, resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return docReqList;
    }

    @Override
    public List<DocumentRequest> getApprovedDocList(int barangayId){
        var query = "SELECT reference_id, user_id, barangay_id, document_id " +
                "FROM document_request WHERE barangay_id = ? AND is_approve = TRUE";

        List<DocumentRequest> approvedDocList = new ArrayList<>();

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);

            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()){
                approvedDocList.add(getDocumentRequest(connection, resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return approvedDocList;
    }

    private DocumentRequest getDocumentRequest(Connection connection, ResultSet resultSet) throws SQLException{
        return new DocumentRequest(
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                getRequirementFileList(connection, resultSet.getString(1))
        );
    }

    private List<File> getRequirementFileList(Connection connection, String referenceId) throws SQLException {
        String query = "SELECT doc_requirement_name, doc_requirement_file " +
                "FROM doc_requirement_request WHERE reference_id = ?";

        List<File> reqFileList = new ArrayList<>();
        String path = FolderConfig.DOC_REQUEST_PATH.getPath();

        // Setting the folder that stores the requirement files.
        setDocReqFilePath(path);

        try(PreparedStatement preStatement = connection.prepareStatement(query)){
            preStatement.setString(1, referenceId);
            ResultSet resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                Optional<File> optionalFile = getOptionalReqFile(resultSet, path);
                optionalFile.ifPresent(reqFileList::add);
            }

            return reqFileList;
        }
    }

    private Optional<File> getOptionalReqFile(ResultSet resultSet, String path) throws SQLException{
        String fileName = resultSet.getString(1);
        InputStream inputStream = resultSet.getBinaryStream(2);

        File file = new File(path + File.separator + fileName);

        if(file.exists()){
            return Optional.of(file);
        }

        return createOptionalNewReqFile(inputStream, file);
    }

    private Optional<File> createOptionalNewReqFile(InputStream inputStream, File file){
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

    private void setDocReqFilePath(String filePath){
        if(!new File(filePath).isDirectory()){
            Folder.createFolder(filePath);
        }
    }

    @Override
    public boolean setRequestApprove(String referenceId){
        String query = "MODIFY document_request SET is_Approve = TRUE WHERE reference_id = ? ";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, referenceId);
            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteDocRequest(String referenceId){
        String query = "DELETE FROM document_request WHERE reference_id = ?";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, referenceId);
            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }

    @Override
    public int getUserDocRequestCount(DocumentRequest documentRequest) {
        String query = "SELECT COUNT(user_id) FROM document_request " +
                "WHERE user_id = ? AND barangay_id = ? AND document_id = ?";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, documentRequest.userId());
            preStatement.setInt(2, documentRequest.barangayId());
            preStatement.setInt(3, documentRequest.documentId());

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return 0;
    }

    public boolean isRequestApprove(String referenceId){
        var query = "SELECT is_approve FROM document_request WHERE reference_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, referenceId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getBoolean(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDocumentRequest.class, e.getMessage());
        }

        return false;
    }
}

package org.isu_std.dao.mysqldao;

import org.isu_std.database.MySQLDBConnection;
import org.isu_std.io.SystemLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Class that handles the deleted documents.
// Doesn't need to create object. Functional programming was used in this class.

public class MySqlDeletedDoc {
    private MySqlDeletedDoc(){}

    public static boolean storeDocumentId(int barangayId, int documentId){
        // Executes when the admin deleted a document. This method store the document id.
        String query = "INSERT INTO deleted_documents(barangay_id, document_id) VALUES(?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDeletedDoc.class, e.getMessage());
        }

        return false;
    }

    public static int getStoredDocumentId(int barangayId){
        // Returns a document id when the admin create a new document.
        String query = "SELECT document_id FROM deleted_documents WHERE barangay_id = ? ORDER BY document_id LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query)
        ){
            preStatement.setInt(1, barangayId);
            ResultSet resultSet = preStatement.executeQuery();
            if(!resultSet.next()){
                return 0;
            }

            int storedDocId = resultSet.getInt(1);
            if(deleteStoredDocumentId(connection, barangayId, storedDocId)){
                return storedDocId;
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlDeletedDoc.class, e.getMessage());
        }

        return 0;
    }

    private static boolean deleteStoredDocumentId(Connection connection, int barangayId, int documentId) throws SQLException{
        // Delete the stored document id once it used.
        String query = "DELETE FROM deleted_documents WHERE barangay_id = ? AND document_id = ?";

        try(PreparedStatement preStatement = connection.prepareStatement(query)){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, documentId);

            return preStatement.executeUpdate() == 1;
        }
    }
}

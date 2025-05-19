package org.isu_std.dao.mysql_dao;

import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

// Class that handles the deleted documents.
// Doesn't need to create object. Functional programming was used in this class.

public class MySqlDeletedDocumentDao {
    private final JDBCHelper jdbcHelper;

    protected MySqlDeletedDocumentDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    protected boolean storeDocumentId(int barangayId, int documentId) throws  SQLException, IOException{
        // Executes when the admin deleted a document. This method store the document id.
        var query = "INSERT INTO deleted_documents(barangay_id, document_id) VALUES(?, ?)";

        int rowsAffected = jdbcHelper.executeUpdate(query, barangayId, documentId);
        return rowsAffected == 1;
    }

    protected Optional<Integer> findStoredDocumentId(int barangayId) throws SQLException, IOException{
        // Returns a document id when the admin create a new document.
        var query = "SELECT document_id FROM deleted_documents " +
                "WHERE barangay_id = ? ORDER BY document_id LIMIT 1";

        return jdbcHelper.executeSingleSet(
                query,
                resultSet -> resultSet.getInt(1),
                barangayId
        );
    }

    private boolean deleteStoredDocumentId(int barangayId, int documentId) throws SQLException, IOException{
        // Delete the stored document id once it used.
        String query = "DELETE FROM deleted_documents WHERE barangay_id = ? AND document_id = ?";
        return jdbcHelper.executeUpdate(query, barangayId, documentId) == 1;
    }
}

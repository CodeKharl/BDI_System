package org.isu_std.dao;

// Class that manages connectors for the connection of database with the UI (front <-> back).

import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.dao.mysql_dao.*;

public class DaoFactory {
    private final JDBCHelper jdbcHelper;

    public DaoFactory(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    public UserDao createUserDao(){
        return new MySqlUserDao(jdbcHelper);
    }

    public BarangayDao createBrgyDao(){
        return new MySqlBarangayDao(jdbcHelper);
    }

    public DocumentDao getDocumentDao(){
        return new MySqlDocumentDao(jdbcHelper, createDeletedDocumentDao());
    }

    public AdminDao createAdminDao(){
        return new MySqlAdminDao(jdbcHelper);
    }

    public DocManageDao getDocManageDao(){
        return new MySqlDocumentDao(jdbcHelper, createDeletedDocumentDao());
    }

    public UserPersonalDao getPersonalDao(){
        return new MySqlUserDao(jdbcHelper);
    }

    public DocumentRequestDao getDocumentRequestDao(){
        return new MySqlDocumentRequestDao(jdbcHelper);
    }

    public PaymentDao paymentDao(){
        return new MySqlPaymentDao(jdbcHelper);
    }

    private MySqlDeletedDocumentDao createDeletedDocumentDao(){
        return new MySqlDeletedDocumentDao(jdbcHelper);
    }
}

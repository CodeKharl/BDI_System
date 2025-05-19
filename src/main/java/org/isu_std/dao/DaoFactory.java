package org.isu_std.dao;

// Class that manages connectors for the connection of database with the UI (front <-> back).

import org.isu_std.dao.mysql_dao.*;

public class DaoFactory {
    public UserDao createUserDao(){
        return new MySqlUserDao();
    }

    public BarangayDao createBrgyDao(){
        return new MySqlBarangayDao();
    }

    public DocumentDao getDocumentDao(){
        return new MySqlDocumentDao();
    }

    public AdminDao createAdminDao(){
        return new MySqlAdminDao();
    }

    public DocManageDao getDocManageDao(){
        return new MySqlDocumentDao();
    }

    public UserPersonalDao getPersonalDao(){
        return new MySqlUserDao();
    }

    public DocumentRequestDao getDocumentRequestDao(){
        return new MySqlDocumentRequestDao();
    }

    public PaymentDao paymentDao(){
        return new MySqlPaymentDao();
    }
}

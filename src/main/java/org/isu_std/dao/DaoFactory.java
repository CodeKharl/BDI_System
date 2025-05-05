package org.isu_std.dao;

// Class that manages connectors for the connection of database with the UI (front <-> back).

import org.isu_std.dao.mysqldao.*;

public class DaoFactory {
    public UserDao createUserDao(){
        return new MySqlUser();
    }

    public BarangayDao createBrgyDao(){
        return new MySqlBarangay();
    }

    public DocumentDao getDocumentDao(){
        return new MySqlDocument();
    }

    public AdminDao createAdminDao(){
        return new MySqlAdmin();
    }

    public DocManageDao getDocManageDao(){
        return new MySqlDocument();
    }

    public UserPersonalDao getPersonalDao(){
        return new MySqlUser();
    }

    public DocumentRequestDao getDocumentRequestDao(){
        return new MySqlDocumentRequest();
    }

    public PaymentDao paymentDao(){
        return new MySqlPayment();
    }
}

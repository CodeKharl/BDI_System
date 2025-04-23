package org.isu_std.dao;

// Class that manages connectors for the connection of database with the UI (front <-> back).

import org.isu_std.dao.mysqldao.*;

public class DaoFactory {
    private static final class Holder{
        private final static DaoFactory daoFactory = new DaoFactory();
    }

    public static DaoFactory getInstance(){
        return Holder.daoFactory;
    }

    public UserDao getUserDao(){
        return new MySqlUser();
    }

    public BarangayDao getBrgyDao(){
        return new MySqlBarangay();
    }

    public DocumentDao getDocumentDao(){
        return new MySqlDocument();
    }

    public AdminDao getAdminDao(){
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

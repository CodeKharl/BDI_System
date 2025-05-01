package org.isu_std.admin.admin_main;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

import java.util.Optional;

public class ReqDocsManagerProvider {
    private ReqDocsManagerProvider(){}

    public static UserPersonal getUserPersonal(UserPersonalDao userPersonalDao, int userId){
        Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);
        return optionalUserPersonal.orElseThrow(
                () -> new OperationFailedException("Failed to get the informations of the user.")
        );
    }

    public static Document getDocument(DocumentDao documentDao, int barangayId, int docId){
        Optional<Document> optionalDocument = documentDao.getOptionalDocument(barangayId, docId);
        return optionalDocument.orElseThrow(
                () -> new OperationFailedException("Failed to get the document.")
        );
    }

    public static Payment getPayment(PaymentDao paymentDao, String referenceId){
        Optional<Payment> optionalPayment = paymentDao.getOptionalPayment(referenceId);
        return optionalPayment.orElse(null);
    }
}

package org.isu_std.admin.admin_main;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

import java.util.Optional;

public class ReqDocsManagerProvider {
    private final UserPersonalDao userPersonalDao;
    private final DocumentDao documentDao;
    private final PaymentDao paymentDao;

    public ReqDocsManagerProvider(UserPersonalDao userPersonalDao, DocumentDao documentDao, PaymentDao paymentDao){
        this.userPersonalDao = userPersonalDao;
        this.documentDao = documentDao;
        this.paymentDao = paymentDao;
    }

    public UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);

        return optionalUserPersonal.orElseThrow(
                () -> new NotFoundException("User personal information not found!")
        );
    }

    public Document getDocument(int barangayId, int docId){
        Optional<Document> optionalDocument = documentDao.getOptionalDocument(barangayId, docId);

        return optionalDocument.orElseThrow(
                () -> new NotFoundException("Document information not found!")
        );
    }

    public Payment getPayment(String referenceId){
        Optional<Payment> optionalPayment = paymentDao.getOptionalPayment(referenceId);
        return optionalPayment.orElse(null);
    }
}

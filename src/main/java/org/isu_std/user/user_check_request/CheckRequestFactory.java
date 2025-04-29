package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.models.Document;

public class CheckRequestFactory {
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final PaymentDao paymentDao;

    public CheckRequestFactory(DocumentDao documentDao, DocumentRequestDao documentRequestDao, PaymentDao paymentDao){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.paymentDao = paymentDao;
    }

    public CheckRequest create(int barangayId, int userId){
        CheckRequestService service = new CheckRequestService(documentRequestDao, documentDao, paymentDao);
        CheckRequestController controller = new CheckRequestController(service, barangayId, userId);

        return new CheckRequest(controller);
    }
}

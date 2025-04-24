package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.models.Document;

public class CheckRequestFactory {
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;

    public CheckRequestFactory(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
    }

    public CheckRequest create(int barangayId, int userId){
        CheckRequestService service = new CheckRequestService(documentRequestDao, documentDao);
        CheckRequestController controller = new CheckRequestController(service, barangayId, userId);

        return new CheckRequest(controller);
    }
}

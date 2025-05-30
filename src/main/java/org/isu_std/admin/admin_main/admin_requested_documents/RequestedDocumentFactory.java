package org.isu_std.admin.admin_main.admin_requested_documents;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.Barangay;

public class RequestedDocumentFactory {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;

    private RequestedDocumentFactory(DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
    }

    private final static class Holder{
        private static RequestedDocumentFactory instance;
    }

    public static RequestedDocumentFactory getInstance(DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao){
        if(Holder.instance == null){
            Holder.instance = new RequestedDocumentFactory(documentRequestDao, documentDao, userPersonalDao);
        }

        return Holder.instance;
    }

    public RequestedDocument createRequestedDocument(Barangay barangay){
        return new RequestedDocument(
                new RequestedDocumentController(
                        new RequestedDocumentService(documentRequestDao, documentDao, userPersonalDao),
                        barangay
                )
        );
    }
}

package org.isu_std.user.user_document_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_document_request.document_request_contexts.UserInfoContext;

public class UserDocumentReqFactory {
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;

    private UserDocumentReqFactory(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
    }

    private static final class Holder{
        private static UserDocumentReqFactory userDocumentReqFactory;
    }

    public static UserDocumentReqFactory getInstance(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        if(Holder.userDocumentReqFactory == null){
            Holder.userDocumentReqFactory = new UserDocumentReqFactory(documentDao, documentRequestDao);
        }

        return Holder.userDocumentReqFactory;
    }

    public UserDocumentRequest createUserDocReq(User user, UserPersonal userPersonal){
        return new UserDocumentRequest(
                new UserDocumentRequestController(
                        new UserDocumentRequestService(documentDao, documentRequestDao),
                        new UserInfoContext(user, userPersonal)
                )
        );
    }
}

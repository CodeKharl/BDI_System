package org.isu_std.user.user_document_request;

import org.isu_std.client_context.UserContext;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;

public class UserDocumentReqFactory {
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;

    public UserDocumentReqFactory(
            DocumentDao documentDao, DocumentRequestDao documentRequestDao, UserPersonalDao userPersonalDao
    ){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.userPersonalDao = userPersonalDao;
    }

    public UserDocumentRequest createUserDocReq(UserContext userContext){
        return new UserDocumentRequest(
                new UserDocRequestController(
                        new UserDocRequestService(documentDao, documentRequestDao, userPersonalDao),
                        userContext
                )
        );
    }
}

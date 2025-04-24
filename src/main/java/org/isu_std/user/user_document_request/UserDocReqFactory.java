package org.isu_std.user.user_document_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_document_request.docReqManager.UserInfoManager;

public class UserDocReqFactory{
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;

    private UserDocReqFactory(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
    }

    private static final class Holder{
        private static UserDocReqFactory userDocReqFactory;
    }

    public static UserDocReqFactory getInstance(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        if(Holder.userDocReqFactory == null){
            Holder.userDocReqFactory = new UserDocReqFactory(documentDao, documentRequestDao);
        }

        return Holder.userDocReqFactory;
    }

    public UserDocumentRequest createUserDocReq(User user, UserPersonal userPersonal){
        return new UserDocumentRequest(
                new UserDocumentRequestController(
                        new UserDocumentRequestService(documentDao, documentRequestDao),
                        new UserInfoManager(user, userPersonal)
                )
        );
    }
}

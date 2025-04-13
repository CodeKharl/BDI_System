package org.isu_std.user.userDocumentRequest;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.userDocumentRequest.docReqManager.UserInfoManager;

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

    public UserDocReq createUserDocReq(User user, UserPersonal userPersonal){
        return new UserDocReq(
                new UserDocReqController(
                        new UserDocReqService(documentDao, documentRequestDao),
                        new UserInfoManager(user, userPersonal)
                )
        );
    }
}

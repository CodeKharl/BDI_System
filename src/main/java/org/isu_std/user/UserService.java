package org.isu_std.user;

import org.isu_std.client_context.UserContext;
import org.isu_std.dao.*;
import org.isu_std.io.Symbols;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_acc_manage.UserManageAcc;
import org.isu_std.user.user_acc_manage.UserManageAccFactory;
import org.isu_std.user.user_check_request.CheckRequest;
import org.isu_std.user.user_check_request.CheckRequestFactory;
import org.isu_std.user.user_document_request.UserDocumentRequest;
import org.isu_std.user.user_document_request.UserDocumentReqFactory;

import java.util.Optional;

public class UserService {
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;
    private final DocumentRequestDao documentRequestDao;
    private final PaymentDao paymentDao;
    private final BarangayDao barangayDao;
    private final UserDao userDao;

    public UserService(
            DocumentDao documentDao, UserPersonalDao userPersonalDao,
            DocumentRequestDao documentRequestDao, PaymentDao paymentDao,
            BarangayDao barangayDao, UserDao userDao
    ){
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.documentRequestDao = documentRequestDao;
        this.paymentDao = paymentDao;
        this.barangayDao = barangayDao;
        this.userDao = userDao;
    }

    protected UserProcess[] createUserProcesses(UserContext userContext) throws NotFoundException{
        UserDocumentRequest userDocumentRequest = getUserDocReq(userContext);
        CheckRequest checkRequest = getCheckRequest(userContext);
        UserManageAcc userManageAcc = getUserManageAcc(userContext);

        return new UserProcess[]{
                userDocumentRequest,
                checkRequest,
                userManageAcc
        };
    }

    private UserDocumentRequest getUserDocReq(UserContext userContext) throws NotFoundException{
        var userDocumentReqFactory = new UserDocumentReqFactory(
                documentDao, documentRequestDao, userPersonalDao
        );

        return userDocumentReqFactory.createUserDocReq(userContext);
    }

    private UserManageAcc getUserManageAcc(UserContext userContext){
        UserManageAccFactory userManageAccFactory = new UserManageAccFactory(userPersonalDao, barangayDao, userDao);
        return userManageAccFactory.createUserManageAcc(userContext);
    }

    private CheckRequest getCheckRequest(UserContext userContext){
        CheckRequestFactory checkRequestFactory = new CheckRequestFactory(documentDao, documentRequestDao, paymentDao);
        return checkRequestFactory.create(userContext);
    }
}

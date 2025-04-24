package org.isu_std.user;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.Symbols;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.User;
import org.isu_std.dao.DocumentDao;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_acc_manage.UserManageAcc;
import org.isu_std.user.user_acc_manage.UserManageAccFactory;
import org.isu_std.user.user_check_request.CheckRequest;
import org.isu_std.user.user_check_request.CheckRequestFactory;
import org.isu_std.user.user_document_request.UserDocumentRequest;
import org.isu_std.user.user_document_request.UserDocReqFactory;

import java.util.Optional;

public class UserService {
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;
    private final DocumentRequestDao documentRequestDao;

    private UserService(DocumentDao documentDao, UserPersonalDao userPersonalDao, DocumentRequestDao documentRequestDao){
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.documentRequestDao = documentRequestDao;
    }

    private final static class Holder{
        private static UserService userService;
    }

    protected static UserService getInstance(DocumentDao documentDao, UserPersonalDao userPersonalDao, DocumentRequestDao documentRequestDao){
        if(Holder.userService == null){
            Holder.userService = new UserService(documentDao, userPersonalDao, documentRequestDao);
        }

        return Holder.userService;
    }

    protected UserProcess[] createUserProcesses(User user) throws NotFoundException{
        int userId = user.userId();
        UserDocumentRequest userDocumentRequest = getUserDocReq(user);
        CheckRequest checkRequest = getCheckRequest(user.barangayId(), userId);
        UserManageAcc userManageAcc = getUserManageAcc(userId);

        return new UserProcess[]{
                userDocumentRequest,
                checkRequest,
                userManageAcc
        };
    }

    private UserDocumentRequest getUserDocReq(User user) throws NotFoundException{
        UserDocReqFactory userDocReqFactory = UserDocReqFactory.getInstance(documentDao, documentRequestDao);
        return userDocReqFactory.createUserDocReq(user, getUserPersonal(user.userId()));
    }

    private UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);

        //message for the user to put some personal information of his/her account if not exist.
        String guideMessage = "Guide : User Menu -> Manage Account -> Personal Information";

        return optionalUserPersonal.orElseThrow(
                () -> new NotFoundException(
                        "Theres no existing personal information of your account!\n%s%s"
                                .formatted(Symbols.MESSAGE.getType(), guideMessage)
                )
        );
    }

    private UserManageAcc getUserManageAcc(int userId){
        return UserManageAccFactory
                .getInstance(userPersonalDao)
                .createUserManageAcc(userId);
    }

    private CheckRequest getCheckRequest(int barangayId, int userId){
        CheckRequestFactory checkRequestFactory = new CheckRequestFactory(documentDao, documentRequestDao);
        return checkRequestFactory.create(barangayId, userId);
    }
}

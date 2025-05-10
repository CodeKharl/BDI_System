package org.isu_std.user.user_document_request.document_request_contexts;

import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.model_builders.UserBuilder;

public class UserInfoContext{
    private final User user;

    private UserPersonal userPersonal;

    public UserInfoContext(User user){
        this.user = user;
    }

    public void setUserPersonal(UserPersonal userPersonal) {
        this.userPersonal = userPersonal;
    }

    public User getUser() {
        return user;
    }

    public UserPersonal getUserPersonal() {
        return userPersonal;
    }
}

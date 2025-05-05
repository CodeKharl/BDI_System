package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.models.User;
import org.isu_std.models.model_builders.UserPersonalBuilder;

public class ModifyPersonalContext {
    private final User user;
    private final UserPersonalBuilder userPersonalBuilder;

    private String chosenDetail;

    public ModifyPersonalContext(User user, UserPersonalBuilder userPersonalBuilder){
        this.user = user;
        this.userPersonalBuilder = userPersonalBuilder;
    }

    protected User getUser() {
        return user;
    }

    protected void setChosenDetail(String chosenDetail){
        this.chosenDetail = chosenDetail;
    }

    protected String getChosenDetail() {
        return chosenDetail;
    }

    protected UserPersonalBuilder getUserPersonalBuilder() {
        return userPersonalBuilder;
    }
}
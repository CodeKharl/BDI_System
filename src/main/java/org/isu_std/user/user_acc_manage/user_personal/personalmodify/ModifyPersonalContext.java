package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.models.User;
import org.isu_std.models.model_builders.UserPersonalBuilder;

public class ModifyPersonalContext {
    private final User user;
    private final UserPersonalBuilder userPersonalBuilder;

    private String chosenAttributeName;

    public ModifyPersonalContext(User user, UserPersonalBuilder userPersonalBuilder){
        this.user = user;
        this.userPersonalBuilder = userPersonalBuilder;
    }

    protected User getUser() {
        return user;
    }

    protected void setChosenAttributeName(String chosenAttributeName){
        this.chosenAttributeName = chosenAttributeName;
    }

    protected String getChosenAttributeName() {
        return chosenAttributeName;
    }

    protected UserPersonalBuilder getUserPersonalBuilder() {
        return userPersonalBuilder;
    }
}
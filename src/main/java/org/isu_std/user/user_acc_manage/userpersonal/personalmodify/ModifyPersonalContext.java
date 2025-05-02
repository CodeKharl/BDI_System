package org.isu_std.user.user_acc_manage.userpersonal.personalmodify;

import org.isu_std.models.modelbuilders.UserPersonalBuilder;

import java.lang.reflect.Field;

public class ModifyPersonalContext {
    private final int userId;
    private final UserPersonalBuilder userPersonalBuilder;

    private String chosenDetail;

    public ModifyPersonalContext(int userId, UserPersonalBuilder userPersonalBuilder){
        this.userId = userId;
        this.userPersonalBuilder = userPersonalBuilder;
    }

    protected int getUserId() {
        return userId;
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

package org.isu_std.user.user_acc_manage.userpersonal.personalmodify;

import org.isu_std.models.modelbuilders.UserPersonalBuilder;

public class ModifyPersonalContext {
    private final int userId;
    private final UserPersonalBuilder userPersonalBuilder;

    private String chosenDetail;

    public ModifyPersonalContext(int userId, UserPersonalBuilder userPersonalBuilder){
        this.userId = userId;
        this.userPersonalBuilder = userPersonalBuilder;
    }

    protected void setChosenDetail(String chosenDetail){
        this.chosenDetail = chosenDetail;
    }

    public int getUserId() {
        return userId;
    }

    public UserPersonalBuilder getUserPersonalBuilder() {
        return userPersonalBuilder;
    }

    public String getChosenDetail() {
        return chosenDetail;
    }
}

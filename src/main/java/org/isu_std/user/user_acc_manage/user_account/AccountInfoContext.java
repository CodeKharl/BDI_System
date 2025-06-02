package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.client_context.UserContext;
import org.isu_std.models.model_builders.UserBuilder;

public class AccountInfoContext {
    private final UserBuilder userBuilder;
    private final UserContext userContext;

    private String chosenAttributeName;
    private String chosenAttributeSpec;

    protected AccountInfoContext(UserContext userContext, UserBuilder userBuilder){
        this.userContext = userContext;
        this.userBuilder = userBuilder;
    }

    protected UserBuilder getUserBuilder(){
        return this.userBuilder;
    }

    public void setChosenAttributeSpec(String chosenDetailSpec) {
        this.chosenAttributeSpec = chosenDetailSpec;
    }

    protected void setChosenAttributeName(String chosenDetail){
        this.chosenAttributeName = chosenDetail;
    }

    protected String getChosenAttributeName(){
        return this.chosenAttributeName;
    }

    protected UserContext getUserContext(){
        return this.userContext;
    }

    protected String getUserAttrNameWithSpec(){
        return "%s (%s)".formatted(
                this.chosenAttributeName,
                this.chosenAttributeSpec
        );
    }
}

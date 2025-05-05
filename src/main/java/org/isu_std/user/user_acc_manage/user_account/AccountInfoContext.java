package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.client_context.UserContext;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.UserBuilder;

public class AccountInfoContext {
    private final UserBuilder userBuilder;
    private final UserContext userContext;

    private String chosenDetail;
    private String chosenDetailSpec;

    protected AccountInfoContext(UserContext userContext, UserBuilder userBuilder){
        this.userContext = userContext;
        this.userBuilder = userBuilder;
    }

    protected UserBuilder getUserBuilder(){
        return this.userBuilder;
    }

    public void setChosenDetailSpec(String chosenDetailSpec) {
        this.chosenDetailSpec = chosenDetailSpec;
    }

    protected void setChosenDetail(String chosenDetail){
        this.chosenDetail = chosenDetail;
    }

    protected String getChosenDetail(){
        return this.chosenDetail;
    }

    protected UserContext getUserContext(){
        return this.userContext;
    }

    protected String getUserDetailWithSpec(){
        return "%s (%s)".formatted(
                this.chosenDetail,
                this.chosenDetailSpec
        );
    }
}

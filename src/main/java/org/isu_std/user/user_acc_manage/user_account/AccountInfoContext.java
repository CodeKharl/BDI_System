package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.models.User;
import org.isu_std.models.modelbuilders.UserBuilder;

public class AccountInfoContext {
    private final UserBuilder userBuilder;

    private User actualUser;
    private String chosenDetail;
    private String chosenDetailSpec;

    protected AccountInfoContext(User user, UserBuilder userBuilder){
        this.actualUser = user;
        this.userBuilder = userBuilder;
    }

    protected UserBuilder getUserBuilder(){
        return this.userBuilder;
    }

    public String getChosenDetailSpec() {
        return chosenDetailSpec;
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

    protected void setActualUser(User actualUser){
        this.actualUser = actualUser;
    }

    protected User getActualUser(){
        return this.actualUser;
    }

    protected String getUserDetailWithSpec(){
        return "%s (%s)".formatted(
                this.chosenDetail,
                this.chosenDetailSpec
        );
    }
}

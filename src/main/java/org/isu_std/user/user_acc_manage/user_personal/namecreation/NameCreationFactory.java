package org.isu_std.user.user_acc_manage.user_personal.namecreation;

public class NameCreationFactory {
    public static NameCreation createNameCreation(){
        var nameCreationController = new NameCreationController(
                new NameCreationService()
        );

        return new NameCreation(nameCreationController);
    }
}

package org.isu_std.user.user_acc_manage.userpersonal.namecreation;

public class NameCreationFactory {
    public static NameCreation createNameCreation(){
        var nameCreationController = new NameCreationController(
                new NameCreationService()
        );

        return new NameCreation(nameCreationController);
    }
}

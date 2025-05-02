package org.isu_std.user.user_acc_manage.userpersonal.personalcreation;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.modelbuilders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalService;
import org.isu_std.user.user_acc_manage.userpersonal.PersonalInfoSetter;

public class CreatePersonalController {
    private final CreatePersonalService createPersonalService;
    private final ManagePersonalService managePersonalService;
    private final User user;

    private final UserPersonalBuilder userPersonalBuilder;
    private final PersonalInfoSetter personalInfoSetter;

    public CreatePersonalController(
            CreatePersonalService createPersonalService, ManagePersonalService managePersonalService, User user
    ){
        this.createPersonalService = createPersonalService;
        this.managePersonalService = managePersonalService;
        this.user = user;

        this.userPersonalBuilder = managePersonalService.createUserPersonalBuilder();
        this.personalInfoSetter = managePersonalService.createPersonalInfoSetter(this.userPersonalBuilder);
    }

    protected String[] getPersonalDetails(){
        return managePersonalService.getPersonalDetails();
    }

    protected String[] getPersonalDetailSpecs(){
        return managePersonalService.getPersonalDetailSpecs();
    }

    protected boolean isNameSet(){
        return personalInfoSetter.setName();
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 1 -> personalInfoSetter.setSex(input);
                case 2 -> personalInfoSetter.setAge(input);
                case 3 -> personalInfoSetter.setBirthDate(input);
                case 4 -> personalInfoSetter.setCivilStatus(input);
                case 5 -> personalInfoSetter.setNationality(input);
                case 6 -> personalInfoSetter.setPhoneNumber(input);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean createPerformed(){
        try{
            UserPersonal userPersonal = userPersonalBuilder.build();
            createPersonalService.savePersonalInfo(user.userId(), userPersonal);

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printUserPersonalInfo(){
        userPersonalBuilder.printPersonalStats();
    }
}

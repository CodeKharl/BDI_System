package org.isu_std.user.user_acc_manage.user_personal.personalcreation;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.model_builders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.user_personal.PersonalInfoSetter;

public class CreatePersonalController {
    private final CreatePersonalService createPersonalService;
    private final User user;

    private final UserPersonalBuilder userPersonalBuilder;
    private final PersonalInfoSetter personalInfoSetter;

    public CreatePersonalController(
            CreatePersonalService createPersonalService, User user
    ){
        this.createPersonalService = createPersonalService;
        this.user = user;

        this.userPersonalBuilder = createPersonalService.getUserPersonalBuilder();
        this.personalInfoSetter = createPersonalService.getPersonalInfoSetter(this.userPersonalBuilder);
    }

    protected String[] getPersonalDetails(){
        return this.createPersonalService.getPersonalDetails();
    }

    protected String[] getPersonalDetailSpecs(){
        return this.createPersonalService.getPersonalDetailSpecs();
    }

    protected boolean setName(){
        return personalInfoSetter.setName();
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 1 -> personalInfoSetter.setSex(input);
                case 2 -> personalInfoSetter.setAge(input);
                case 3 -> personalInfoSetter.setBirthDate(input);
                case 4 -> personalInfoSetter.setBirthPlace(input);
                case 5 -> personalInfoSetter.setCivilStatus(input);
                case 6 -> personalInfoSetter.setNationality(input);
                case 7 -> personalInfoSetter.setPhoneNumber(input);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean savePersonalInfo(){
        try{
            UserPersonal userPersonal = userPersonalBuilder.build();
            createPersonalService.savePersonalInfoPerform(user.userId(), userPersonal);

            return true;
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printUserPersonalInfo(){
        userPersonalBuilder.printPersonalStats();
    }
}

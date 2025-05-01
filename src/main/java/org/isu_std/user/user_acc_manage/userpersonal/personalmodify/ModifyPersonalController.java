package org.isu_std.user.user_acc_manage.userpersonal.personalmodify;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalService;
import org.isu_std.user.user_acc_manage.userpersonal.PersonalInfoSetter;

public class ModifyPersonalController {
    private final ManagePersonalService managePersonalService;
    private final ModifyPersonalManager modifyPersonalManager;
    private final PersonalInfoSetter personalInfoSetter;

    public ModifyPersonalController(ManagePersonalService managePersonalService, int userId){
        this.managePersonalService = managePersonalService;
        this.modifyPersonalManager = managePersonalService.createPersonalModifierManager(userId);
        this.personalInfoSetter = managePersonalService.createPersonalChecker(
                this.modifyPersonalManager.getUserPersonalBuilder()
        );
    }

    protected String[] personalDetails(){
        return managePersonalService.getPersonalDetails();
    }

    protected String personalDetailSpecs(int choice) {
        return managePersonalService.getPersonalDetailSpecs()[choice - 1];
    }

    protected void setDetailToModify(int choice){
        String personalDetail = personalDetails()[choice - 1];
        modifyPersonalManager.setChosenDetail(personalDetail);
    }

    protected String getChosenDetail(){
        return modifyPersonalManager.getChosenDetail();
    }

    protected boolean isNameSet(){
        return personalInfoSetter.setName();
    }

    protected boolean isInputValue(int choice, String value){
        try{
            switch(choice){
                case 2 -> personalInfoSetter.setSex(value);
                case 3 -> personalInfoSetter.setAge(value);
                case 4 -> personalInfoSetter.setBirthDate(value);
                case 5 -> personalInfoSetter.setCivilStatus(value);
                case 6 -> personalInfoSetter.setNationality(value);
                case 7 -> personalInfoSetter.setPhoneNumber(value);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean modifyPerformed(){
        try {
            managePersonalService
                    .saveModifiedPersonalInfo(this.modifyPersonalManager);

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}

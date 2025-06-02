package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.user.user_acc_manage.user_personal.PersonalInfoSetter;

public class ModifyPersonalController {
    private final ModifyPersonalService modifyPersonalService;

    private final ModifyPersonalContext modifyPersonalContext;
    private final PersonalInfoSetter personalInfoSetter;

    public ModifyPersonalController(ModifyPersonalService modifyPersonalService, User user){
        this.modifyPersonalService = modifyPersonalService;

        this.modifyPersonalContext = modifyPersonalService.createPersonalModifierContext(
                user, modifyPersonalService.getUserPersonalBuilder()
        );

        this.personalInfoSetter = modifyPersonalService.getPersonalInfoSetter(
                this.modifyPersonalContext.getUserPersonalBuilder()
        );
    }

    protected String[] getPersonalAttributeNames(){
        return modifyPersonalService.getPersonalAttributeNames();
    }

    protected String getPersonalAttributeSpec(int choice) {
        return modifyPersonalService.getPersonalAttributeSpecs()[choice - 1];
    }

    protected void setDetailToModify(String[] personalAttributeNames, int choice){
        String personalAttributeName = personalAttributeNames[choice - 1];

        modifyPersonalContext.setChosenAttributeName(personalAttributeName);
    }

    protected String getChosenAttributeName(){
        return modifyPersonalContext.getChosenAttributeName();
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
                case 5 -> personalInfoSetter.setBirthPlace(value);
                case 6 -> personalInfoSetter.setCivilStatus(value);
                case 7 -> personalInfoSetter.setNationality(value);
                case 8 -> personalInfoSetter.setPhoneNumber(value);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean modifyPerform(){
        try {
            modifyPersonalService.saveModifiedPersonalInfo(this.modifyPersonalContext);
            modifyPersonalContext.getUserPersonalBuilder().resetValues();

            return true;
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}

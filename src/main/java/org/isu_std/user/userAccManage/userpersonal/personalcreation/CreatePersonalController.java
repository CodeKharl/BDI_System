package org.isu_std.user.userAccManage.userpersonal.personalcreation;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.UserPersonal;

public class CreatePersonalController {
    private final CreatePersonalService createPersonalService;
    private final int userId;
    private final UserPersonal userPersonal;

    public CreatePersonalController(CreatePersonalService createPersonalService, int userId){
        this.createPersonalService = createPersonalService;
        this.userId = userId;
        this.userPersonal = createPersonalService.createUserPersonal();
    }

    protected void setName(){
        userPersonal.setName(
                createPersonalService
                        .createNameCreation()
                        .getCreatedName()
        );
    }

    protected boolean isNameSet(){
        setName(); // Different approach than other info.
        return userPersonal.getName() != null;
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 1 -> setSex(input.toUpperCase().charAt(0));
                case 2 -> setAge(input);
                case 3 -> setBirthDate(input);
                case 4 -> setCivilStatus(input);
                case 5 -> setNationality(input);
                case 6 -> setPhoneNumber(input);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    private void setSex(char sex){
        userPersonal.setSex(
                createPersonalService.getCheckedSex(sex)
        );
    }

    private void setAge(String strAge){
        userPersonal.setAge(
                createPersonalService.getCheckedAge(strAge)
        );
    }

    private void setBirthDate(String strBirthDate){
        userPersonal.setBirthDate(
                createPersonalService.getCheckedBirthDate(strBirthDate)
        );
    }

    private void setCivilStatus(String civilStatus){
        userPersonal.setCivilStatus(
                createPersonalService.getCheckedStrInput("civil status", civilStatus)
        );
    }

    private void setNationality(String nationality){
        userPersonal.setNationality(
                createPersonalService.getCheckedStrInput("nationality", nationality)
        );
    }

    private void setPhoneNumber(String strPhoneNum){
        userPersonal.setPhoneNumber(
                createPersonalService.getCheckedPhoneNum(strPhoneNum)
        );
    }

    protected boolean createPerformed(){
        try{
            createPersonalService.savePersonalInfo(userId, userPersonal);
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printUserPersonalInfo(){
        userPersonal.printPersonalStats();
    }
}

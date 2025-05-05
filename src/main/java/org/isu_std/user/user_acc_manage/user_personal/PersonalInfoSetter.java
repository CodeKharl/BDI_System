package org.isu_std.user.user_acc_manage.user_personal;

import org.isu_std.models.modelbuilders.UserPersonalBuilder;

import java.util.Optional;

public class PersonalInfoSetter {
    private final ManagePersonalService managePersonalService;
    private final UserPersonalBuilder userPersonalBuilder;

    public PersonalInfoSetter(ManagePersonalService managePersonalService, UserPersonalBuilder userPersonalBuilder){
        this.managePersonalService = managePersonalService;
        this.userPersonalBuilder = userPersonalBuilder;
    }

    public boolean setName(){
        Optional<String> fullName = managePersonalService
                .getNameCreation().getOptionalCreatedName();

        if(fullName.isPresent()){
            this.userPersonalBuilder.name(fullName.get());
            return true;
        }

        return false;
    }

    public void setSex(String input){
        char sex = input.charAt(0);
        managePersonalService.checkSex(sex);
        userPersonalBuilder.sex(sex);
    }

    public void setAge(String input){
        int age = managePersonalService.getCheckedAge(input);
        userPersonalBuilder.age(age);
    }

    public void setBirthDate(String input){
        String birthDate = managePersonalService.getCheckedBirthDate(input);
        userPersonalBuilder.birthDate(birthDate);
    }

    public void setCivilStatus(String input){
        String civilStatus = managePersonalService.getCheckedStrInput("civil status", input);
        userPersonalBuilder.civilStatus(civilStatus);
    }

    public void setNationality(String input){
        String nationality = managePersonalService.getCheckedStrInput("nationality", input);
        userPersonalBuilder.nationality(nationality);
    }

    public void setPhoneNumber(String input){
        managePersonalService.checkPhoneNum(input);
        userPersonalBuilder.phoneNumber(input);
    }
}

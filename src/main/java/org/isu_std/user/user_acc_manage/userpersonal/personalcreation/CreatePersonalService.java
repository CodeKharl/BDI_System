package org.isu_std.user.user_acc_manage.userpersonal.personalcreation;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_acc_manage.userpersonal.PersonalInfoConfig;
import org.isu_std.user.user_acc_manage.userpersonal.namecreation.NameCreation;
import org.isu_std.user.user_acc_manage.userpersonal.namecreation.NameCreationController;
import org.isu_std.user.user_acc_manage.userpersonal.namecreation.NameCreationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreatePersonalService {
    private final UserPersonalDao userPersonalDao;

    public CreatePersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    protected UserPersonal createUserPersonal(){
        return new UserPersonal();
    }

    protected NameCreation createNameCreation(){
        return new NameCreation(
                new NameCreationController(
                        new NameCreationService()
                )
        );
    }

    protected char getCheckedSex(char sex){
        if(sex == PersonalInfoConfig.MALE.getCharValue()
                || sex == PersonalInfoConfig.FEMALE.getCharValue()
        ){
            return sex;
        }

        throw new IllegalArgumentException(
                "The input is not a valid sex! Please enter a choice from the question guide"
        );
    }

    protected int getCheckedAge(String strAge){
        if(!Validation.isInputMatchesNumbers(strAge)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("age")
            );
        }

        int age = Integer.parseInt(strAge);

        if(age < PersonalInfoConfig.MINIMUM_AGE.getIntValue()){
            throw new IllegalArgumentException(
                    "The age you input is not too old enough! Please follow the minimum age."
            );
        }

        return age;
    }

    protected String getCheckedBirthDate(String strBirthDate){
        try{
            LocalDate date = LocalDate.parse(
                    strBirthDate,
                    DateTimeFormatter.ofPattern(
                            PersonalInfoConfig.BIRTHDATE_FORMAT.getStrValue()
                    )
            );

            return date.toString();
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid BirthDate! Please follow the guide format.");
        }
    }

    protected String getCheckedStrInput(String type, String input){ // Used for civil status and nationality.
        if(!Validation.isInputMatchesLetters(input)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_LETTERS_ONLY.getFormattedMessage(type)
            );
        }

        return input.toUpperCase().charAt(0)
                + input.substring(1); // E.g. -> 'A' + 'ssss'
    }

    protected String getCheckedPhoneNum(String phoneNumber){
        if(phoneNumber.matches(PersonalInfoConfig.PHONE_NUMBER.getStrValue())){
           return phoneNumber;
        }

        throw new IllegalArgumentException(
                InputMessageCollection.INPUT_INVALID.getFormattedMessage("phone number")
        );
    }

    protected void savePersonalInfo(int userId, UserPersonal userPersonal){
        if(!userPersonalDao.addUserPersonal(userId, userPersonal)){
            throw new OperationFailedException(
                    "Failed to save your personal information! Please try again!"
            );
        }
    }
}

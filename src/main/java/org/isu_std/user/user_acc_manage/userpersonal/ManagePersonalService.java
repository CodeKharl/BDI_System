package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.Validation;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.modelbuilders.BuilderFactory;
import org.isu_std.models.modelbuilders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.userpersonal.namecreation.NameCreation;
import org.isu_std.user.user_acc_manage.userpersonal.namecreation.NameCreationFactory;
import org.isu_std.user.user_acc_manage.userpersonal.personalcreation.CreatePersonal;
import org.isu_std.user.user_acc_manage.userpersonal.personalcreation.CreatePersonalController;
import org.isu_std.user.user_acc_manage.userpersonal.personalmodify.ModifyPersonal;
import org.isu_std.user.user_acc_manage.userpersonal.personalmodify.ModifyPersonalController;
import org.isu_std.user.user_acc_manage.userpersonal.personalmodify.ModifyPersonalContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ManagePersonalService {
    private final UserPersonalDao userPersonalDao;

    public ManagePersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    protected UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> userPersonal = userPersonalDao.getOptionalUserPersonal(userId);
        return userPersonal.orElseThrow(
                () -> new NotFoundException("Theres no existing personal information of your account.")
        );
    }

    protected CreatePersonal createPersonal(int userId){
        var createPersonalController = new CreatePersonalController(this, userId);
        return new CreatePersonal(createPersonalController);
    }

    protected ModifyPersonal createModifyPersonal(int userId){
        var modifyPersonalController = new ModifyPersonalController(this, userId);
        return new ModifyPersonal(modifyPersonalController);
    }

    public UserPersonalBuilder createUserPersonalBuilder(){
        return BuilderFactory.createUserPersonalBuilder();
    }

    public NameCreation getNameCreation(){
        return NameCreationFactory.createNameCreation();
    }

    public ModifyPersonalContext createPersonalModifierManager(int userId){
        return new ModifyPersonalContext(
                userId, BuilderFactory.createUserPersonalBuilder()
        );
    }

    public String[] getPersonalDetails(){
        return (String[]) PersonalInfoConfig.PERSONAL_INFORMATIONS.getValue();
    }

    public String[] getPersonalDetailSpecs(){
        return (String[]) PersonalInfoConfig.PERSONAL_SPECIFICATION.getValue();
    }

    public PersonalInfoSetter createPersonalChecker(UserPersonalBuilder userPersonalBuilder){
        return new PersonalInfoSetter(this, userPersonalBuilder);
    }

    public void checkSex(char sex){
        char maleValue = (char) PersonalInfoConfig.MALE.getValue();
        char femaleValue = (char) PersonalInfoConfig.FEMALE.getValue();

        if(sex == maleValue || sex == femaleValue){
            return;
        }

        throw new IllegalArgumentException(
                "The input is not a valid sex! Please enter a choice from the question guide"
        );
    }

    public int getCheckedAge(String strAge){
        if(!Validation.isInputMatchesNumbers(strAge)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("age")
            );
        }

        int minimumAge = (int) PersonalInfoConfig.MINIMUM_AGE.getValue();
        int age = Integer.parseInt(strAge);

        if(age < minimumAge){
            throw new IllegalArgumentException(
                    "The age you input is not too old enough! Please follow the minimum age."
            );
        }

        return age;
    }

    public String getCheckedBirthDate(String strBirthDate){
        try{
            String birthDateFormat = (String) PersonalInfoConfig.BIRTHDATE_FORMAT.getValue();
            LocalDate date = LocalDate.parse(
                    strBirthDate,
                    DateTimeFormatter.ofPattern(birthDateFormat)
            );

            return date.toString();
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid BirthDate! Please follow the guide format.");
        }
    }

    public String getCheckedStrInput(String type, String input){ // Used for civil status and nationality.
        if(!Validation.isInputMatchesLetters(input)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_LETTERS_ONLY.getFormattedMessage(type)
            );
        }

        return input.toUpperCase().charAt(0)
                + input.substring(1); // E.g. -> 'A' + 'ssss'
    }

    public void checkPhoneNum(String phoneNumber){
        String phoneNumFormat = (String) PersonalInfoConfig.PHONE_NUMBER.getValue();
        if(phoneNumber.matches(phoneNumFormat)){
            return;
        }

        throw new IllegalArgumentException(
                InputMessageCollection.INPUT_INVALID.getFormattedMessage("phone number")
        );
    }

    public void savePersonalInfo(int userId, UserPersonal userPersonal){
        if(!userPersonalDao.addUserPersonal(userId, userPersonal)){
            throw new OperationFailedException(
                    "Failed to save your personal information! Please try again!"
            );
        }
    }

    public void saveModifiedPersonalInfo(ModifyPersonalContext modifyPersonalContext){
        int userId  = modifyPersonalContext.getUserId();
        String chosenDetail = modifyPersonalContext.getChosenDetail();
        UserPersonal userPersonal = modifyPersonalContext.getUserPersonalBuilder().build();

        if(!userPersonalDao.modifyUserPersonal(userId, chosenDetail, userPersonal)){
            throw new OperationFailedException(
                    "Failed to modify personal information. Please try again."
            );
        }
    }
}

package org.isu_std.user.user_acc_manage.user_personal;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.Validation;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.dynamic_enum_handler.*;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.models.model_builders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.user_personal.namecreation.NameCreation;
import org.isu_std.user.user_acc_manage.user_personal.namecreation.NameCreationFactory;
import org.isu_std.user.user_acc_manage.user_personal.personalcreation.CreatePersonal;
import org.isu_std.user.user_acc_manage.user_personal.personalcreation.CreatePersonalController;
import org.isu_std.user.user_acc_manage.user_personal.personalcreation.CreatePersonalService;
import org.isu_std.user.user_acc_manage.user_personal.personalmodify.ModifyPersonal;
import org.isu_std.user.user_acc_manage.user_personal.personalmodify.ModifyPersonalController;
import org.isu_std.user.user_acc_manage.user_personal.personalmodify.ModifyPersonalService;

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

    protected CreatePersonal createPersonal(User user){
        var createPersonalService = new CreatePersonalService(userPersonalDao);
        var createPersonalController = new CreatePersonalController(
                createPersonalService, this, user
        );

        return new CreatePersonal(createPersonalController);
    }

    protected ModifyPersonal createModifyPersonal(User user){
        var modifyPersonalService = new ModifyPersonalService(userPersonalDao);
        var modifyPersonalController = new ModifyPersonalController(
                modifyPersonalService, this, user
        );

        return new ModifyPersonal(modifyPersonalController);
    }

    public UserPersonalBuilder createUserPersonalBuilder(){
        return BuilderFactory.createUserPersonalBuilder();
    }

    public NameCreation getNameCreation(){
        return NameCreationFactory.createNameCreation();
    }

    public String[] getPersonalDetails(){
        ConfigValue arrValue = PersonalInfoConfig.PERSONAL_INFORMATIONS.getValue();
        return EnumValueProvider.getStringArrValue(arrValue);
    }

    public String[] getPersonalDetailSpecs(){
        ConfigValue arrValue = PersonalInfoConfig.PERSONAL_SPECIFICATION.getValue();
        return EnumValueProvider.getStringArrValue(arrValue);
    }

    public PersonalInfoSetter createPersonalInfoSetter(UserPersonalBuilder userPersonalBuilder){
        return new PersonalInfoSetter(this, userPersonalBuilder);
    }

    public void checkSex(char sex){
        char maleValue = EnumValueProvider.getCharValue(PersonalInfoConfig.MALE.getValue());
        char femaleValue = EnumValueProvider.getCharValue(PersonalInfoConfig.FEMALE.getValue());

        if (sex == maleValue || sex == femaleValue) {
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

        int minimumAge = EnumValueProvider.getIntValue(PersonalInfoConfig.MINIMUM_AGE.getValue());
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
            String birthDateFormat = EnumValueProvider.getStringValue(PersonalInfoConfig.BIRTHDATE_FORMAT.getValue());
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
        String phoneNumFormat = EnumValueProvider.getStringValue(PersonalInfoConfig.PHONE_NUMBER.getValue());
        if(phoneNumber.matches(phoneNumFormat)){
            return;
        }

        throw new IllegalArgumentException(
                InputMessageCollection.INPUT_INVALID.getFormattedMessage("phone number")
        );
    }
}

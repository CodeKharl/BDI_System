package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.io.dynamic_enum_handler.*;

public enum PersonalInfoConfig {
    PERSONAL_INFORMATIONS(
            new StringArrValue(
                new String[]{
                        "Name", "Sex","Age",
                        "Birth_Date", "Civil_Status",
                        "Nationality", "Contact_Number"
                }
            )
    ),

    PERSONAL_SPECIFICATION(
            new StringArrValue(
                new String[]{
                        "", " (M == Male, F == Female)", " (min. 10)",
                        " (format. YYYY-MM-DD, 2000-03-12)", "",
                        "", " (start. 09, min.11)"
                }
            )
    ),

    MINIMUM_AGE(new IntValue(10)),
    MALE(new CharValue('M')), FEMALE(new CharValue('F')),
    BIRTHDATE_FORMAT(new StringValue("yyyy-MM-dd")),
    PHONE_NUMBER(new StringValue("^09\\d{9}$"));

    private final ConfigValue configValue;

    PersonalInfoConfig(ConfigValue configValue){
        this.configValue = configValue;
    }

    public ConfigValue getValue(){
        return this.configValue;
    }
}

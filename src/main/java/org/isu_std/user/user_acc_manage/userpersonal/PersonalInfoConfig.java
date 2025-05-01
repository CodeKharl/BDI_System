package org.isu_std.user.user_acc_manage.userpersonal;

public enum PersonalInfoConfig {
    PERSONAL_INFORMATIONS(
            new String[]{
                    "Name", "Sex","Age",
                    "Birth_Date", "Civil_Status",
                    "Nationality", "Contact_Number"
            }
    ),

    PERSONAL_SPECIFICATION(
            new String[]{
                    "", " (M == Male, F == Female)", " (min. 10)",
                    " (format. YYYY-MM-DD, 2000-03-12)", "",
                    "", " (start. 09, min.11)"
            }
    ),

    MINIMUM_AGE(10),
    MALE('M'), FEMALE('F'),
    BIRTHDATE_FORMAT("yyyy-MM-dd"),
    PHONE_NUMBER("^09\\d{9}$");

    private final Object value;

    PersonalInfoConfig(Object value){
        this.value = value;
    }
    public Object getValue(){
        return this.value;
    }
}

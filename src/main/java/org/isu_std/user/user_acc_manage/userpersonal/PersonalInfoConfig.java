package org.isu_std.user.user_acc_manage.userpersonal;

public enum PersonalInfoConfig {
    PERSONAL_NEEDS(
            new String[]{
                    "Name", "Sex","Age",
                    "Birth Date", "Civil Status",
                    "Nationality", "Contact Number"
            }
    ),

    PERSONAL_SPECIFICATION(
            new String[]{
                    "", "(M == Male, F == Female)", "(Min. 10)",
                    "(format. YYYY-MM-DD, 2000-03-12)", "",
                    "", "(start. 09, Min.11)"
            }
    ),

    MINIMUM_AGE(10),

    MALE('M'), FEMALE('F'),

    BIRTHDATE_FORMAT("yyyy-MM-dd"),

    PHONE_NUMBER("^09\\d{9}$");

    private String[] values;
    private int iValue;
    private char cValue;
    private String strValue;

    PersonalInfoConfig(String[] values){
        this.values = values;
    }

    PersonalInfoConfig(int iValue){
        this.iValue = iValue;
    }

    PersonalInfoConfig(char cValue){
        this.cValue = cValue;
    }

    PersonalInfoConfig(String strValue){
        this.strValue = strValue;
    }

    public String[] getValues(){
        return this.values;
    }

    public int getIntValue(){
        return this.iValue;
    }

    public char getCharValue(){
        return this.cValue;
    }

    public String getStrValue(){
        return this.strValue;
    }
}

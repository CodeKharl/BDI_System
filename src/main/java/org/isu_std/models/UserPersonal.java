package org.isu_std.models;

import org.isu_std.io.Util;

public record UserPersonal(
        String name, char sex, int age, String birthDate, String birthPlace,
        String civilStatus, String nationality, String phoneNumber
){
    public void printPersonalStats() {
        String[] attributes = getAttributesStrArr();
        Object[] values = getValues();

        Util.printSubSectionTitle("Personal Information");

        for(int i = 0; i < attributes.length; i++){
            Util.printInformation(
                    "%s : %s".formatted(attributes[i], values[i])
            );
        }
    }

    public String[] getAttributesStrArr(){
        return new String[]{
                "Name [FN, MN, LN]",
                "Sex",
                "Age",
                "Birth Date",
                "Birth Place",
                "Civil Status",
                "Nationality",
                "Phone Number"
        };
    }

    public Object[] getValues(){
        return new Object[]{
                name,
                sex,
                age,
                birthDate,
                birthPlace,
                civilStatus,
                nationality,
                phoneNumber
        };
    }
}

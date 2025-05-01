package org.isu_std.models;

import org.isu_std.io.Util;

public record UserPersonal(String name, char sex, int age, String birthDate, String civilStatus, String nationality,
                           String phoneNumber) {

    public void printPersonalStats() {
        String[] infos = {
                "Name [FN, MN, LN]: " + name,
                "Sex : " + sex,
                "Age : " + age,
                "Birth date : " + birthDate,
                "Civil Status : " + civilStatus,
                "Nationality : " + nationality,
                "Contact Number : " + phoneNumber
        };


        Util.printSubSectionTitle("Personal Information");
        for (String info : infos) {
            Util.printInformation(info);
        }
    }

    public String[] valueToStringArr() {
        return new String[]{
                name,
                String.valueOf(sex),
                String.valueOf(age),
                birthDate,
                civilStatus,
                nationality,
                phoneNumber
        };
    }
}

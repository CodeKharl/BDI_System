package org.isu_std.models.model_builders;

import org.isu_std.io.Util;
import org.isu_std.models.UserPersonal;

public class UserPersonalBuilder {
    private String name;
    private char sex;
    private int age;
    private String birthDate;
    private String birthPlace;
    private String civilStatus;
    private String nationality;
    private String phoneNumber;

    public UserPersonalBuilder name(String name){
        this.name = name;
        return this;
    }

    public UserPersonalBuilder sex(char sex){
        this.sex = sex;
        return this;
    }

    public UserPersonalBuilder age(int age){
        this.age = age;
        return this;
    }

    public UserPersonalBuilder birthDate(String birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public UserPersonalBuilder birthPlace(String birthPlace){
        this.birthPlace = birthPlace;
        return this;
    }

    public UserPersonalBuilder civilStatus(String civilStatus){
        this.civilStatus = civilStatus;
        return this;
    }

    public UserPersonalBuilder nationality(String nationality){
        this.nationality = nationality;
        return this;
    }

    public UserPersonalBuilder phoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserPersonal build(){
        return new UserPersonal(
                this.name,
                this.sex,
                this.age,
                this.birthDate,
                this.birthPlace,
                this.civilStatus,
                this.nationality,
                this.phoneNumber
        );
    }

    public String getName() {
        return name;
    }

    public char getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void printPersonalStats() {
        String[] attributesWithValues = getAttributesWithValues();

        Util.printSubSectionTitle("Personal Information");
        for (String info : attributesWithValues) {
            Util.printInformation(info);
        }
    }

    public String[] getAttributesWithValues(){
        return new String[]{
                "Name [FN, MN, LN]: " + name,
                "Sex : " + sex,
                "Age : " + age,
                "Birth Date : " + birthDate,
                "Birth Place : " + birthPlace,
                "Civil Status : " + civilStatus,
                "Nationality : " + nationality,
                "Contact Number : " + phoneNumber
        };
    }

    public void resetValues(){
        this.name = null;
        this.sex = '\u0000';
        this.age = 0;
        this.birthDate = null;
        this.birthPlace = null;
        this.civilStatus = null;
        this.nationality = null;
        this.phoneNumber = null;
    }
}

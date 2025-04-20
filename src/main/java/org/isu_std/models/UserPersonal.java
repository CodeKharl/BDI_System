package org.isu_std.models;

import org.apache.poi.ss.formula.ptg.AreaNPtg;
import org.isu_std.io.Util;

public class UserPersonal{
    private String name;
    private char sex;
    private int age;
    private String birthDate;
    private String civilStatus;
    private String nationality;
    private String phoneNumber;

    public UserPersonal(String name, char sex, int age, String birthDate, String civilStatus, String nationality, String phoneNumber) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthDate = birthDate;
        this.civilStatus = civilStatus;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
    }

    public UserPersonal(){

    }

    public void printPersonalStats(){
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
        for(String info : infos){
            Util.printInformation(info);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] valueToStringArr(){
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

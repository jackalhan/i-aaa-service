package com.iaaa.dto;

import javax.persistence.*;

/**
 * Created by jackalhan on 3/21/16.
 */

public class Person {

    private String name_surname;

    private String birthDate;

    private String age;

    private String cityOfResidence;

    private char male_female;

    private String personType;


    public Person() {
    }

    public Person(String name_surname, String birthDate, String age, String cityOfResidence, char male_female, String personType) {
        this.name_surname = name_surname;
        this.birthDate = birthDate;
        this.age = age;
        this.cityOfResidence = cityOfResidence;
        this.male_female = male_female;
        this.personType = personType;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    public char getMale_female() {
        return male_female;
    }

    public void setMale_female(char male_female) {
        this.male_female = male_female;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name_surname='" + name_surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", age=" + age +
                ", cityOfResidence='" + cityOfResidence + '\'' +
                ", male_female=" + male_female +
                ", personType='" + personType + '\'' +
                '}';
    }
}

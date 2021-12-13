package com.foxminded.andreimarkov.warehouse.model;

import java.util.Objects;

public class Person extends Customer {
    private String firstName;
    private String surName;

    public Person() {
    }

    public Person(int id, String firstName, String surName, int balance, String address, String phone) {
        super(id, balance, address, phone);
        this.firstName = firstName;
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(surName, person.surName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surName);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                '}';
    }
}

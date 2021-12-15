package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.Objects;

@Data
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

}

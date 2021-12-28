package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Person{
    private Long id;
    private String firstName;
    private String surName;
    private int balance;
    private String address;
    private String phone;

}

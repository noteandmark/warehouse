package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

@Data
public class PersonDTO extends CustomerDTO {
    private Long id;
    private String firstName;
    private String surName;
    private int balance;
    private String address;
    private String phone;
}

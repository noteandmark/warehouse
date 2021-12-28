package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Company {
    private Long id;
    private String name;
    private int balance;
    private String address;
    private String phone;

}

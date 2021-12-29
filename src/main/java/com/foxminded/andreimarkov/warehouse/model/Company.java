package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Company extends Customer{
    private Long id;
    private String name;
    private int balance;
    private String address;
    private String phone;
}

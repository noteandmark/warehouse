package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.Objects;

@Data
public class Company extends Customer {
    private String name;

    public Company() {
    }

    public Company(Long id, int balance, String address, String phone, String name) {
        super(id, balance, address, phone);
        this.name = name;
    }

}

package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Customer {
    private int id;
    private int balance;
    private String address;
    private String phone;

    public Customer() {
    }

    public Customer(int id, int balance, String address, String phone) {
        this.id = id;
        this.balance = balance;
        this.address = address;
        this.phone = phone;
    }

}

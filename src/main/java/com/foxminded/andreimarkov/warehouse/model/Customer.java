package com.foxminded.andreimarkov.warehouse.model;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

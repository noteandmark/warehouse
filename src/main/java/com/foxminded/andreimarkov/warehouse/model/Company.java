package com.foxminded.andreimarkov.warehouse.model;

import java.util.Objects;

public class Company extends Customer {
    private String name;

    public Company() {
    }

    public Company(int id, int balance, String address, String phone, String name) {
        super(id, balance, address, phone);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                '}';
    }
}

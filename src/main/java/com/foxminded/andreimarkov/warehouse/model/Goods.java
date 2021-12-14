package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.Objects;

@Data
public class Goods {
    private String name;
    private int quantity;
    private int price;
    private Catalog category;
    private Location location;

    public Goods() {
    }

    public Goods(String name, int quantity, int price, Catalog category, Location location) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.location = location;
    }

}

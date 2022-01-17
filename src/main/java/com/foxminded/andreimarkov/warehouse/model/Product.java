package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Product extends AbstractEntity {
    private Long id;
    private String code;
    private String name;
    private String description;
    private int quantity;
    private int price;
    private Catalog category;
    private Location location;
}

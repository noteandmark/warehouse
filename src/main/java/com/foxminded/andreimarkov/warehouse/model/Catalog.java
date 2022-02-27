package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.List;

@Data
public class Catalog {
    private Long catalogId;
    private String name;
    private List<Catalog> group;
    private List<Product> goods;
}

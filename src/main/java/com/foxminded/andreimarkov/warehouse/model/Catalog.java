package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.List;

@Data
public class Catalog extends AbstractEntity {
    private Long id;
    private String name;
    private List<Catalog> group;
    private List<Product> goods;
}

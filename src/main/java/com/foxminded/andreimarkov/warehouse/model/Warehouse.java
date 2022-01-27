package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

import java.util.Map;

@Data
public class Warehouse {
    private Long id;
    private String name;
    private Catalog root;
    private Map<Customer, Order> orders;
}

package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

import java.util.Map;

@Data
public class Warehouse extends AbstractEntity  {
    private Long id;
    private Catalog root;
    private Map<Customer, Order> orders;
}

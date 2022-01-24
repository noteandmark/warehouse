package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long id;
    private String status;
    private String date;
    public List<OrderPosition> items;
}

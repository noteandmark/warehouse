package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String status;
    private String date;
    public List<OrderPosition> items;

}

package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Order extends AbstractEntity  {
    private Long id;
    private String status;
    private String date;
    public List<OrderPosition> items;
}

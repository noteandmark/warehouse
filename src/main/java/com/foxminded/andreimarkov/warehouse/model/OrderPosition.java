package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

@Data
public class OrderPosition {
    private Long id;
    private int amount;
    private int productId;
}

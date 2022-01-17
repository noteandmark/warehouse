package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

@Data
public class OrderPosition extends AbstractEntity {
    private Long id;
    private Product item;
    private int amount;
}

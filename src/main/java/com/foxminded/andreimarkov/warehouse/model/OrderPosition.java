package com.foxminded.andreimarkov.warehouse.model;

import lombok.Data;

@Data
public class OrderPosition {
    private Goods item;
    private int amount;

}

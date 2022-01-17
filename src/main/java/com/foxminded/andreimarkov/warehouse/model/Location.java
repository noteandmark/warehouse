package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

@Data
public class Location extends AbstractEntity {
    private Long id;
    private String warehouseName;
    private int shelfNumber;
}

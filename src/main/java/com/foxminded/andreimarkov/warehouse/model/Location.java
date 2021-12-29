package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.Objects;

@Data
public class Location {
    private Long id;
    private String warehouseName;
    private int shelfNumber;
}

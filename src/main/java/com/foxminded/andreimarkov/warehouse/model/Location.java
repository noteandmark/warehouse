package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.Objects;

@Data
public class Location {
    private String warehouseName;
    private int shelfNumber;

    public Location() {
    }

    public Location(String warehouseName, int shelfNumber) {
        this.warehouseName = warehouseName;
        this.shelfNumber = shelfNumber;
    }

}

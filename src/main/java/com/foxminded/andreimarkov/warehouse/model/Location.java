package com.foxminded.andreimarkov.warehouse.model;

import java.util.Objects;

public class Location {
    private String warehouseName;
    private int shelfNumber;

    public Location() {
    }

    public Location(String warehouseName, int shelfNumber) {
        this.warehouseName = warehouseName;
        this.shelfNumber = shelfNumber;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return shelfNumber == location.shelfNumber && Objects.equals(warehouseName, location.warehouseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseName, shelfNumber);
    }

    @Override
    public String toString() {
        return "Location{" +
                "warehouseName='" + warehouseName + '\'' +
                ", shelfNumber=" + shelfNumber +
                '}';
    }
}

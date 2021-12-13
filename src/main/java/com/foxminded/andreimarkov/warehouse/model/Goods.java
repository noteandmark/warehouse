package com.foxminded.andreimarkov.warehouse.model;

import java.util.Objects;

public class Goods {
    private String name;
    private int quantity;
    private int price;
    private Catalog category;
    private Location location;

    public Goods() {
    }

    public Goods(String name, int quantity, int price, Catalog category, Location location) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Catalog getCategory() {
        return category;
    }

    public void setCategory(Catalog category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return quantity == goods.quantity && price == goods.price && Objects.equals(name, goods.name) && Objects.equals(category, goods.category) && Objects.equals(location, goods.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, price, category, location);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category=" + category +
                ", location=" + location +
                '}';
    }
}

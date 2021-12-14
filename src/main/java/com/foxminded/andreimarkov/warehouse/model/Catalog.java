package com.foxminded.andreimarkov.warehouse.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class Catalog {
    private List<Catalog> group;
    private List<Goods> goods;

    public Catalog() {
        this.group = new ArrayList<>();
        this.goods = new ArrayList<>();
    }

}

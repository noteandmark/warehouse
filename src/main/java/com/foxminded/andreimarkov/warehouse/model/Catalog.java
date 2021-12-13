package com.foxminded.andreimarkov.warehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Catalog> group;
    private List<Goods> goods;

    public Catalog() {
        this.group = new ArrayList<>();
        this.goods = new ArrayList<>();
    }

    public List<Catalog> getGroup() {
        return group;
    }

    public void setGroup(List<Catalog> group) {
        this.group = group;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}

package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private int quantity;
    private int price;
    private CatalogDTO category;
    private LocationDTO location;
}

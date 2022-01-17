package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

import java.util.List;

@Data
public class CatalogDTO extends AbstractDTO {
    private Long id;
    private String name;
    private List<CatalogDTO> group;
    private List<ProductDTO> goods;
}

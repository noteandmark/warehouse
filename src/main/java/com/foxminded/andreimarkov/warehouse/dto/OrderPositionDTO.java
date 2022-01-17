package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

@Data
public class OrderPositionDTO extends AbstractDTO {
    private Long id;
    private ProductDTO item;
    private int amount;
}

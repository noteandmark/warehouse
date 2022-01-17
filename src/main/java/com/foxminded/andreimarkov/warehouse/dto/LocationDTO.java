package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

@Data
public class LocationDTO extends AbstractDTO {
    private Long id;
    private String warehouseName;
    private int shelfNumber;
}

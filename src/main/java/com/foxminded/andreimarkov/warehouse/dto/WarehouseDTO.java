package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WarehouseDTO {
    private Long id;
    private String name;
    private CatalogDTO root;
    private Map<CustomerDTO, OrderDTO> orders;
}

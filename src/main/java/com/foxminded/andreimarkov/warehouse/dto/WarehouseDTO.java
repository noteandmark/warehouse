package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WarehouseDTO extends AbstractDTO {
    private Long id;
    private CatalogDTO root;
    private Map<CustomerDTO, OrderDTO> orders;
}

package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO extends AbstractDTO {
    private Long id;
    private String status;
    private String date;
    public List<OrderPositionDTO> items;
}

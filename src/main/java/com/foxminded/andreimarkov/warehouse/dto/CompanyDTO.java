package com.foxminded.andreimarkov.warehouse.dto;

import lombok.Data;

@Data
public class CompanyDTO extends CustomerDTO {
    private Long id;
    private String name;
    private int balance;
    private String address;
    private String phone;
}

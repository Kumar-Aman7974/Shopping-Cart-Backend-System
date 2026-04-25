package com.dailycodework.demoshops.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class orderItemDto {

    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;


}

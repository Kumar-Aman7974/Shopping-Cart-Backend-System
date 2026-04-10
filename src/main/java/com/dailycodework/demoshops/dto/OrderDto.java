package com.dailycodework.demoshops.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {


    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;

    private Long userId;
    private String status;
    private List<orderItemDto> items;
}

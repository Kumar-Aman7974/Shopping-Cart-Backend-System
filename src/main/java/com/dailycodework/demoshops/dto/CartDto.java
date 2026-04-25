package com.dailycodework.demoshops.dto;

import com.dailycodework.demoshops.model.CartItem;
import com.dailycodework.demoshops.service.cart.CartItemService;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {

    private Long cartId;
    private Set<CartItemDto> items;

    private BigDecimal totalAmount;

}

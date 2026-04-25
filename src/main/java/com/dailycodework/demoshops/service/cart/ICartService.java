package com.dailycodework.demoshops.service.cart;

import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    // create a method


    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}

package com.dailycodework.demoshops.repository;

import com.dailycodework.demoshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {


}

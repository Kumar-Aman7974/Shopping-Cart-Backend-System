package com.dailycodework.demoshops.repository;

import com.dailycodework.demoshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {


    void deleteAllById(Long id);

    Cart findByUserId(Long userId);
}

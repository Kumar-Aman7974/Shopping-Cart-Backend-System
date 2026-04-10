package com.dailycodework.demoshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    // This car is one to many items so one cart is to many items;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // means once  when a cart is deleted that means
    // all the cart items is going to be deleted;
    // -> orphanRemoval = true means when if there's any cart items that is not referenced to by any cart
    //  is going to be removed;
    private Set<CartItem> items = new HashSet<>();// here we are used set collection framework because we don't want to store duplicate value inside
    // this cartItem;

    // after 5:30
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void addItem(CartItem item)
    {
        this.items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item)
    {
        this.items.remove(item);
        item.setCart(null);
        updateTotalAmount();

    }

    public void updateTotalAmount() {
        this.totalAmount = items.stream()
                .map(item -> {
                    BigDecimal unitPrice = item.getUnitPrice();
                    if(unitPrice == null)
                    {
                        return BigDecimal.ZERO;
                    }
                    return  unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}

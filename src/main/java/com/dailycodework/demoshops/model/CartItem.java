package com.dailycodework.demoshops.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;


    // many product actually has one cart means  which is many cartItems to one cart
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // another entity

    @JsonIgnore
    //So we said that one cart has many items which means  many items belongs to  one cart
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart; //another entity



    public void setTotalPrice() {
                                         // here multiply is a method of BigDecimal class and it  returns BigDecimal.
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
        // so here we are just going to say that this do total prices equals
    }
}

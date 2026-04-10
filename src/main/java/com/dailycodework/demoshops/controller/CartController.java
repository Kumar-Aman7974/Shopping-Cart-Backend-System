package com.dailycodework.demoshops.controller;

import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.array.ArrayViaElementArgumentReturnTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts") // we are using the api prefix
public class CartController {

    private final ICartService cartService;


    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId)
    {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

     // clear the cart
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse>  clearCart(@PathVariable Long cartId){

        try {
            cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("CLear cart Success!", null));
        }
        catch (ResourceNotFoundException e )
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //
    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse>  getTotalAmount(@PathVariable Long cartId) {
       try {
           BigDecimal totalPrice = cartService.getTotalPrice(cartId);
           return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
       }
       catch (ResourceNotFoundException e )
       {
           return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
       }
    }

}

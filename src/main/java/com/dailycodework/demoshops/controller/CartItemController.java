package com.dailycodework.demoshops.controller;

import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.cart.ICartItemService;
import com.dailycodework.demoshops.service.cart.ICartService;
import com.dailycodework.demoshops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;

    private final ICartService cartService;
    private final IUserService userService;



    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity)
    {
       try {
           User user = userService.getAuthenticatedUser();
           Cart cart = cartService.initializeNewCart(user);



            // Here two things Controller is doing.first one is, take the data from the user and then go to the serviceMethod  using the call
            // the service class method and then  check this and return
            // Second is , return if data is empty and another type error so we  are used ResponseEntity.status() and create a new object of the new ApiResponse

            //call this cartItemService
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));

        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
       catch (JwtException e)
       {
           return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));

       }

    }


    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public  ResponseEntity<ApiResponse> removeItemFromCart( @PathVariable Long cartId, @PathVariable Long itemId) {

        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
        public ResponseEntity<ApiResponse>  updateQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity){ // here why are used two variable using the @PathVariable and another variable is @RequestParam

           try {
               cartItemService.updateItemQuantity(cartId, itemId, quantity);
               return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
           }
           catch (ResourceNotFoundException e) {

               return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
           }
        }



}

package com.dailycodework.demoshops.service.cart;

import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.repository.CartItemRepository;
import com.dailycodework.demoshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor // what do you mean?
public class CartService  implements ICartService{

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final CartItemRepository cartItemRepository;

    // here we are using the Atomic value generator
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart   = cartRepository.findById(id).
                orElseThrow( () -> new ResourceNotFoundException("Cart not found")); // we are created our custom exception class

        BigDecimal totalAmount = cart.getTotalAmount();// first we get the totalAmount of data then we have set the totalAmount into the cart
        cart.setTotalAmount(totalAmount);
        // now we will save this
        return cartRepository.save(cart);

    }

    @Override
    public void clearCart(Long id) {
        // first get the cart by id;
        Cart cart = getCart(id); // here we call the method which we described above;
        cartRepository.deleteAllById(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);



    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);


//        return cart.getItems()
//                .stream()
//                .map( CartItem:: getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
        return cart.getTotalAmount();

    }
    
    // create a method
//    @Override
//    public Long initializeNewCart() {
//
//        Cart newCart = new Cart();
//
//        Long newCartId = cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
//
//        return cartRepository.save(newCart).getId();
//        // go to the Controller
//    }

    @Override
    public Cart initializeNewCart(User user) {

        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet( () -> {
                   Cart cart = new Cart();
                   cart.setUser(user);
                   return cartRepository.save(cart);
                        });

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

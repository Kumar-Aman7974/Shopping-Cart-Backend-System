package com.dailycodework.demoshops.service.cart;

import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.CartItem;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.repository.CartItemRepository;
import com.dailycodework.demoshops.repository.CartRepository;
import com.dailycodework.demoshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;

    private final IProductService productService;

    private final ICartService cartService;

    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId,Long productId, int quantity) {

        // 1. Get the cart
        // 2. Get the product
        // 3. Check if the product already in the cart
        //4. if Yes, then increase the quantity with the requested quantity;
        //5. if NO, the initiate a new CartItem entry,

        Cart cart = cartService.getCart(cartId);

        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getItems()
                .stream()
                .filter( item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() == null)
        {
            cartItem.setCart(cart);// put the cart id
            cartItem.setProduct(product); // put the productId
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());


        }
        else  {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);// after that we are saved this inside the cart class not to cartItem because we are adding the item into the cart;
        // here we are using the cartRepository because it interacts with the database directly;


    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

        // here first find out the cartId 
        Cart cart = cartService.getCart(cartId);
                             // here we are using the helper method
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);// removeItem is the method of the Cart class
        cartRepository.save(cart);


 
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        // ✅ update values
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(cartItem.getProduct().getPrice());

        cartItem.setTotalPrice(
                cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity))
        );

        // ✅ update cart total properly
        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);

        // 🔥 IMPORTANT
        cartRepository.save(cart);


        //So these just all we need to do right here to update the quantity
        // of our product right inside the cart



    }

     // i like to pull this one up in case we want to get access to it from the
    // controller we can actually get access to it.
    @Override
    public CartItem getCartItem(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);

        return   cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow( () -> new ResourceNotFoundException("Item not found"));

    }
}
// 4:20
// 4:53

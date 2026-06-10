package com.dailycodework.demoshops.service.cart;

import com.dailycodework.demoshops.exceptions.ProductNotFoundException;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.CartItem;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.repository.CartItemRepository;
import com.dailycodework.demoshops.repository.CartRepository;
import jakarta.persistence.ManyToMany;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private  CartRepository cartRepository;

    @Mock
    private  CartItemRepository cartItemRepository;

    @InjectMocks private CartService cartService;

    private Cart testCart;
    private User testUser;


    @BeforeEach
    void setup() {

        // Creating dummy user
        testUser = new User();
        ReflectionTestUtils.setField(testUser, "id", 1L);

        // creating a dummy Cart
        testCart = new Cart();
        ReflectionTestUtils.setField(testCart, "id", 100L);
        testCart.setUser(testUser);

    }

   // TEST FOR : getCart(Long id)
    @Nested
    @DisplayName("Tests fro getCart")
    class GetCartTests {

        @Test
       @DisplayName("Should return cart when ID exists")
       void getCart_ExistingId_ReturnsCart()  {

            when(cartRepository.findById(100L)).thenReturn(Optional.of(testCart));
            when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

            //
            Cart result = cartService.getCart(100L);

            //assert
            assertNotNull(result);
            assertEquals(100L, result.getId());

            verify(cartRepository).save(any(Cart.class));

        }

        @Test
       @DisplayName("Should throw ResourceNotFOundException when ID doesn't exist")
       void getCart_NotExistingId_ThrowsException() {
            //ARRANGE
            when(cartRepository.findById(999L)).thenReturn(Optional.empty());


            //ACT & ASSERT
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                cartService.getCart(999L);
            });

            assertEquals("Cart not found", exception.getMessage());
            verify(cartRepository, never()).save(any());

        }
   }

   @Nested
    @DisplayName("Tests for clearCart")
    class ClearCartTests {

        @Test
       @DisplayName("Should clear all items from cart and save")
       void clearCart_ValidId_ClearItems() {

            CartItem item = new CartItem();
            testCart.addItem(item);
            assertFalse(testCart.getItems().isEmpty());//

            // mocking getCart(id)
            when(cartRepository.findById(100L)).thenReturn(Optional.of(testCart));
            when(cartRepository.save(any(Cart.class))).thenReturn(testCart);


            cartService.clearCart(100L);

            assertTrue(testCart.getItems().isEmpty());

            // verify
            verify(cartRepository).save(testCart);
        }
   }

// TEST FOR : getTotalPrice
    @Nested
    @DisplayName("Test for getTotalPrice")
    class GetTotalPriceTests {

        @Test
    @DisplayName("Should return total amount of the cart")
    void getTotalPrice_ValidId_ReturnsPrice() {

            testCart.setTotalAmount(new BigDecimal("150.00"));

            when(cartRepository.findById(100L)).thenReturn(Optional.of(testCart));
            when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

            BigDecimal total = cartService.getTotalPrice(100L);

            //ASSERT
            assertEquals(0, new BigDecimal("150.00").compareTo(total));
            // Compare BigDecimals using compareTo to avoid scale issues (150.00 vs 150.0)

        }
}




}
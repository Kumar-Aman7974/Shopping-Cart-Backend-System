package com.dailycodework.demoshops.service.order;

import com.dailycodework.demoshops.dto.OrderDto;
import com.dailycodework.demoshops.enums.OrderStatus;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Cart;
import com.dailycodework.demoshops.model.Order;
import com.dailycodework.demoshops.model.OrderItem;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.repository.OrderRepository;
import com.dailycodework.demoshops.repository.ProductRepository;
import com.dailycodework.demoshops.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService  implements IOrderService{

    @Autowired
    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder = orderRepository.save(order);

        // when save this order is being saved. then we are going to clear the cart
        cartService.clearCart(cart.getId());

        return  saveOrder;

    }

    // helper methods
    private Order createOrder(Cart cart) {
        Order order = new Order();
        // set the user
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }


    //helper methods
     private List<OrderItem> createOrderItems(Order order, Cart cart) {

         return cart.getItems().stream()
                 .map(cartItem -> {
                     // first get the product item num
                     Product product = cartItem.getProduct();
                     product.setInventory(product.getInventory() - cartItem.getQuantity());
                     productRepository.save(product);

                     return new OrderItem(
                             order,
                             product,
                             cartItem.getQuantity(),
                             cartItem.getUnitPrice()
                     );
                     // map close
                 }).toList(); // collect everythings to list
     }

     //helper methods
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList)
    {
        return  orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add); // here we are accumulate all these data
    }


    @Override
    public OrderDto getOrder(Long orderId) {


        return  orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow( () -> new ResourceNotFoundException("Order not found"));
    }


    // Get a list of order to a particular user
    @Override
    public List<OrderDto> getUserOrders(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return  orders.stream().map(this :: convertToDto).toList();

    }


    @Override
    public OrderDto convertToDto(Order order)

    {

        // this is the method of the map from order to OrderDto class
        return modelMapper.map(order, OrderDto.class);
    }
}

/* Here we are going to bring in ModelMapper dependency

  private final ModelMapper modelMapper;
  and then we are going to right here to create a service that actually help us to do the
  conversion;
 */

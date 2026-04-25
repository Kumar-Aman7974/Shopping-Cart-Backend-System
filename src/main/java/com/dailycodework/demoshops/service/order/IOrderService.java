package com.dailycodework.demoshops.service.order;

import com.dailycodework.demoshops.dto.OrderDto;
import com.dailycodework.demoshops.model.Order;

import java.util.List;

public interface IOrderService {


    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}

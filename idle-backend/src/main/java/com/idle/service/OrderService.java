package com.idle.service;

import com.idle.entity.orders.Orders;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Orders> getUserOrder(String nickname);
    boolean createOrder(Long id, String create_time, String seller, String buyer, String payment, BigDecimal price,
                        String shipping_address);
    boolean deleteOrder(Long id);
    List<Orders> getOrderById(Long id, String nickname);
}

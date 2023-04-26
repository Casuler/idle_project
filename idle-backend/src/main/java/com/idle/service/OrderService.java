package com.idle.service;

import com.idle.entity.orders.Orders;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Orders> getUserOrder(String nickname);
    boolean createOrder(Integer id, String create_time, String seller, String buyer, String payment, BigDecimal price);
    boolean deleteOrder(Integer id);
    List<Orders> getOrderById(Integer id, String nickname);
}

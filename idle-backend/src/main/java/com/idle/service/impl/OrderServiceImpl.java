package com.idle.service.impl;

import com.idle.entity.orders.Orders;
import com.idle.mapper.OrdersMapper;
import com.idle.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrdersMapper ordersMapper;

    @Override
    public boolean createOrder(Long id, String product_name, String create_time, String seller, String buyer, String payment,
                                BigDecimal price, String shipping_address) {
        return ordersMapper.createOrder(id, product_name, create_time, seller, buyer, payment, price, shipping_address) > 0;
    }

    @Override
    public List<Orders> getUserOrder(String nickname) {
        return ordersMapper.getUserOrders(nickname);
    }

    @Override
    public List<Orders> getOrderById(Long id, String nickname) {
        return ordersMapper.getOrderById(id, nickname);
    }

    @Override
    public boolean checkOrder(Long id) {
        return ordersMapper.checkOrder(id) > 0;
    }

    @Override
    public boolean deleteOrder(Long id) {
        return ordersMapper.deleteOrder(id) > 0;
    }
}

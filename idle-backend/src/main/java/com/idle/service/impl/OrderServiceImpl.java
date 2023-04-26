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
    public boolean createOrder(Integer id, String create_time, String seller, String buyer, String payment,
                                BigDecimal price) {
        return ordersMapper.createOrder(id, create_time, seller, buyer, payment, price) > 0;
    }

    @Override
    public List<Orders> getUserOrder(String nickname) {
        return ordersMapper.getUserOrders(nickname);
    }

    @Override
    public List<Orders> getOrderById(Integer id, String nickname) {
        return ordersMapper.getOrderById(id, nickname);
    }

    @Override
    public boolean deleteOrder(Integer id) {
        return ordersMapper.deleteOrder(id) > 0;
    }
}

package com.idle.mapper;

import com.idle.entity.orders.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface OrdersMapper {
    @Insert("insert into db_orders (id, create_time, seller, buyer, payment, price) values " +
            "(#{id}, #{create_time}, #{seller}, #{buyer}, #{payment}, #{price})")
    int createOrder(Integer id, String create_time, String seller, String buyer, String payment, BigDecimal price);

    @Select("select * from db_orders where #{username}=db_orders.seller or #{username}=db_orders.buyer")
    List<Orders> getUserOrders(String username);
}

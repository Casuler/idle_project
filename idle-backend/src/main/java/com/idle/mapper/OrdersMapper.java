package com.idle.mapper;

import com.idle.entity.orders.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface OrdersMapper {
    @Insert("insert into db_orders (id, create_time, seller, buyer, payment, price, shipping_address) values " +
            "(#{id}, #{create_time}, #{seller}, #{buyer}, #{payment}, #{price}, #{shipping_address})")
    int createOrder(Long id, String create_time, String seller, String buyer, String payment, BigDecimal price,
                    String shipping_address);

    @Select("select * from db_orders where #{nickname}=db_orders.seller or #{nickname}=db_orders.buyer")
    List<Orders> getUserOrders(String nickname);

    @Delete("delete * from db_orders where id = #{id}")
    int deleteOrder(Long id);

    @Select("select * from db_orders where id = #{id} and (db_orders.buyer = #{nickname} or db_orders.seller = #{nickname})")
    List<Orders> getOrderById(Long id, String nickname);
}

package com.idle.entity.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Orders {
    Integer id;
    String createTime;
    String seller;
    String buyer;
    String payment;
    Integer status;
    BigDecimal price;
}

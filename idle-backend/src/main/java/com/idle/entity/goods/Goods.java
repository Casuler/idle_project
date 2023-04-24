package com.idle.entity.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {
    Integer id;
    Integer publisherId;
    String productName;
    BigDecimal price;
    String category;
    String introduce;
    String picture;
    String createTime;
    Integer status;
}

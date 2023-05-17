package com.idle.entity.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {
    Long id;
    Long publisher_id;
    String product_name;
    BigDecimal price;
    String category;
    String introduce;
    String picture;
    Integer status;
    String create_time;
}

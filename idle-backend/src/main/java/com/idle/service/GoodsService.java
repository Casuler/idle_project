package com.idle.service;

import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsById(Long id) throws Exception;

    boolean createGoods(Long id, Long publisher_id, String product_name, BigDecimal price, String category,
                      String introduce, String picture, String create_time);

    boolean updateGoods(Long id, String product_name, BigDecimal price, String category,
                      String introduce, String picture);

    boolean updateStatus(Integer status, Long id);

    List<Goods> getAllGoods();

    List<Goods> getGoodsByPublisherId(Long publisher_id);

    AccountUser getUserByPublisherId(Long publisher_id);

    List<Goods> getGoodsByCategory(String category);

    List<Goods> getGoodsByProductNameOrIntroduce(String search);

    String getPriceById(Long id);
}

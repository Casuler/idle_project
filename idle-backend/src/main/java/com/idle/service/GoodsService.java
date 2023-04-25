package com.idle.service;

import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsById(Integer id) throws Exception;

    boolean createGoods(Integer id, Integer publisherId, String productName, BigDecimal price, String category,
                      String introduce, String picture, String createTime);

    boolean updateGoods(Integer id, String productName, BigDecimal price, String category,
                      String introduce, String picture);

    boolean updateStatus(Integer id, Integer status);

    List<Goods> getAllGoods();

    List<Goods> getGoodsByPublisherId(Integer publisherId);

    AccountUser getUserByPublisherId(Integer publisherId);

    List<Goods> getGoodsByCategory(String category);

    List<Goods> getGoodsByProductNameOrIntroduce(String search);
}

package com.idle.service.impl;

import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;
import com.idle.mapper.GoodsMapper;
import com.idle.mapper.UserMapper;
import com.idle.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Goods> findGoodsById(Integer id){
       return goodsMapper.findGoodsById(id);
    }

    @Override
    public boolean createGoods(Integer id, Integer publisherId, String productName, BigDecimal price, String category,
                             String introduce, String picture) {
        return goodsMapper.createGoods(id, publisherId, productName, price, category, introduce, picture) > 0;
    }

    public boolean updateGoods(Integer id, String productName, BigDecimal price, String category,
                           String introduce, String picture) {
        return goodsMapper.updateGoods(id,productName,price,category,introduce,picture) > 0;
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    @Override
    public List<Goods> getGoodsByPublisherId(Integer publisherId) {
        return goodsMapper.getGoodsByPublisherId(publisherId);
    }

    @Override
    public AccountUser getUserByPublisherId(Integer publisherId){
        return goodsMapper.getUserByPublisherId(publisherId);
    }

    public List<Goods> getGoodsByCategory(String category){
        return goodsMapper.getGoodsByCategory(category);
    }

    public List<Goods> getGoodsByProductName(String productName) {
        return goodsMapper.getGoodsByProductName(productName);
    }
}

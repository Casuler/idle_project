package com.idle.service.impl;

import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;
import com.idle.mapper.GoodsMapper;
import com.idle.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> findGoodsById(Long id){
       return goodsMapper.findGoodsById(id);
    }

    @Override
    public boolean createGoods(Long id, Long publisher_id, String product_name, BigDecimal price, String category,
                             String introduce, String picture, String create_time) {
        return goodsMapper.createGoods(id, publisher_id, product_name, price, category, introduce, picture, create_time) > 0;
    }

    public boolean updateGoods(Long id, String product_name, BigDecimal price, String category,
                           String introduce, String picture) {
        return goodsMapper.updateGoods(id,product_name,price,category,introduce,picture) > 0;
    }

    @Override
    public boolean updateStatus(Long id) {
        return goodsMapper.updateStatus(id) > 0;
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    @Override
    public List<Goods> getGoodsByPublisherId(Long publisher_id) {
        return goodsMapper.getGoodsByPublisherId(publisher_id);
    }

    @Override
    public AccountUser getUserByPublisherId(Long publisher_id){
        return goodsMapper.getUserByPublisherId(publisher_id);
    }

    public List<Goods> getGoodsByCategory(String category){
        return goodsMapper.getGoodsByCategory(category);
    }

    public List<Goods> getGoodsByProductNameOrIntroduce(String search) {
        return goodsMapper.getGoodsByProductNameOrIntroduce(search);
    }

    @Override
    public String getPriceById(Long id) {
        return goodsMapper.getPriceById(id);
    }
}

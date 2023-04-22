package com.idle.controller;

import com.idle.entity.RestBean;
import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;
import com.idle.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController{

    @Resource
    GoodsService goodsService;

    //发布成功，但系统内部错误
    @PostMapping("/set-goods")
    public RestBean<String> setGoods(Integer id, Integer publisherId, String productName, BigDecimal price,
                                     String category, String introduce, String picture){
        boolean goods = goodsService.createGoods(id, publisherId, productName, price, category, introduce,picture);
        if(goods) return RestBean.success("商品发布成功");
        else return RestBean.failure(400,"商品发布失败，请联系管理员");
    }

    @GetMapping("/get-goods")
    public RestBean<?> getAllGoods(Integer id, Integer publisherId,String productName, BigDecimal price,
                                        String category, String introduce, String picture){
        List<Goods> goods = goodsService.getAllGoods(id,publisherId, productName, price, category, introduce, picture);
        if(goods == null) return RestBean.failure(401,"当前已无上架的商品");
        else return RestBean.success(goods);
    }

    @PostMapping("get-category-good")
    public RestBean<?> getGoodsByCategory(String category){
        List<Goods> goods = goodsService.getGoodsByCategory(category);
        if(goods == null || goods.size() == 0) return RestBean.failure(401,"该分类不存在");
        else return RestBean.success(goods);
    }

    @PostMapping("get-good-name")
    public RestBean<?> getGoodsByProductName(String productName){
        List<Goods> goods = goodsService.getGoodsByProductName(productName);
        if(goods == null || goods.size() == 0) return RestBean.failure(401,"查询的商品还未上架");
        else return RestBean.success(goods);
    }

    @PostMapping("/find-goods")
    public RestBean<?>findGoodsById(Integer id) throws Exception {
        List<Goods> goods = goodsService.findGoodsById(id);
        if( goods == null || goods.size() == 0) return RestBean.failure(401,"当前商品不存在");
        else return RestBean.success(goods);
    }

    @PostMapping("/get-publisher-goods")
    public RestBean<?> getGoodsByPublisherId(Integer publisherId){
        List<Goods> goods = goodsService.getGoodsByPublisherId(publisherId);
        if(goods == null || goods.size() == 0) return RestBean.failure(401, "该用户还未上架商品");
        else return RestBean.success(goods);
    }

    @PostMapping("/get-publisher")
    public RestBean<?> getUsernameByPublisherId(Integer publisherId){
        AccountUser user = goodsService.getUserByPublisherId(publisherId);
        if(user == null) return RestBean.failure(401,"该用户不存在");
        else return RestBean.success(user);
    }

    //修改成功，系统内部错误，和增加商品相同报错
    @PostMapping("/update-goods")
    public RestBean<String> updateGoods(Integer id, String productName, BigDecimal price, String category,
                                        String introduce, String picture){
       boolean goods = goodsService.updateGoods(id, productName, price, category, introduce, picture);
       if(goods) return RestBean.success("商品修改成功");
       else return RestBean.failure(401,"当前商品不存在");
    }

}

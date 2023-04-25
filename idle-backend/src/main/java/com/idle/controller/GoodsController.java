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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController{

    @Resource
    GoodsService goodsService;

    //发布成功，但系统内部错误
    @PostMapping("/set-goods")
    public RestBean<String> setGoods(Integer id, Integer publisher_id, String product_name, BigDecimal price,
                                     String category, String introduce, String picture, String create_time){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        boolean goods = goodsService.createGoods(id, publisher_id, product_name, price, category, introduce,picture,time);
        if(goods) return RestBean.success("商品发布成功");
        else return RestBean.failure(400,"商品发布失败，请联系管理员");
    }

    @GetMapping("/get-goods")
    public RestBean<?> getAllGoods(){
        List<Goods> goods = goodsService.getAllGoods();
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
    public RestBean<?> getGoodsByProductName(String search){
        List<Goods> goods = goodsService.getGoodsByProductNameOrIntroduce(search);
        if(goods == null || goods.size() == 0) return RestBean.failure(401,"未查询到相关商品");
        else return RestBean.success(goods);
    }

    @PostMapping("/find-goods")
    public RestBean<?>findGoodsById(Integer id) throws Exception {
        List<Goods> goods = goodsService.findGoodsById(id);
        if( goods == null || goods.size() == 0) return RestBean.failure(401,"当前商品不存在");
        else return RestBean.success(goods);
    }

    @PostMapping("/get-publisher-goods")
    public RestBean<?> getGoodsByPublisherId(Integer publisher_id){
        List<Goods> goods = goodsService.getGoodsByPublisherId(publisher_id);
        if(goods == null || goods.size() == 0) return RestBean.failure(401, "该用户还未上架商品");
        else return RestBean.success(goods);
    }

    @PostMapping("/get-publisher")
    public RestBean<?> getUsernameByPublisherId(Integer publisher_id){
        AccountUser user = goodsService.getUserByPublisherId(publisher_id);
        if(user == null) return RestBean.failure(401,"该用户不存在");
        else return RestBean.success(user);
    }

    //修改成功，系统内部错误，和增加商品相同报错
    @PostMapping("/update-goods")
    public RestBean<String> updateGoods(Integer id, String product_name, BigDecimal price, String category,
                                        String introduce, String picture){
       boolean goods = goodsService.updateGoods(id, product_name, price, category, introduce, picture);
       if(goods) return RestBean.success("商品修改成功");
       else return RestBean.failure(401,"当前商品不存在");
    }

    @PostMapping("/update-status")
    public RestBean<String> updateStatus(Integer id, Integer status){
        boolean goods = goodsService.updateStatus(id, status);
        if(goods) return RestBean.success("商品下架成功");
        else return RestBean.failure(500,"内部错误，请联系管理员");
    }

}

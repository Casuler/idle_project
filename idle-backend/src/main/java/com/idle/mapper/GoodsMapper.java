package com.idle.mapper;

import com.idle.entity.goods.Goods;
import com.idle.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select * from db_goods where id = #{id}")
    List<Goods> findGoodsById(Integer id);

    @Insert("insert into db_goods (id, publisherId, productName, price, category, introduce, picture, createTime)" +
            " values (#{id}, #{publisherId}, #{productName}, #{price}, #{category}, #{introduce}," +
            " #{picture}, #{createTime})")
    int createGoods(Integer id, Integer publisherId, String productName, BigDecimal price, String category,
                    String introduce, String picture, String createTime);

    @Update("update db_goods set productName = #{productName}, price = #{price}, category = #{price}, introduce = " +
            "#{introduce}, picture = #{picture} where id = #{id}")
    int updateGoods(Integer id,String productName, BigDecimal price, String category, String introduce, String picture);

    @Select("select * from db_goods")
    List<Goods> getAllGoods();

    @Select("select * from db_goods join db_account on db_account.id = db_goods.publisherId where" +
            " db_goods.publisherId = #{publisherId}")
    List<Goods> getGoodsByPublisherId(Integer publisherId);

    @Select("select * from db_account where db_account.id = #{publisherId}")
    AccountUser getUserByPublisherId(Integer publisherId);

    @Select("select * from db_goods where category = #{category}")
    List<Goods> getGoodsByCategory(String category);

    @Select("select * from db_goods where db_goods.productName like CONCAT('%', #{search}, '%')" +
            "OR db_goods.introduce like CONCAT('%', #{search}, '%')")
    List<Goods> getGoodsByProductNameOrIntroduce(String search);
}

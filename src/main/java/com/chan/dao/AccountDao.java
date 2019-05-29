package com.chan.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author Chan
 */
@Mapper
public interface AccountDao {

    /**
     * 根据ID查询指定用户
     *
     * @param id 用户ID
     */
    @Select("SELECT * FROM `t_account` WHERE `id` = #{id} LIMIT 1")
    JSONObject findById(@Param("id") Integer id);

    /**
     * @return 返回当前总余额
     * */
    @Select("SELECT SUM(money) FROM `t_account`")
    BigDecimal findCountMoney();

    /**
     * 转出
     *
     * @param outId 转出人ID
     * @param money 转出余额
     */
    @Update("UPDATE  `t_account` SET money = money - #{money} WHERE id = #{outId}")
    int updateForOut(@Param("outId") Integer outId, @Param("money") BigDecimal money);

    /**
     * 转入 *
     *
     * @param inId 转入人ID
     * @param money 转出余额
     */
    @Update("UPDATE  `t_account` SET money = money + #{money} WHERE id = #{inId}")
    int updateForIn(@Param("inId") Integer inId, @Param("money") BigDecimal money);

}

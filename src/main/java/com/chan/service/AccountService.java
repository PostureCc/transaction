package com.chan.service;

import com.alibaba.fastjson.JSONObject;
import com.chan.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public BigDecimal findCountMoney(){
        return accountDao.findCountMoney();
    }

    /**
     * 模拟转账场景
     *
     * @param inId  转入人ID
     * @param outId 转出人ID
     * @param money 转出余额
     */
    @Transactional
    public void updateForOut(final Integer outId, final Integer inId, final BigDecimal money) throws Exception {
        if (outId.equals(inId)) throw new Exception("不能给自己转账");

        JSONObject resOutJson = accountDao.findById(outId);
        //以下两句if判断为保证数据的一致性 简单理解 原有的数据总数量是多少 修改后的总数量还是多少 如果在转账时 转入的人不存在 则会导致数据平白无故的消失 从而使数据不一致
        //为什么抛出异常? 因为这里是由Spring事务来管理数据库事务的 当抛出异常后 就会触发Spring事务的回滚 PS:也可以用return代替
        if (Objects.isNull(resOutJson) || resOutJson.isEmpty()) throw new Exception("请选择正确的转账人");
        if (money.compareTo(resOutJson.getBigDecimal("money")) >= 0) throw new Exception("余额不足");

        int i = accountDao.updateForOut(outId, money);
        i += accountDao.updateForIn(inId, money);

        //转账属于两个数据的效果 如果修改记录大于2 则表明数据可能有异常
        if (2 > i) throw new Exception("系统异常");
    }

}

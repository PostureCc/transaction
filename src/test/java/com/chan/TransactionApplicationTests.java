package com.chan;

import com.chan.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionApplicationTests {

    @Autowired
    private AccountService accountService;

    /** 数据库中的总余额数值 */
    public static final BigDecimal countMoney = new BigDecimal(10000000.0000);

    @Test
    public void test() {
        try {
            accountService.updateForOut(2, 1, new BigDecimal(1000));
            System.err.println(String.format("数据是否一致%s", countMoney.compareTo(accountService.findCountMoney()) == 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

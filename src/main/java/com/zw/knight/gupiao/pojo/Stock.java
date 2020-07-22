package com.zw.knight.gupiao.pojo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author zw
 * @date 2020/7/20
 */
@Data
public class Stock {
    private static final Logger log = LoggerFactory.getLogger(Stock.class);
    private String code;
    private String name;
    private String cost;
    private String nowPrice;
    private String maxPrice;
    private String minPrice;
    private String openPrice;
    private String num;
    private String rate;  // 佣金费率
    private String minCommission; // 最低佣金
    private String taxRate = "10"; // 印花税
    private String last; // 当前结余
    private String sale; // 不亏本卖出价格

    public Stock(String[] stockArr) {
        this.code = stockArr[0];
        this.name = stockArr[1];
        this.cost = stockArr[2];
        this.num = stockArr[3];
        this.rate = stockArr[4];
        this.minCommission = stockArr[5];
    }

    public String getCash() {
        BigDecimal now = new BigDecimal(this.nowPrice);
        this.last = (now.subtract(new BigDecimal(this.cost))).multiply(new BigDecimal(num)).toString();
        log.info(this.name);
        log.info("当前：" + now + " 增减：" + now.subtract(new BigDecimal(this.cost)) + " 结余：" + this.last);
        return this.last;
    }

    public BigDecimal getSale() {
        BigDecimal now = new BigDecimal(this.nowPrice);
        BigDecimal cost = new BigDecimal(this.cost);
        BigDecimal rate = new BigDecimal(this.rate).divide(BigDecimal.valueOf(10000));
        BigDecimal minCommission = new BigDecimal(this.minCommission);
        BigDecimal taxRate = new BigDecimal(this.taxRate).divide(BigDecimal.valueOf(10000));
        BigDecimal num = new BigDecimal(this.num);
        log.info(this.name);
        BigDecimal sale = cost.divide(BigDecimal.ONE.subtract(taxRate).subtract(rate).subtract(rate), 3, BigDecimal.ROUND_UP);
        if (sale.compareTo(now) < 0) {
            sale = now;
        }
        BigDecimal todayRate = sale.subtract(new BigDecimal(this.openPrice)).divide(sale, 4, BigDecimal.ROUND_UP).multiply(BigDecimal.valueOf(100));
        BigDecimal commission = sale.multiply(num).multiply(rate);
        if (commission.compareTo(minCommission) < 0) {
            commission = minCommission;
        }
        // 计算印花税
        BigDecimal tax = sale.multiply(num).multiply(taxRate);
        if (todayRate.compareTo(BigDecimal.valueOf(10)) <= 0) {
            log.info("不亏本卖出价格：" + sale + " 今日涨幅：" + todayRate + "% 全仓佣金,卖:" + commission + " 全仓印花税：" + tax);
        } else {
            BigDecimal loss = new BigDecimal(this.maxPrice).subtract(cost).multiply(num);
            log.info("不适现在卖出 - 现最高价:" + this.maxPrice + " 此价卖出亏损：" + loss);
        }
        return sale;
    }

    @Override
    public String toString() {
        String last = getLast();
        String desc = Double.parseDouble(last) > 0 ? "盈利" : "亏损";
        return "[" + this.name + ":" + desc + last + "]";
    }
}

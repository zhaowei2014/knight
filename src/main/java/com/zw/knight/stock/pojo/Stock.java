package com.zw.knight.stock.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author zw
 * @date 2020/7/20
 */
public class Stock {
    private static final Logger log = LoggerFactory.getLogger(Stock.class);
    private String code;
    private String name;
    private BigDecimal cost;
    private BigDecimal nowPrice;
    private String maxPrice;
    private String minPrice;
    private BigDecimal openPrice;
    private BigDecimal num;
    private BigDecimal rate;  // 佣金费率
    private BigDecimal minCommission; // 最低佣金
    private BigDecimal taxRate = BigDecimal.valueOf(0.001); // 印花税
    private String last; // 当前结余
    private BigDecimal sale; // 不亏本卖出价格

    public Stock(String[] stockArr) {
        this.code = stockArr[0];
        this.name = stockArr[1];
        this.cost = new BigDecimal(stockArr[2]);
        this.num = new BigDecimal(stockArr[3]);
        this.rate = new BigDecimal(stockArr[4]).divide(BigDecimal.valueOf(10000), 6);
        this.minCommission = new BigDecimal(stockArr[5]);
    }

    public String getCash() {
        this.last = (this.nowPrice.subtract(this.cost)).multiply(num).toString();
        log.info("当前：" + this.nowPrice + " 增减：" + this.nowPrice.subtract(this.cost) + " 结余：" + this.last);
        return this.last;
    }

    public BigDecimal getSale() {
        this.sale = this.cost.divide(BigDecimal.ONE.subtract(this.taxRate).subtract(this.rate).subtract(this.rate), 3, BigDecimal.ROUND_UP);
        if (this.sale.compareTo(this.nowPrice) < 0) {
            this.sale = this.nowPrice;
        }
        BigDecimal todayRate = this.sale.subtract(this.openPrice).multiply(BigDecimal.valueOf(100)).divide(this.sale, 2, BigDecimal.ROUND_UP);
        BigDecimal commission = this.sale.multiply(this.num).multiply(this.rate);
        if (commission.compareTo(this.minCommission) < 0) {
            commission = this.minCommission;
        }
        // 计算印花税
        BigDecimal tax = this.sale.multiply(num).multiply(taxRate);
        if (todayRate.compareTo(BigDecimal.valueOf(10)) <= 0) {
            log.info("盈利卖出价格：" + this.sale + " 今日涨幅：" + todayRate + "% 全仓佣金,卖:" + commission + " 全仓印花税：" + tax);
        } else {
            BigDecimal loss = new BigDecimal(this.maxPrice).subtract(cost).multiply(num);
            log.info("不适现在卖出 - 现最高价:" + this.maxPrice + " 此价卖出亏损：" + loss);
        }
        return this.sale;
    }

//    @Override
//    public String toString() {
//        String last = getLast();
//        String desc = Double.parseDouble(last) > 0 ? "盈利" : "亏损";
//        return "[" + this.name + ":" + desc + last + "]";
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(BigDecimal nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMinCommission() {
        return minCommission;
    }

    public void setMinCommission(BigDecimal minCommission) {
        this.minCommission = minCommission;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }
}

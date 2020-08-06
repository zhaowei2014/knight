package com.zw.knight.stock.pojo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * 盈利
 *
 * @author zw
 * @date 2020/7/27
 */
public class Profit {

    /**
     * 总价
     */
    private BigDecimal total = BigDecimal.ZERO;
    /**
     * 数量
     */
    private BigDecimal hand = BigDecimal.ZERO;
    /**
     * 佣金
     */
    private BigDecimal commission = BigDecimal.ZERO;
    /**
     * 印花税
     */
    private BigDecimal tax = BigDecimal.ZERO;
    /**
     * 佣金费率
     */
    private BigDecimal rate = BigDecimal.ZERO;

    /**
     * 印花税率
     */
    private BigDecimal taxRate = BigDecimal.valueOf(0.001);


    /**
     * 卖出后回收总值
     */
    public BigDecimal getSaleProfit() {
        return this.total.subtract(this.commission).subtract(this.tax);
    }

    /**
     * 买入消耗总金额
     */
    public BigDecimal getBuyCost() {
        return this.total.add(this.commission).add(this.tax);
    }

    /**
     * 根据盈利 获取买入价格
     *
     * @param profit 盈利
     */
    public BigDecimal getBuyPrice(Integer profit) {
        BigDecimal saleProfit = this.getSaleProfit().subtract(BigDecimal.valueOf(profit));
        BigDecimal add = BigDecimal.ONE.add(this.rate);
        return saleProfit.divide(this.hand, 4).divide(add, 2, BigDecimal.ROUND_DOWN);
    }


    public BigDecimal getSalePrice(Integer profit) {
        BigDecimal buyCost = this.getBuyCost().add(BigDecimal.valueOf(profit));
        BigDecimal add = BigDecimal.ONE.subtract(this.rate).subtract(this.taxRate);
        return buyCost.divide(this.hand, 4).divide(add, 2, BigDecimal.ROUND_DOWN);
    }

    public Map<String, String> getSaleThenBuyResult() {
        Map<String, String> result = new TreeMap<>();
        result.put("不亏损", this.getBuyPrice(0).doubleValue() + "");
        result.put("盈利100", this.getBuyPrice(100).doubleValue() + "");
        result.put("盈利200", this.getBuyPrice(200).doubleValue() + "");
        result.put("盈利400", this.getBuyPrice(400).doubleValue() + "");
        return result;
    }

    public Map<String, String> getBuyThenSaleResult() {
        Map<String, String> result = new TreeMap<>();
        result.put("不亏损", this.getSalePrice(0).doubleValue() + "");
        result.put("盈利100", this.getSalePrice(100).doubleValue() + "");
        result.put("盈利200", this.getSalePrice(200).doubleValue() + "");
        result.put("盈利400", this.getSalePrice(400).doubleValue() + "");
        return result;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getHand() {
        return hand;
    }

    public void setHand(BigDecimal hand) {
        this.hand = hand;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}

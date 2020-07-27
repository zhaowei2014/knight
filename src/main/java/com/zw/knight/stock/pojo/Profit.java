package com.zw.knight.stock.pojo;

import java.math.BigDecimal;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/27
 */
public class Profit {

    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal hand = BigDecimal.ZERO;
    private BigDecimal commission = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;

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
}

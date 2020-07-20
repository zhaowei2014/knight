package com.zw.knight.gupiao.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zw
 * @date 2020/7/20
 */
@Data
public class Stock {
    private String code;
    private String name;
    private String cost;
    private String nowPrice;
    private String maxPrice;
    private String minPrice;
    private String num;

    public Stock(String[] stockArr) {
        this.code = stockArr[0];
        this.name = stockArr[1];
        this.cost = stockArr[2];
        this.num = stockArr[3];
    }

    public double getLast() {
        BigDecimal now = new BigDecimal(this.nowPrice);
        BigDecimal last = (now.subtract(new BigDecimal(this.cost))).multiply(new BigDecimal(num));
        System.out.println(this.name);
        System.out.println("当前：" + now.toString());
        System.out.println("增减：" + now.subtract(new BigDecimal(this.cost)));
        System.out.println("结余：" + last.toString());
        return last.doubleValue();
    }

    @Override
    public String toString() {
        double last = getLast();
        String desc = last > 0 ? "盈利" : "亏损";
        return "[" + this.name + ":" + desc + last + "]";
    }
}

package com.zw.knight.stock.pojo;

import lombok.Data;

/**
 * 股票榜单数据
 *
 * @author zhaow-s
 * @date 2023/1/18
 */
@Data
public class StockRank {
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 榜单日期
     */
    private String date;
    /**
     * 排序
     */
    private String order;
}

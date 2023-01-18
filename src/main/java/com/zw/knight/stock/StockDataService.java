package com.zw.knight.stock;

import com.zw.knight.stock.pojo.Stock;
import com.zw.knight.stock.pojo.StockRank;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

/**
 * 数据服务
 *
 * @author zhaow-s
 * @date 2023/1/18
 */
public class StockDataService {

    WebClient webClient = WebClient.builder().build();
    // print(readjson())
    // 一周一个文件/文件命名 年-月-第X周
    // 首先查询今天是周几，以及是本月的第几周
    //  周一的话，需要新建文件(文件名：年-月-第X周)
    //  周一、周二、周三、周四，新建sheet，依次填充前面sheet的信息，并且寻找上一周的文件，填充信息
    //  周五新建sheet，依次填充前面sheet的信息即可
    //  特殊判断，如果没找到上周文件的话，说明没有无需填写
    //////////// 需要的方法 //////////
    // 获取今天是周几；获取当前的 年-月-第X周；或者上一周的 年-月-第X周(有可能跨月跨年)；
    // 获取当天的净流入排行榜，存入内存中，除当天数据直接存外，所有需要填充的数据都基于这个数据遍历拿到
    // 考虑每天定时触发，发邮件

    // 查询主力净流入
//    getzljlrHttp()

    // 写入Excel中
//    writejsontoExcel()

    // 根据主力净流入，单独查一个股票的情况

    /**
     * 主力净流入数据
     */
    public void mainNetInflow() {
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();
    }

    /**
     * 写入榜单数据
     *
     * @param stockRanks 股票榜单数据
     */
    public void setRank(List<StockRank> stockRanks) {
    }

    /**
     * 写入股票数据
     *
     * @param stockData 股票数据
     */
    public void setStockData(List<Stock> stockData) {

    }


    public void processData() {
        var now = LocalDate.now().toString();
        var start = LocalDate.now().minusDays(10).toString();

        // 获取日期内的榜单数据并去重 - 可写缓存

        // 获取今日数据集合
        // 写入今日榜单 - 前30
        // 融合榜单数据并去重，
        // 根据榜单融合代码，整理数据集中数据
        // 存储有效数据
    }

}

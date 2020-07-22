package com.zw.knight.gupiao;

import com.zw.knight.gupiao.pojo.Stock;
import com.zw.knight.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StockService
 *
 * @author zw
 * @date 2020/7/20
 */
public class StockService {
    private static final Logger log = LoggerFactory.getLogger(StockService.class);

    private static final String URL = "http://hq.sinajs.cn/list=";

    public List<Stock> getStock() throws IOException {
        List<Stock> myStock = getMyStock();
        String codes = myStock.stream().map(Stock::getCode).collect(Collectors.toList()).toString();
        codes = codes.substring(1, codes.length() - 1).replaceAll(" ", "");
        String data = HttpClient.get(URL + codes);
        this.replaceStock(myStock, data);
        return myStock;
    }

    public List<Stock> getMyStock() throws IOException {
        List<Stock> stocks = new ArrayList<>();
        // 获取自选股票信息
        File file = new File("D:/stock");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        while ((data = br.readLine()) != null) {
            if (data.startsWith("#")) continue;
            Stock stock = getStock(data);
            if (stock != null) {
                stocks.add(stock);
            }
        }
        return stocks;
    }

    private Stock getStock(String data) {
        String[] stockArr = data.split(",");
        if (stockArr.length >= 3) {
            return new Stock(stockArr);
        }
        return null;
    }

    private void replaceStock(List<Stock> stocks, String data) {
        String[] datas = data.split(";");
        for (String stockData : datas) {
            for (Stock stock : stocks) {
                if (stockData.contains(stock.getCode())) {
                    String[] stockArr = stockData.split(",");
                    stock.setOpenPrice(stockArr[1]);
                    stock.setNowPrice(stockArr[3]);
                    stock.setMaxPrice(stockArr[4]);
                    stock.setMinPrice(stockArr[5]);
                    break;
                }
            }
        }
    }

    private void sum() throws IOException {
        List<Stock> stocks = this.getStock();
        stocks.forEach(Stock::getCash);
        stocks.forEach(Stock::getSale);
        BigDecimal sum = BigDecimal.ZERO;
        for (Stock stock : stocks) {
            sum = sum.add(new BigDecimal(stock.getLast()));
        }
        log.info("当前持仓" + (sum.compareTo(BigDecimal.ZERO) > 0 ? "盈利：" : "亏损" + sum.toString()));
    }


    public static void main(String[] args) throws IOException {
        StockService stockService = new StockService();
        stockService.sum();
    }
}

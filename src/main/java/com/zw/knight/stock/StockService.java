package com.zw.knight.stock;

import com.zw.knight.stock.pojo.Profit;
import com.zw.knight.stock.pojo.Stock;
import com.zw.knight.util.GsonUtils;
import com.zw.knight.util.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StockService
 *
 * @author zw
 * @date 2020/7/20
 */
@Slf4j
@Service
public class StockService {
    private static final String URL = "http://hq.sinajs.cn/list=";

    private static List<Stock> myStock;

    static {
        try {
            myStock = getMyStock();
            getStock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Stock> getStock() throws IOException {
        String codes = myStock.stream().map(Stock::getCode).collect(Collectors.toList()).toString();
        codes = codes.substring(1, codes.length() - 1).replace(" ", "");
        String data = HttpClient.get(URL + codes);
        replaceStock(data);
        return myStock;
    }

    private static List<Stock> getMyStock() {
        List<Stock> stocks = new ArrayList<>();
        // 获取自选股票信息
        File file = new File(StockService.class.getResource("/").getPath() + "/config/stock");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = br.readLine()) != null) {
                if (data.startsWith("#")) {
                    continue;
                }
                Stock stock = getStock(data);
                if (stock != null) {
                    stocks.add(stock);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    private static Stock getStock(String data) {
        String[] stockArr = data.split(",");
        if (stockArr.length >= 3) {
            return new Stock(stockArr);
        }
        return null;
    }

    private static void replaceStock(String data) {
        String[] datas = data.split(";");
        for (String stockData : datas) {
            for (Stock stock : myStock) {
                if (stockData.contains(stock.getCode())) {
                    String[] stockArr = stockData.split(",");
                    stock.setOpenPrice(new BigDecimal(stockArr[1]));
                    stock.setNowPrice(new BigDecimal(stockArr[3]));
                    stock.setMaxPrice(stockArr[4]);
                    stock.setMinPrice(stockArr[5]);
                    break;
                }
            }
        }
    }

    public List<Stock> sum() throws IOException {
        List<Stock> stocks = getStock();
        stocks.forEach(Stock::getCash);
        stocks.forEach(Stock::getSale);
        BigDecimal sum = BigDecimal.ZERO;
        for (Stock stock : stocks) {
            sum = sum.add(new BigDecimal(stock.getLast()));
        }
        log.info("当前持仓：{}", (sum.compareTo(BigDecimal.ZERO) > 0 ? "盈利：" : "亏损" + sum.toString()));
        return stocks;
    }

    // 根据卖价，买价，计算盈亏
    public static double profit(Map<String, String> buys, Map<String, String> sales, String code) throws Exception {
        // 所有的卖价
        Profit profit1 = calculator(sales, code, new Profit());
        // 计算所有买价的佣金
        Profit profit2 = calculator(buys, code, new Profit());
        // 利润
        return profit1.getTotal().subtract(profit2.getTotal()).subtract(profit1.getCommission())
                .subtract(profit1.getTax()).subtract(profit2.getCommission()).doubleValue();
    }

    private static Profit calculator(Map<String, String> sales, String code, Profit profit) throws Exception {
        Stock stock = getStockByCode(code);
        profit.setRate(stock.getRate());
        for (Map.Entry<String, String> sale : sales.entrySet()) {
            BigDecimal amount = new BigDecimal(sale.getKey());
            BigDecimal hand = new BigDecimal(sale.getValue());
            // 计算所有卖价的佣金
            profit.setCommission(profit.getCommission().add(amount.multiply(hand).multiply(profit.getRate())));
            // 计算所有的卖价的印花税
            profit.setTax(profit.getTax().add(amount.multiply(hand).multiply(profit.getTaxRate())));
            profit.setTotal(profit.getTotal().add(amount.multiply(hand)));
            profit.setHand(profit.getHand().add(hand));
        }
        return profit;
    }

    /**
     * 根据传入code 从配置文件中查出相关信息
     *
     * @param code 代号
     */
    private static Stock getStockByCode(String code) throws Exception {
        List<Stock> stocks = myStock.stream().filter(stock -> stock.getCode().equals(code)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(stocks)) {
            throw new Exception();
        }
        return stocks.get(0);
    }

    /**
     * 先卖后买，按盈利给出买入价格
     */
    public static Map<String, String> saleThenBuy(Map<String, String> sales, String code) throws Exception {
        Profit profit = calculator(sales, code, new Profit());
        return profit.getSaleThenBuyResult();
    }

    /**
     * 先买后买，按盈利给出卖出价格
     */
    public static Map<String, String> buyThenSale(Map<String, String> buy, String code) throws Exception {
        Profit profit = calculator(buy, code, new Profit());
        return profit.getBuyThenSaleResult();
    }

    public static void main(String[] args) throws Exception {
        log.info(StockService.class.getResource("").getPath());

        StockService stockService = new StockService();
        Map<String, String> buy = new HashMap<>(2);
        buy.put("266.6", "100");
        Map<String, String> sale = new HashMap<>(2);
        sale.put("268", "100");
        log.info(StockService.profit(buy, sale, "sz000858") + "");
        log.info(GsonUtils.toJson(StockService.saleThenBuy(sale, "sz002905")));
        log.info(GsonUtils.toJson(StockService.buyThenSale(buy, "sz002905")));
        stockService.sum();
        log.info(StockService.getStock().get(0).getNowPrice().toString());
    }


}

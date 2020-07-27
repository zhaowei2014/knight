package com.zw.knight.stock;

import com.zw.knight.stock.pojo.Profit;
import com.zw.knight.stock.pojo.Stock;
import com.zw.knight.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class StockService {
    private static final Logger log = LoggerFactory.getLogger(StockService.class);

    private static final String URL = "http://hq.sinajs.cn/list=";

    private static List<Stock> myStock;

    {
        try {
            myStock = this.getMyStock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Stock> getStock() throws IOException {
        String codes = myStock.stream().map(Stock::getCode).collect(Collectors.toList()).toString();
        codes = codes.substring(1, codes.length() - 1).replaceAll(" ", "");
        String data = HttpClient.get(URL + codes);
        this.replaceStock(data);
        return myStock;
    }

    private List<Stock> getMyStock() throws IOException {
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

    private void replaceStock(String data) {
        String[] datas = data.split(";");
        for (String stockData : datas) {
            for (Stock stock : myStock) {
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

    // 根据卖价，买价，计算盈亏
    private double profit(Map<String, String> buys, Map<String, String> sales, String code) {
        Stock stock = getStockByCode(code);
        // 所有的卖价
        Profit profit1 = calculator(sales, stock, new Profit());
        // 计算所有买价的佣金
        Profit profit2 = calculator(buys, stock, new Profit());
        // 利润
        return profit1.getTotal().subtract(profit2.getTotal()).subtract(profit1.getCommission())
                .subtract(profit1.getTax()).subtract(profit2.getCommission()).subtract(profit2.getTax()).doubleValue();
    }

    private Profit calculator(Map<String, String> sales, Stock stock, Profit profit) {
        for (Map.Entry<String, String> sale : sales.entrySet()) {
            BigDecimal amount = new BigDecimal(sale.getKey());
            BigDecimal hand = new BigDecimal(sale.getValue());
            // 计算所有卖价的佣金
            profit.setCommission(profit.getCommission().add(amount.multiply(hand).multiply(new BigDecimal(stock.getRate())).divide(BigDecimal.valueOf(10000))));
            // 计算所有的卖价的印花税
            profit.setTax(profit.getTax().add(amount.multiply(hand).multiply(new BigDecimal(stock.getTaxRate())).divide(BigDecimal.valueOf(10000))));
            profit.setTotal(profit.getTotal().add(amount.multiply(hand)));
            profit.setHand(profit.getHand().add(hand));
        }
        return profit;
    }

    public Stock getStockByCode(String code) {
        List<Stock> stocks = myStock.stream().filter(stock -> stock.getCode().equals(code)).collect(Collectors.toList());
        return stocks.get(0);
    }

    public void saleThenBuy(Map<String, String> sales, String code) {
        Stock stock = getStockByCode(code);
        Profit profit = calculator(sales, stock, new Profit());
        // 当前
        BigDecimal saleProfit = profit.getTotal().subtract(profit.getCommission()).subtract(profit.getTax());
        BigDecimal add = BigDecimal.ONE.add(new BigDecimal(stock.getRate()).divide(BigDecimal.valueOf(10000)));
        BigDecimal buyAmount = saleProfit
                .divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        BigDecimal buyAmount200 = saleProfit.subtract(BigDecimal.valueOf(200)).divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        BigDecimal buyAmount400 = saleProfit.subtract(BigDecimal.valueOf(400)).divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        System.out.println(buyAmount);
        System.out.println(buyAmount200);
        System.out.println(buyAmount400);

    }

    private void buyThenSale(Map<String, String> buy, String code) {
        Stock stock = getStockByCode(code);
        Profit profit = calculator(buy, stock, new Profit());
        // 当前
        BigDecimal saleProfit = profit.getTotal().subtract(profit.getCommission()).subtract(profit.getTax());
        BigDecimal add = BigDecimal.ONE.add(new BigDecimal(stock.getRate()).divide(BigDecimal.valueOf(10000)));
        BigDecimal buyAmount = saleProfit
                .divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        BigDecimal buyAmount200 = saleProfit.subtract(BigDecimal.valueOf(200)).divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        BigDecimal buyAmount400 = saleProfit.subtract(BigDecimal.valueOf(400)).divide(profit.getHand())
                .divide(add, 2, BigDecimal.ROUND_DOWN);
        System.out.println(buyAmount);
        System.out.println(buyAmount200);
        System.out.println(buyAmount400);
    }


    public static void main(String[] args) throws IOException {
        StockService stockService = new StockService();
//        stockService.sum();
//        Map<String, String> buy = new HashMap<>();
//        buy.put("9.55", "600");
//        buy.put("9.53", "400");
//        Map<String, String> sale = new HashMap<>();
//        sale.put("9.7", "400");
//        sale.put("9.65", "400");
//        sale.put("9.64", "200");
//        System.out.println(stockService.profit(buy, sale, "sz002670"));

        Map<String, String> buy = new HashMap<>();
        buy.put("37.29", "100");
        buy.put("37.3", "100");
        buy.put("37.32", "100");
        buy.put("37.33", "100");
        Map<String, String> sale = new HashMap<>();
        sale.put("37.84", "300");
        sale.put("37.6", "100");
//        System.out.println(stockService.profit(buy, sale, "sz002151"));

        stockService.saleThenBuy(sale, "sz002151");
    }
}

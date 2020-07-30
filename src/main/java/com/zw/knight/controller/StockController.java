package com.zw.knight.controller;

import com.zw.knight.stock.StockService;
import com.zw.knight.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/30
 */
@RequestMapping("/stock")
@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/sum")
    public String sum() throws IOException {
        return GsonUtils.toJson(stockService.sum());
    }

    @RequestMapping("/saleThanBuy")
    public String saleThanBuy(@RequestParam String sale, String code) throws Exception {
        return GsonUtils.toJson(stockService.saleThenBuy(business(sale), code));
    }

    @RequestMapping("/buyThanSale")
    public String buyThanSale(@RequestParam String buy, String code) throws Exception {
        return GsonUtils.toJson(stockService.buyThenSale(business(buy), code));
    }

    private Map<String, String> business(String businesses) {
        String[] part = businesses.split(",");
        Map<String, String> buyMap = new HashMap<>();
        for (int i = 0; i < part.length; i += 2) {
            if (i % 2 == 0) {
                buyMap.put(part[i], part[i + 1]);
            }
        }
        return buyMap;
    }

}

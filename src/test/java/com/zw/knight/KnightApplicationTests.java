package com.zw.knight;

import com.zw.knight.config.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnightApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void contextLoads() {
        redisUtil.set("zw123123","123123123");
        System.out.println(redisUtil.get("zw123123"));
    }

}

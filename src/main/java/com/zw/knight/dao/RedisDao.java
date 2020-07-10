package com.zw.knight.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * redis
 *
 * @author zw
 * @date 2020/7/10
 */
@Repository
public class RedisDao {
    public String get(String key) {
        return key;
    }

    public void incry(String userTimeKey, Long time) {
    }

    public void zincrby(String duration_zset, Long duration, int i) {
    }

    public List<String> zrangeByScores(String duration_zset, Long duration, int i) {
        return new ArrayList<>();
    }
}

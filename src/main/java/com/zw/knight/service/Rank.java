package com.zw.knight.service;

import com.zw.knight.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 实时排名算法
 *
 * @author zw
 * @date 2020/7/10
 */
public class Rank {

    @Autowired
    private RedisDao redisDao;

    /**
     * //优化算法逻辑增加排名统计任务，一种伪实时方式统计
     * //可以写个定时脚本 每个5分钟统计阅读时长的排名的关系
     * //100 ->100 名
     * //99 ->102 名
     * //1 ->1000000 名
     */
    private Long rank(String user, Long time) {

        // 获取用户现有阅读时长
        Long duration = getDuration(user);

        // 增加用户阅读时长
        incryDuration(user, time);

        // 调整总量数据该用户所处位置
        replaceRank(duration, time);

        // 确定用户排名
        return fixRank(duration + time);
    }

    // 获取用户当前时长
    private Long getDuration(String user) {
        String userTimeKey = String.format("user_time_key_%s", user);
        String duration = redisDao.get(userTimeKey);
        //获取用户现有阅读时长
        if (StringUtils.isNotBlank(duration)) {
            return Long.parseLong(duration);
        }
        return null;
    }

    // 增加用户时长
    private void incryDuration(String user, Long time) {
        String userTimeKey = String.format("user_time_key_%s", user);
        redisDao.incry(userTimeKey, time);
    }

    // 进行用户所处位置变更
    private void replaceRank(Long duration, Long time) {
        String duration_zset = "duration_zset";
        //减少老时长人数
        redisDao.zincrby(duration_zset, duration, -1);
        //增加新时长人数
        redisDao.zincrby(duration_zset, duration + time, 1);
    }

    // 确定用户排名
    private long fixRank(Long duration) {
        String duration_zset = "duration_zset";
        // 根据当前用户时长获取所有
        List<String> infos = redisDao.zrangeByScores(duration_zset, duration - 1, -1);
        // 将所有数据加起来   就是当前用户前面的所有用户；
        long rank = infos.stream().count();
        return rank + 1;
    }

}

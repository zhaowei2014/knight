package com.zw.knight.config.codis;


import com.mysql.cj.util.LogUtils;
import com.zhangyue.common2.bean.MsetBean;
import com.zhangyue.common2.codis.CodisClient;
import com.zhangyue.common2.codis.Holder;
import com.zhangyue.common2.exception.ServiceException;
import com.zw.knight.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Client;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;

import java.util.*;


/**
 * 文件名称：CodisUtils.java
 * 描述：codis工具类
 *
 * @author xingyh
 * @date 2016/6/23 17:03
 */
public class CodisUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CodisUtils.class);
    private CodisClient codisClient;

    public CodisClient getCodisClient() {
        return codisClient;
    }

    public void setCodisClient(CodisClient codisClient) {
        this.codisClient = codisClient;
    }

    private static String CLIENT = "CLIENT";

    public CodisUtils(CodisClient codisClient) {
        this.codisClient = codisClient;
    }

    public static String getCLIENT() {
        return CLIENT;
    }

    public static void setCLIENT(String CLIENT) {
        CodisUtils.CLIENT = CLIENT;
    }

    public String getHots(Map<String, Client> params) {
        if (params != null && params.get(CLIENT) != null) {
            return params.get(CLIENT).getHost();
        }
        return "";
    }

    public int getPort(Map<String, Client> params) {
        if (params != null && params.get(CLIENT) != null) {
            return params.get(CLIENT).getPort();
        }
        return 0;
    }

    public int getDb(Map<String, Client> params) {
        if (params != null && params.get(CLIENT) != null) {
            return Integer.parseInt(String.valueOf(params.get(CLIENT).getDB()));
        }
        return 0;
    }


    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {
        Holder<String, String> holder = new Holder<String, String>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();

        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.set(key, value));
            });
        } catch (Exception e) {
            LOG.error("set is error ,key=" + key + ",value=" + value, e);
            throw new ServiceException(e, "set is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "set ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @param expireTime 秒
     * @return
     */
    public String setex(final String key, final String value, int expireTime) {
        Holder<String, String> holder = new Holder<String, String>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.setex(key, expireTime, value));
            });
        } catch (Exception e) {
            LOG.error(
                    " setex is error ,key=" + key + ",value=" + value + ",expireTime=" + expireTime,
                    e);
            throw new ServiceException(e, "setex is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "setex ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Boolean sismember(final String key, final String member) {
        Holder<Object, Boolean> holder = new Holder<Object, Boolean>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.sismember(key, member));
            });
        } catch (Exception e) {
            LOG.error("sismember is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "sismember is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "sismember ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public long setnx(final String key, final String value) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.setnx(key, value));
            });
        } catch (Exception e) {
            LOG.error("setnx is error ,key=" + key + ",value=" + value, e);
            throw new ServiceException(e, "setnx is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "setnx ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * Pfcount 命令
     *
     * @param key
     * @return
     */
    public Long pfcount(final String key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.pfcount(key));
            });
        } catch (Exception e) {
            LOG.error("pfcount is error ,key=" + key, e);
            throw new ServiceException(e, "pfcount is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "pfcount ,keys=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * Pfadd 命令
     *
     * @param key
     * @return
     */
    public Long pfadd(final String key, final String... elements) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.pfadd(key, elements));
            });
        } catch (Exception e) {
            LOG.error("pfcount is error ,key=" + key, e);
            throw new ServiceException(e, "pfcount is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "pfcount ,keys=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * bitCount 命令
     *
     * @param key
     * @return
     */
    public Long bitCount(final String key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.bitcount(key));
            });
        } catch (Exception e) {
            LOG.error("bitCount is error ,key=" + key, e);
            throw new ServiceException(e, "bitCount is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "bitCount ,keys=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 批量获取数据
     *
     * @param keys
     * @return
     */
    public List<String> mget(final String... keys) {
        Holder<String, List<String>> holder = new Holder<String, List<String>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.mget(keys));
            });
        } catch (Exception e) {
            LOG.error("mget is error ,key=" + keys, e);
            throw new ServiceException(e, "mget is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "mget ,keys=" + keys, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        Holder<String, String> holder = new Holder<String, String>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.get(key));
            });
        } catch (Exception e) {
            LOG.error("get is error ,key=" + key, e);
            throw new ServiceException(e, "get is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "get ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 对应key增加数量
     *
     * @param key key
     * @param num 增加的值
     * @return
     */
    public long incrBy(final String key, final long num) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.incrBy(key, num));
            });
        } catch (Exception e) {
            LOG.error("incrBy is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "incrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "incrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 对应key增加数量
     *
     * @param key key
     * @param num 增加的值
     * @return 总值
     */
    public double incrByFloat(final String key, final double num) {
        Holder<String, Double> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.incrByFloat(key, num));
            });
        } catch (Exception e) {
            LOG.error("incrBy is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "incrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "incrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 设定过期时间
     *
     * @param key key
     * @param num 过期时间 秒
     * @return
     */
    public long expire(final String key, final int num) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.expire(key, num));
            });
        } catch (Exception e) {
            LOG.error("expire is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "expire is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "expire ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 设定过期时间
     *
     * @param key      key
     * @param unixTime 过期时间 秒
     * @return
     */
    public Long expireAt(final String key, final long unixTime) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.expireAt(key, unixTime));
            });
        } catch (Exception e) {
            LOG.error("expireAt is error ,key=" + key + ",unixTime=" + unixTime, e);
            throw new ServiceException(e, "expireAt is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "expireAt ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * hash获取数据
     *
     * @param key   key
     * @param field 列
     * @return
     */
    public String hget(final String key, final String field) {
        Holder<String, String> holder = new Holder<String, String>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hget(key, field));
            });
        } catch (Exception e) {
            LOG.error("hget is error ,key=" + key + ",field=" + field, e);
            throw new ServiceException(e, "hget is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hget ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }


    /**
     * hash获取key 全部数据
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(final String key) {
        Holder<String, Map<String, String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Map<String, String> result = jedis.hgetAll(key);
                holder.setValue(result);
            });
        } catch (Exception e) {
            LOG.error("hgetAll is error ,key=" + key, e);
            throw new ServiceException(e, "hgetAll is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hgetAll ,key=" + key, endTime - startTime);
        }
        Map<String, String> map = holder.getValue();
        if (map != null && map.size() < 1) {
            return null;
        }
        return map;
    }

    /**
     * 删除hash中的key-field
     *
     * @param key
     * @param field
     * @return
     */
    public Long hdel(final String key, final String field) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> holder.setValue(jedis.hdel(key, field)));
        } catch (Exception e) {
            LOG.error("hdel is error ,key=" + key + ",field=" + field, e);
            throw new ServiceException(e, "hdel is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hdel ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    // 返回 1 一添加
    // 0 已存在
    // -1 key 不存在
    public Long sadd(final String ketStr, final String val, final boolean checkExist) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                if (checkExist) {
                    if (jedis.exists(ketStr)) {
                        holder.setValue(jedis.sadd(ketStr, val));
                    } else {
                    }
                } else {
                    holder.setValue(jedis.sadd(ketStr, val));
                }
            });
        } catch (Exception e) {
            LOG.error("sadd is error ,key=" + ketStr + ",val=" + val + ",checkExist=" + checkExist,
                    e);
            throw new ServiceException(e, "sadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "sadd ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }

    // 返回 1 一添加
    // 0 已存在
    // -1 key 不存在
    public Long saddArray(final String ketStr, final boolean checkExist, final String... val) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                if (checkExist) {
                    if (jedis.exists(ketStr)) {
                        holder.setValue(jedis.sadd(ketStr, val));
                    } else {
                    }
                } else {
                    holder.setValue(jedis.sadd(ketStr, val));
                }
            });
        } catch (Exception e) {
            LOG.error("sadd is error ,key=" + ketStr + ",val=" + val + ",checkExist=" + checkExist,
                    e);
            throw new ServiceException(e, "sadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "sadd ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }


    public Long srem(final String ketStr, final String member) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> holder.setValue(jedis.srem(ketStr, member)));
        } catch (Exception e) {
            LOG.error("srem is error ,key=" + ketStr + ",member=" + member, e);
            throw new ServiceException(e, "srem is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "srem ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * hash中是否存在某个域
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(final String key, final String field) {
        Holder<String, Boolean> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> holder.setValue(jedis.hexists(key, field)));
        } catch (Exception e) {
            LOG.error("hexists is error ,key=" + key + ",field=" + field, e);
            throw new ServiceException(e, "hexists is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hexists ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * hash保存数据
     *
     * @param key   key
     * @param field 列
     * @param value 值
     * @return
     */
    public Long hset(final String key, final String field, final String value) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hset(key, field, value));
            });
        } catch (Exception e) {
            LOG.error("hset is error ,key=" + key + ",field=" + field + ",value=" + value, e);
            throw new ServiceException(e, "hset is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hset ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * 哈希表长度
     *
     * @param key key
     * @return 长度
     */
    public Long hlen(final String key) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hlen(key));
            });
        } catch (Exception e) {
            LOG.error("hlen is error key={}", key, e);
            throw new ServiceException(e, "hlen is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hlen ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * hash 数量增加
     *
     * @param key   key
     * @param field 列
     * @param num   值
     * @return
     */
    public Long hincrBy(final String key, final String field, final long num) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hincrBy(key, field, num));
            });
        } catch (Exception e) {
            LOG.error("hincrBy is error ,key=" + key + ",field=" + field + ",num=" + num, e);
            throw new ServiceException(e, "hincrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hincrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * 删除key的数据
     *
     * @param key key
     * @return
     */
    public Long del(final String key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.del(key));
            });
        } catch (Exception e) {
            LOG.error("del is error ,key=" + key, e);
            throw new ServiceException(e, "del is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "del ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * 批量删除key的数据
     *
     * @param key key
     * @return
     */
    public Long batchDel(final String... key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.del(key));
            });
        } catch (Exception e) {
            LOG.error("batchDel is error ,key=" + key, e);
            throw new ServiceException(e, "batchDel is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "batchDel ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return
     */
    public Boolean exists(final String key) {
        Holder<String, Boolean> holder = new Holder<String, Boolean>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.exists(key));
            });
        } catch (Exception e) {
            LOG.error("exists is error ,key=" + key, e);
            throw new ServiceException(e, "exists is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "exists ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key的存储元素的个数
     *
     * @param key key
     * @return
     */
    public Long zcard(final String key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zcard(key));
            });
        } catch (Exception e) {
            LOG.error("zcard is error ,key=" + key, e);
            throw new ServiceException(e, "zcard is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zcard ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key的存储元素在一定范围内的个数
     *
     * @param key key
     * @return
     */
    public Long zcount(final String key, double min, double max) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zcount(key, min, max));
            });
        } catch (Exception e) {
            LOG.error("zcard is error ,key=" + key, e);
            throw new ServiceException(e, "zcard is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zcard ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key的存储元素的个数
     *
     * @param key key
     * @return
     */
    public Long scard(final String key) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.scard(key));
            });
        } catch (Exception e) {
            LOG.error("scard is error ,key=" + key, e);
            throw new ServiceException(e, "scard is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "scard ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key对应的集合列表
     *
     * @param key key
     *            start 开始值
     *            limit 查询数量
     * @return
     */
    public Set<String> zrevrange(final String key, final long start, final long limit) {
        Holder<String, Set<String>> holder = new Holder<String, Set<String>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrange(key, start, start + limit - 1));
            });
        } catch (Exception e) {
            LOG.error("zrevrange is error ,key=" + key + ",start=" + start + ",limit=" + limit, e);
            throw new ServiceException(e, "zrevrange is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrange ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key对应的集合列表
     *
     * @param key key
     * @return
     */
    public Long zadd(final String key, final long score, final String member) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zadd(key, score, member));
            });
        } catch (Exception e) {
            LOG.error("zadd is error ,key=" + key + ",score=" + score + ",member=" + member, e);
            throw new ServiceException(e, "zadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zadd ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key对应的集合列表
     *
     * @param key key
     * @return
     */
    public List<Object> zaddAndSetExpire(final String key, final long score, final String member, final int expire) {
        Holder<String, List<Object>> holder = new Holder<String, List<Object>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                pipeline.zadd(key, score, member);
                pipeline.expire(key, expire);
                holder.setValue(pipeline.syncAndReturnAll());
                // holder.setValue(jedis.zadd(key, score, member));
            });
        } catch (Exception e) {
            LOG.error("zaddAndSetExpire is error ,key=" + key + ",score=" + score + ",member=" + member, e);
            throw new ServiceException(e, "zadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zaddAndSetExpire ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key对应的添加后的数
     *
     * @param key key
     * @return
     */
    public Double zincrby(final String key, final double score, final String member) {
        Holder<String, Double> holder = new Holder<String, Double>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zincrby(key, score, member));
            });
        } catch (Exception e) {
            LOG.error("zincrby is error ,key=" + key + ",score=" + score + ",member=" + member, e);
            throw new ServiceException(e, "zadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zincrby ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回指定key对应的集合列表
     *
     * @param key key
     * @return
     */
    public Long zaddBatch(final String key, final Map<String, Double> scoreMembers) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zadd(key, scoreMembers));
            });
        } catch (Exception e) {
            LOG.error("zaddBatch is error ,key=" + key + ",scoreMembers=" + scoreMembers, e);
            throw new ServiceException(e, "zaddBatch is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zaddBatch ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回影响的数目
     *
     * @param key key
     * @return
     */
    public List<Object> zaddBatchAndSetExpre(final String key, final Map<String, Double> scoreMembers, int expireTime) {
        Holder<String, List<Object>> holder = new Holder<String, List<Object>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                pipeline.zadd(key, scoreMembers);
                pipeline.expire(key, expireTime);
                holder.setValue(pipeline.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("zaddBatchAndSetExpre is error ,key=" + key + ",scoreMembers=" + scoreMembers, e);
            throw new ServiceException(e, "zaddBatchAndSetExpre is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zaddBatchAndSetExpre ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回影响的数目
     *
     * @param < List<Map<String,String>> members
     * @return
     */
    public List<Object> hmsetBatchAndSetExpre(final List<Map<String, String>> members) {
        Holder<String, List<Object>> holder = new Holder<String, List<Object>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                for (Map<String, String> map : members) {
                    String key = map.get("redis_key");
                    int expireTime = Integer.parseInt(map.get("expire"));
                    pipeline.hmset(key, map);
                    pipeline.expire(key, expireTime);
                }
                List<Object> objects = pipeline.syncAndReturnAll();
                holder.setValue(objects);
            });
        } catch (Exception e) {

            throw new ServiceException(e, "hmsetBatchAndSetExpre is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hmsetBatchAndSetExpre", endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回影响的数目
     *
     * @param < List<Map<String,String>> members
     * @return
     */
    public List<List<String>> hmgetBatch(final List<String> keys) {
        List<Response<List<String>>> responses = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        //  Holder<String, List<List<String>>> holder = new Holder<String, List<List<String>>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                for (String key : keys) {
                    responses.add(pipeline.hmget(key, "userName", "productType", "expireTime"));
                }
                pipeline.sync();
            });
        } catch (Exception e) {
            throw new ServiceException(e, "hmsetBatchAndSetExpre is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hmgetBatch", endTime - startTime);
        }
        for (Response<List<String>> response : responses) {
            result.add(response.get());
        }
        return result;
    }

    /**
     * 返回指定key对应的集合列表
     *
     * @param key key
     * @return
     */
    public Long zadd(final String key, final double score, final String member) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zadd(key, score, member));
            });
        } catch (Exception e) {
            LOG.error("zadd is error ,key=" + key + ",score=" + score + ",member=" + member, e);
            throw new ServiceException(e, "zadd is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zadd ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回有序集中指定分数区间内的所有的成员
     * <li>返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。</li><br/>
     * <li>有序集成员按 score 值递减(从大到小)的次序排列。</li><br/>
     * <li>具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。</li>
     *
     * @param key key
     *            max 最大
     *            min 最小
     * @return
     */
    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrangeByScore(key, max, min));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回有序集中指定分数区间内的所有的成员
     * 有序集成员按分数值递减(从大到小)的次序排列。
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @param key key
     *            max 最大
     *            min 最小
     *            * @param offset 从此开始
     *            * @param count  查几个
     * @return
     */
    public Set<String> zrevrangeByScoreLimit(final String key, final double max, final double min, final int offset, final int count) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrangeByScore(key, max, min, offset, count));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回有序集中指定分数区间内的所有的成员
     * 有序集成员按分数值递减(从小到大)的次序排列。
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @param key key
     *            max 最大
     *            min 最小
     *            * @param offset 从此开始
     *            * @param count  查几个
     * @return
     */
    public Set<String> zrangeByScore(final String key, final double max, final double min, final int offset, final int count) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrangeByScore(key, max, min, offset, count));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 返回有序集中指定分数区间内的所有的成员
     * 有序集成员按分数值递减(从小到大)的次序排列。
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @param key key
     *            max 最大
     *            min 最小
     * @return
     */
    public Set<String> zrangeByScore(final String key, final double max, final double min) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrangeByScore(key, max, min));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max,
                                                 final double min) {
        Holder<String, Set<Tuple>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrangeByScoreWithScores(key, max, min));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public long zremrangeByScore(final String key, final double min, final double max) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zremrangeByScore(key, min, max));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeByScore is error ,key=" + key + ",max=" + max + ",min=" + min, e);
            throw new ServiceException(e, "zrevrangeByScore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeByScore ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public String lpop(final String ketStr) {
        Holder<String, String> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.lpop(ketStr));
            });
        } catch (Exception e) {
            LOG.error("lpop is error ,key=" + ketStr, e);
            throw new ServiceException(e, "lpop is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "lpop ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }

    public String rpop(final String ketStr) {
        Holder<String, String> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.rpop(ketStr));
            });
        } catch (Exception e) {
            LOG.error("rpop is error ,key=" + ketStr, e);
            throw new ServiceException(e, "rpop is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "rpop ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * <li>移除有序集 key 中，指定排名(rank)区间内的所有成员。</li> <br/>
     * <li>区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内。 下标参数 start 和 stop 都以
     * 0为底，也就是说，以 0 表示有序集第一个成员， 以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员，
     * -2 表示倒数第二个成员，以此类推。</li>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zremrangeByRank(final String key, final int start, final int end) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zremrangeByRank(key, start, end));
            });
        } catch (Exception e) {
            LOG.error("zremrangeByRank is error ,key=" + key + ",start=" + start + ",end=" + end,
                    e);
            throw new ServiceException(e, "zremrangeByRank is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zremrangeByRank ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Long hdel(final String key, final String... field) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hdel(key, field));
            });
        } catch (Exception e) {
            LOG.error("hdel is error ,key=" + key + ",field=" + field, e);
            throw new ServiceException(e, "hdel is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hdel ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     *
     * @param key
     * @param member
     * @return
     */
    public long zrem(final String key, final String... member) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrem(key, member));
            });
        } catch (Exception e) {
            LOG.error("zrem is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "zrem is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrem ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     *
     * @param key
     * @param member
     * @return
     */
    public long zremBatch(final String key, final String... member) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrem(key, member));
            });
        } catch (Exception e) {
            LOG.error("zrem is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "zrem is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrem ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * <li>返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。</li><br/>
     * <li>有序集成员按 score 值递减(从大到小)的次序排列。</li><br/>
     * <li>具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。</li>
     *
     * @param key
     * @param start
     * @param limit
     * @return
     */
    public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int limit) {

        Holder<String, Set<Tuple>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrangeWithScores(key, start, start + limit - 1));
            });
        } catch (Exception e) {
            LOG.error("zrevrangeWithScores is error ,key=" + key + ",start=" + start + ",limit="
                    + limit, e);
            throw new ServiceException(e, "zrevrangeWithScores is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeWithScores ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * zrangeWithScores 方法
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {

        Holder<String, Set<Tuple>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrangeWithScores(key, start, end));
            });
        } catch (Exception e) {
            LOG.error("zrangeWithScores is error ,key=" + key + ",start=" + start + ",end="
                    + end, e);
            throw new ServiceException(e, "zrangeWithScores is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrangeWithScores ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * zrange 方法
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(final String key, final int start, final int end) {

        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrange(key, start, end));
            });
        } catch (Exception e) {
            LOG.error("zrangeWithScores is error ,key=" + key + ",start=" + start + ",end="
                    + end, e);
            throw new ServiceException(e, "zrangeWithScores is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrangeWithScores ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * 根据ke获取接口，排序获取查询接口
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return
     */
    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {

        Holder<Object, Set<Tuple>> holder = new Holder<Object, Set<Tuple>>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrangeWithScores(key, start, end));
            });
        } catch (Exception e) {
            LOG.error(
                    "zrevrangeWithScores is error ,key=" + key + ",start=" + start + ",end=" + end,
                    e);
            throw new ServiceException(e, "zrevrangeWithScores is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrangeWithScores ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    public Long lpush(final String ketStr, final String val, final boolean checkExist) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                if (checkExist) {
                    if (jedis.exists(ketStr)) {
                        holder.setValue(jedis.lpush(ketStr, val));
                    }
                } else {
                    holder.setValue(jedis.lpush(ketStr, val));
                }
            });
        } catch (Exception e) {
            LOG.error("lpush is error ,key=" + ketStr + ",val=" + val + ",checkExist=" + checkExist,
                    e);
            throw new ServiceException(e, "lpush is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "lpush ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }

    public Long rpush(final String ketStr, final String... val) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.rpush(ketStr, val));
            });
        } catch (Exception e) {
            LOG.error("rpush is error ,key=" + ketStr + ",val=" + GsonUtils.toJson(val), e);
            throw new ServiceException(e, "rpush is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "rpush ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }


    public List<String> hmget(final String key, final String... fields) {
        Holder<String, List<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hmget(key, fields));
            });
        } catch (Exception e) {
            LOG.error("hmget is error ,key=" + key + ",fields=" + fields, e);
            throw new ServiceException(e, "hmget is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hmget ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Long rpush(final String ketStr, final String val) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.rpush(ketStr, val));
            });
        } catch (Exception e) {
            LOG.error("rpush is error ,key=" + ketStr + ",val=" + val, e);
            throw new ServiceException(e, "rpush is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "rpush ,key=" + ketStr, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * 获取key剩余有效期
     *
     * @param key
     * @return
     */
    public Long ttl(final String key) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.ttl(key));
            });
        } catch (Exception e) {
            LOG.error("ttl is error ,key=" + key, e);
            throw new ServiceException(e, "ttl is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "ttl ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * hash保存数据并且设置有效期
     *
     * @param key key
     * @return
     */
    public void hmsetExpire(final String key, Map<String, String> map, int expireTime) {
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                jedis.hmset(key, map);
                jedis.expire(key, expireTime);
            });
        } catch (Exception e) {
            LOG.error("hmsetExpire is error ,key=" + key + ",map=" + map + ",expireTime="
                    + expireTime, e);
            throw new ServiceException(e, "hmsetExpire is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hmsetExpire ,key=" + key, endTime - startTime);
        }
    }

    /**
     * 批量保存数据
     *
     * @param keysValues
     * @return
     */
    public void mset(final String... keysValues) {
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                jedis.mset(keysValues);
            });
        } catch (Exception e) {
            LOG.error("mset is error ,keysValues=" + GsonUtils.toJson(keysValues), e);
            throw new ServiceException(e, "mset is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "mset ,keysValues=" + GsonUtils.toJson(keysValues), endTime - startTime);
        }
    }

    /**
     * 批量设置key并设置过期时间
     *
     * @param msetBeanList
     */
    public void msetExpire(final List<MsetBean> msetBeanList) {
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                for (MsetBean msetBean : msetBeanList) {
                    pipeline.setex(msetBean.getKey(), msetBean.getExpire(), msetBean.getValue());
                }
                pipeline.sync();
            });
        } catch (Exception e) {
            LOG.error("msetExpire is error ,msetBeanList=" + GsonUtils.toJson(msetBeanList), e);
            throw new ServiceException(e, "msetExpire is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "msetExpire ,msetBeanList=" + GsonUtils.toJson(msetBeanList), endTime - startTime);
        }
    }

    /**
     * 批量设置key的有效期
     *
     * @param keys
     * @return
     */
    public void expires(int expire, final List<String> keys) {
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                for (String key : keys) {
                    pipeline.expire(key, expire);
                }
                pipeline.sync();
            });
        } catch (Exception e) {
            LOG.error("expires is error ,expire=" + expire + ",keys=" + keys, e);
            throw new ServiceException(e, "mset is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "mset ,expire=" + expire + ",keys=" + keys, endTime - startTime);
        }
    }

    /**
     * hash保存数据
     *
     * @param key key
     * @return
     */
    public void hmset(final String key, Map<String, String> map) {
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                jedis.hmset(key, map);
            });
        } catch (Exception e) {
            LOG.error("hmset is error ,key=" + key + ",map=" + map, e);
            throw new ServiceException(e, "hmset is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hmset ,key=" + key, endTime - startTime);
        }
    }

    /**
     * hash 数量增加
     *
     * @param key   key
     * @param field 列
     * @param num   值
     * @return
     */
    public Double hincrByFloat(final String key, final String field, final double num) {
        Holder<String, Double> holder = new Holder<String, Double>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.hincrByFloat(key, field, num));

            });
        } catch (Exception e) {
            LOG.error("hincrByFloat is error ,key=" + key + ",field=" + field + ",num=" + num, e);
            throw new ServiceException(e, "hincrByFloat is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hincrByFloat ,key=" + key, endTime - startTime);
        }
        return holder.getValue();

    }

    /**
     * 批量增加hash结构中数字的值
     *
     * @param key  hash结构的key
     * @param data 需要更新的值
     * @return 更新后的集合
     */
    public List<Object> hincrByFloatBatch(final String key, final TreeMap<String, Double> data) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                Pipeline pipeline = jedis.pipelined();
                params.put(CLIENT, jedis.getClient());
                for (String field : data.keySet()) {
                    pipeline.hincrByFloat(key, field, data.get(field));
                }
                holder.setValue(pipeline.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("hincrByFloatBatch error,key:{},data:{}", key, GsonUtils.toJson(data));
            throw new ServiceException(e, "hincrByFloatBatch is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hincrByFloat ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 批量增加hash结构中数字的值，保证返回值有顺序
     *
     * @param key  hash结构的key
     * @param data 需要更新的值
     * @return 更新后的集合
     */
    public List<Object> hincrByFloatBatchOrder(final String key, final LinkedHashMap<String, Double> data) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                Pipeline pipeline = jedis.pipelined();
                params.put(CLIENT, jedis.getClient());
                data.forEach((field, val) -> pipeline.hincrByFloat(key, field, val));
                holder.setValue(pipeline.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("hincrByFloatBatchOrder error,key:{},data:{}", key, GsonUtils.toJson(data));
            throw new ServiceException(e, "hincrByFloatBatchOrder is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "hincrByFloat ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Set<String> smembers(final String key) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.smembers(key));
            });
        } catch (Exception e) {
            LOG.error("smembers is error ,key=" + key, e);
            throw new ServiceException(e, "smembers is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "smembers ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    public Set<String> sdiff(String... key) {
        Holder<String, Set<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.sdiff(key));
            });
        } catch (Exception e) {
            LOG.error("smembers is error ,key=" + key, e);
            throw new ServiceException(e, "smembers is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "smembers ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * llen 获取list长度
     *
     * @param key
     * @return
     */
    public Long llen(final String key) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.llen(key));
            });
        } catch (Exception e) {
            LOG.error("llen is error ,key=" + key, e);
            throw new ServiceException(e, "llen is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "llen ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * lrem
     *
     * @param key
     * @return
     */
    public Long lrem(final String key, final int count, final String member) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.lrem(key, count, member));
            });
        } catch (Exception e) {
            LOG.error("lrem is error ,key=" + key + ",member=" + member + ",count=" + count, e);
            throw new ServiceException(e, "lrem is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "lrem ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * lremBatch
     *
     * @param map
     * @return
     */
    public List<Object> lremBatch(Map<String, String> map) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                map.entrySet().forEach(p -> pl.lrem(p.getKey(), 0, p.getValue()));
                holder.setValue(pl.syncAndReturnAll());

            });
        } catch (Exception e) {
            LOG.error("lremBatch is error ,map=" + GsonUtils.toJson(map), e);
            throw new ServiceException(e, "lrem is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "lremBatch ,map=" + GsonUtils.toJson(map), endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * list 批量添加
     *
     * @param map
     * @return
     */
    public List<Object> rpushBatch(Map<String, String> map) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                map.entrySet().forEach(p -> pl.rpush(p.getKey(), p.getValue()));
                holder.setValue(pl.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("rpushBatch is error ,map=" + GsonUtils.toJson(map), e);
            throw new ServiceException(e, "rpushBatch is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "rpushBatch ,map=" + GsonUtils.toJson(map), endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * lrange
     *
     * @param key
     * @return
     */
    public List<String> lrange(final String key, final long start, final long end) {
        Holder<String, List<String>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.lrange(key, start, end));
            });
        } catch (Exception e) {
            LOG.error("lrange is error ,key=" + key + ",start=" + start + ",end=" + end, e);
            throw new ServiceException(e, "lrange is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "lrange ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取bit数据
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean getBit(final String key, final long offset) {
        Holder<String, Boolean> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(new Boolean(jedis.getbit(key, offset)));
            });
        } catch (Exception e) {
            LOG.error("getBit is error ,key=" + key + ",offset=" + offset, e);
            throw new ServiceException(e, "getBit is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "getBit ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 批量获取bit数据
     *
     * @param key redis的key
     * @param sId 开始位
     * @param eId 结束位
     * @return 集合
     */
    public List<Object> getBitsByPipeline(final String key, final long sId, final long eId) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                for (long i = sId; i <= eId; i++) {
                    pl.getbit(key, i);
                }
                holder.setValue(pl.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("getBitsByPipeline is error ,key=" + key + ",sId=" + sId + ",eId=" + eId, e);
            throw new ServiceException(e, "getBitsByPipeline is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "getBitsByPipeline ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * 批量获取bit数据
     *
     * @param key    redis的key
     * @param offset
     * @return 集合
     */
    public List<Object> getBitsByPipeline(final String key, final List<String> offset, int num) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                for (int i = 0; i < offset.size(); i++) {
                    pl.getbit(key, Integer.parseInt(offset.get(i)) + num);
                }
                holder.setValue(pl.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error(
                    "getBitsByPipeline is error ,key=" + key + ",offset=" + offset + ",num=" + num,
                    e);
            throw new ServiceException(e, "getBitsByPipeline is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "getBitsByPipeline ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取位
     *
     * @param key redisKey
     * @return byte[]
     */
    public byte[] getBits(final String key) {
        Holder<String, byte[]> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.get(key.getBytes()));
            });
        } catch (Exception e) {
            LOG.error("getBits is error ,key=" + key, e);
            throw new ServiceException(e, "getBits is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "getBits ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 批量设置位图
     *
     * @param key redisKey
     * @param sId 起始位
     * @param eId 结束位
     * @return 结果
     */
    public List<Object> setBitsByPipeline(final String key, final long sId, final long eId) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                for (long i = sId; i <= eId; i++) {
                    pl.setbit(key, i, true);
                }
                holder.setValue(pl.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("setBitsByPipeline is error ,key=" + key + ",sId=" + sId + ",eId=" + eId, e);
            throw new ServiceException(e, "setBitsByPipeline is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "setBitsByPipeline ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 设置位图值
     *
     * @param key    redisKey
     * @param offset 位
     * @param value  值
     * @return
     */
    public boolean setBit(final String key, final long offset, final boolean value) {
        Holder<String, Boolean> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                boolean result = jedis.setbit(key, offset, value);
                holder.setValue(result);
            });
        } catch (Exception e) {
            LOG.error("setBit is error ,key=" + key + ",offset=" + offset + ",value=" + value, e);
            throw new ServiceException(e, "setBit is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "setBit ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 设置位图
     *
     * @param key     redisKey
     * @param offsets 集合
     * @return
     */
    public List<Object> setBitsByPipeline(final String key, final List<Long> offsets) {
        Holder<String, List<Object>> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute(jedis -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pl = jedis.pipelined();
                for (int i = 0; i < offsets.size(); i++) {
                    pl.setbit(key, offsets.get(i), true);
                }
                holder.setValue(pl.syncAndReturnAll());
            });
        } catch (Exception e) {
            LOG.error("setBitsByPipeline is error ,key=" + key + ",offset=" + offsets, e);
            throw new ServiceException(e, "setBitsByPipeline is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "setBitsByPipeline ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 存储数据到缓存中，并制定过期时间和当Key存在时是否覆盖。
     *
     * @param key
     * @param value
     * @param nxxx  nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
     * @param expx  expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
     * @param time  过期时间，单位是expx所代表的单位。
     * @return
     */
    public String set(final String key, final String value, String nxxx, String expx,
                      final int time) {
        Holder<String, String> holder = new Holder<String, String>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.set(key, value, nxxx, expx, time));
            });
        } catch (Exception e) {
            LOG.error("set is error ,key=" + key + ",value=" + value + ",nxxx=" + nxxx + ",expx="
                    + expx + ",time=" + time, e);
            throw new ServiceException(e, "set is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "set ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取key，对应数据对应的分数
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(final String key, final String member) {
        Holder<Object, Double> holder = new Holder<Object, Double>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zscore(key, member));
            });
        } catch (Exception e) {
            LOG.error("zscore is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "zscore is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zscore ,key=" + key + ",member=" + member, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取zset顺序排名：由小到大
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrank(final String key, final String member) {
        Holder<Object, Long> holder = new Holder<Object, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrank(key, member));
            });
        } catch (Exception e) {
            LOG.error("zrank is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "zrank is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrank ,key=" + key + ",member=" + member, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 获取zset逆序排名：由大到小
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(final String key, final String member) {
        Holder<Object, Long> holder = new Holder<Object, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.zrevrank(key, member));
            });
        } catch (Exception e) {
            LOG.error("zrevrank is error ,key=" + key + ",member=" + member, e);
            throw new ServiceException(e, "zrevrank is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "zrevrank ,key=" + key + ",member=" + member, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * incrby并设置过期时间
     *
     * @param key    key
     * @param num    增加的值
     * @param expire 过期时间
     * @return
     */
    public long incrbyWithExpire(String key, final long num, final int expire) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                Response<Long> value = pipeline.incrBy(key, num);
                pipeline.expire(key, expire);
                pipeline.sync();
                holder.setValue(value.get());
            });
        } catch (Exception e) {
            LOG.error("incrBy is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "incrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "incrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }


    /**
     * decrby并设置过期时间
     *
     * @param key    key
     * @param num    减少的值
     * @param expire 过期时间
     * @return
     */
    public long decrByWithExpire(String key, final long num, final int expire) {
        Holder<String, Long> holder = new Holder<>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                Pipeline pipeline = jedis.pipelined();
                Response<Long> value = pipeline.decrBy(key, num);
                pipeline.expire(key, expire);
                pipeline.sync();
                holder.setValue(value.get());
            });
        } catch (Exception e) {
            LOG.error("decrBy is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "decrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params), "decrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

    /**
     * 对应key减少数量
     *
     * @param key key
     * @param num 减少的值
     * @return
     */
    public long decrBy(final String key, final long num) {
        Holder<String, Long> holder = new Holder<String, Long>();
        long startTime = System.currentTimeMillis();
        Map<String, Client> params = new HashMap<>();
        try {
            codisClient.execute((jedis) -> {
                params.put(CLIENT, jedis.getClient());
                holder.setValue(jedis.decrBy(key, num));
            });
        } catch (Exception e) {
            LOG.error("decrBy is error ,key=" + key + ",num=" + num, e);
            throw new ServiceException(e, "decrBy is error");
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.logRedisResTime(getHots(params), getPort(params), getDb(params),
                    "decrBy ,key=" + key, endTime - startTime);
        }
        return holder.getValue();
    }

}

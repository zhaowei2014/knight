package com.zw.knight.config.redis;

import com.google.gson.annotations.SerializedName;
import com.zhangyue.common2.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * redis错误日志
 *
 * @author hexiaosong
 * @date 2019-04-16
 */
public class RedisErrorLog {

    /**
     * 项目名
     */
    private String project;

    /**
     * 名字空间
     */
    private String namespace;

    /**
     * ip：存在没有名字空间的情况，有名字空间时，该字段为空
     */
    private String host;

    /**
     * 端口：存在没有名字空间的情况，有名字空间时，该字段为空
     */
    private int port;

    /**
     * redis db
     */
    private int db;

    /**
     * redis操作列表
     */
    @SerializedName(value = "op_list")
    private List<RedisOp> opList;

    /**
     * 时间，格式：yyyy-MM-dd HH:mm:ss
     */
    private String time;

    public RedisErrorLog(String project, String namespace, int db) {
        this.project = project;
        this.namespace = namespace;
        this.db = db;
        this.opList = new ArrayList<>();
        this.time = DateUtils.getDateStr(new Date(), DateUtils.DEFAULT_PATTERN);
    }

    public RedisErrorLog(String project, String host, int port, int db) {
        this.project = project;
        this.host = host;
        this.port = port;
        this.db = db;
        this.opList = new ArrayList<>();
        this.time = DateUtils.getDateStr(new Date(), DateUtils.DEFAULT_PATTERN);
    }

    /**
     * 常用：zadd操作bean
     *
     * @param key    redis key
     * @param score  score
     * @param member member
     */
    public void addZaddOp(String key, long score, String member) {
        // 不存在并发
        if (null == opList) {
            opList = new ArrayList<>();
        }
        opList.add(new RedisOp("zadd", key, score, member));
    }

    /**
     * 常用：lpush操作bean
     *
     * @param key   redis key
     * @param value 值
     */
    public void addLpushOp(String key, String value) {
        if (null == opList) {
            opList = new ArrayList<>();
        }
        opList.add(new RedisOp("rpush", key, value));
    }

    /**
     * redis操作封装类
     */
    public class RedisOp {

        /**
         * redis命令
         */
        private String op;

        /**
         * redis key
         */
        private String key;

        /**
         * 参数列表
         */
        private List args;

        public RedisOp(String op, String key, Object... args) {
            this.op = op;
            this.key = key;
            this.args = Arrays.asList(args);
        }

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List getArgs() {
            return args;
        }

        public void setArgs(List args) {
            this.args = args;
        }
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public List<RedisOp> getOpList() {
        return opList;
    }

    public void setOpList(List<RedisOp> opList) {
        this.opList = opList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

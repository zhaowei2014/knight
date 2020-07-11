package com.zw.knight.config.hbase;

/**
 * Hbase ZK 配置
 *
 *
 * @author WangYu
 * @date 2017/11/9
 */
public class QuorumConfig {
    private String quorum;
    private String port;
    public QuorumConfig(){

    }
    public QuorumConfig(String quorum, String port){
        this.quorum = quorum;
        this.port = port;
    }
    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

package com.zw.knight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * hbase配置
 *
 * @author xqh
 * @date 2020/4/24
 */
@Configuration
@PropertySource("classpath:config/hbase.properties")
public class HbaseConfig {

    @Value("${write.buffer.size}")
    private String writeBufferSize;

    @Value("${scanner.caching}")
    private String scannerCachingSize;

    @Value("${pool.size}")
    private int poolSize;

    @Value("${hbase.asset.namespace}")
    private String hbaseNamespace;

    @Value("${hbase.client.operation.timeout}")
    private String operationTimeout;

    @Value("${hbase.client.scanner.timeout.period}")
    private String scanTimeout;

    @Value("${hbase.rpc.timeout}")
    private String rpcTimeout;

    @Value("${hbase.client.retries.number}")
    private String retryNum;

    @Value("${hbase.client.pause}")
    private String clientPause;

    @Value("${zookeeper.znode.parent}")
    private String zookeeperNodeParent;

    @Value("${hbase.env}")
    private String hbaseEnv;

    /*@Bean(name = "hbaseTemplate")
    public HbasePoolTemplate getHbaseTemplate() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        QuorumConfig config = NameSpaceUtils.getHbaseQuorumConfig(hbaseNamespace, nsClient);
        configuration.set(HbaseConstants.ZOOKEEPER_QUORUM, config.getQuorum());
        configuration.set(HbaseConstants.ZOOKEEPER_CLIENT_PORT, config.getPort());
        // 150开发环境，ZOOKEEPER_NODE_PARENT该配置需注释
        if (!hbaseEnv.equals("dev")) {
            configuration.set(HbaseConstants.ZOOKEEPER_NODE_PARENT, zookeeperNodeParent);
        }
        configuration.set(HbaseConstants.CLIENT_WRITE_BUFFER, writeBufferSize);
        configuration.set(HbaseConstants.CLIENT_SCANNER_CACHING, scannerCachingSize);
        configuration.set(HbaseConstants.CLIENT_OPERATION_TIMEOUT, operationTimeout);
        configuration.set(HbaseConstants.CLIENT_SCAN_TIMEOUT, scanTimeout);
        configuration.set(HbaseConstants.CLIENT_SCAN_RPC_TIMEOUT, rpcTimeout);
        configuration.set(HbaseConstants.CLIENT_RETRIES_NUMBER, retryNum);
        configuration.set(HbaseConstants.CLIENT_PAUSE, clientPause);
        return new HbasePoolTemplate(configuration, poolSize);
    }*/
}

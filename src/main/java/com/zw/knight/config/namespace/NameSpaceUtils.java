package com.zw.knight.config.namespace;

import com.zw.knight.config.hbase.QuorumConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 域名空间工具
 *
 * @author WangYu
 * @date 2017/11/6
 */
public class NameSpaceUtils {
    private static final Logger LOG = LoggerFactory.getLogger(NameSpaceUtils.class);
    //主库
    public static String DB_MASTER = "master";
    //从库
    public static String DB_SLAVE = "slave";

    public static List<NodeConfig> getIPPortList(String namespace, NSClient nsClient) {
        if (StringUtils.isNotBlank(namespace) && nsClient != null) {
            List<NodeConfig> list = nsClient.getServices(namespace);
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyList();
            }
            return list.stream()
                    .filter(config -> config != null && StringUtils.isNotBlank(config.getHost())
                            && config.getPort() > 0).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static Optional<NodeConfig> getIPPort(String namespace, NSClient nsClient) {
        if (StringUtils.isNotBlank(namespace) && nsClient != null) {
            NodeConfig nodeConfig = nsClient.getService(namespace, "/");
            if (nodeConfig != null && StringUtils.isNotBlank(nodeConfig.getHost())
                    && nodeConfig.getPort() > 0) {
                return Optional.of(nodeConfig);
            } else {
                LOG.warn("namespace [{}] is null!", namespace);
            }
        }
        return Optional.empty();
    }

    public static NodeConfig getNodeConfig(String namespace, NSClient nsClient) {
        if (StringUtils.isNotBlank(namespace) && nsClient != null) {
            NodeConfig nodeConfig = nsClient.getService(namespace, "/");
            if (nodeConfig != null && StringUtils.isNotBlank(nodeConfig.getHost())
                    && nodeConfig.getPort() > 0) {
                return nodeConfig;
            } else {
                LOG.warn("namespace [{}] is null!", namespace);
            }
        }
        return null;
    }

    public static String getMysqlUrlForNameSpace(String url, String namespace, NSClient nsClient) {
        if (StringUtils.isNotBlank(url) && null != nsClient) {
            NodeConfig nodeConfig = nsClient.getService(namespace, "/");
            if (nodeConfig != null && StringUtils.isNotBlank(nodeConfig.getHost())
                    && nodeConfig.getPort() > 0) {
                String ipPort = nodeConfig.toString();
                return url.replace(namespace, ipPort);
            } else {
                LOG.warn("namespace [{}] is null!", namespace);
            }
        }
        return url;
    }

    /**
     * mysql 配置，支持主从
     *
     * @param url       mysql 的url
     * @param namespace mysql的域名空间
     * @param nsClient
     * @param type      主库： master  从库： slave
     * @return
     */
    public static String getMysqlUrlForNameSpace(String url, String namespace, NSClient nsClient,
                                                 String type) {
        if (StringUtils.isNotBlank(url) && null != nsClient) {
            NodeConfig nodeConfig;
            if (DB_SLAVE.equals(type)) {
                nodeConfig = nsClient.getSlaveService(namespace);
            } else {
                nodeConfig = nsClient.getMasterService(namespace);
            }
            if (nodeConfig != null && StringUtils.isNotBlank(nodeConfig.getHost())
                    && nodeConfig.getPort() > 0) {
                String ipPort = nodeConfig.toString();
                return url.replace(namespace, ipPort);
            } else {
                LOG.warn("namespace [{}] is null!", namespace);
            }
        }
        return url;
    }

    /**
     * memcached获取批量的域名配置
     *
     * @param namespace
     * @param client
     * @return
     */
    public static List<InetSocketAddress> getMemcachedConfig(String namespace, NSClient client) {
        List<InetSocketAddress> socketAddresses = new ArrayList<>();
        List<NodeConfig> config = client.getServices(namespace);
        if (CollectionUtils.isNotEmpty(config)) {
            config.stream().forEach(nodeConfig -> {
                if (nodeConfig != null && StringUtils.isNotBlank(nodeConfig.getHost())
                        && nodeConfig.getPort() > 0) {
                    socketAddresses
                            .add(new InetSocketAddress(nodeConfig.getHost(), nodeConfig.getPort()));
                }
            });
        }
        return socketAddresses;
    }

    public static QuorumConfig getHbaseQuorumConfig(String namespace, NSClient nsClient) {
        List<NodeConfig> list = getIPPortList(namespace, nsClient);
        List<String> ips = new ArrayList<>();
        Map<String, String> ports = new HashMap<>();
        list.forEach(config -> {
            ips.add(config.getHost());
            ports.put("port", String.valueOf(config.getPort()));
        });
        String quorum = StringUtils.join(ips, ",");
        return new QuorumConfig(quorum, ports.get("port"));
    }
}

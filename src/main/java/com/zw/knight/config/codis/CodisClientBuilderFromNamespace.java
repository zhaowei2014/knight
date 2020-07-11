/*
 * Copyright (c) 2017.掌阅科技
 * All rights reserved.
 */

/*
 * Copyright (c) 2017 掌阅科技. All rights reserved.
 */
package com.zw.knight.config.codis;

import com.zhangyue.arch.nsc.NSClient;
import com.zhangyue.arch.nsc.config.NodeConfig;
import com.zhangyue.common2.exception.ErrorStatus;
import com.zhangyue.common2.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 适用于Namespace获取配置的codis入口
 *
 * @author yangming
 * @date 2017年12月13日 上午9:45:11
 */
public class CodisClientBuilderFromNamespace extends CodisClientBuilder {
    private static final Logger logger =
            LoggerFactory.getLogger(CodisClientBuilderFromNamespace.class);

    private NSClient nsClient;

    /**
     * eg:/cache2
     */
    private String clusterName;

    private String namespace;

    private final static String SERVICE_TPL = "{0}_{1}";
    private final static String CODISPROXY_TPL = "redis://{0}:{1}/{2}?weight={3}";

    @Override
    public CodisClientBuilder parsePathConfig() {
        List<NodeConfig> services = new ArrayList<NodeConfig>();
        try {
            services = nsClient.getServices(namespace);
        } catch (Exception e) {
            logger.error("Can not get Qconf", e);
            throw new ServiceException(ErrorStatus.QCONF_CONNECTION_EXCEPTION,
                    "Can not get Qconf!");
        }

        Map<String, String> confs = new HashMap<String, String>();
        if (services != null) {
            for (NodeConfig nodeConfig : services) {
                String service = MessageFormat.format(SERVICE_TPL, nodeConfig.getHost(),
                        nodeConfig.getPort() + "");
                String uri = MessageFormat.format(CODISPROXY_TPL, nodeConfig.getHost(),
                        nodeConfig.getPort() + "", namespace, nodeConfig.getWeight() + "");
                confs.put(service, uri);
            }
        }

        /* 每次构建前先清理下原有集合，解决周期性加载配置时老数据不能清除的bug(2017-02-15 by chenchao) */
        this.pathConfigList.clear();

        /* 加载新的配置内容 */
        for (String pathConfig : confs.values()) {
            if (getClusterNameFromKey(pathConfig).equals(clusterName)) {
                this.pathConfigList.add(pathConfig);
            }
        }
        return this;
    }

    /**
     * 获取clusterName
     *
     * @param key qconf配置的路径
     * @return clusterName
     *
     */
    private String getClusterNameFromKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        String url = key.replaceFirst("redis", "http");
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (StringUtils.isNoneEmpty(path)) {
                return path.replace("/", "");
            }

        } catch (Exception e) {
            logger.error("get codis conf:", e);
        }
        return key;
    }

    public CodisClientBuilderFromNamespace withNamespace(String namespace) {
        this.namespace = namespace;
        this.clusterName = namespace;
        return this;
    }

    public CodisClientBuilderFromNamespace withNSClient(NSClient nsClient) {
        this.nsClient = nsClient;
        return this;
    }

    public String getClusterName() {
        return clusterName;
    }
}

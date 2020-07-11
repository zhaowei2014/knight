package com.zw.knight.config.namespace;

import com.zw.knight.util.ZKUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class NSService {
    private static final Logger logger = LoggerFactory.getLogger(NSService.class);
    private static final String NAMESPACE_TPL = "/arch_group/zkapi/{0}";
    private static final String PROVIDERS_TPL = "/arch_group/zkapi/{0}/providers";
    private static final String CONSUMERS_TPL = "/arch_group/zkapi/{0}/consumers";
    private static final String SERVICE_PATH_TPL = "{0}/{1}";
    private static final String CONSUMER_TPL = "{0}_{1}";
    private TTLCache<String, Map<String, String>> ttlCache = new TTLCache(5000L);

    public NSService() {
    }

    public String getNamespacePath(String namespace) {
        return MessageFormat.format("/arch_group/zkapi/{0}", namespace);
    }

    public String getProvidersPath(String namespace) {
        return MessageFormat.format("/arch_group/zkapi/{0}/providers", namespace);
    }

    public String getConsumersPath(String namespace) {
        return MessageFormat.format("/arch_group/zkapi/{0}/consumers", namespace);
    }

    public String getServicePath(String path, String provider) {
        return MessageFormat.format("{0}/{1}", path, provider);
    }

    public NamespaceConfig getNamespaceConfig(String namespace) {
        if (StringUtils.isEmpty(namespace)) {
            return null;
        } else {
            String value = ZKUtil.getConf(this.getNamespacePath(namespace));
            NamespaceConfig namespaceConfig = new NamespaceConfig(value);
            return namespaceConfig;
        }
    }

    public List<NodeConfig> getServices(String namespace) {
        if (StringUtils.isEmpty(namespace)) {
            return null;
        } else {
            Map<String, String> nodes = this.getProvidersWithConfig(namespace);
            if (nodes != null && nodes.size() != 0) {
                List<NodeConfig> services = new ArrayList();
                Iterator entries = nodes.entrySet().iterator();

                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) entries.next();
                    String[] hostport = null;
                    boolean var7 = false;

                    int port;
                    try {
                        hostport = ((String) entry.getKey()).split("_", -1);
                        port = Integer.parseInt(hostport[1]);
                    } catch (Exception var10) {
                        logger.error(var10.getMessage(), var10);
                        continue;
                    }

                    String host = hostport[0];
                    NodeConfig nodeConfig = new NodeConfig(host, port, (String) entry.getValue());
                    if (nodeConfig.getDisable() == 0 && nodeConfig.getWeight() > 0) {
                        services.add(nodeConfig);
                    }
                }

                return services;
            } else {
                return null;
            }
        }
    }

    public String getProvider(String namespace, String provider) {
        return ZKUtil.getConf(this.getServicePath(this.getProvidersPath(namespace), provider));
    }

    public List<String> getProviders(String namespace) {
        return ZKUtil.getBatchKeys(this.getProvidersPath(namespace));
    }

    public Map<String, String> getProvidersWithConfig(String namespace) {
        Map<String, String> nodes = null;
        if (this.ttlCache.containsKey(namespace)) {
            nodes = (Map) this.ttlCache.get(namespace);
            if (nodes == null || nodes.size() == 0) {
                nodes = ZKUtil.getBatchConf(this.getProvidersPath(namespace));
                if (nodes != null && nodes.size() != 0) {
                    this.ttlCache.put(namespace, nodes);
                }
            }
        } else {
            nodes = ZKUtil.getBatchConf(this.getProvidersPath(namespace));
            if (nodes != null && nodes.size() != 0) {
                this.ttlCache.put(namespace, nodes);
            }
        }

        return nodes;
    }

    public boolean addConsumer(String namespace, String host, String port) {
        return ZKUtil.createZknode(namespace, MessageFormat.format("{0}_{1}", host, port));
    }

    public List<NodeConfig> filterServicesByUri(List<NodeConfig> services, String uri) {
        if ("/".equals(StringUtils.trim(uri))) {
            return null;
        } else {
            List<NodeConfig> filterServices = new ArrayList();
            Iterator var5 = services.iterator();

            while (var5.hasNext()) {
                NodeConfig service = (NodeConfig) var5.next();
                if (service.getUri().equals(StringUtils.trim(uri))) {
                    filterServices.add(service);
                }
            }

            return filterServices;
        }
    }

    public boolean isConsumerExist(String namespace, String host, String port) {
        List keys = null;

        try {
            keys = ZKUtil.getBatchKeys(this.getConsumersPath(namespace));
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return false;
        }

        return keys != null && keys.size() != 0 && keys.contains(MessageFormat.format("{0}_{1}", host, port));
    }
}


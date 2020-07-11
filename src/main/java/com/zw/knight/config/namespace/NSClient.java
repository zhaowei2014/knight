package com.zw.knight.config.namespace;

import com.zw.knight.util.IPUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class NSClient {
    private static final Logger logger = LoggerFactory.getLogger(NSClient.class);
    private String host = IPUtil.getFirstValidEthIP();
    private String port;
    private boolean isRegister = true;
    private NSService nsService = new NSService();
    //    private Balancer<NodeConfig> weightRandomBalancer = new WeightRandomBalancer();
    private Map<String, String> registertedNamespaces = new HashMap();
    private Map<String, List<NodeConfig>> defaultServiceLists = new HashMap();

    public NSClient() {
        this.port = System.getenv("ZYAGENT_HTTPPORT");
    }

    public NSClient(boolean isRegister) {
        this.port = System.getenv("ZYAGENT_HTTPPORT");
        this.isRegister = isRegister;
    }

    public NSClient(String port) {
        this.port = port;
    }

    public NSClient(String port, boolean isRegister) {
        this.port = port;
        this.isRegister = isRegister;
    }

    private boolean register(String namespace) {
        this.registertedNamespaces.put(namespace, this.host + "_" + this.port);
        return !namespace.endsWith(".tcp") ? false : this.nsService.addConsumer(namespace, this.host, this.port);
    }

    private boolean isRegistered(String namespace) {
        return this.registertedNamespaces.containsKey(namespace) ? true : this.nsService.isConsumerExist(namespace, this.host, this.port);
    }

    public boolean isMock(String namespace) {
        if (StringUtils.isEmpty(namespace)) {
            return false;
        } else {
            try {
                return this.nsService.getNamespaceConfig(namespace).isMock();
            } catch (Exception var3) {
                logger.error(var3.getMessage(), var3);
                return false;
            }
        }
    }

    public boolean isMock(List<String> namespaces) {
        if (namespaces != null && !namespaces.isEmpty()) {
            Iterator var3 = namespaces.iterator();

            while (var3.hasNext()) {
                String namespace = (String) var3.next();
                if (this.isMock(namespace)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public NodeConfig getService(String namespace, String uri) {
        if (StringUtils.isEmpty(namespace)) {
            return null;
        } else {
            if (this.isRegister && !this.isRegistered(namespace)) {
                this.register(namespace);
            }

            List services = null;

            try {
                services = this.nsService.getServices(namespace);
            } catch (Exception var6) {
                logger.error(var6.getMessage(), var6);
                if (this.defaultServiceLists.containsKey(namespace)) {
                    services = (List) this.defaultServiceLists.get(namespace);
                }
            }

            if (services != null && services.size() != 0) {
                List<NodeConfig> filterServices = this.nsService.filterServicesByUri(services, uri);
                NodeConfig nodeConfig = null;
//                if (filterServices != null && !filterServices.isEmpty()) {
//                    nodeConfig = (NodeConfig) this.weightRandomBalancer.choiceNode(filterServices);
//                } else {
//                    nodeConfig = (NodeConfig) this.weightRandomBalancer.choiceNode(services);
//                }

                this.defaultServiceLists.put(namespace, services);
                return nodeConfig;
            } else {
                return null;
            }
        }
    }

    public List<NodeConfig> getServices(String namespace) {
        if (StringUtils.isEmpty(namespace)) {
            return null;
        } else {
            if (this.isRegister && !this.isRegistered(namespace)) {
                this.register(namespace);
            }

            List services = null;

            try {
                services = this.nsService.getServices(namespace);
            } catch (Exception var4) {
                logger.error(var4.getMessage(), var4);
                if (this.defaultServiceLists.containsKey(namespace)) {
                    return (List) this.defaultServiceLists.get(namespace);
                }

                return null;
            }

            this.defaultServiceLists.put(namespace, services);
            return services;
        }
    }

    public List<NodeConfig> getServices(String namespace, String uri) {
        if (StringUtils.isEmpty(namespace)) {
            return null;
        } else {
            if (this.isRegister && !this.isRegistered(namespace)) {
                this.register(namespace);
            }

            List services = null;

            try {
                services = this.nsService.getServices(namespace);
            } catch (Exception var5) {
                logger.error(var5.getMessage(), var5);
                if (this.defaultServiceLists.containsKey(namespace)) {
                    return (List) this.defaultServiceLists.get(namespace);
                }

                return null;
            }

            List<NodeConfig> filterServices = this.nsService.filterServicesByUri(services, uri);
            this.defaultServiceLists.put(namespace, services);
            return filterServices != null && !filterServices.isEmpty() ? filterServices : services;
        }
    }

    public List<NodeConfig> getMasterServices(String namespace) {
        return this.getServices(namespace, "/master");
    }

    public NodeConfig getMasterService(String namespace) {
        return this.getService(namespace, "/master");
    }

    public List<NodeConfig> getSlaveServices(String namespace) {
        return this.getServices(namespace, "/slave");
    }

    public NodeConfig getSlaveService(String namespace) {
        return this.getService(namespace, "/slave");
    }

    public List<NodeConfig> getSlowServices(String namespace) {
        return this.getServices(namespace, "/slow");
    }

    public NodeConfig getSlowService(String namespace) {
        return this.getService(namespace, "/slow");
    }
}

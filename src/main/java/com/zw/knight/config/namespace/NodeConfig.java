package com.zw.knight.config.namespace;


import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeConfig implements Balanceable {
    private static final Logger logger = LoggerFactory.getLogger(NodeConfig.class);
    private String host;
    private int port;
    private int weight;
    private String uri;
    private int disable;
    Gson gson = new Gson();

    public NodeConfig(String host, int port, String json) {
        this.host = host;
        this.port = port;

        try {
            NodeConfig.Conf conf = (NodeConfig.Conf) this.gson.fromJson(json, NodeConfig.Conf.class);
            this.weight = conf.getWeight();
            this.uri = StringUtils.isNotBlank(conf.getUri()) ? conf.getUri() : "/";
            this.disable = conf.getDisable();
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
            this.weight = 1;
            this.uri = "/";
            this.disable = 0;
        }

    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public String toString() {
        return this.host + ":" + this.port;
    }

    class Conf {
        private int weight;
        private String uri;
        private int disable;

        Conf() {
        }

        public int getWeight() {
            return this.weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getUri() {
            return this.uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public int getDisable() {
            return this.disable;
        }

        public void setDisable(int disable) {
            this.disable = disable;
        }
    }
}

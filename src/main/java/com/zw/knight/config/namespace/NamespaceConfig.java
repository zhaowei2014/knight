package com.zw.knight.config.namespace;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class NamespaceConfig {
    private static final Logger logger = LoggerFactory.getLogger(NamespaceConfig.class);
    private boolean mock;
    Gson gson = new Gson();

    public NamespaceConfig(String json) {
        try {
            NamespaceConfig.Conf conf = (NamespaceConfig.Conf)this.gson.fromJson(json, NamespaceConfig.Conf.class);
            this.mock = conf.isMock();
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            this.mock = false;
        }

    }

    public boolean isMock() {
        return this.mock;
    }

    public void setMock(boolean mock) {
        this.mock = mock;
    }

    class Conf {
        private boolean mock = false;

        Conf() {
        }

        public boolean isMock() {
            return this.mock;
        }

        public void setMock(boolean mock) {
            this.mock = mock;
        }
    }
}

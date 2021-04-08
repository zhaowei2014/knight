package com.zw.knight.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class ZKUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZKUtil.class);
    private static Random random = new Random();
    private static final String ZKAPI_ADD_CONSUMER = "/v1/zkapi/consumers/{0}/{1}";
    private static final String ZKAPI_PATH = "/arch_group/zkapi/arch.zkapi.http/providers";

    public ZKUtil() {
    }

    private static String getZkapiHostport() {
        List<String> keys = getBatchKeys("/arch_group/zkapi/arch.zkapi.http/providers");
        if (keys != null && keys.size() != 0) {
            String key = (String) keys.get(random.nextInt(keys.size()));
            String[] hostport = key.split("_", -1);
            return MessageFormat.format("http://{0}:{1}", hostport[0], hostport[1]);
        } else {
            return null;
        }
    }

    private static String getZkapiConsumerUrl(String namespace, String consumer) {
        String zkapiUri = MessageFormat.format("/v1/zkapi/consumers/{0}/{1}", namespace, consumer);
        String zkapiHostport = getZkapiHostport();
        return zkapiHostport == null ? null : zkapiHostport + zkapiUri;
    }

    public static Boolean createZknode(String namespace, String consumer) {
        String url = null;

        try {
            url = getZkapiConsumerUrl(namespace, consumer);
        } catch (Exception var6) {
            logger.error("get ZkapiConsumerUrl error!", var6);
            return false;
        }

        if (url == null) {
            return false;
        } else {
//            Request request = Request.Post(url).connectTimeout(3000).socketTimeout(3000);
//            HttpResponse response = null;
//
//            try {
//                response = request.execute().returnResponse();
//                if (response.getStatusLine().getStatusCode() != 201) {
//                    logger.error("exec create failed, status_code:{}, namespace:{}, consumer:{}", new Object[]{response.getStatusLine().getStatusCode(), namespace, consumer});
//                    return false;
//                }
//            } catch (Exception var7) {
//                logger.error("exec request error!", var7);
//                return false;
//            }
            return true;
        }
    }

    public static String getConf(String path) {
        String value = null;
//        try {
//            value = Qconf.getConf(path);
//
//        } catch (Exception var3) {
//            throw new ZynscException("exec getConf error!", var3);
//        }
        return value;
    }

    public static List<String> getBatchKeys(String path) {
        ArrayList keys = null;
//        try {
//            keys = Qconf.getBatchKeys(path);
//
//        } catch (Exception var3) {
//            throw new ZynscException("exec getBatchKeys error!", var3);
//        }
        return keys;
    }

    public static Map<String, String> getBatchConf(String path) {
        Map conf = null;
//        try {
//            conf = Qconf.getBatchConf(path);
//        } catch (Exception var3) {
//            throw new ZynscException("exec getBatchConf error!", var3);
//        }
        return conf;
    }
}

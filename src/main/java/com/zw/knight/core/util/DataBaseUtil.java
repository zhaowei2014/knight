package com.zw.knight.core.util;

import com.zw.knight.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

public class DataBaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseUtil.class);

    /**
     * 获取shrding表名
     *
     * @param pTableName 原始表名
     * @param key        分表的key种子
     * @param mod        分表取模数
     * @return sharding后的表名, eg:user_account_58
     */
    public static String getShardingTable(String pTableName, String key, int mod) {
        if (StringUtils.isEmpty(pTableName)) {
            throw new ServiceException("pTableName is empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new ServiceException("key is empty");
        }
        String md5 = md5Key(key);
        return String.format("%s_%s", pTableName, Integer.parseInt(md5.substring(0, 2), 16) % mod);
    }

    /**
     * spring md5
     */
    private static String md5Key(String value) {
        try {
            // 16进制
            return DigestUtils.md5DigestAsHex(value.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("md5Key exception: ", e);
            throw new ServiceException("UnsupportedEncodingException");
        }
    }
}

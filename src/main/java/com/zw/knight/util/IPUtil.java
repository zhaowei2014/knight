package com.zw.knight.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class IPUtil {
    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);
    private static String[] ifNames = new String[]{"bond0", "team0", "eth0"};

    public IPUtil() {
    }

    public static String getFirstValidEthIP() {
        String x_ret = null;
        String[] var4;
        int var3 = (var4 = ifNames).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            String ifName = var4[var2];
            x_ret = getIp(ifName);
            if (StringUtils.isNotEmpty(x_ret)) {
                return x_ret;
            }
        }

        return x_ret;
    }

    public static String getIp(String sif) {
        String x_ret = "";

        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();

            while(true) {
                NetworkInterface netInterface;
                do {
                    if (!allNetInterfaces.hasMoreElements()) {
                        return x_ret;
                    }

                    netInterface = (NetworkInterface)allNetInterfaces.nextElement();
                } while(sif.compareToIgnoreCase(netInterface.getName()) != 0);

                Enumeration addresses = netInterface.getInetAddresses();

                while(addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress)addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return x_ret;
        }
    }
}

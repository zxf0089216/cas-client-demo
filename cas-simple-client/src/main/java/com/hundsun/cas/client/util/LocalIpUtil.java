package com.hundsun.cas.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

/**
 * 获取当前ip
 *
 * @author <a href="mailto:zhangxf21203@hundsun.com">zhangxf</a>
 * @version 1.0
 */
public class LocalIpUtil {
    private static Logger logger = LoggerFactory.getLogger(LocalIpUtil.class);

    private static final String WINDOWS = "WINDOWS";

    /**
     * 获取本地服务器ID地址
     *
     * @return 本机IP地址
     */
    public static String getLocalIp() {
        String os = System.getProperty("os.name").toUpperCase();
        if (os.contains(WINDOWS)) {
            return getWindowIp();
        } else {
            return getLinuxIP();
        }
    }

    /**
     * 获取Linux类型服务器IP地址
     *
     * @return
     */
    private static String getLinuxIP() {
        String address = "";
        Enumeration<NetworkInterface> allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;

            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();

                if (!netInterface.isUp() || netInterface.isLoopback() || netInterface.isVirtual()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();

                    if (ip.isLoopbackAddress()) {
                        continue;
                    }

                    if (ip != null && ip instanceof Inet4Address) {
                        address = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("linux获取本地IP出错", e);
        }
        return address;
    }

    private static String getWindowIp() {
        Enumeration allNetInterfaces;
        String ipLocalAddr = null;
        InetAddress ip;

        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

                if (!netInterface.isUp()
                        || netInterface.isLoopback()
                        || netInterface.isVirtual()
                        || netInterface.getDisplayName().indexOf("VMware") != -1) { // filter vmware interface
                    continue;
                }

                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();

                    if (ip != null && ip instanceof Inet4Address) {
                        ipLocalAddr = ip.getHostAddress();
                    }

                }
            }
        } catch (SocketException e) {
            logger.error("get windows ip error", e);
            return null;
        }

        return ipLocalAddr;
    }
}

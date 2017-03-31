package com.hundsun.cas.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

/**
 * 获取当前ip
 *
 * @author zhangxf
 * @version 1.0
 */
public class LocalIpUtil {
    private static Logger logger = LoggerFactory.getLogger(LocalIpUtil.class);

    private static final String WINDOWS = "WINDOWS";

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8080/client1";
////        String url = "http://localhost:8080/client1";
//        String localIp = getLocalIp();
//        System.out.println(localIp);
//        if (url.contains("localhost") || url.contains("127.0.0.1")) {
//            System.out.println("有localhost或者127.0.0.1");
//            url=url.replaceAll("localhost", localIp).replaceAll("127.0.0.1", localIp);
//        }
//        System.out.println(url);

        System.out.println(replaceTrueIpIfLocalhost(url));
    }

    public static String replaceTrueIpIfLocalhost(String url){
        String localIp = getLocalIp();
//        String localIp = "192.168.122.175";
        if (url.contains("localhost") || url.contains("127.0.0.1")) {
            url=url.replaceAll("localhost", localIp).replaceAll("127.0.0.1", localIp);
        }
        return url;
    }

    /**
     * 获取本地服务器ID地址
     *
     * @return 本机IP地址
     */
    private static String getLocalIp() {
        String os = System.getProperty("os.name").toUpperCase();
        String address = "";
        if (os.contains(WINDOWS)) {
            try {
                address = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("windows获取本地IP出错", e);
            }
        } else {
            address = getLinuxIP();
        }
        return address;
    }

    /*
     * 获取非windows类型服务器IP地址
     */
    private static String getLinuxIP() {
        String address = "";
        Enumeration<NetworkInterface> allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (!netInterface.isUp() || netInterface.isLoopback() || netInterface.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip.isLoopbackAddress())
                        continue;
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
}

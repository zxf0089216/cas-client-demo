package com.hundsun.cas.client.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author <a href="mailto:zhangxf21203@hundsun.com">zhangxf</a>
 * @version 1.0
 * @sinece 2017/5/3 11:43
 */
public class LocalIpUtilTest {

    private static Logger logger = LoggerFactory.getLogger(LocalIpUtilTest.class);

    @Test
    public void testReplaceLocalIp() throws Exception {

        String url = "http://127.0.0.1:8080/client1";
////        String url = "http://localhost:8080/client1";
//        String localIp = getLocalIp();
//        System.out.println(localIp);
//        if (url.contains("localhost") || url.contains("127.0.0.1")) {
//            System.out.println("有localhost或者127.0.0.1");
//            url=url.replaceAll("localhost", localIp).replaceAll("127.0.0.1", localIp);
//        }
//        System.out.println(url);

        System.out.println(LocalIpUtil.replaceTrueIpIfLocalhost(url));

    }

    @Test
    public void getWindowsIpWithoutVirtualSystem() throws Exception {
//        System.out.println(LocalIpUtil.getWindowIp());
    }
}

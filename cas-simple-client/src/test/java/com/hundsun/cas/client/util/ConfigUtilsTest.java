package com.hundsun.cas.client.util;

import org.junit.Test;

import static com.hundsun.cas.client.util.ConfigUtils.replaceIpAndPort;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author <a href="mailto:zhangxf21203@hundsun.com">zhangxf</a>
 * @version 1.0
 * @since 2018/1/18 20:29
 */
public class ConfigUtilsTest {

    @Test
    public void test1() throws Exception {
        assertThat(replaceIpAndPort("http://localhost", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net\\webapps\\ROOT"), is("http://192.168.94.54:8081"));

        // tomcat配置文件找不到,未配置IP
        assertThat(replaceIpAndPort("http://localhost", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net"), is("http://192.168.94.54"));

        // tomcat配置文件找不到，配置了IP
        assertThat(replaceIpAndPort("http://192.168.48.81", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net\\webapps\\ROOT"), is("http://192.168.48.81"));

        // tomcat配置文件存在，配置了IP,未配置端口
        assertThat(replaceIpAndPort("http://192.168.48.81/acm", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net\\webapps\\ROOT"), is("http://192.168.48.81/acm"));

        // tomcat配置文件存在，配置了IP,配置端口
        assertThat(replaceIpAndPort("http://192.168.48.81:8888/acm", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net\\webapps\\ROOT"), is("http://192.168.48.81:8888/acm"));

        // tomcat配置文件存在，未配置IP,未配置端口
        assertThat(replaceIpAndPort("http://localhost:8888/acm", "D:\\zxf\\java\\soft\\apache\\tomcat\\tomcat7-cas-client.hs.net\\webapps\\ROOT"), is("http://192.168.94.54:8888/acm"));
    }
}

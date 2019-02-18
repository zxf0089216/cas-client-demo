package com.hundsun.cas.client.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author <a href="mailto:zhangxf21203@hundsun.com">zhangxf</a>
 * @version 1.0
 * @since 2018/1/18 18:39
 */
public class ConfigUtils {
    private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static String replaceIpAndPort(String url, String applicationPath)  {
        // 只有不是默认配置才会设置读取端口和IP
        if (!url.contains("localhost") && !url.contains("127.0.0.1")) {
            return url;
        }

        // 替换IP
        try {
            url = replaceIp(url);

            // 如果找不到tomcat配置文件就不替换端口
            File tomcatServerXmlFile = getTomcatServerXmlFile(applicationPath);
            if (!tomcatServerXmlFile.exists()) {
//                    logger.info("Cannot find tomcat server.xml,tomcatServerXmlFile->{}", tomcatServerXmlFile);
                return url;
            }

            int tomcatPort = getTomcatPort(tomcatServerXmlFile);
            url = replacePort(url, tomcatPort);
        } catch (Exception e) {
            logger.error("ReplaceIpAndPort error", e);
        }

        return url;
    }

    private static int getTomcatPort(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        Element serviceElement = root.element("Service");
        Element connectorElement = serviceElement.element("Connector");
        Attribute attribute = connectorElement.attribute("port");
        String portString = attribute.getValue();
        return Integer.parseInt(portString);
    }

    private static File getTomcatServerXmlFile(String applicationPath) {
        File file = new File(applicationPath);
        String tomcatPath = file.getParentFile().getParentFile().getAbsolutePath();
        String tomcatServerXmlPath = tomcatPath + File.separator + "conf" + File.separator + "server.xml";
        File tomcatServerXmlFile = new File(tomcatServerXmlPath);
        return tomcatServerXmlFile;
    }

    private static String replaceIp(String url) {
        String localIp = LocalIpUtil.getLocalIp();

        if (localIp == null || "".equals(localIp.trim())) {
            if (logger.isWarnEnabled()) {
                logger.warn("Can not get local host,so replace true Ip for Localhost failed!");
            }
            return url;
        }

        return url.replaceAll("localhost", localIp).replaceAll("127.0.0.1", localIp);
    }

    private static String replacePort(String oldUrl, int port) {
        if (oldUrl.indexOf("//") == -1) {
            throw new IllegalArgumentException("url format error:" + oldUrl);
        }

        String[] split1 = oldUrl.split("//");
        if (split1 == null || split1.length <= 1) {
            throw new IllegalArgumentException("url format error:" + oldUrl);
        }

        String[] split2 = split1[1].split("/");
        if (split2 == null || split2.length == 0) {
            throw new IllegalArgumentException("url format error:" + oldUrl);
        }

        if (split2[0].contains(":")) {
            if (logger.isInfoEnabled()) {
                logger.info("URL has port defined,do not replace port again");
            }
            return oldUrl;
        }

        String portString = "";
        if (port != 80) {
            portString = ":" + port;
        }

        String newUrl = split1[0] + "//" + split2[0] + portString;
        if (split2.length == 1) {
            newUrl = oldUrl.endsWith("/") ? newUrl + "/" : newUrl;
            return newUrl;
        }

        for(int i = 1; i < split2.length; i++) {
            newUrl = newUrl + "/" + split2[i];
        }

//            logger.debug("ReplaceUrlPort success,oldUrl->{},newUrl->{}", oldUrl, newUrl);
        return newUrl;
    }

}

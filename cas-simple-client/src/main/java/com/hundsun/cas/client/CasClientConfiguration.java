package com.hundsun.cas.client;

import com.hundsun.cas.client.util.LocalIpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CasClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CasClientConfiguration.class);

    private static final Properties props = new Properties();

    private static final CasConfig config = new CasConfig();

    public static final String CONFIG_NAME = "cas.properties";

    public static void init(ServletContext servletContext) {
        String configFilePath=CONFIG_NAME;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath);
            if (is == null) {
                logger.info("configFilePath[{}] not exist in default classpath", configFilePath);
                String realPath = servletContext.getRealPath("");
                configFilePath = realPath + File.separator + "WEB-INF" + File.separator + "conf" + File.separator + CONFIG_NAME;
                File configFile = new File(configFilePath);

                if (!configFile.exists()){
                    logger.warn("configFile not exist either,configFilePath[{}]", configFilePath);
                    return;
                }

                is=new FileInputStream(configFile);
            }

            props.load(is);
        } catch (IOException e) {
            logger.error("load cas-client configfile[cas.properties] error", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("release input stream error !", e);
                }
            }
        }

        String isEnableValue = props.getProperty("cas.enable");
        if (isEmpty(isEnableValue)) {
            logger.debug("Property [cas.enable] not found.  Using default value [true]");
        } else {
            config.isEnbale = Boolean.parseBoolean(isEnableValue);
            logger.debug("Property [cas.enable] loaded , value is [{}]", config.isEnbale);
        }

        String serverUrlValue = props.getProperty("cas.server_url");
        if (isEmpty(serverUrlValue)) {
            logger.debug("Property [cas.server_url] not found.  Using default value [{}]", config.serverUrl);
        } else {
            config.serverUrl = serverUrlValue;
            logger.debug("Property [cas.server_url] loaded , value is [{}]", config.serverUrl);
        }
        config.serverUrl = LocalIpUtil.replaceTrueIpIfLocalhost(config.serverUrl);


        String appUrlValue = props.getProperty("cas.app_url");
        if (isEmpty(appUrlValue)) {
            logger.debug("Property [cas.app_url] not found.  Using default value [{}]", config.appUrl);
        } else {
            config.appUrl = appUrlValue;
            logger.debug("Property [cas.app_url] loaded , value is [{}]", config.appUrl);
        }
        config.appUrl = LocalIpUtil.replaceTrueIpIfLocalhost(config.appUrl);

        String logoutUrlValue = props.getProperty("cas.logout_url");
        if (isEmpty(logoutUrlValue)) {
            logger.debug("Property [cas.logout_url] not found.  Using default value [{}]", config.logoutUrl);
        } else {
            config.logoutUrl = logoutUrlValue;
            logger.debug("Property [cas.logout_url] loaded , value is [{}]", config.logoutUrl);
        }
        config.logoutUrl = LocalIpUtil.replaceTrueIpIfLocalhost(config.logoutUrl);

        String filterMappingValue = props.getProperty("cas.filter_mapping");
        if (isEmpty(filterMappingValue)) {
            logger.debug("Property [cas.filter_mapping] not found.  Using default value [{}]", config.filterMapping);
        } else {
            config.filterMapping = filterMappingValue;
            logger.debug("Property [cas.filter_mapping] loaded , value is [{}]", config.filterMapping);
        }

        String encodingValue = props.getProperty("cas.filter_encoding");
        if (isEmpty(encodingValue)) {
            logger.debug("Property [cas.filter_encoding] not found.  Using default value [{}]", config.filterEncoding);
        } else {
            config.filterEncoding = encodingValue;
            logger.debug("Property [cas.filter_encoding] loaded , value is [{}]", config.filterEncoding);
        }

        String filterExcludesValue = props.getProperty("cas.filter_exclusions");
        if (isEmpty(filterExcludesValue)) {
            logger.debug("Property [cas.filter_exclusions] not found.  Dont use filterExcludes");
        } else {
            config.filterExclusions = filterExcludesValue;
            logger.debug("Property [cas.filter_exclusions] loaded , value is [{}]", filterExcludesValue);
        }
    }

    public static boolean isEnable() {
        return config.isEnbale;
    }

    public static String getCasServerUrlPrefix() {
        return config.serverUrl;
    }

    public static String getCasServerLoginUrl() {
        return getCasServerUrlPrefix() + "/login";
    }

    public static String getCasLogoutUrl() {
        return CasClientConfiguration.getCasServerUrlPrefix() + "/logout?service=" + config.logoutUrl;
    }

    public static String getServerName() {
        return config.appUrl;
    }

    public static String getFilterMapping() {
        return config.filterMapping;
    }

    public static String getFilterEncoding() {
        return config.filterEncoding;
    }

    public static String getFilterExclusions() {
        return config.filterExclusions;
    }

    private static Boolean isEmpty(String prop) {
        return prop == null || "".trim().equals(prop);
    }

    private static class CasConfig {
        public Boolean isEnbale = true;
        public String appUrl = "http://localhost";
        public String logoutUrl = appUrl;
        public String serverUrl = "http://localhost/cas";
        public String filterMapping = "/*";
        public String filterEncoding = "UTF-8";
        public String filterExclusions = "";

        @Override
        public String toString() {
            return "CasConfig{" +
                    "isEnbale=" + isEnbale +
                    ", appUrl='" + appUrl + '\'' +
                    ", serverUrl='" + serverUrl + '\'' +
                    ", filterMapping='" + filterMapping + '\'' +
                    ", filterEncoding='" + filterEncoding + '\'' +
                    '}';
        }
    }

}

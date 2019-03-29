package com.hundsun.cas.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes(CasClientInitializer.class)
public class CasClientServletContainerInitializer implements ServletContainerInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CasClientServletContainerInitializer.class);

    @Override
    public void onStartup(Set<Class<?>> webApplicationInitializerClassSet, ServletContext servletContext) {
        if (webApplicationInitializerClassSet == null || webApplicationInitializerClassSet.size() == 0) {
            return;
        }

        CasClientConfiguration.init(servletContext);

        for (Class<?> webApplicationInitializerClass : webApplicationInitializerClassSet) {
            try {
                CasClientInitializer casClientInitializer = (CasClientInitializer) webApplicationInitializerClass.newInstance();
                casClientInitializer.init(servletContext);
            } catch (Exception e) {
                logger.error("initialize CasClientServletContainerInitializer error for [{}] !", webApplicationInitializerClass, e);
            }
        }

    }
}
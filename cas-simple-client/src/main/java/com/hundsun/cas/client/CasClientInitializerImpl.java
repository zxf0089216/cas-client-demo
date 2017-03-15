package com.hundsun.cas.client;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

public class CasClientInitializerImpl implements CasClientInitializer {

    @Override
    public void init(ServletContext servletContext) {
        if (CasClientConfiguration.isEnable()) {
            String casServerUrlPrefix = CasClientConfiguration.getCasServerUrlPrefix();
            String casServerLoginUrl = CasClientConfiguration.getCasServerLoginUrl();
            String serverName = CasClientConfiguration.getServerName();
            String filterMapping = CasClientConfiguration.getFilterMapping();
            String encoding = CasClientConfiguration.getFilterEncoding();

            servletContext.addListener(SingleSignOutHttpSessionListener.class);

            FilterRegistration.Dynamic singleSignOutFilter = servletContext.addFilter("SingleSignOutFilter", SingleSignOutFilter.class);
            singleSignOutFilter.addMappingForUrlPatterns(null, false, filterMapping);

            FilterRegistration.Dynamic authenticationFilter = servletContext.addFilter("AuthenticationFilter", AuthenticationFilter.class);
            authenticationFilter.setInitParameter("casServerLoginUrl", casServerLoginUrl);
            authenticationFilter.setInitParameter("serverName", serverName);
            authenticationFilter.addMappingForUrlPatterns(null, false, filterMapping);

            FilterRegistration.Dynamic ticketValidationFilter = servletContext.addFilter("TicketValidationFilter", Cas20ProxyReceivingTicketValidationFilter.class);
            ticketValidationFilter.setInitParameter("casServerUrlPrefix", casServerUrlPrefix);
            ticketValidationFilter.setInitParameter("serverName", CasClientConfiguration.getServerName());
            ticketValidationFilter.setInitParameter("encoding", encoding);
            ticketValidationFilter.addMappingForUrlPatterns(null, false, filterMapping);

            FilterRegistration.Dynamic requestWrapperFilter = servletContext.addFilter("RequestWrapperFilter", HttpServletRequestWrapperFilter.class);
            requestWrapperFilter.addMappingForUrlPatterns(null, false, filterMapping);

            FilterRegistration.Dynamic assertionThreadLocalFilter = servletContext.addFilter("AssertionThreadLocalFilter", AssertionThreadLocalFilter.class);
            assertionThreadLocalFilter.addMappingForUrlPatterns(null, false, filterMapping);
        }
    }
}

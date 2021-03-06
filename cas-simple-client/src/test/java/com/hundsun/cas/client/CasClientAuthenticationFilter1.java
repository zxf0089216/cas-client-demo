package com.hundsun.cas.client; /**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.hundsun.cas.client.util.RegexUtils;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * Filter implementation to intercept all requests and attempt to authenticate
 * the user by redirecting them to CAS (unless the user has a ticket).
 * <p>
 * This filter allows you to specify the following parameters (at either the context-level or the filter-level):
 * <ul>
 * <li><code>casServerLoginUrl</code> - the url to log into CAS, i.e. https://cas.rutgers.edu/login</li>
 * <li><code>renew</code> - true/false on whether to use renew or not.</li>
 * <li><code>gateway</code> - true/false on whether to use gateway or not.</li>
 * </ul>
 * <p>
 * <p>Please see AbstractCasFilter for additional properties.</p>
 *
 * @author Scott Battaglia
 * @version $Revision: 11768 $ $Date: 2007-02-07 15:44:16 -0500 (Wed, 07 Feb 2007) $
 * @since 3.0
 */
public class CasClientAuthenticationFilter1 extends AbstractCasFilter {

    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;

    /**
     * match exclude uri
     */
    private Pattern excludeUriPattern = null;

    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);

            //设置需要过滤拦截地址
            setExclusions(getPropertyFromInitParams(filterConfig, "exclusions", null));

            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e, e);
                    throw new ServletException(e);
                }
            }
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        // 判断是否需要过滤
        if (isExcluded(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }

        final String modifiedServiceUrl;

        log.debug("no ticket and no assertion found");
        if (this.gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        if (log.isDebugEnabled()) {
            log.debug("Constructed service url: " + modifiedServiceUrl);
        }

        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        if (log.isDebugEnabled()) {
            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }

        //====================mine start=========================
        String header = request.getHeader("x-requested-with");
        if (header != null && header.equalsIgnoreCase("XMLHttpRequest")) {
            log.debug("request is a XMLHttpRequest...");

            String url = request.getRequestURL().toString();
            String contextPath = request.getContextPath();
            log.debug("contextPath=" + contextPath);

            url = url.substring(0, (url.indexOf(contextPath) + contextPath.length()));
            log.debug("url = ------session消失,截取到项目的url---" + url);
            String urls = urlToRedirectTo;

            //判断是否是第一次转到.
            if (url == null || "".equals(url) || url.length() == 0) {
                log.debug("url--第一次为空,不截取-----" + url);
            } else {
                urls = urls.substring(0, (urls.indexOf("service=") + 8)) + URLEncoder.encode(url, "UTF-8");
            }

            log.debug("urls --最终输入到浏览器的地址是-----------" + urls);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print("{\"error\":\"session_timeout\",\"location\":\"" + urls + "\"}");
            writer.flush();
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print("<script languge='javascript'>window.location.href='" + urls + "/'</script>");
//            response.getWriter().write("<script languge='javascript'>window.parent.location.href='" + urls + "/'</script>");
//            response.getWriter().flush();
        } else {
            response.sendRedirect(urlToRedirectTo);
        }
        //====================mine end=========================

        //=================old start===================
//        response.sendRedirect(urlToRedirectTo);
        //=================old end===================
    }

    /**
     * 判断请求地址是否拦截
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private boolean isExcluded(HttpServletRequest request) throws IOException, ServletException {
        if (excludeUriPattern == null) {
            return false;
        }

        String requestURI = request.getRequestURI();
        if (CommonUtils.isNotBlank(requestURI)) {
            requestURI = requestURI.replace(request.getContextPath(), "");
        }

        return excludeUriPattern.matcher(requestURI).matches();
    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
        this.gatewayStorage = gatewayStorage;
    }

    public void setExclusions(String exclusions) {

        if (CommonUtils.isNotBlank(exclusions)) {
            String excludeRegex = RegexUtils.assemblyRegexStr(exclusions);
            excludeUriPattern = Pattern.compile(excludeRegex);
        }
    }

}

/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.support.oauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.utils.OAuthEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This class has some usefull methods to output data in plain text, handle redirects, add parameter in url or find the right provider.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class OAuthUtils {
    
    private static final Logger log = LoggerFactory.getLogger(OAuthUtils.class);
    
    /**
     * Write to the ouput this error text and return a null view.
     * 
     * @param response
     * @param error
     * @param status
     * @return a null view
     */
    public static ModelAndView writeTextError(final HttpServletResponse response, final String error, final int status) {
        return OAuthUtils.writeText(response, "error=" + error, status);
    }
    
    /**
     * Write to the ouput the text and return a null view.
     * 
     * @param response
     * @param text
     * @param status
     * @return a null view
     */
    public static ModelAndView writeText(final HttpServletResponse response, final String text, final int status) {
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            response.setStatus(status);
            printWriter.print(text);
        } catch (final IOException e) {
            log.error("Failed to write to response", e);
        }
        return null;
    }
    
    /**
     * Return a view which is a redirection to an url with an error parameter.
     * 
     * @param url
     * @param error
     * @return A view which is a redirection to an url with an error parameter
     */
    public static ModelAndView redirectToError(String url, final String error) {
        if (StringUtils.isBlank(url)) {
            url = "/";
        }
        return OAuthUtils.redirectTo(OAuthUtils.addParameter(url, "error", error));
    }
    
    /**
     * Return a view which is a redirection to an url.
     * 
     * @param url
     * @return A view which is a redirection to an url
     */
    public static ModelAndView redirectTo(final String url) {
        return new ModelAndView(new RedirectView(url));
    }
    
    /**
     * Add a parameter with given name and value to an url.
     * 
     * @param url
     * @param name
     * @param value
     * @return the url with the parameter
     */
    public static String addParameter(final String url, final String name, final String value) {
        final StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (url.indexOf("?") >= 0) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append(name);
        sb.append("=");
        if (value != null) {
            sb.append(OAuthEncoder.encode(value));
        }
        return sb.toString();
    }
    
    /**
     * Return the provider for the given type or null if no provider was found.
     * 
     * @param providers
     * @param type
     * @return the provider for the given type or null if no provider was found
     */
    public static OAuthProvider getProviderByType(final List<OAuthProvider> providers, final String type) {
        for (final OAuthProvider provider : providers) {
            if (provider != null && type.equals(provider.getType())) {
                return provider;
            }
        }
        return null;
    }
}

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

/**
 * This class has the main constants for the OAuth implementation.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public interface OAuthConstants {
    
    public final static String OAUTH_PROVIDER = "oauth_provider";
    
    public final static String PROVIDER_TYPE = "providerType";
    
    public final static String REDIRECT_URI = "redirect_uri";
    
    public final static String CLIENT_ID = "client_id";
    
    public final static String CLIENT_SECRET = "client_secret";
    
    public final static String CODE = "code";
    
    public final static String SERVICE = "service";
    
    public final static String THEME = "theme";
    
    public final static String LOCALE = "locale";
    
    public final static String METHOD = "method";
    
    public final static String TICKET = "ticket";
    
    public final static String STATE = "state";
    
    public final static String ACCESS_TOKEN = "access_token";
    
    public final static String OAUTH20_CALLBACKURL = "oauth20_callbackUrl";
    
    public final static String OAUTH20_SERVICE_NAME = "oauth20_service_name";
    
    public final static String OAUTH20_STATE = "oauth20_state";
    
    public final static String MISSING_ACCESS_TOKEN = "missing_accessToken";
    
    public final static String EXPIRED_ACCESS_TOKEN = "expired_accessToken";
    
    public final static String CONFIRM_VIEW = "oauthConfirmView";
    
    public final static String ERROR_VIEW = "viewServiceErrorView";
    
    public final static String INVALID_REQUEST = "invalid_request";
    
    public final static String INVALID_GRANT = "invalid_grant";
    
    public final static String AUTHORIZE_URL = "authorize";
    
    public final static String CALLBACK_AUTHORIZE_URL = "callbackAuthorize";
    
    public final static String ACCESS_TOKEN_URL = "accessToken";
    
    public final static String PROFILE_URL = "profile";
    
    public final static String OAUTH10_LOGIN_URL = "oauth10login";
}

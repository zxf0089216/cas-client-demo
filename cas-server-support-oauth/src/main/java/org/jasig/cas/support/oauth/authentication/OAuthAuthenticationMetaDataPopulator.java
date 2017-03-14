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
package org.jasig.cas.support.oauth.authentication;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.MutableAuthentication;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.support.oauth.OAuthConstants;
import org.jasig.cas.support.oauth.authentication.principal.OAuthCredentials;

/**
 * This class is a meta data populator for OAuth authentication. As attributes are stored in OAuthCredentials (through user profile), they
 * are added to the returned principal. The provider type associated to the authentication is also added to the authentication attributes.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class OAuthAuthenticationMetaDataPopulator implements AuthenticationMetaDataPopulator {
    
    public Authentication populateAttributes(final Authentication authentication, final Credentials credentials) {
        if (credentials instanceof OAuthCredentials) {
            OAuthCredentials oauthCredentials = (OAuthCredentials) credentials;
            final Principal simplePrincipal = new SimplePrincipal(authentication.getPrincipal().getId(),
                                                                  oauthCredentials.getUserProfile().getAttributes());
            final MutableAuthentication mutableAuthentication = new MutableAuthentication(simplePrincipal,
                                                                                          authentication
                                                                                              .getAuthenticatedDate());
            mutableAuthentication.getAttributes().putAll(authentication.getAttributes());
            mutableAuthentication.getAttributes().put(OAuthConstants.PROVIDER_TYPE,
                                                      oauthCredentials.getCredential().getProviderType());
            return mutableAuthentication;
        }
        return authentication;
    }
}

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
package org.jasig.cas.support.oauth.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.RegisteredServiceImpl;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.support.oauth.OAuthConstants;
import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * This class tests the {@link OAuth20AccessTokenController} class.
 * 
 * @author Jerome Leleu
 * @since 3.5.2
 */
public final class OAuth20AccessTokenControllerTests {
    
    private static final String CONTEXT = "/oauth2.0/";
    
    private static final String CLIENT_ID = "1";
    
    private static final String CLIENT_SECRET = "secret";
    
    private static final String WRONG_CLIENT_SECRET = "wrongSecret";
    
    private static final String CODE = "ST-1";
    
    private static final String TGT_ID = "TGT-1";
    
    private static final String REDIRECT_URI = "http://someurl";
    
    private static final String OTHER_REDIRECT_URI = "http://someotherurl";
    
    private static final int TIMEOUT = 7200;
    
    @Test
    public void testNoClientId() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("missing clientId");
    }
    
    @Test
    public void testNoRedirectUri() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("missing redirectUri");
    }
    
    @Test
    public void testNoClientSecret() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("missing clientSecret");
    }
    
    @Test
    public void testNoCode() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("missing code");
    }
    
    @Test
    public void testNoCasService() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        when(servicesManager.getAllServices()).thenReturn(new ArrayList<RegisteredService>());
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("Unknown clientId : {}", CLIENT_ID);
    }
    
    @Test
    public void testRedirectUriDoesNotStartWithServiceId() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        registeredServiceImpl.setName(CLIENT_ID);
        registeredServiceImpl.setServiceId(OTHER_REDIRECT_URI);
        final List<RegisteredService> services = new ArrayList<RegisteredService>();
        services.add(registeredServiceImpl);
        when(servicesManager.getAllServices()).thenReturn(services);
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("Unsupported redirectUri : {} for serviceId : {}", REDIRECT_URI, OTHER_REDIRECT_URI);
    }
    
    @Test
    public void testWrongSecret() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        registeredServiceImpl.setName(CLIENT_ID);
        registeredServiceImpl.setServiceId(REDIRECT_URI);
        registeredServiceImpl.setDescription(WRONG_CLIENT_SECRET);
        final List<RegisteredService> services = new ArrayList<RegisteredService>();
        services.add(registeredServiceImpl);
        when(servicesManager.getAllServices()).thenReturn(services);
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_REQUEST, mockResponse.getContentAsString());
        verify(logger).error("Wrong client secret : {} for service description : {}", CLIENT_SECRET,
                             WRONG_CLIENT_SECRET);
    }
    
    @Test
    public void testNoServiceTicket() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        registeredServiceImpl.setName(CLIENT_ID);
        registeredServiceImpl.setServiceId(REDIRECT_URI);
        registeredServiceImpl.setDescription(CLIENT_SECRET);
        final List<RegisteredService> services = new ArrayList<RegisteredService>();
        services.add(registeredServiceImpl);
        when(servicesManager.getAllServices()).thenReturn(services);
        final TicketRegistry ticketRegistry = mock(TicketRegistry.class);
        when(ticketRegistry.getTicket(CODE)).thenReturn(null);
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.setTicketRegistry(ticketRegistry);
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_GRANT, mockResponse.getContentAsString());
        verify(logger).error("Code expired : {}", CODE);
    }
    
    @Test
    public void testExpiredServiceTicket() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        registeredServiceImpl.setName(CLIENT_ID);
        registeredServiceImpl.setServiceId(REDIRECT_URI);
        registeredServiceImpl.setDescription(CLIENT_SECRET);
        final List<RegisteredService> services = new ArrayList<RegisteredService>();
        services.add(registeredServiceImpl);
        when(servicesManager.getAllServices()).thenReturn(services);
        final TicketRegistry ticketRegistry = mock(TicketRegistry.class);
        final ServiceTicket serviceTicket = mock(ServiceTicket.class);
        when(serviceTicket.isExpired()).thenReturn(true);
        when(ticketRegistry.getTicket(CODE)).thenReturn(serviceTicket);
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.setTicketRegistry(ticketRegistry);
        oauth20WrapperController.afterPropertiesSet();
        final Logger logger = mock(Logger.class);
        OAuth20AccessTokenController.setLogger(logger);
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        assertEquals(400, mockResponse.getStatus());
        assertEquals("error=" + OAuthConstants.INVALID_GRANT, mockResponse.getContentAsString());
        verify(logger).error("Code expired : {}", CODE);
    }
    
    @Test
    public void testOK() throws Exception {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", CONTEXT
                                                                                     + OAuthConstants.ACCESS_TOKEN_URL);
        mockRequest.setParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        mockRequest.setParameter(OAuthConstants.REDIRECT_URI, REDIRECT_URI);
        mockRequest.setParameter(OAuthConstants.CLIENT_SECRET, CLIENT_SECRET);
        mockRequest.setParameter(OAuthConstants.CODE, CODE);
        final MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        final ServicesManager servicesManager = mock(ServicesManager.class);
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        registeredServiceImpl.setName(CLIENT_ID);
        registeredServiceImpl.setServiceId(REDIRECT_URI);
        registeredServiceImpl.setDescription(CLIENT_SECRET);
        final List<RegisteredService> services = new ArrayList<RegisteredService>();
        services.add(registeredServiceImpl);
        when(servicesManager.getAllServices()).thenReturn(services);
        final TicketRegistry ticketRegistry = mock(TicketRegistry.class);
        final ServiceTicket serviceTicket = mock(ServiceTicket.class);
        final TicketGrantingTicket ticketGrantingTicket = mock(TicketGrantingTicket.class);
        // 10 seconds
        final int TIME_BEFORE = 10;
        when(ticketGrantingTicket.getCreationTime()).thenReturn(System.currentTimeMillis() - TIME_BEFORE * 1000);
        when(ticketGrantingTicket.getId()).thenReturn(TGT_ID);
        when(serviceTicket.isExpired()).thenReturn(false);
        when(serviceTicket.getId()).thenReturn(CODE);
        when(serviceTicket.getGrantingTicket()).thenReturn(ticketGrantingTicket);
        when(ticketRegistry.getTicket(CODE)).thenReturn(serviceTicket);
        final OAuth20WrapperController oauth20WrapperController = new OAuth20WrapperController();
        oauth20WrapperController.setServicesManager(servicesManager);
        oauth20WrapperController.setTicketRegistry(ticketRegistry);
        oauth20WrapperController.setTimeout(TIMEOUT);
        oauth20WrapperController.afterPropertiesSet();
        oauth20WrapperController.handleRequest(mockRequest, mockResponse);
        verify(ticketRegistry).deleteTicket(CODE);
        assertEquals("text/plain", mockResponse.getContentType());
        assertEquals(200, mockResponse.getStatus());
        final String body = mockResponse.getContentAsString();
        assertTrue(body.startsWith("access_token=" + TGT_ID + "&expires="));
        // delta = 2 seconds
        final int DELTA = 2;
        final int timeLeft = Integer.parseInt(StringUtils.substringAfter(body, "&expires="));
        assertTrue(timeLeft >= TIMEOUT - TIME_BEFORE - DELTA);
        assertTrue(timeLeft <= TIMEOUT - TIME_BEFORE + DELTA);
    }
}

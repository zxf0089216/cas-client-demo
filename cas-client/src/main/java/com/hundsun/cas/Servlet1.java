package com.hundsun.cas;

import com.hundsun.cas.client.CasClientConfiguration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet1 extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
//        response.sendRedirect("http://acm.hs.net:8488/cas/logout?service=http://acm.hs.net/client1");
//        response.sendRedirect("http://cas-server:8488/cas/logout?service=http%3A%2F%2Fcas-client%3A8080%2F");
        response.sendRedirect(CasClientConfiguration.getCasLogoutUrl());
    }

}
package com.hundsun.cas;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet1 extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
        // http://cas-client1:9080
//        response.sendRedirect("http://cas-server:8488/cas/login?service=http%3A%2F%2Fcas-client1%3A9080%2F");
//        response.sendRedirect("http://cas-server:8488/cas/logout?service=http%3A%2F%2Fcas-client1%3A9080%2F");
        response.sendRedirect("http://192.168.48.139:8081/cas/logout?service=http://localhost:18080");
//        response.sendRedirect(CasClientConfiguration.getCasLogoutUrl());
    }

}
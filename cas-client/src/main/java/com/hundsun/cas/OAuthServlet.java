package com.hundsun.cas;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OAuthServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ret = request.getParameter("ret");
        String msg = request.getParameter("msg");
        if (ret == null) {
            ret = "0";
        }
        String result = "<html><head><script language='javascript'>" + "parent.logincallback({'ret':" + ret + ",'msg':'" + msg + "'});" + "</script></head> </html>";
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (Exception e) {
        }

    }

}
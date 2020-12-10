package servlet;

import http.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet",urlPatterns = "/servlet.loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txtUserID = request.getParameter("txtUserID");
        String txtUserPwd = request.getParameter("txtUserPwd");


        boolean result=http.login(txtUserID, txtUserPwd);
        if(result)
            response.getWriter().println("success");
        else response.getWriter().println("error");
        System.out.println("loginServlet : "+(result?"success":"error"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

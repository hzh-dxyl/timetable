package servlet;

import dataBase.DbBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteServlet",urlPatterns = "/servlet.deleteServlet")
public class deleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("delete");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String txtUserID=request.getParameter("txtUserID");
        String name=request.getParameter("name");
        DbBean dbBean=new DbBean();
        if(dbBean.deleteLesson(txtUserID,name)){
            response.getWriter().println("success");
        }
        else response.getWriter().println("error");
    }
}

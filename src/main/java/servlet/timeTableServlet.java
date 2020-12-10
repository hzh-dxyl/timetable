package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import http.*;

@WebServlet(name = "timeTable",urlPatterns = "/servlet.timeTableServlet")
public class timeTableServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txtUserID = request.getParameter("txtUserID");
        String txtUserPwd = request.getParameter("txtUserPwd");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String xn = request.getParameter("xn");
        String xq = request.getParameter("xq");
        ArrayList<Lesson> lessons=http.getTimeTable(txtUserID,txtUserPwd,xn,xq);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("length",String.valueOf(lessons.size()));
        for (int i=0;i<lessons.size();i++) {
            Lesson lesson=lessons.get(i);
            JSONObject json = new JSONObject();
            json.put("name", lesson.name);
            json.put("no", lesson.no);
            json.put("teacher", lesson.teacher);
            json.put("classroom", lesson.classroom);
            json.put("during", lesson.during[0] + "-" + lesson.during[1]);
            json.put("credit",lesson.credit);
            for (int j = 0; j < lesson.time.length; j++) {
                json.put(String.valueOf(j), lesson.time[j]);
            }
            jsonObject.put("lesson"+i, json.toString());
        }
        response.getWriter().println(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

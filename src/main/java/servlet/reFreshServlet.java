package servlet;

import com.alibaba.fastjson.JSONObject;
import dataBase.DbBean;
import http.Lesson;
import http.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "reFreshServlet",urlPatterns = "/servlet.reFreshServlet")
public class reFreshServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txtUserID = request.getParameter("txtUserID");
        String txtUserPwd = request.getParameter("txtUserPwd");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String xn = request.getParameter("xn");
        String xq = request.getParameter("xq");

        DbBean dbBean=new DbBean();
        JSONObject jsonObject=new JSONObject();

        ArrayList<Lesson> lessons =http.getTimeTable(txtUserID,txtUserPwd,xn,xq);
        int i=0;
        for (;i<lessons.size(); i++) {
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
            json.put("note",dbBean.getNote(txtUserID, lesson.name));
            jsonObject.put("lesson"+i, json.toString());
        }
        ArrayList<Lesson> lessons1=dbBean.getLessons(txtUserID);
        for(int j=0;j<lessons1.size();j++){
            Lesson lesson=lessons1.get(j);
            JSONObject json = new JSONObject();
            json.put("name", lesson.name);
            json.put("no", lesson.no);
            json.put("teacher", lesson.teacher);
            json.put("classroom", lesson.classroom);
            json.put("during", lesson.during[0] + "-" + lesson.during[1]);
            json.put("credit",lesson.credit);
            for (int h = 0; h < lesson.time.length; h++) {
                json.put(String.valueOf(h), lesson.time[h]);
            }
            json.put("note",lesson.note);
            jsonObject.put("lesson"+(i+j), json.toString());
        }

        jsonObject.put("length",String.valueOf(lessons.size()+lessons1.size()));
        response.getWriter().println(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


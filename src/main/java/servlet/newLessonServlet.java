package servlet;

import dataBase.DbBean;
import http.Lesson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "newLessonServlet", urlPatterns = "/servlet.newLessonServlet")

public class newLessonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("newLesson");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String txtUserID=request.getParameter("txtUserID");
        Lesson lesson = new Lesson();
        lesson.name = request.getParameter("name");
        lesson.no = request.getParameter("no");  //课程号
        lesson.teacher = request.getParameter("teacher");  //教师
        lesson.classroom = request.getParameter("classroom");  //教室
        lesson.during[0] = Integer.parseInt(request.getParameter("duringb"));  //持续时间
        lesson.during[1] = Integer.parseInt(request.getParameter("duringe"));  //持续时间
        for(int i=0;i<7;i++){
            lesson.time[i]=request.getParameter("time"+i);
        }
        lesson.credit = request.getParameter("credit");  //学分
        lesson.note = request.getParameter("note"); //备注
        DbBean dbBean = new DbBean();
        System.out.println(lesson.note);
        if (dbBean.addLesson(lesson,txtUserID))
            response.getWriter().println("success");
        else response.getWriter().println("error");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

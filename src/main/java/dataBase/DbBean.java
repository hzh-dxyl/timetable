package dataBase;


import com.alibaba.fastjson.JSONObject;
import http.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;


public class DbBean {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String ADDRESS = "jdbc:mysql://localhost:3306/timeTable";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    Connection con = null;
    Statement stat = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    //无参数的构造函数
    public DbBean() {}

    //取得数据库连接
    public Connection getCon(){
        try{
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName(DRIVER);	// 加载驱动
//			String url = "jdbc:mysql://localhost:3306/enwebside?user=root&password=1234&useUnicode=true&characterEncoding=utf-8";
//	        con = DriverManager.getConnection(url);
            con = (Connection) DriverManager.getConnection(ADDRESS, USER, PASSWORD);
            //连接数据库
            if (con != null && !con.isClosed())
            {
                System.out.println("MySQL Connection Succeeded!");
            }
            else
            {
                System.err.println("MySQL Connection Failed!");
                return null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return con;
    }

    //执行数据库查询并返回查询结果
    public ResultSet query(String sql){
        try{
            con = getCon();
            stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stat.executeQuery(sql);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rs;
    }

    //执行数据库更新
    public void update(String sql){
        try{
            con = getCon();
            stat = con.createStatement();
            stat.executeUpdate(sql);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //执行数据库更新
    public void update(String sql,String[] args){
        try{
            con = getCon();
            pstat = con.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                pstat.setString(i+1,args[i]);
            }
            pstat.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String getNote(String txtUserID, String name){
        try {
            con=getCon();
            pstat=con.prepareStatement("select note from timetable.notes where txtUserID=? and name=?");
            pstat.setString(1,txtUserID);
            pstat.setString(2,name);
            rs=pstat.executeQuery();
            if(rs.next())
                return rs.getString("note");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
        return "";
    }

    public ArrayList<Lesson> getLessons(String txtUserID){
        ArrayList<Lesson> lessons=new ArrayList<>();
        try {
            con=getCon();
            pstat=con.prepareStatement("select * from timetable.lessons where txtUserID=?");
            pstat.setString(1,txtUserID);
            rs=pstat.executeQuery();
            while (rs.next()){
                Lesson lesson=new Lesson();
                lesson.name=rs.getString("name");
                lesson.no=rs.getString("no");
                lesson.during[0]=rs.getInt("duringb");
                lesson.during[1]=rs.getInt("duringe");
                for(int i=0;i<7;i++){
                    lesson.time[i]=rs.getString("time"+i);
                }
                lesson.classroom=rs.getString("classroom");
                lesson.teacher=rs.getString("teacher");
                lesson.credit=rs.getString("credit");
                lesson.note=rs.getString("note");
                lessons.add(lesson);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lessons;
    }

    public boolean addLesson(Lesson lesson,String txtUserID){
        try {
            con=getCon();
            pstat=con.prepareStatement("insert into timetable.lessons" +
                            "(txtUserID,name,teacher,classroom,credit,no,note,duringb,duringe,time0,time1,time2,time3,time4,time5,time6)"+
                            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstat.setString(1,txtUserID);
            pstat.setString(2,lesson.name);
            pstat.setString(3,lesson.teacher);
            pstat.setString(4,lesson.classroom);
            pstat.setString(5,lesson.credit);
            pstat.setString(6,lesson.no);
            pstat.setString(7,lesson.note);
            pstat.setInt(8,lesson.during[0]);
            pstat.setInt(9,lesson.during[1]);
            pstat.setString(10,lesson.time[0]);
            pstat.setString(11,lesson.time[1]);
            pstat.setString(12,lesson.time[2]);
            pstat.setString(13,lesson.time[3]);
            pstat.setString(14,lesson.time[4]);
            pstat.setString(15,lesson.time[5]);
            pstat.setString(16,lesson.time[6]);
            pstat.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            close();
        }
    }

    public boolean modifyNote(String name,String txtUserID,String note){
        try {
            con=getCon();
            pstat=con.prepareStatement("update timetable.notes set note=? where name=? and txtUserID=?");
            pstat.setString(1,note);
            pstat.setString(2,name);
            pstat.setString(3,txtUserID);
            pstat.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            close();
        }
    }

    public boolean modifyLesson(Lesson lesson,String txtUserID){
        try {
            con=getCon();
            pstat=con.prepareStatement("delete from timetable.lessons where txtUserID=? and name=?");
            pstat.setString(1,txtUserID);
            pstat.setString(2,lesson.name);
            pstat.executeUpdate();
            pstat=con.prepareStatement("insert into timetable.lessons" +
                    "(txtUserID,name,teacher,classroom,credit,no,note,duringb,duringe,time0,time1,time2,time3,time4,time5,time6)"+
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstat.setString(1,txtUserID);
            pstat.setString(2,lesson.name);
            pstat.setString(3,lesson.teacher);
            pstat.setString(4,lesson.classroom);
            pstat.setString(5,lesson.credit);
            pstat.setString(6,lesson.no);
            pstat.setString(7,lesson.note);
            pstat.setInt(8,lesson.during[0]);
            pstat.setInt(9,lesson.during[1]);
            pstat.setString(10,lesson.time[0]);
            pstat.setString(11,lesson.time[1]);
            pstat.setString(12,lesson.time[2]);
            pstat.setString(13,lesson.time[3]);
            pstat.setString(14,lesson.time[4]);
            pstat.setString(15,lesson.time[5]);
            pstat.setString(16,lesson.time[6]);
            pstat.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            close();
        }
    }

    public boolean deleteLesson(String txtUserID,String name){
        try {
            con=getCon();
            pstat=con.prepareStatement("delete from timetable.lessons where txtUserID=? and name=?");
            pstat.setString(1,txtUserID);
            pstat.setString(2,name);
            pstat.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            close();
        }
    }
    //关闭数据库连接
    public void close(){
        try{
            if (rs != null)rs.close();
            if (stat != null)stat.close();
            if (pstat != null)pstat.close();
            if (con != null)con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}

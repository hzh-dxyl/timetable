package http;

public class jj {
    public static void main(String[] args) {
        addLesson(new Lesson());
    }
    public static boolean addLesson(Lesson lesson){
        try {
            System.out.println("try begin");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch begin");
            return false;
        }
        finally {
            System.out.println("finally begin");
        }
    }
}

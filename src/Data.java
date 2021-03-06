/**
 *
 * @author paulhemingway
 */

// class to hold the current user data
// used to keep consistent information when switching scenes
public class Data {
    public static Teacher currentTeacher = null;
    public static Student currentStudent = null;
    public static Quiz currentQuiz = null;
    public static boolean isTeacher = false;
    public static boolean isStudent = false;
    
    public static void clearCurrent(){
        currentTeacher = null;
        currentStudent = null;
        isTeacher = false;
        isStudent = false;
    }
}
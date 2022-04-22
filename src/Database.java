
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author paulhemingway
 */
public class Database {
    // https://medium.com/modernnerd-code/connecting-to-mysql-db-on-aws-ec2-with-jdbc-for-java-91dba3003abb 
    public static Connection getConnection() throws Exception{
        try{
            // credentials for database
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://ec2-18-217-237-90.us-east-2.compute.amazonaws.com/quizit";
            String user = "pshfmg";
            String password = "QuizIt2022!";
            
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
            return conn;
        }
        catch (Exception e){ System.out.println(e);  }
        
        return null;
    }
    
    // function to pull all data from specific table
    public static ArrayList<HashMap<String,Object>> getAll(String table) throws Exception{
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> row;
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
            ResultSet result = statement.executeQuery();
            
            // get the information on the columns
            ResultSetMetaData metaData = result.getMetaData();
            Integer columnCount = metaData.getColumnCount();
            
            while(result.next()){
                row = new HashMap<String, Object>();
                for (int i = 1; i<= columnCount; i++){
                    row.put(metaData.getColumnName(i), result.getObject(i));
                }
                resultList.add(row);
            }
            
            con.close();
            return resultList;
           
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public static void post(String query) throws Exception{
        try {
            Connection con = getConnection();
            PreparedStatement post = con.prepareStatement(query);
            
            post.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean usernameExists(String testUsername) throws Exception{
        Connection con = getConnection();
        try {          
            PreparedStatement statement = con.prepareStatement("SELECT * FROM accounts WHERE username LIKE '" + testUsername +"'");
            ResultSet result = statement.executeQuery();
            
            // if the username doens't exist, result won't have a "next"
            if(result.next()){
                con.close();
                return true;
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        con.close();
        return false;
    }
    
    public static String[] checkLogin(String testUsername, String testPassword) throws Exception{
        Connection con = getConnection();
        String username, password, firstName, lastName, accountType;
        Account currentUser;
        try {
             PreparedStatement statement = con.prepareStatement("SELECT * FROM accounts WHERE username LIKE '" + testUsername +"'");
            ResultSet result = statement.executeQuery();
            if(result.next()){
                username = result.getString("username");
                password = result.getString("password");
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
                accountType = result.getString("accountType");
                
                if(password.equals(testPassword)){
                    return new String[]{username, password, firstName, lastName, accountType};
                } else {
                    return new String[]{"Incorrect password!"};
                }  
            } else {
                return new String[]{"Username not found!"};
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return new String[]{"Something went wrong... Try again."};
    } 
    
    // after teacher creates the quiz and it posts, we need to get the 
    // quizID from the quiz that just posted.
    public static int getQuizID(Quiz quiz){
        try {
            int quizID;
            Connection con = getConnection();
            // just incase there's an identical author/title, grab the latest one
            PreparedStatement statement = con.prepareStatement(String.format("SELECT quizID FROM quizzes WHERE title = '%s' AND author = '%s' ORDER BY quizID DESC LIMIT 1", 
                    quiz.getTitle().replace("'","''"), quiz.getAuthor()));
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                quizID = Integer.parseInt(result.getString("quizID"));
                return quizID;
            }
            con.close(); 
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static void postQuiz(Quiz quiz) throws Exception {
        String quizQuery = String.format("INSERT INTO quizzes (author, title, possibleScore) VALUES ('%s', '%s', '%d')",
                quiz.getAuthor(), quiz.getTitle().replace("'","''"), quiz.getQuestions().size());
        System.out.println("Query: " + quizQuery);
        post(quizQuery);
        
        try {
            Connection con = getConnection();
            String questionQuery;
            Statement stmt = con.createStatement();
            int quizID = getQuizID(quiz);
            System.out.println("quizID + " + quizID);
                
            for(Question question : quiz.getQuestions()){
                questionQuery = String.format("INSERT INTO questions (quizID, question, options, answer) VALUES ('%d', '%s', '%s', '%s')", 
                        quizID, question.getQuestion().replace("'","''"), question.getOptionsString().replace("'","''"), question.getAnswer().replace("'","''"));
                System.out.println(questionQuery);
                stmt.addBatch(questionQuery);
            }
            stmt.executeBatch();
            con.close();
        }
        catch(Exception e){System.out.println(e);}
        
    }
    


// function to pull all data from specific table
    public static ArrayList<Question> getQuestions(int quizID) throws Exception{
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> row;
        ArrayList<Question> questions = new ArrayList();
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(String.format("SELECT * FROM questions WHERE quizID = '%s'", quizID));
            ResultSet result = statement.executeQuery();
            
            // get the information on the columns
            ResultSetMetaData metaData = result.getMetaData();
            Integer columnCount = metaData.getColumnCount();
            
            while(result.next()){
                row = new HashMap<String, Object>();
                for (int i = 1; i<= columnCount; i++){
                    row.put(metaData.getColumnName(i), result.getObject(i));
                }
                resultList.add(row);
            }
            
            con.close();
            
            for (HashMap<String, Object> i : resultList){
                Question question = new Question((String)i.get("question"), (String)i.get("options"), (String)i.get("answer"));
                questions.add(question);
            }
            return questions;
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
}

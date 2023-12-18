
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author icy
 */
public class ClassQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addClass;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getSeats;
    private static PreparedStatement dropClass;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry cls)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester,coursecode,seats) values (?,?,?)");
            addClass.setString(1, cls.getSemester());
            addClass.setString(2, cls.getCourseCode());
            addClass.setInt(3, cls.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select courseCode from app.course order by coursecode");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    
    public static ArrayList<String> getSemesterClassCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> classes = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select courseCode from app.class where semester = ? order by coursecode");
            getCourseList.setString(1, semester);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                classes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classes;
    }
    
    
    public static int getClassSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int seatNo = 0;
        try{
            getSeats = connection.prepareStatement("select seats from app.class where semester = ? and coursecode = ?");
            getSeats.setString(1, semester);
            getSeats.setString(2, courseCode);
            resultSet = getSeats.executeQuery();
            
            while(resultSet.next())
                seatNo = resultSet.getInt(1);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return seatNo;
        
    }
    
    public static void dropClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            dropClass = connection.prepareStatement("delete from app.class where semester = ? and coursecode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}

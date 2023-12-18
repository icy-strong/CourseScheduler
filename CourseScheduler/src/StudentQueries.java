
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author icy
 */
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static PreparedStatement getStudentName;
    private static PreparedStatement getStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid,firstname,lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getStudentList()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getStudentList = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    
    public static StudentEntry getStudentByName(String lastName, String firstName){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try{
            getStudentName = connection.prepareStatement("select studentid from app.student where firstname = ? and lastname = ?");
            getStudentName.setString(1, firstName);
            getStudentName.setString(2, lastName);
            resultSet = getStudentName.executeQuery();
            
            while(resultSet.next())
                student = new StudentEntry(resultSet.getString(1), firstName, lastName);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try{
            getStudent = connection.prepareStatement("select firstname, lastname from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
                student = new StudentEntry(studentID, resultSet.getString(1), resultSet.getString(2));
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return student;
    }
    
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try{
            getStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            getStudent.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
    }
}

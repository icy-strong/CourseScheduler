
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author icy
 */
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getClassDescpt;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classes = new ArrayList<ClassDescription>();
        try
        {
            getClassDescpt = connection.prepareStatement("select app.class.coursecode, description, seats from app.class, "
                    + "app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            
            getClassDescpt.setString(1, semester);
            resultSet = getClassDescpt.executeQuery();
            
            while(resultSet.next())
            {
                classes.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2),resultSet.getInt(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classes;
        
    }
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getClassDescpt = connection.prepareStatement("select app.schedule.studentid, firstname, lastname from app.schedule, "
                    + "app.student where semester = ? and coursecode = ? and status = ? and app.schedule.studentid = app.student.studentid order by app.schedule.timestamp");
            
            getClassDescpt.setString(1, semester);
            getClassDescpt.setString(2, courseCode);
            getClassDescpt.setString(3, "Scheduled");
            resultSet = getClassDescpt.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));

            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getClassDescpt = connection.prepareStatement("select app.schedule.studentid, firstname, lastname from app.schedule, "
                    + "app.student where semester = ? and coursecode = ? and status = ? and app.schedule.studentid = app.student.studentid order by app.schedule.timestamp");
            
            getClassDescpt.setString(1, semester);
            getClassDescpt.setString(2, courseCode);
            getClassDescpt.setString(3, "Waitlisted");
            resultSet = getClassDescpt.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));

            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
}


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author icy
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentC;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester,coursecode,studentid,status,timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try{
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where studentid = ? and semester = ? order by status");
            getScheduleByStudent.setString(1, studentID);
            getScheduleByStudent.setString(2, semester);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next()){
                schedule.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            
        }
        return schedule;
    }
    
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        int scheduledS = 0;
        connection = DBConnection.getConnection();
        try{
            getScheduledStudentC = connection.prepareStatement("select studentid from app.schedule where semester = ? and coursecode = ?");
            getScheduledStudentC.setString(1, currentSemester);
            getScheduledStudentC.setString(2, courseCode);
            resultSet = getScheduledStudentC.executeQuery();
            
            while(resultSet.next()){
                scheduledS++;
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return scheduledS;
    }
    
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try{
            getScheduleByStudent = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and coursecode = ? and status = ? order by timestamp");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, courseCode);
            getScheduleByStudent.setString(3, "Waitlisted");
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next()){
                schedule.add(new ScheduleEntry(semester, courseCode, resultSet.getString(1), "Waitlisted", resultSet.getTimestamp(2)));
            }

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            
        }
        return schedule;
        
    }
    
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            dropStudent = connection.prepareStatement("delete from app.schedule where studentid = ? and coursecode = ? and semester = ?");
            dropStudent.setString(1, studentID);
            dropStudent.setString(2, courseCode);
            dropStudent.setString(3, semester);
            dropStudent.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    
    public static void updateScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        
        try{
            dropStudent = connection.prepareStatement("update app.schedule set status = ? where studentid = ? and coursecode = ? and semester = ?");
            dropStudent.setString(1, "Scheduled");
            dropStudent.setString(2, entry.getStudentID());
            dropStudent.setString(3, entry.getCourseCode());
            dropStudent.setString(4, entry.getSemester());

            dropStudent.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}

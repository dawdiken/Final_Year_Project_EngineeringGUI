import java.sql.*;
import java.util.ArrayList;

public interface EngineeringDataAccess {
    ArrayList<NewJobEntry> findPerson(String userName , String password );
    ArrayList<String> findCustomer();
    ArrayList<String> findSop(String custName);
    ArrayList<String> findTechDrawing(String custName);
    ArrayList<String> findDepartment();
    Integer findMaxJobId();
    ResultSet findAllJobs();
    ArrayList<NewJobEntry> findJobsByNumber(String jobNumber);
    ArrayList<String> findJobsByDept(String department);
    void sqlGetTechDrawing(String name, int choice);
    void sqlGetTechDrawingByID(int name, int choice,NewJobEntry newJob);
    Integer updateWorkQty( int qty, int choice, String jobNumber );
    //boolean updatefinished( String qty );

    boolean newUser( NewJobEntry person ) throws DataAccessException;
    boolean saveJob(NewJobEntry person ) throws DataAccessException;

    boolean newDocument(NewJobEntry job, int table )throws DataAccessException;
    // close data source connection
    void close();
}  // end interface EngineeringDataAccess
// Java core packages
import java.sql.*;
import java.util.ArrayList;

public interface AddressBookDataAccess {

    // Locate specified person by last name. Return
    // AddressBookEntry containing information.
    public ArrayList<NewJobEntry>  findPerson(String userName , String password );

        public boolean newUser( NewJobEntry person )
            throws DataAccessException;

    // Update information for specified person.
    // Return boolean indicating success or failure.
    public boolean savePerson(
            NewJobEntry person ) throws DataAccessException;
//
//    // Insert a new person. Return boolean indicating
//    // success or failure.
//    public boolean newPerson( AddressBookEntry person )
//            throws DataAccessException;
//
//    // Delete specified person. Return boolean indicating if
//    // success or failure.
//    public boolean deletePerson(
//            AddressBookEntry person ) throws DataAccessException;

    // close data source connection
    public void close();
}  // end interface AddressBookDataAccess
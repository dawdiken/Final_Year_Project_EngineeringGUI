import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseAccess implements EngineeringDataAccess {

    // reference to database connection
    private Connection connection;

    // reference to prepared statement for locating entry
    private PreparedStatement sqlFind;

    // reference to prepared statement for determining personID
    private PreparedStatement sqlPersonID;

    // references to prepared statements for inserting entry
    private PreparedStatement sqlInsertName;
    private PreparedStatement sqlInsertAddress;
    private PreparedStatement sqlInsertPhone;
    private PreparedStatement sqlInsertEmail;

    private PreparedStatement sqlInsertUser;

    // references to prepared statements for updating entry
    private PreparedStatement sqlUpdateName;
    private PreparedStatement sqlUpdateAddress;
    private PreparedStatement sqlUpdatePhone;
    private PreparedStatement sqlUpdateEmail;

    // references to prepared statements for updating entry
    private PreparedStatement sqlDeleteName;
    private PreparedStatement sqlDeleteAddress;
    private PreparedStatement sqlDeletePhone;
    private PreparedStatement sqlDeleteEmail;
    private PreparedStatement sqlSingleFindPersonID;
    private PreparedStatement sqlFindPersonID;
    private PreparedStatement sqlFindName;
    private PreparedStatement sqlInsertJob;
    private PreparedStatement sqlFindCustomer;
    private PreparedStatement sqlFindSopByCustomer;
    private PreparedStatement sqlFindDepartmentByCustomer;
    private PreparedStatement sqlFindTechDrawingByCustomer;
    private PreparedStatement sqlFindSopByName;
    private PreparedStatement sqlFindTechDrawingByName;
    private PreparedStatement sqlFindDepartmentByName;
    private PreparedStatement sqlInsertBlob;
    private PreparedStatement sqlInsertDrawing;
    private PreparedStatement sqlInsertSOP;
    private PreparedStatement sqlFindCustomerID;
    private PreparedStatement sqlFindMaxJobID;
    private PreparedStatement sqlFindAllJobs;
    private PreparedStatement sqlInsertDimensions;
    private PreparedStatement sqlFindDimensions;

    private PreparedStatement sqlReturnTechDrawing;
    private PreparedStatement sqlReturnSOP;


    // set up PreparedStatements to access database
    public DataBaseAccess() throws Exception
    {
        // connect to addressbook database
        connect();

        // Insert userName and password in table user of Engineering Database.
        // For referential integrity, this must be performed
        sqlInsertUser = connection.prepareStatement(
                "INSERT INTO users ( userName, pass ) " +
                        "VALUES ( ? , ? )" );

        sqlInsertJob = connection.prepareStatement(
                "INSERT INTO workon_copy ( jobNum, active, customer_ID, department_ID, partID, batchNum, qty_ordered,  machineID, qty_finished, qty_scrap, sop_ID, drawing_ID) " +
                        "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ?, ?, ?, ? )" );

        sqlInsertBlob =  connection.prepareStatement(
                "INSERT INTO workon_copy ( job_doc ) " +
                        "VALUES (? )" );

        sqlInsertDrawing =  connection.prepareStatement(
                "INSERT INTO technical_drawing ( customer_ID, drawingName, document_blob, dimension_json ) " +
                        "VALUES (? , ?, ?, ? )" );


        sqlInsertSOP =  connection.prepareStatement(
                "INSERT INTO sop_document ( customer_ID, sopName, document_blob ) " +
                        "VALUES (? , ?, ? )" );

        sqlInsertDimensions =  connection.prepareStatement(
                "INSERT INTO DIMENSIONS ( dimension_json ) " +
                        "VALUES (? )" );

        // locate person
//        sqlFind = connection.prepareStatement(
//            "SELECT Password" +
//                    "FROM users" +
//                    "WHERE Password = ?");
        sqlSingleFindPersonID = connection.prepareStatement("SELECT userName, pass FROM users WHERE userName = ? AND pass = ?");
        sqlFindPersonID = connection.prepareStatement("SELECT personID FROM users WHERE pass LIKE ?");
        sqlFindName = connection.prepareStatement("SELECT userName, pass FROM users WHERE pass = ?");
        sqlFindCustomer = connection.prepareStatement("SELECT cust_name, customer_ID FROM customer");
        sqlFindCustomerID = connection.prepareStatement("SELECT customer_ID FROM customer WHERE cust_name = ?");
        sqlFindSopByCustomer = connection.prepareStatement("SELECT sopName FROM sop_document WHERE customer_ID = ?");
        sqlFindDepartmentByCustomer = connection.prepareStatement("SELECT department_name FROM department");
        sqlFindTechDrawingByCustomer = connection.prepareStatement("SELECT drawingName FROM technical_drawing WHERE customer_ID = ?");
        sqlFindSopByName = connection.prepareStatement("SELECT sopID FROM sop_document WHERE sopName = ?");
        sqlFindTechDrawingByName = connection.prepareStatement("SELECT drawingID FROM technical_drawing WHERE drawingName = ?");
        sqlFindDepartmentByName = connection.prepareStatement("SELECT department_id FROM department WHERE department_name = ?");
        sqlFindMaxJobID = connection.prepareStatement("SELECT MAX(jobID) FROM workon_copy");
        sqlFindAllJobs = connection.prepareStatement("SELECT * FROM workon_copy");
        sqlFindDimensions = connection.prepareStatement("SELECT dimension_json FROM technical_drawing WHERE drawingID = ?");

        sqlReturnSOP = connection.prepareStatement("SELECT document_blob FROM sop_document WHERE sopName = ?");

        sqlReturnTechDrawing = connection.prepareStatement("SELECT document_blob FROM technical_drawing WHERE drawingName = ?");

        sqlPersonID = connection.prepareStatement("SELECT MAX(jobID) FROM workon_copy");

        // Insert first and last names in table names.
        // For referential integrity, this must be performed
        // before sqlInsertAddress, sqlInsertPhone and
        // sqlInsertEmail.
        sqlInsertName = connection.prepareStatement(
                "INSERT INTO names ( firstName, lastName ) " +
                        "VALUES ( ? , ? )" );

        sqlInsertUser = connection.prepareStatement(
                "INSERT INTO users ( userName, pass ) " +
                        "VALUES ( ? , ? )" );

        // insert address in table addresses
        sqlInsertAddress = connection.prepareStatement(
                "INSERT INTO addresses ( personID, address1, " +
                        "address2, city, state, zipcode ) " +
                        "VALUES ( ? , ? , ? , ? , ? , ? )" );

        // insert phone number in table phoneNumbers
        sqlInsertPhone = connection.prepareStatement(
                "INSERT INTO phoneNumbers " +
                        "( personID, phoneNumber) " +
                        "VALUES ( ? , ? )" );

        // insert email in table emailAddresses
        sqlInsertEmail = connection.prepareStatement(
                "INSERT INTO emailAddresses " +
                        "( personID, emailAddress ) " +
                        "VALUES ( ? , ? )" );

        // update first and last names in table names
        sqlUpdateName = connection.prepareStatement(
                "UPDATE names SET firstName = ?, lastName = ? " +
                        "WHERE personID = ?" );

        // update address in table addresses
        sqlUpdateAddress = connection.prepareStatement(
                "UPDATE addresses SET address1 = ?, address2 = ?, " +
                        "city = ?, state = ?, zipcode = ? " +
                        "WHERE addressID = ?" );

        // update phone number in table phoneNumbers
        sqlUpdatePhone = connection.prepareStatement(
                "UPDATE phoneNumbers SET phoneNumber = ? " +
                        "WHERE phoneID = ?" );

        // update email in table emailAddresses
        sqlUpdateEmail = connection.prepareStatement(
                "UPDATE emailAddresses SET emailAddress = ? " +
                        "WHERE emailID = ?" );

        // Delete row from table names. This must be executed
        // after sqlDeleteAddress, sqlDeletePhone and
        // sqlDeleteEmail, because of referential integrity.
        sqlDeleteName = connection.prepareStatement(
                "DELETE FROM names WHERE personID = ?" );

        // delete address from table addresses
        sqlDeleteAddress = connection.prepareStatement(
                "DELETE FROM addresses WHERE personID = ?" );

        // delete phone number from table phoneNumbers
        sqlDeletePhone = connection.prepareStatement(
                "DELETE FROM phoneNumbers WHERE personID = ?" );

        // delete email address from table emailAddresses
        sqlDeleteEmail = connection.prepareStatement(
                "DELETE FROM emailAddresses WHERE personID = ?" );
    }  // end DataBaseAccess constructor

    // Obtain a connection to addressbook database. Method may
    // may throw ClassNotFoundException or SQLException. If so,
    // exception is passed via this class's constructor back to
    // the AddressBook application so the application can display
    // an error message and terminate.
    private void connect() throws Exception
    {
        // Cloudscape database driver class name
        String driver = "com.mysql.jdbc.Driver";

        // URL to connect to books database
//        String url = "jdbc:mysql://localhost:3306/addressbook?autoReconnect=true&useSSL=false";
        String url = "jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false";

        // load database driver class
        Class.forName( driver );

        // connect to database
//        connection = DriverManager.getConnection( url, "root", "root" );
        connection = DriverManager.getConnection( url, "root", "test12" );

        // Require manual commit for transactions. This enables
        // the program to rollback transactions that do not
        // complete and commit transactions that complete properly.
        connection.setAutoCommit( false );
    }


    // Insert new User. Method returns boolean indicating
    // success or failure.
    public boolean newPerson( NewJobEntry person )
            throws DataAccessException
    {
        // insert person in database
        try {
            int result;

            // insert first and last name in names table
//            sqlInsertUser.setString( 1, person.getFirstName() );
//            sqlInsertUser.setString( 2, person.getLastName() );
            result = sqlInsertUser.executeUpdate();

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }

            connection.commit();   // commit insert
            return true;           // insert successful
            }

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                exception.printStackTrace();
                throw new DataAccessException( exception );
            }
        }
    }  // end method newUser


    // Insert new User. Method returns boolean indicating
    // success or failure.
    public Integer findMaxJobId()
    {
        // insert person in database
        try {
            Integer max =0;
            System.out.println("result");
            ResultSet resultSet = sqlFindMaxJobID.executeQuery();
            //System.out.println("result" + result);
            if (resultSet.next()) {
                System.out.println("result max");
                max = resultSet.getInt(1);
                System.out.println("result max" + max);
            }

            // if insert fails, rollback and discontinue
            if ( max == 0 ) {
                connection.rollback(); // rollback insert
                System.out.println("result for job id here 3");
                return null;          // insert unsuccessful
            }

            System.out.println("result for job id here 4");

            return max;           // insert successful
        }

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                System.out.println("result for job id 1111");
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return null;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                System.out.println("result for job id2222222222");
                exception.printStackTrace();
                return null;
            }
        }
    }  // end method newUser

    public ResultSet findAllJobs()
    {
        // insert person in database
        try {

            System.out.println("result");
            ResultSet resultSet = sqlFindAllJobs.executeQuery();
            //System.out.println("result" + result);
//            if (resultSet.next()) {
//                System.out.println("result max");
//
//            }

            // if insert fails, rollback and discontinue
            if (!resultSet.next()) {
                connection.rollback(); // rollback insert
                System.out.println("result for job id here 3");
                return null;          // insert unsuccessful
            }

            System.out.println("result for job id here 4");

            return resultSet;           // insert successful
        }

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                System.out.println("result for job id 1111");
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return null;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                System.out.println("result for job id2222222222");
                exception.printStackTrace();
                return null;
            }
        }
    }  // end method newUser

    // Locate specified User. Method returns AddressBookEntry
    // containing information.
    public ArrayList<NewJobEntry> findPerson( String username, String password )
    {
        try {
            // set query parameter and execute query
//            sqlFind.setString( 1, lastName);
//            ResultSet resultSet = sqlFind.executeQuery();

            //Working for just password field
//            sqlFindName.setString(1,password);
//            ResultSet resultSet = sqlFindName.executeQuery();
            sqlSingleFindPersonID.setString(1, username);
            sqlSingleFindPersonID.setString(2, password);
            ResultSet resultSet = sqlSingleFindPersonID.executeQuery();

            // if no records found, return immediately
            if ( !resultSet.next() ){
                System.out.println("nulll");
                return null;
            }

            System.out.println("hererererererer 3");
            ArrayList<NewJobEntry> arraylist = new ArrayList<>();
            System.out.println(resultSet.getString("userName"));
            System.out.println(resultSet.getString("pass"));

                NewJobEntry person = new NewJobEntry();
                // set AddressBookEntry properties
                person.setUserName(resultSet.getString("userName"));
//                System.out.println(person.getPassword());
                System.out.println("person == " + person.getUserName());
//                person.setFirstName(resultSet.getString(2));
//                person.setLastName(resultSet.getString(3));
//
//                person.setAddressID(resultSet.getInt(4));
//                person.setAddress1(resultSet.getString(5));
//                person.setAddress2(resultSet.getString(6));
//                person.setCity(resultSet.getString(7));
//                person.setState(resultSet.getString(8));
//                person.setZipcode(resultSet.getString(9));
//
//                person.setPhoneID(resultSet.getInt(10));
//                person.setPhoneNumber(resultSet.getString(11));
//
//                person.setEmailID(resultSet.getInt(12));
//                person.setEmailAddress(resultSet.getString(13));

                arraylist.add(person);

            return arraylist;
        }

        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findPerson

    // Insert new entry. Method returns boolean indicating
    // success or failure.
    public boolean newDocument(NewJobEntry job, int table )
            throws DataAccessException
    {
        // insert doucument in database
        try {
            System.out.println("database" + job.getCustomerName());

            sqlFindCustomerID.setString(1,job.getCustomerName());
            ResultSet resultSet = sqlFindCustomerID.executeQuery();

            // if no records found, return immediately
            if ( !resultSet.isBeforeFirst()){
                System.out.println("nulll");
                return false;
            }
            Integer cust_ID = 0;
            while(resultSet.next()) {
                cust_ID = resultSet.getInt("customer_ID");
            }

            int result = 0;
            String filePath = job.getDropPath().trim();
            InputStream inputStream = new FileInputStream(new File(filePath));

            //use the table value of 1 or 2 to decide if its a
            //sop or tech drawing and write to the correct table..
            if (table == 1){
                // insert into technical_drawing table
                sqlInsertDrawing.setInt( 1, cust_ID );
                sqlInsertDrawing.setString( 2, job.getTechniaclDrawing() );
                sqlInsertDrawing.setBlob( 3, inputStream );
                sqlInsertDrawing.setString( 4, job.getDimension());
                result = sqlInsertDrawing.executeUpdate();
            }
            else if (table == 2){
                // insert into SOP table
                sqlInsertSOP.setInt( 1, cust_ID );
                sqlInsertSOP.setString( 2, job.getPartSop() );
                sqlInsertSOP.setBlob( 3, inputStream );
                result = sqlInsertSOP.executeUpdate();
            }

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }
            connection.commit();   // commit insert
            return true;           // insert successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                System.out.println("failed here 5");
                return false;
            }
            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                exception.printStackTrace();
                throw new DataAccessException( exception );
            }
        }catch (IOException ex) {
            System.out.println("failed here 6");
            ex.printStackTrace();
            return false;
        }
    }  // end method newPerson


    // Locate specified User. Method returns AddressBookEntry
    // containing information.
    public ArrayList<String> findCustomer()
    {
        try {
             ResultSet resultSet = sqlFindCustomer.executeQuery();

            // if no records found, return immediately
            if ( !resultSet.isBeforeFirst()){
                System.out.println("nulll");
                return null;
            }

            System.out.println("hererererererer 3");
            ArrayList<String> arraylist = new ArrayList<>();
            //System.out.println(resultSet.getString("cust_name"));

            //NewJobEntry customer = new NewJobEntry();
            // set AddressBookEntry properties
//            for (int i = 0; i != resultSet.isLast() ; i++) {
//                System.out.println("customer names here");
//                System.out.println(customerNames.get(i));
//            }
            int i =0;
            String names;
            while(resultSet.next()){
                //NewJobEntry customer = new NewJobEntry();
                names =resultSet.getString("cust_name");
                arraylist.add(names);
                System.out.println("person == " + names);
            }
//            customer.setCustomerName(resultSet.getString("cust_name"));
//            System.out.println("person == " + customer.getCustomerName().toString());
//
//            arraylist.add(customer);

            return arraylist;
        }

        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findPerson

    // Locate specified Sop's for the correct customer only.
    public ArrayList<String> findSop(String custName )
    {
        try {
            sqlFindCustomerID.setString(1,custName);
            ResultSet resultSet2 = sqlFindCustomerID.executeQuery();

            // if no customer matching the ID is found, return immediately
            if ( !resultSet2.isBeforeFirst()){
                return null;
            }
            Integer cust_ID = 0;
            while(resultSet2.next()) {
                cust_ID = resultSet2.getInt("customer_ID");
            }

            sqlFindSopByCustomer.setInt(1,cust_ID);
            ResultSet resultSet = sqlFindSopByCustomer.executeQuery();

            // if no Sop's found, return immediately
            if ( !resultSet.isBeforeFirst()){
                return null;
            }
            ArrayList<String> sopList = new ArrayList<>();
            String names;
            while(resultSet.next()){
                names =resultSet.getString("sopName");
                sopList.add(names);
            }
            return sopList;
        }
        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findSop findDepartment

    // Locate specified Sop's for the correct customer only.
    public ArrayList<String> findDepartment( )
    {
        try {
//            sqlFindCustomerID.setString(1,custName);
//            ResultSet resultSet2 = sqlFindCustomerID.executeQuery();
//
//            // if no customer matching the ID is found, return immediately
//            if ( !resultSet2.isBeforeFirst()){
//                return null;
//            }
//            Integer cust_ID = 0;
//            while(resultSet2.next()) {
//                cust_ID = resultSet2.getInt("customer_ID");
//            }

//            sqlFindSopByCustomer.setInt(1,cust_ID);
            ResultSet resultSet = sqlFindDepartmentByCustomer.executeQuery();

            // if no Sop's found, return immediately
            if ( !resultSet.isBeforeFirst()){
                return null;
            }
            ArrayList<String> departmenList = new ArrayList<>();
            String names;
            while(resultSet.next()){
                names =resultSet.getString("department_name");
                departmenList.add(names);
                System.out.println(names);
            }
            return departmenList;
        }
        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findDepartment

    // Locate specified Sop's for the correct customer only.
    public ArrayList<String> findTechDrawing(String custName )
    {
        try {
            sqlFindCustomerID.setString(1,custName);
            ResultSet resultSet2 = sqlFindCustomerID.executeQuery();

            // if no customer matching the ID is found, return immediately
            if ( !resultSet2.isBeforeFirst()){
                return null;
            }
            Integer cust_ID = 0;
            while(resultSet2.next()) {
                cust_ID = resultSet2.getInt("customer_ID");
            }

            sqlFindTechDrawingByCustomer.setInt(1,cust_ID);
            ResultSet resultSet = sqlFindTechDrawingByCustomer.executeQuery();

            // if no Sop's found, return immediately
            if ( !resultSet.isBeforeFirst()){
                return null;
            }
            ArrayList<String> sopList = new ArrayList<>();
            String names;
            while(resultSet.next()){
                names =resultSet.getString("drawingName");
                sopList.add(names);
            }
            return sopList;
        }
        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findSop

    // Insert new entry. Method returns boolean indicating
    // success or failure.
    public boolean newUser( NewJobEntry person )
            throws DataAccessException
    {
        // insert person in database
        try {
            int result;

            // insert first and last name in names table
            sqlInsertUser.setString( 1, person.getUserName() );
            sqlInsertUser.setString( 2, person.getPassword() );
            result = sqlInsertUser.executeUpdate();

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }
                connection.commit();   // commit insert
                return true;           // insert successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                exception.printStackTrace();
                throw new DataAccessException( exception );
            }
        }
    }  // end method newPerson


    // Insert new entry. Method returns boolean indicating
    // success or failure.
    public boolean newDimensions( String jsonarray )
            throws DataAccessException
    {
        // insert person in database
        try {
            int result;

            // insert first and last name in names table
            sqlInsertDimensions.setString( 1, jsonarray );
            result = sqlInsertDimensions.executeUpdate();

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }
            connection.commit();   // commit insert
            return true;           // insert successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                exception.printStackTrace();
                throw new DataAccessException( exception );
            }
        }
    }  // end method newPerson

    public String sqlFindDimensions(int id)
    {
        // insert person in database
        try {

            System.out.println("result");
            sqlFindDimensions.setInt( 1, id );
            ResultSet resultSet = sqlFindDimensions.executeQuery();
            //System.out.println("result" + result);
//            if (resultSet.next()) {
//                System.out.println("result max");
//
//            }

            // if insert fails, rollback and discontinue
            if (!resultSet.next()) {
                connection.rollback(); // rollback insert
                System.out.println("result for job id here 3");
                return null;          // insert unsuccessful
            }

            String dimesions = resultSet.getString(1);

            System.out.println("result for job id here 4" + dimesions);

            return dimesions;           // insert successful
        }

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                System.out.println("result for job id 1111");
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return null;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                System.out.println("result for job id2222222222");
                exception.printStackTrace();
                return null;
            }
        }
    }  // end method newUser


    public void sqlGetTechDrawing(String name, int choice) {
        // insert person in database
        System.out.println("trying here");
        System.out.println(name);
        String pathto="";
        try {
            if (choice == 1) {
                sqlReturnTechDrawing.setString(1, name);
                ResultSet resultSet = sqlReturnTechDrawing.executeQuery();

                int i = 0;
                while (resultSet.next()) {
                    InputStream in = resultSet.getBinaryStream(1);
                    pathto = "C:\\EDHRHOME\\documents\\" + name;
                    System.out.println(pathto + "path to");
                    OutputStream f = new FileOutputStream(new File("C:\\EDHRHOME\\documents\\" + name));
                    i++;
                    int c = 0;
                    while ((c = in.read()) > -1) {
                        f.write(c);
                    }
                    f.close();
                    in.close();
                }
            }
            else{
                sqlReturnSOP.setString(1, name);
                ResultSet resultSet = sqlReturnSOP.executeQuery();
                int i = 0;
                while (resultSet.next()) {
                    InputStream in = resultSet.getBinaryStream(1);
                    pathto = "C:\\EDHRHOME\\documents\\" + name;
                    System.out.println(pathto + "path to");
                    OutputStream f = new FileOutputStream(new File("C:\\EDHRHOME\\documents\\" + name));
                    i++;
                    int c = 0;
                    while ((c = in.read()) > -1) {
                        f.write(c);
                    }
                    f.close();
                    in.close();
                }
            }
        }
        catch (Exception ee){
            ee.printStackTrace();
        }
        ViewFileDropped vf = new ViewFileDropped();
        vf.viewFileDropped(pathto);

    }






    // Update an entry. Method returns boolean indicating
    // success or failure.
    public boolean saveJob(NewJobEntry newJob )
            throws DataAccessException
    {
        // update person in database
        try {
            sqlFindSopByName.setString(1,newJob.getPartSop().trim());
            System.out.println("getPartSop ===============" + newJob.getPartSop());
            ResultSet resultSet2 = sqlFindSopByName.executeQuery();

            // if no customer matching the ID is found, return immediately
            if ( !resultSet2.isBeforeFirst()){
                System.out.println("failed 1");
                return false;
            }
            Integer sop_ID = 0;
            while(resultSet2.next()) {
                System.out.println("failed 2");
                sop_ID = resultSet2.getInt("sopID");
            }
            System.out.println("failed 3");
            sqlFindTechDrawingByName.setString(1,newJob.getTechniaclDrawing().trim());
            System.out.println("getTechniaclDrawing ===============" + newJob.getTechniaclDrawing());
            ResultSet resultSet3 = sqlFindTechDrawingByName.executeQuery();

            // if no customer matching the ID is found, return immediately
            if ( !resultSet3.isBeforeFirst()){
                System.out.println("failed 4");
                return false;
            }
            Integer drawing_ID = 0;
            while(resultSet3.next()) {
                System.out.println("failed 5");
                drawing_ID = resultSet3.getInt("drawingID");
            }

            System.out.println("failed 3");
            sqlFindDepartmentByName.setString(1,newJob.getDepartment().trim());
            System.out.println("getTechniaclDrawing ===============" + newJob.getDepartment());
            ResultSet resultSet4 = sqlFindDepartmentByName.executeQuery();

            // if no customer matching the ID is found, return immediately
            if ( !resultSet4.isBeforeFirst()){
                System.out.println("failed 7");
                return false;
            }
            Integer dept_ID = 0;
            while(resultSet4.next()) {
                System.out.println("failed 5");
                dept_ID = resultSet4.getInt("department_id");
            }


            System.out.println("failed 6");






            int result;
            //String filePath = person.getDropPath().trim();
            //InputStream inputStream = new FileInputStream(new File(filePath));

            // update work table with new job
            sqlInsertJob.setString( 1, newJob.getJobNumber() );
            sqlInsertJob.setString( 2, newJob.getActive() );
            sqlInsertJob.setString( 3, newJob.getCustomerName() );
            sqlInsertJob.setInt( 4, dept_ID );
            sqlInsertJob.setString( 5, newJob.getPartName() );
            sqlInsertJob.setInt( 6, newJob.getBatchNumber() );
            sqlInsertJob.setString( 7, newJob.getBatchQty() );
            sqlInsertJob.setInt( 8, newJob.getMachineID() );
            sqlInsertJob.setInt( 9, newJob.getQtyMade() );
            sqlInsertJob.setInt( 10, newJob.getQtyScrap() );
            sqlInsertJob.setInt( 11, sop_ID );
            sqlInsertJob.setInt( 12, drawing_ID );
            //sqlInsertJob.setBlob( 11, inputStream );
            result = sqlInsertJob.executeUpdate();

            // if update fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            connection.commit();   // commit update
            return true;           // update successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            System.out.println(sqlException);
            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch (SQLException exception) {
                System.out.println("failed here 5");

                throw new DataAccessException(exception);
            }
//        }catch (IOException ex) {
//            System.out.println("failed here 6");
//            ex.printStackTrace();
//            return false;
//        }
        }


    }  // end method saveJob
//
//    // Insert new entry. Method returns boolean indicating
//    // success or failure.
//    public boolean newPerson( AddressBookEntry person )
//            throws DataAccessException
//    {
//        // insert person in database
//        try {
//            int result;
//
//            // insert first and last name in names table
//            sqlInsertName.setString( 1, person.getFirstName() );
//            sqlInsertName.setString( 2, person.getLastName() );
//            result = sqlInsertName.executeUpdate();
//
//            // if insert fails, rollback and discontinue
//            if ( result == 0 ) {
//                connection.rollback(); // rollback insert
//                return false;          // insert unsuccessful
//            }
//
//            // determine new personID
//            ResultSet resultPersonID = sqlPersonID.executeQuery();
//
//            if ( resultPersonID.next() ) {
//                int personID =  resultPersonID.getInt( 1 );
//
//                // insert address in addresses table
//                sqlInsertAddress.setInt( 1, personID );
//                sqlInsertAddress.setString( 2,
//                        person.getAddress1() );
//                sqlInsertAddress.setString( 3,
//                        person.getAddress2() );
//                sqlInsertAddress.setString( 4,
//                        person.getCity() );
//                sqlInsertAddress.setString( 5,
//                        person.getState() );
//                sqlInsertAddress.setString( 6,
//                        person.getZipcode() );
//                result = sqlInsertAddress.executeUpdate();
//
//                // if insert fails, rollback and discontinue
//                if ( result == 0 ) {
//                    connection.rollback(); // rollback insert
//                    return false;          // insert unsuccessful
//                }
//
//                // insert phone number in phoneNumbers table
//                sqlInsertPhone.setInt( 1, personID );
//                sqlInsertPhone.setString( 2,
//                        person.getPhoneNumber() );
//                result = sqlInsertPhone.executeUpdate();
//
//                // if insert fails, rollback and discontinue
//                if ( result == 0 ) {
//                    connection.rollback(); // rollback insert
//                    return false;          // insert unsuccessful
//                }
//
//                // insert email address in emailAddresses table
//                sqlInsertEmail.setInt( 1, personID );
//                sqlInsertEmail.setString( 2,
//                        person.getEmailAddress() );
//                result = sqlInsertEmail.executeUpdate();
//
//                // if insert fails, rollback and discontinue
//                if ( result == 0 ) {
//                    connection.rollback(); // rollback insert
//                    return false;          // insert unsuccessful
//                }
//
//                connection.commit();   // commit insert
//                return true;           // insert successful
//            }
//
//            else
//                return false;
//        }  // end try
//
//        // detect problems updating database
//        catch ( SQLException sqlException ) {
//            // rollback transaction
//            try {
//                sqlException.printStackTrace();
//                connection.rollback(); // rollback update
//                return false;          // update unsuccessful
//            }
//
//            // handle exception rolling back transaction
//            catch ( SQLException exception ) {
//                exception.printStackTrace();
//                throw new DataAccessException( exception );
//            }
//        }
//    }  // end method newPerson
//
//    // Delete an entry. Method returns boolean indicating
//    // success or failure.
//    public boolean deletePerson( AddressBookEntry person )
//            throws DataAccessException
//    {
//        // delete a person from database
//        try {
//            int result;
//
//            // delete address from addresses table
//            sqlDeleteAddress.setInt( 1, person.getPersonID() );
//            result = sqlDeleteAddress.executeUpdate();
//
//            // if delete fails, rollback and discontinue
//            if ( result == 0 ) {
//                connection.rollback(); // rollback delete
//                return false;          // delete unsuccessful
//            }
//
//            // delete phone number from phoneNumbers table
//            sqlDeletePhone.setInt( 1, person.getPersonID() );
//            result = sqlDeletePhone.executeUpdate();
//
//            // if delete fails, rollback and discontinue
//            if ( result == 0 ) {
//                connection.rollback(); // rollback delete
//                return false;          // delete unsuccessful
//            }
//
//            // delete email address from emailAddresses table
//            sqlDeleteEmail.setInt( 1, person.getPersonID() );
//            result = sqlDeleteEmail.executeUpdate();
//
//            // if delete fails, rollback and discontinue
//            if ( result == 0 ) {
//                connection.rollback(); // rollback delete
//                return false;          // delete unsuccessful
//            }
//
//            // delete name from names table
//            sqlDeleteName.setInt( 1, person.getPersonID() );
//            result = sqlDeleteName.executeUpdate();
//
//            // if delete fails, rollback and discontinue
//            if ( result == 0 ) {
//                connection.rollback(); // rollback delete
//                return false;          // delete unsuccessful
//            }
//
//            connection.commit();   // commit delete
//            return true;           // delete successful
//        }  // end try
//
//        // detect problems updating database
//        catch ( SQLException sqlException ) {
//            // rollback transaction
//            try {
//                connection.rollback(); // rollback update
//                return false;          // update unsuccessful
//            }
//
//            // handle exception rolling back transaction
//            catch ( SQLException exception ) {
//                throw new DataAccessException( exception );
//            }
//        }
//    }  // end method deletePerson

    // method to close statements and database connection
    public void close()
    {
        // close database connection
        try {
            //connection.close();
            //sqlFind.close();
            sqlPersonID.close();
            sqlInsertName.close();
            sqlInsertAddress.close();
            sqlInsertPhone.close();
            sqlInsertEmail.close();
            sqlUpdateName.close();
            sqlUpdateAddress.close();
            sqlUpdatePhone.close();
            sqlUpdateEmail.close();
            sqlDeleteName.close();
            sqlDeleteAddress.close();
            sqlDeletePhone.close();
            sqlDeleteEmail.close();
            sqlInsertJob.close();
            connection.close();

            sqlInsertUser.close();
        }  // end try

        // detect problems closing statements and connection
        catch ( SQLException sqlException ) {
            System.out.println("here now");
            sqlException.printStackTrace();
        }
    }  // end method close

    // Method to clean up database connection. Provided in case
    // DataBaseAccess object is garbage collected.
    protected void finalize()
    {
        close();
    }
}  // end class DataBaseAccess
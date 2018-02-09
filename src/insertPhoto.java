import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class insertPhoto {


        public static void main(String[] args) {

            String url = "jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "test12";
            String filePath = "C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\https___www.vhi.ie_pdf_claims_healthstepsgold.pdf";
//            String filePath = "workIcon.png";

            try {

                // Cloudscape database driver class name
                String driver = "com.mysql.jdbc.Driver";

                // URL to connect to books database
//        String url = "jdbc:mysql://localhost:3306/addressbook?autoReconnect=true&useSSL=false";
                String urll = "jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false";

                // load database driver class
                try{
                    Class.forName( driver );
                }
                catch (Exception e){
                    System.out.println(e);
                }



                //private Connection connection;
                Connection conn = DriverManager.getConnection( url, "root", "test12" );
                //Connection conn = DriverManager.getConnection(url, user, password);

//                String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;";  // 10 MB
//                Statement stSetLimit = conn.createStatement();
//                stSetLimit.execute(querySetLimit);

                String sql = "INSERT INTO person (first_name, last_name, photo) values (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, "pdf");
                statement.setString(2, "Eagar");
                InputStream inputStream = new FileInputStream(new File(filePath));
                statement.setBlob(3, inputStream);

                int row = statement.executeUpdate();
                if (row > 0) {
                    System.out.println("A contact was inserted with photo image.");
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            try {
//                Connection conn = DriverManager.getConnection(url, user, password);
//
//                String sql = "INSERT INTO person (photo) values (LOAD_FILE(?))";
//                PreparedStatement statement = conn.prepareStatement(sql);
//
//                statement.setString(1, filePath);
//
//                int row = statement.executeUpdate();
//                if (row > 0) {
//                    System.out.println("A contact was inserted with photo image.");
//                }
//                conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
    }


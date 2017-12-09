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

            String filePath = "C:\\Users\\david\\Desktop\\Final_project_ENG_GUI\\src\\adddress_database_diag.PNG";
            try {
                Connection conn = DriverManager.getConnection(url, user, password);

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


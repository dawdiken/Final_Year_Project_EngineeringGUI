
import java.sql.*;
import java.io.*;
public class RetriveImagesMysql{
    public static void main(String[] args){
        System.out.println("Retrive Image Example!");
        String driverName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&use";
        String dbName = "test";
        String userName = "root";
        String password = "test12";
        Connection con = null;
        try{
            Class.forName(driverName);
            con = DriverManager.getConnection(url+dbName,userName,password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select photo from person where person_id = 15");
            int i = 0;
            while (rs.next()) {
                InputStream in = rs.getBinaryStream(1);
                OutputStream f = new FileOutputStream(new File("test"+i+".pdf"));
                i++;
                int c = 0;
                while ((c = in.read()) > -1) {
                    f.write(c);
                }
                f.close();
                in.close();
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

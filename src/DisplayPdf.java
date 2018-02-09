import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class DisplayPdf extends JFrame{
    JButton button, button1 ;
    JLabel label;
    JTextField jtf;

    public DisplayPdf(){
        super("retrieve image from database in java");

        button = new JButton("Retrieve");
        button.setBounds(250,300,100,40);

        button1 = new JButton("view");
        button1.setBounds(50,300,100,40);

        jtf = new JTextField();
        jtf.setBounds(360,310,100,20);

        label = new JLabel();
        label.setBounds(10,10,670,250);

        add(button);
        add(button1);
        add(label);
        add(jtf);
//        String pathtofile = "";

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                WindowsPlatformAppPDF(pathtofile);
            }
        });

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false","root","test12");
                    Statement st = con.createStatement();
//                    ResultSet rs = st.executeQuery("select * from myimages where ID = '"+jtf.getText()+"'");
                    ResultSet rs = st.executeQuery("select photo from person where person_id = 15");

                    String pathtofile = pdfsaveme(rs);
                    System.out.println("after func call " +  pathtofile);
                    WindowsPlatformAppPDF(pathtofile);

                    System.out.println("pathtofile" + pathtofile);
                    System.out.println(rs);
                    if(rs.next()){
                        byte[] img = rs.getBytes("photo");



                        //Resize The ImageIcon

                        ImageIcon image = new ImageIcon(img);
                        Image im = image.getImage();

                        Image myImg = im.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
                        ImageIcon newImage = new ImageIcon(myImg);
                        label.setIcon(newImage);
                    }

                    else{
                        JOptionPane.showMessageDialog(null, "No Data");
                    }


                }catch(Exception ex){
                    ex.printStackTrace();
                }


            }
        });

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#bdb76b"));
        setLocationRelativeTo(null);
        setSize(700,400);
        setVisible(true);
    }

    public String pdfsaveme(ResultSet rs) {
        String pathtofile = "";
        try {
            int i = 0;

            while (rs.next()) {
                InputStream in = rs.getBinaryStream(1);
                File out = new File("test");
                OutputStream f = new FileOutputStream(out+ ".pdf");
                i++;
                int c = 0;
                pathtofile = out.getAbsolutePath();
                System.out.println("path" + out.getAbsolutePath());
                while ((c = in.read()) > -1) {
                    f.write(c);
                }
                f.close();
                in.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pathtofile;
    }

    public void WindowsPlatformAppPDF(String pathto) {

            try {
//                C:\Users\G00070718\Desktop\project_gui\Final_Year_Project_EngineeringGUI\test.pdf


                String command = "rundll32 url.dll,FileProtocolHandler " + pathto + ".pdf";
                System.out.println(command);
                    Process p = Runtime
                            .getRuntime()
//                            .exec(command);
                            .exec(command);
                    p.waitFor();


                System.out.println("Done");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


    public static void main(String[] args){
        new DisplayPdf();
    }
}


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;

public class OpperatorGui extends JFrame
{
    private EngineeringDataAccess database;
    JTextField c[] = new JTextField[10];

    public OpperatorGui()
    {



        Vector<Object> columnNames = new Vector<Object>();
        Vector<Object> data = new Vector<Object>();

        try
        {
            database = new DataBaseAccess();
            ResultSet rs = database.findAllJobs();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names

            for (int i = 1; i <= columns; i++)
            {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next())
            {
                Vector<Object> row = new Vector<Object>(columns);

                for (int i = 1; i <= columns; i++)
                {
                    row.addElement( rs.getObject(i) );
                }

                data.addElement( row );
            }

            rs.close();
            database.close();
        }
        catch(Exception e)
        {
            System.out.println( e );
        }

        //  Create table with database data

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };

        DefaultTableModel model1 = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };



        JFrame frame = new JFrame("Login Form");
        //JLabel l1 = new JLabel("Login Form");
        //l1.setForeground(Color.blue);
        //l1.setFont(new Font("Serif", Font.BOLD, 100));
        JPanel mainPanel = new JPanel(new MigLayout("","[grow][grow]","[][grow][][][]"));
       // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel paintPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(new GridLayout(1,4));
        Border border = LineBorder.createGrayLineBorder();

        JLabel label1 = new JLabel("Job Number:", JLabel.LEFT);
        label1.setFont(new Font("Courier New", Font.BOLD, 26));
        label1.setForeground(Color.BLUE);
        //label1.setText("Job Number:");
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setBorder(border);
        label1.setOpaque(true);
        //label1.setForeground(Color.blue);
        label1.setBackground(Color.ORANGE);
        titlePanel.add(label1);

        JLabel label2 = new JLabel("Customer Name:", JLabel.LEFT);
        label2.setFont(new Font("Courier New", Font.BOLD, 26));
        label2.setForeground(Color.BLUE);
        //label2.setText("Customer Name:");
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.CENTER);
        label2.setBorder(border);
        //label1.setForeground(Color.blue);
        label2.setOpaque(true);
        label2.setBackground(Color.ORANGE);
        titlePanel.add(label2);

        JLabel label3 = new JLabel("Employee:",JLabel.LEFT);
        label3.setFont(new Font("Courier New", Font.BOLD, 26));
        label3.setForeground(Color.BLUE);
        //label3.setText("Employee:");
        label3.setHorizontalTextPosition(JLabel.CENTER);
        label3.setVerticalTextPosition(JLabel.CENTER);
        label3.setBorder(border);
        //label3.setMinimumSize(width, height);
        label3.setPreferredSize(new Dimension(400,50));
        //label3.setMaximumSize(width, height);
        //label1.setForeground(Color.blue);
        label3.setOpaque(true);
        label3.setBackground(Color.ORANGE);
        titlePanel.add(label3);

        JLabel label4 = new JLabel("QTY Finished:",JLabel.LEFT);
        label4.setFont(new Font("Courier New", Font.BOLD, 26));
        label4.setForeground(Color.BLUE);
        //label4.setText("QTY Finished");
        label4.setHorizontalTextPosition(JLabel.CENTER);
        label4.setVerticalTextPosition(JLabel.CENTER);
        label4.setBorder(border);
        //label1.setForeground(Color.blue);
        label4.setOpaque(true);
        label4.setBackground(Color.ORANGE);
        titlePanel.add(label4);



        JPanel tablePanel = new JPanel();
        JTable table = new JTable( model );
        table.setPreferredScrollableViewportSize(new Dimension(1500,100));
        table.setFillsViewportHeight(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        table.setDefaultRenderer(Integer.class, centerRenderer);
        JScrollPane scrollPane = new JScrollPane( table );
        tablePanel.add(scrollPane);

        String[] info =  parseDimensions();
        JPanel tablePanel2 = new JPanel(new MigLayout("wrap 4","[][]","[10][10]"));

        tablePanel2.add(new JLabel("Dimension"),"span 1,");
        tablePanel2.add(new JLabel("Measurement"),"span 2");
        tablePanel2.add(new JLabel("Tolerance"),"span 3,wrap");
        ArrayList<JTextField> cps = new ArrayList<>();//List to store JtextFields so i can access them to get text to write to the Database
        for (int i = 0; i <info.length ; i++) {
            JLabel l5[] =new JLabel[]{
                 new JLabel(),
                 //new JLabel("Dimension " +i + " = " +info[i])
                    new JLabel(info[i])
            };
            JTextField a[] = new JTextField[]
                    {
                            new JTextField(),
                            new JTextField("", 20)};
            JLabel b[] = new JLabel[]
                    {
                            new JLabel(),
                            new JLabel("  +/-0.10")
                    };
            cps.add(a[1]);
            tablePanel2.add( l5[1],"span 1,");
            tablePanel2.add(a[1],"span 2");
            tablePanel2.add(b[1],"span 3,wrap");
        }

        for (int i = 0; i <info.length ; i++) {

            System.out.println(cps.get(i).getText());

        }

        tablePanel2.setBorder(border);
        JScrollPane scrollPane1 = new JScrollPane( tablePanel2 );
        scrollPane1.setPreferredSize(new Dimension(600,200));

        JButton saveMeasurments = new JButton("Save Measurments");
        saveMeasurments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count =0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                for (int i = 0; i <cps.size() ; i++) {
                    System.out.println("in here action");
                    if (cps.get(i).getText().equals("")){
                        System.out.println(cps.get(i).getText());
                        JOptionPane.showMessageDialog(frame,"Dimension "+(i+1) + " must be filled in!!","Alert",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        count ++;
                        System.out.println(cps.get(i).getText());
                        //cps.get(i).setText("");
                    }
                    if (count==cps.size()){
                        JOptionPane.showMessageDialog(frame,"Mesurments saved succesfully","Success",JOptionPane.PLAIN_MESSAGE);
                        try {
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            FileWriter writer = new FileWriter("output.txt",true);
                            int num = 1;
                            for (JTextField str : cps) {
                                System.out.println(str.getText());

                                System.out.println("timeStamp" + timestamp);
                                writer.write("Dim " + num + ": "+  str.getText()+",\n");
                                num++;
                            }
                            writer.write("Opperator name Here: Date:" +  sdf.format(timestamp)+ "\n");
                            writer.close();
                        }
                        catch (IOException ee){
                            ee.printStackTrace();
                        }
                    }
                }
            }
        });

        JButton closeJob = new JButton("Close Job");
        closeJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int count =0;
                for (int i = 0; i <cps.size() ; i++) {
                    System.out.println("in here action");
                    if (cps.get(i).getText().equals("")){
                        System.out.println(cps.get(i).getText());
                        JOptionPane.showMessageDialog(frame,"Dimension "+(i+1) + " must be filled in!!","Alert",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        count ++;
                        System.out.println(cps.get(i).getText());
                        //cps.get(i).setText("");
                    }
                    if (count==cps.size()){
                        JOptionPane.showMessageDialog(frame,"Mesurments saved succesfully","Success",JOptionPane.PLAIN_MESSAGE);
                        try {
                            FileWriter writer = new FileWriter("output.txt",true);
                            int num = 1;
                            for (JTextField str : cps) {
                                System.out.println(str.getText());

                                writer.write("Dim " + num + ": "+  str.getText()+ ",\n");
                                num++;
                            }
                            writer.close();
                        }
                        catch (IOException ee){
                            ee.printStackTrace();
                        }
                    }
                }
            }
        });

        mainPanel.add(titlePanel,"wrap");
        mainPanel.add(tablePanel,"wrap");
        mainPanel.add(scrollPane1,"wrap");
        mainPanel.add(saveMeasurments,"wrap");
        mainPanel.add(closeJob,"wrap");

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        frame.setSize(1550  , 800);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public String[] parseDimensions (){
        String splitdime[] = {""};
        try {
            DataBaseAccess thisconn = new DataBaseAccess();

            //get the string from the database and format it for the gui removing andy characters i do not want to display
            String finalDimensions = thisconn.sqlFindDimensions(164).replaceAll("Dim", "Dimension ").replaceAll("\"", "").replace("[","").replace("]","");
            splitdime = finalDimensions.split(",");

            for (int i = 0; i < splitdime.length; i++) {
                System.out.println(splitdime[i]);
            }
        }
        catch (Exception ee){
                System.out.println(ee);
            }
        return splitdime;
    }

    public static void main(String[] args) {
        OpperatorGui asd = new OpperatorGui();
    }
}

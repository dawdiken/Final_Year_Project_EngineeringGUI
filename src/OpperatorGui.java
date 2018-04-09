import java.awt.*;
import java.sql.*;
import java.text.MessageFormat;
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
        for (int i = 0; i <10 ; i++) {
            System.out.println("info = " +info[i]);
        }

        for (int i = 0; i <info.length ; i++) {
            JLabel l5[] =new JLabel[]{
                 new JLabel(),
                 new JLabel("Dimension " +i + " = " +info[i])
            };
            JTextField a[] = new JTextField[]
                    {
                            new JTextField(),
                            //new JTextField(10),
                            //new JTextField("Hello"),
                            //new JTextField("Goodbye", 20),
                            new JTextField(info[i])};
            JTextField b[] = new JTextField[]
                    {
                            new JTextField(),
                            //new JTextField(10),
                            //new JTextField("Hello"),
                            //new JTextField("Goodbye", 20),
                            new JTextField(info[i])};
            tablePanel2.add( l5[1],"span 1,");
            tablePanel2.add(a[1],"span 2");
            tablePanel2.add(b[1],"span 3,wrap");
        }
//        JTextField a[] = new JTextField[]
//                {
//                        new JTextField(),
//                        //new JTextField(10),
//                        //new JTextField("Hello"),
//                        //new JTextField("Goodbye", 20),
//                        new JTextField(info[1], 20),
//                        new JTextField(info[2], 20),
//                        new JTextField(info[3], 20),
//                        new JTextField(info[4], 20)
//
//                };


//        for (int i = 0; i <4 ; i++) {
//            tablePanel2.add(a[i]);
////            tablePanel2.add(a[i]);
////            tablePanel2.add(a[i],2,2);
//        }

//        tablePanel2.add(a[1]);
//        tablePanel2.add(a[2]);
//        tablePanel2.add(a[3],2,2);
        //tablePanel2.add(a[4]);
        //tablePanel2.setPreferredSize(new Dimension(800,500));

        tablePanel2.setBorder(border);
        //tablePanel2.add(label4);
        JScrollPane scrollPane1 = new JScrollPane( tablePanel2 );
        scrollPane1.setPreferredSize(new Dimension(1000,500));
        //getContentPane().add( scrollPane );
        //tablePanel.add(scrollPane);




//        try {
//            if (! table1.print()) {
//                System.err.println("User cancelled printing");
//            }
//        } catch (java.awt.print.PrinterException e) {
//            System.err.format("Cannot print %s%n", e.getMessage());
//        }
//        MessageFormat header = new MessageFormat("Page {0,number,String}");
//        try {
//            table.print(JTable.PrintMode.FIT_WIDTH, header, null);
//        } catch (java.awt.print.PrinterException e) {
//            System.err.format("Cannot print %s%n", e.getMessage());
//        }
//        JOptionPane.showMessageDialog(frame, colValues.toString());



        mainPanel.add(titlePanel,"wrap");
        mainPanel.add(tablePanel,"wrap");
        mainPanel.add(scrollPane1,"wrap");


        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        frame.setSize(1550  , 800);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        OpperatorGui asd = new OpperatorGui();
       // asd.

    }

    public String[] parseDimensions (){
        String splitdime[] = {""};
        try {


            DataBaseAccess thisconn = new DataBaseAccess();

            String finalDimensions = thisconn.sqlFindDimensions(22);

            System.out.println("dimensions = " + finalDimensions);
            splitdime = finalDimensions.split(",");


            for (int i = 0; i < splitdime.length; i++) {
                System.out.println(splitdime[i]);
            }
            //return splitdime;
        }
        catch (Exception ee){
                System.out.println(ee);
            }

        return splitdime;
    }
}

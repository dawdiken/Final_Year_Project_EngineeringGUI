import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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

public class OppGui extends JFrame
{
    private EngineeringDataAccess database;
    JTextField c[] = new JTextField[10];
    JTable table = new JTable( );

    public OppGui(String jobNum, String userName)
    {
        NewJobEntry newJob = new NewJobEntry();
        try
        {
            database = new DataBaseAccess();
            ArrayList<NewJobEntry> jobInfo = database.findJobsByNumber(jobNum);
            newJob.setCustomerName(jobInfo.get(0).getCustomerName());
            newJob.setJobNumber(jobInfo.get(0).getJobNumber());
            newJob.setPartName(jobInfo.get(0).getPartName());
            newJob.setQtyOrdered(jobInfo.get(0).getQtyOrdered());
            newJob.setQtyMade(jobInfo.get(0).getQtyMade());
            newJob.setQtyScrap(jobInfo.get(0).getQtyScrap());
            newJob.setPartSop(jobInfo.get(0).getPartSop());
            newJob.setTechniaclDrawing(jobInfo.get(0).getTechniaclDrawing());
            newJob.setJobNumber(jobNum);
            newJob.setUserName(userName);
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        JFrame frame = new JFrame("Opperator");
        JPanel mainPanel = new JPanel(new MigLayout("","[grow][grow]","[][grow][][][]"));
        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(new GridLayout(1,4));
        Border border = LineBorder.createGrayLineBorder();

        JLabel label1 = new JLabel("Job Number:"+ newJob.getJobNumber(), JLabel.LEFT);
        label1.setFont(new Font("Courier New", Font.BOLD, 26));
        label1.setForeground(Color.BLUE);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setBorder(border);
        label1.setOpaque(true);
        label1.setBackground(Color.ORANGE);
        titlePanel.add(label1);

        JLabel label2 = new JLabel("Customer Name:"+ newJob.getCustomerName(), JLabel.LEFT);
        label2.setFont(new Font("Courier New", Font.BOLD, 26));
        label2.setForeground(Color.BLUE);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.CENTER);
        label2.setBorder(border);
        label2.setOpaque(true);
        label2.setBackground(Color.ORANGE);
        titlePanel.add(label2);

        JLabel label3 = new JLabel("Employee:"+ newJob.getUserName(),JLabel.LEFT);
        label3.setFont(new Font("Courier New", Font.BOLD, 26));
        label3.setForeground(Color.BLUE);
        label3.setHorizontalTextPosition(JLabel.CENTER);
        label3.setVerticalTextPosition(JLabel.CENTER);
        label3.setBorder(border);
        label3.setPreferredSize(new Dimension(400,50));
        label3.setOpaque(true);
        label3.setBackground(Color.ORANGE);
        titlePanel.add(label3);

        JLabel label4 = new JLabel("QTY Ordered:" + newJob.getQtyOrdered(),JLabel.LEFT);
        label4.setFont(new Font("Courier New", Font.BOLD, 26));
        label4.setForeground(Color.BLUE);
        label4.setHorizontalTextPosition(JLabel.CENTER);
        label4.setVerticalTextPosition(JLabel.CENTER);
        label4.setBorder(border);
        label4.setOpaque(true);
        label4.setBackground(Color.ORANGE);
        titlePanel.add(label4);

        String[] info =  parseDimensions(newJob);
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
        createFileForDim(cps,newJob);

        for (int i = 0; i <info.length ; i++) {

            System.out.println(cps.get(i).getText());

        }

        tablePanel2.setBorder(border);
        JScrollPane scrollPane1 = new JScrollPane( tablePanel2 );
        scrollPane1.setPreferredSize(new Dimension(600,200));

        JPanel tablePanel = new JPanel();
        JTable table = new JTable( populateTable(0, newJob) );
        table.setPreferredScrollableViewportSize(new Dimension(1500,100));
        table.setFillsViewportHeight(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        table.setDefaultRenderer(Integer.class, centerRenderer);
        JScrollPane scrollPane = new JScrollPane( table );
        tablePanel.add(scrollPane);

        JButton saveMeasurments = new JButton("Save Measurments");
        saveMeasurments.setPreferredSize(new Dimension(150,20));
        saveMeasurments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count =0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                for (int i = 0; i <cps.size() ; i++) {
                    System.out.println("in here action");
                    if (cps.get(i).getText().equals("")){
                        cps.get(i).setBackground(Color.red);
                        JOptionPane.showMessageDialog(frame,"Dimension "+(i+1) + " must be filled in!!","Alert",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        count ++;
                    }
                    if (count==cps.size()){
                        try {
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            FileWriter writer = new FileWriter("C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+".txt",true);
                            int num = 1;
                            for (JTextField str : cps) {
                                System.out.println(str.getText());
                                writer.write( str.getText()+",");
                                str.setBackground(Color.WHITE);
                                str.setText("");
                                num++;
                            }
                            writer.write(newJob.getUserName() + ","+ sdf.format(timestamp)+ "\n");
                            writer.close();
                        }
                        catch (IOException ee){
                            ee.printStackTrace();
                            JOptionPane.showMessageDialog(frame,"Mesurments save failed","Alert",JOptionPane.WARNING_MESSAGE);
                        }
                        JOptionPane.showMessageDialog(frame,"Mesurments saved succesfully","Success",JOptionPane.PLAIN_MESSAGE);
                    }
                }
                populateTable(1,newJob);
            }
        });

        JButton closeJob = new JButton("Close Job");
        closeJob.setPreferredSize(new Dimension(150,20));
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

        JButton recordScrap = new JButton("Record Scrap Part");
        recordScrap.setPreferredSize(new Dimension(150,20));
        recordScrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count =0;
                for (int i = 0; i <cps.size() ; i++) {
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
        mainPanel.add(saveMeasurments,"split 3");
        mainPanel.add(recordScrap,"split 3, gap 35");
        mainPanel.add(closeJob," split 3, gap 35");

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        frame.setSize(1550  , 800);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void createFileForDim(ArrayList<JTextField> cps, NewJobEntry newJob){
        try{
//            File file2 = new File("C:\\EDHRHOME\\NewJob\\"+ newJob.getJobNumber());
//            file2.mkdir();
//            if (!file2.exists()) {
//                if (file2.mkdir()) {
//                    System.out.println("Directory is created!");
//                }
//            }
            File logFile = new File("C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+".txt");
            if (!logFile.exists()) {
                BufferedWriter writer = null;
                writer = new BufferedWriter(new FileWriter(logFile));
                for (int i = 0; i < cps.size(); i++) {
                    writer.write("Dimension " + (i + 1) + ",");
                }
                writer.write("Opperator Name" + ",");
                writer.write("Date" + "," + "\n");
                writer.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] parseDimensions (NewJobEntry newJob){
        String splitdime[] = {""};
        try {
            DataBaseAccess thisconn = new DataBaseAccess();
            //get the string from the database and format it for the gui removing andy characters i do not want to display
            String finalDimensions = thisconn.sqlFindDimensions(Integer.parseInt(newJob.getTechniaclDrawing())).replaceAll("Dim", "Dimension ").replaceAll("\"", "").replace("[","").replace("]","");
            splitdime = finalDimensions.split(",");
        }
        catch (Exception ee){
                ee.printStackTrace();
            }
        return splitdime;
    }

    public DefaultTableModel populateTable(int choice,NewJobEntry newJob){
        String filePath = "C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+".txt";
        File file = new File(filePath);
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            // get the first line
            // get the columns name from the first line
            // set columns name to the jtable model
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split(",");
            model = (DefaultTableModel)table.getModel();
            model.setColumnIdentifiers(columnsName);

            // get lines from txt file
            Object[] tableLines = br.lines().toArray();



            // extratct data from lines
            // set data to jtable model
            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                if (choice==1){
                    if(i==tableLines.length-1) {
                        model.addRow(dataRow);
                    }
                }
                else {
                    model.addRow(dataRow);
                }
            }


        } catch (Exception ex) {

        }

        return model;
//        JPanel tablePanel = new JPanel();
//        JTable table = new JTable(  );
//        table.setPreferredScrollableViewportSize(new Dimension(1500,100));
//        table.setFillsViewportHeight(true);
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
//        table.setDefaultRenderer(String.class, centerRenderer);
//        table.setDefaultRenderer(Integer.class, centerRenderer);
//        JScrollPane scrollPane = new JScrollPane( table );
//        tablePanel.add(scrollPane);
    }


    public static void main(String[] args) {
        OppGui asd = new OppGui("95", "David");
    }
}

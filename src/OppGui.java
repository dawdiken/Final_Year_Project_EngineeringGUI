import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;

public class OppGui extends JFrame
{
    private EngineeringDataAccess database;
    JTextField c[] = new JTextField[10];
    JTable table = new JTable( );
    private JButton viewTechDrawing, viewSop;
    private Worker5 worker5;
    int finalvalue = 0;
    int scrapvalue = 0;

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

        tablePanel2.setBorder(border);
        JScrollPane scrollPane1 = new JScrollPane( tablePanel2 );
        scrollPane1.setPreferredSize(new Dimension(600,200));

        JPanel tablePanel = new JPanel();
        JTable table = new JTable( populateTable(0, newJob) );
        table.setPreferredScrollableViewportSize(new Dimension(1500,200));
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
                    if (cps.get(i).getText().equals("")){
                        cps.get(i).setBackground(Color.red);
                        JOptionPane.showMessageDialog(frame,"Dimension "+(i+1) + " must be filled in!!","Alert",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        count ++;
                    }
                    if (count==cps.size()){
                        try {
                            String inputValue = JOptionPane.showInputDialog("Enter Value Made");
                            finalvalue = finalvalue + Integer.parseInt(inputValue);
                            writeFinishedToDb(finalvalue,newJob.getJobNumber());
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            FileWriter writer = new FileWriter("C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+"\\"+newJob.getJobNumber()+".txt",true);
                            int num = 1;
                            for (JTextField str : cps) {
                                writer.write( str.getText()+",");
                                str.setBackground(Color.WHITE);
                                str.setText("");
                                num++;
                            }
                            writer.write(newJob.getUserName() + ","+ sdf.format(timestamp)+ "\n");
                            writer.close();
                            populateTable(1,newJob);
                        }
                        catch (IOException ee){
                            ee.printStackTrace();
                            JOptionPane.showMessageDialog(frame,"Mesurments save failed","Alert",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        JButton closeJob = new JButton("Close Job");
        closeJob.setPreferredSize(new Dimension(150,20));
        closeJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeJob(newJob);
                JOptionPane.showMessageDialog (null, "All information save to the cloud", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        JButton recordScrap = new JButton("Record Scrap Part");
        recordScrap.setPreferredSize(new Dimension(150,20));
        recordScrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Please input scrap quantity");
                scrapvalue = scrapvalue + Integer.parseInt(inputValue);
                String jobnum = newJob.getJobNumber();
                writeScrapToDb(scrapvalue,jobnum);
            }
        });
        JProgressBar jpb = new JProgressBar();
        jpb.setBackground(Color.red);
        JLabel progressLabel = new JLabel("Loading Bar: ");
        progressLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ));

        viewSop = new JButton("View document");
        viewSop.setPreferredSize(new Dimension(150,20));
        viewSop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker5 = new Worker5(2,jpb, newJob);
                worker5.execute();
            }
        });

        viewTechDrawing = new JButton("View Drawing");
        viewTechDrawing.setPreferredSize(new Dimension(150,20));
        viewTechDrawing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker5 = new Worker5(1, jpb,newJob);
                worker5.execute();
            }
        });

        mainPanel.add(titlePanel,"wrap");
        mainPanel.add(tablePanel,"wrap");
        mainPanel.add(scrollPane1,"wrap");
        mainPanel.add(saveMeasurments,"split 3");
        mainPanel.add(recordScrap,"split 3, gap 35");
        mainPanel.add(closeJob," split 3, gap 35,wrap");
        mainPanel.add(viewSop,"split 3");
        mainPanel.add(viewTechDrawing,"split 3,gap 35,wrap");
        mainPanel.add(progressLabel,"split 3");
        mainPanel.add(jpb," split 3, gap 35");

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        frame.setSize(1550  , 650);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void createFileForDim(ArrayList<JTextField> cps, NewJobEntry newJob){
        try{
            File logFile = new File("C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+"\\"+ newJob.getJobNumber()+".txt");
            File logFile2 = new File("C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber());
            if (!logFile2.exists()) {
                if (logFile2.mkdir()) {
                    System.out.println("Directory is created!");
                }
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
            thisconn.close();
        }
        catch (Exception ee){
                ee.printStackTrace();
            }
        return splitdime;
    }

    public void writeScrapToDb(int val, String jobNum) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false", "root", "test12");
            Statement st = con.createStatement();
            Integer rs = st.executeUpdate("UPDATE workon_copy SET qty_scrap ="+ val +" WHERE jobNum="+ jobNum);
            con.close();
            System.out.println(rs);
            if (rs==1){
                JOptionPane.showMessageDialog (null, "Scrap value saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception eee){
            eee.printStackTrace();
        }
    }

    public void writeFinishedToDb(int val, String jobNum) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false", "root", "test12");
            Statement st = con.createStatement();
            Integer rs = st.executeUpdate("UPDATE workon_copy SET qty_finished ="+ val +", active='true' WHERE jobNum="+ jobNum);
            con.close();
            System.out.println(rs);
            if (rs==1){
                JOptionPane.showMessageDialog (null, "Finished value saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception eee){
            eee.printStackTrace();
        }

    }

    private Integer closeJob(NewJobEntry newJob){
        String filePath = "C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+"\\"+newJob.getJobNumber()+".txt";
        try{
            InputStream inputStream = new FileInputStream(new File(filePath));
            Connection con = DriverManager.getConnection("jdbc:mysql://35.184.175.243:3306/engineering?autoReconnect=true&useSSL=false", "root", "test12");

            String sql = "UPDATE `workon_copy` SET `active`= ?, `job_doc`= ? WHERE jobNum ='"+newJob.getJobNumber()+"'";
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setString(1, "false");
            prest.setBlob(2, inputStream);
            prest.executeUpdate();
            con.close();
        }
        catch (Exception ee){
            ee.printStackTrace();
        }

        return 0;
    }

    public DefaultTableModel populateTable(int choice,NewJobEntry newJob){
        String filePath = "C:\\EDHRHOME\\NewJob\\"+newJob.getJobNumber()+"\\"+newJob.getJobNumber()+".txt";
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
            ex.printStackTrace();
        }
        return model;
    }

    public class Worker5 extends SwingWorker< String[], Integer> {
        private Worker5(Integer choice,JProgressBar jpb, NewJobEntry newJob) {
            this.choice = choice;
            this.jpb = jpb;
            this.newJob = newJob;
        }

        @Override
        public  String[] doInBackground() {
            String[] result3 = {};
            int i = 0;
            process(i);
            try {
                i=5;
                process(i);
                try {
                    database = new DataBaseAccess();
                    i=20;
                    process(i);
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
                try{
                    i=30;
                    process(i);
                    if (choice == 2){
                        database.sqlGetTechDrawingByID(Integer.parseInt(newJob.getPartSop()),2,newJob);
                    }
                    else{
                        i=40;
                        process(i);
                        database.sqlGetTechDrawingByID(Integer.parseInt(newJob.getTechniaclDrawing()),1,newJob);
                    }
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }

            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            i=100;
            process(i);
            return result3;
        }
        public void process(Integer chunks)
        {
            int val = chunks;
            jpb.setValue(val);
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
        }
        private Integer choice;
        private  JProgressBar jpb;
        private NewJobEntry newJob;
    }
}

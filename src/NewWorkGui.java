
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import javax.crypto.Cipher;
import javax.swing.*;
import java.beans.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewWorkGui extends JPanel {
    private EngineeringDataAccess database;
    private ProgressMonitor progressMonitor;
    private JButton saveNewJob, viewTechDrawing, viewSop;
    private JTextArea taskOutput;
    private JTextField JobNumField, batchQtyField;
    private JLabel jobNumLabel, customerLabel, batchQtyLabel,departmentLabel, sopLabel,partNameLabel,custLabel,progressLabel;
    private JPanel dataPanel;
    private Worker worker;
    private Worker2 worker1;
    private Worker3 worker3;
    private Worker4 worker4;
    private Worker5 worker5;
    private JComboBox<String> partNames, sopNames, depNames;
    private JFrame frame;


    public NewWorkGui(NewJobEntry job) {
       // super();
        JProgressBar jpb = new JProgressBar();
        jpb.setBackground(Color.red);
        progressLabel = new JLabel("Loading Bar: ");
        progressLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        dataPanel = new JPanel(new MigLayout("wrap 4","[grow][grow]","[][grow][][][]"));
        dataPanel.setPreferredSize(new Dimension(600,250));
        //dataPanel = new JPanel(new net.miginfocom.swing.MigLayout("wrap 4","[grow][grow]","[][grow][][][]"));
        //dataPanel = new JPanel();
        worker = new Worker();
        worker.execute();
        worker1 = new Worker2();
        worker1.execute();
        worker3 = new Worker3(job);
        worker3.execute();
        worker4 = new Worker4(job,jpb);
        worker4.execute();
        custLabel=new JLabel("Customer Name: ");
        customerLabel=new JLabel(job.getCustomerName());

        jobNumLabel = new JLabel("Job Number: ");
        jobNumLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        JobNumField = new JTextField( );
        JobNumField.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        JobNumField.setBackground(Color.white);

        partNameLabel = new JLabel("Part Name: ");
        partNameLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        partNames = new JComboBox<String>();

        sopLabel = new JLabel("Select SOP: ");
        sopLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        sopNames = new JComboBox<String>();

        departmentLabel = new JLabel("Select Department: ");
        departmentLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        depNames = new JComboBox<String>();
        batchQtyLabel = new JLabel("Set Bacth Quantity: ");
        batchQtyLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        batchQtyField = new JTextField( );
        batchQtyField.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        batchQtyField.setBackground(Color.white);
        saveNewJob = new JButton("Save New Work Order");
        saveNewJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getJobInfo(job,jpb);
            }
        });

        viewTechDrawing = new JButton("View Drawing");
        viewTechDrawing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker5 = new Worker5(1, jpb);
                worker5.execute();
                //database.sqlGetTechDrawing(partNames.getSelectedItem().toString(),1);
            }
        });

        viewSop = new JButton("View document");
        viewSop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker5 = new Worker5(2, jpb);
                worker5.execute();
                //database.sqlGetTechDrawing(sopNames.getSelectedItem().toString(),2);
            }
        });

        dataPanel.add(custLabel);
        dataPanel.add(customerLabel, "growx,growx,wrap" );
        dataPanel.add(jobNumLabel);
        dataPanel.add(JobNumField, "growx,growx,wrap");
        dataPanel.add(partNameLabel);
        dataPanel.add(partNames, "growx,growx");
        dataPanel.add(viewTechDrawing,"growx,growx,wrap");
        dataPanel.add(sopLabel);
        dataPanel.add(sopNames, "growx,growx");
        dataPanel.add(viewSop, "growx,growx,wrap");
        dataPanel.add(departmentLabel);
        dataPanel.add(depNames, "growx,growx,wrap");
        dataPanel.add(batchQtyLabel);
        dataPanel.add(batchQtyField, "growx,growx,wrap");
        dataPanel.add(saveNewJob, "wrap");
        dataPanel.add(progressLabel);
        dataPanel.add(jpb, "growx , gap 35,growx,wrap" );


        add(dataPanel);
    }

    public void createAndShowGUI(NewJobEntry job) {
        //Create and set up the window.
        frame = new JFrame("New Work Order Menu:");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(200,200);
        JComponent newContentPane = new NewWorkGui(job);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void saveNewJobToDB (NewJobEntry job){
        //boolean success;
        try {
            boolean success = database.saveJob(job);
            if(success){
                JOptionPane.showMessageDialog( dataPanel, "Success!\nNew works order saved to the database",
                        "Save successful", JOptionPane.PLAIN_MESSAGE );
                int jobnum = Integer.parseInt(job.getJobNumber()) + 1 ;
                String newValue = Integer.toString(jobnum);
                JobNumField.setText(newValue);
                batchQtyField.setText("");
            }
        }
        // detect problems saving job
        catch ( DataAccessException exception ) {
            JOptionPane.showMessageDialog( dataPanel, exception,
                    "Save failed", JOptionPane.ERROR_MESSAGE );
            exception.printStackTrace();
        }
    }

    private void getJobInfo(NewJobEntry job, JProgressBar jpb)
    {
        int val = 0;
        jpb.setValue(val);
        job.setCustomerName( job.getCustomerName());
        job.setJobNumber( JobNumField.getText() );
        job.setPartName( partNames.getSelectedItem().toString().replace(".pdf", "").replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
        val = 10;
        jpb.setValue(val);
        job.setTechniaclDrawing( partNames.getSelectedItem().toString() );
        job.setPartSop( sopNames.getSelectedItem().toString() );
        val = 30;
        jpb.setValue(val);
        job.setDepartment(depNames.getSelectedItem().toString() );
        job.setBatchQty( batchQtyField.getText() );
        job.setActive("false");
        job.setQtyMade(0);
        job.setQtyScrap(0);
        job.setBatchNumber(1234);
        job.setMachineID( 1 );
        val = 55;
        jpb.setValue(val);
        saveNewJobToDB(job);
        val = 85;
        jpb.setValue(val);
        val = 100;
        jpb.setValue(val);
    }

    private class Worker extends SwingWorker <Integer,Integer> {
        //get paths and filename to save as sent into the string worker
        private Worker() {
                    }

        @Override
        public  Integer doInBackground() {
            Integer jobNum = 0;
            try {
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
                try{
                    jobNum = database.findMaxJobId();
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
                jobNum++;
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            return jobNum;
        }

        @Override
        public void done() {
            try{
                Integer jobNum = get();
                JobNumField.setText(jobNum.toString());
                JobNumField.setEditable(false);
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private class Worker2 extends SwingWorker< String[], Integer> {
        private Worker2() {
        }

        @Override
        public  String[] doInBackground() {
            ArrayList<String> list = new ArrayList<String>();
            String[] result3 = {};
            try {
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
                try{
                    list = database.findDepartment();
                    for (int ii = 0; ii < list.size() ; ii++) {
                        result3 = list.toArray(new String[]{});
                    }
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            return result3;
        }

        @Override
        public void done() {
            try{
                String[] list = get();
                for (int i = 0; i <list.length ; i++) {
                    depNames.addItem(list[i]);
                }
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private class Worker3 extends SwingWorker< String[], Integer> {
        private Worker3(NewJobEntry job) {
            this.job = job;
        }

        @Override
        public  String[] doInBackground() {
            ArrayList<String> list = new ArrayList<String>();
            String[] result3 = {};
            try {
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
                try{
                    list = database.findTechDrawing(job.getCustomerName());
                    for (int ii = 0; ii < list.size() ; ii++) {
                        result3 = list.toArray(new String[]{});
                    }
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            return result3;
        }

        public void process(Integer chunks)
        {
            int val = chunks;
            setProgress(val);
        }

        @Override
        public void done() {
            try{
                String[] list2 = get();
                for (int i = 0; i <list2.length ; i++) {
                    partNames.addItem(list2[i]);
                }
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
        }
        private NewJobEntry job;
    }

    private class Worker4 extends SwingWorker< String[], Integer> {
        private Worker4(NewJobEntry job,JProgressBar jpb) {
            this.job = job;
            this.jpb = jpb;
        }

        @Override
        public  String[] doInBackground() {
            ArrayList<String> list = new ArrayList<String>();
            String[] result3 = {};
            int i = 0;
            process(i);
            try {
                i=5;
                process(i);
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
                i=25;
                process(i);
                try{
                    list = database.findSop(job.getCustomerName());
                    for (int ii = 0; ii < list.size() ; ii++) {
                        result3 = list.toArray(new String[]{});
                        i++;
                        process(i);
                    }
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }

            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            i=70;
            process(i);
            i=80;
            process(i);
            i=100;
            process(i);
            return result3;
        }

        //        @Override
        public void process(Integer chunks)
        {
            int val = chunks;
            jpb.setValue(val); // The last value in this array is all we care about.
        }

        @Override
        public void done() {
            try{
                String[] list = get();
                for (int i = 0; i <list.length ; i++) {
                    sopNames.addItem(list[i]);
                }
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
        }
        private NewJobEntry job;
        private  JProgressBar jpb;
    }

    private class Worker5 extends SwingWorker< String[], Integer> {
        private Worker5(Integer choice,JProgressBar jpb) {
            this.choice = choice;
            this.jpb = jpb;
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
                        database.sqlGetTechDrawing(sopNames.getSelectedItem().toString(),2);
                    }
                    else{
                        i=40;
                        process(i);
                        database.sqlGetTechDrawing(partNames.getSelectedItem().toString(),1);
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
    }
}

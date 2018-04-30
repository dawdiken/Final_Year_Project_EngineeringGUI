
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import javax.crypto.Cipher;
import javax.swing.*;
import java.beans.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewWorkGui extends JPanel
        implements ActionListener,
        PropertyChangeListener {
    private EngineeringDataAccess database;
    private ProgressMonitor progressMonitor;
    private JButton selectWorksOrder, saveNewJob;
    private JTextArea taskOutput;
    private JTextField JobNumField, batchQtyField;
    private JLabel jobNumLabel, customerLabel, batchQtyLabel,departmentLabel, sopLabel,partNameLabel,custLabel;
    private JPanel dataPanel;
    private Worker worker;
    private JComboBox<String> partNames, sopNames, depNames;
    private JFrame frame;


    public NewWorkGui(NewJobEntry job) {
       // super();

//        JFrame frame = new JFrame("Login Form");
        dataPanel = new JPanel(new MigLayout("wrap 4","[grow][grow]","[][grow][][][]"));
        //dataPanel = new JPanel(new net.miginfocom.swing.MigLayout("wrap 4","[grow][grow]","[][grow][][][]"));
        //dataPanel = new JPanel();

        custLabel=new JLabel("Customer Name: ");
        customerLabel=new JLabel(job.getCustomerName());

        jobNumLabel = new JLabel("Job Number: ");
        jobNumLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
//        jobNumLabel.setBackground(Color.white);

        JobNumField = new JTextField( );
        JobNumField.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        JobNumField.setBackground(Color.white);

        ////////////////////////////////////////////////////////////////////
        //worker stuff here
        Integer jobNum = 0;
            try {
                database = new DataBaseAccess();
            }
            // detect problems with database connection
            catch ( Exception exception ) {
                exception.printStackTrace();
                System.exit( 1 );
            }
            System.out.println("job numeber search");
            try{
                jobNum = database.findMaxJobId();
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            jobNum++;
        JobNumField.setText(jobNum.toString());
        JobNumField.setEditable(false);
        /////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //Get the part name
        partNameLabel = new JLabel("Part Name: ");
        partNameLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        ArrayList<String> PartNames = new ArrayList<>();
        String[] result = {};
        PartNames = database.findTechDrawing(job.getCustomerName());
        for (int i = 0; i < PartNames.size() ; i++) {
            result = PartNames.toArray(new String[]{});
        }
        //result.toString().replace("[", "").replace("]", "");
        partNames = new JComboBox<String>(result);

        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //Get sop
        sopLabel = new JLabel("Select SOP: ");
        sopLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        ArrayList<String> SopNames = new ArrayList<>();
        String[] result2 = {};
        SopNames = database.findSop(job.getCustomerName());

        for (int i = 0; i < SopNames.size() ; i++) {
            result2 = SopNames.toArray(new String[]{});
        }
        //result.toString().replace("[", "").replace("]", "");
        sopNames = new JComboBox<String>(result2);

        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //Get dept
        departmentLabel = new JLabel("Select Department: ");
        departmentLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        ArrayList<String> DeptNames = new ArrayList<>();
        String[] result3 = {};
        DeptNames = database.findDepartment();

        for (int i = 0; i < DeptNames.size() ; i++) {
            result3 = DeptNames.toArray(new String[]{});
        }
        //result.toString().replace("[", "").replace("]", "");
        depNames = new JComboBox<String>(result3);

        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //Get dept

        batchQtyLabel = new JLabel("Set Bacth Quantity: ");
        batchQtyLabel.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        batchQtyField = new JTextField( );
        batchQtyField.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        batchQtyField.setBackground(Color.white);


        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //Button
        saveNewJob = new JButton("Save New Work Order");
        saveNewJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getJobInfo(job);
            }
        });






//        worker = new Worker(job);
//        worker.addPropertyChangeListener(this);
//        worker.execute();




        dataPanel.add(custLabel, "" );
        dataPanel.add(customerLabel, "growx , gap 35,growx,wrap" );
        dataPanel.add(jobNumLabel, "" );
        dataPanel.add(JobNumField, "growx , gap 35,growx,wrap");
        dataPanel.add(partNameLabel);
        dataPanel.add(partNames, "growx , gap 35,growx,wrap");
        dataPanel.add(sopLabel);
        dataPanel.add(sopNames, "growx , gap 35,growx,wrap");
        dataPanel.add(departmentLabel);
        dataPanel.add(depNames, "growx , gap 35,growx,wrap");
        dataPanel.add(batchQtyLabel);
        dataPanel.add(batchQtyField, "growx , gap 35,growx,wrap");
        dataPanel.add(saveNewJob, "growx , gap 35,growx,wrap");




        add(dataPanel);

       // setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//        List<String> list = new ArrayList<String>();
//        list = CloudContents();
//        taskOutput.append("Archive Contents = \n");
//        for (String number : list) {
//            taskOutput.append(number+ "\n");
//        }
    }

    public void actionPerformed(ActionEvent evt) {
        progressMonitor = new ProgressMonitor(NewWorkGui.this,
                "Encrypting and pushing files to the cloud",
                "", 0, 100);
        progressMonitor.setProgress(0);
        SendToCloud();
    }

    /**
     * Invoked when worker's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            if (progress<35){
                taskOutput.append("Folder selected\n");
            }
            else if (progress<65){
                taskOutput.append("Encrypting Data\n");
            }
            else if (progress<100){
                taskOutput.append("Pushing to cloud\n");
            }
            progressMonitor.setProgress(progress);
            String message =
                    String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            taskOutput.append(message);
            if (progressMonitor.isCanceled() || worker.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    worker.cancel(true);
                    taskOutput.append("Worker canceled.\n");
                } else {
                    taskOutput.append("Files Stored To Cloud.\n");
                }
                selectWorksOrder.setEnabled(true);
            }
        }
    }

    public void createAndShowGUI(NewJobEntry job) {
        //Create and set up the window.
        frame = new JFrame("New Work Order Menu:");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComponent newContentPane = new NewWorkGui(job);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void SendToCloud() {
        JFileChooser jfc = new JFileChooser("C:\\EDHRHOME\\FinishedWorksOrders");
        jfc.setDialogTitle("Choose Works Order to Archive: ");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = jfc.showDialog(null, "Select Folder");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isDirectory()) {
                String pathToFolder = jfc.getSelectedFile().toString();
                String saveAs = jfc.getSelectedFile().getName();
                System.out.println("pathtofolder2" + saveAs);
                //check that you are only trying to zip and push finished works orders to the cloud
                if (pathToFolder.contains("C:\\EDHRHOME\\FinishedWorksOrders\\")){
//                    worker = new Worker(pathToFolder,saveAs);
//                    worker.addPropertyChangeListener(this);
//                    worker.execute();
//                    selectWorksOrder.setEnabled(false);
                }
            }
        }
    }

//    private void ZippFolder(String pathToFolder, String saveAs){
//        ZipUtils appZip = new ZipUtils();
//        appZip.generateFileList(new File(pathToFolder),pathToFolder);
//        appZip.zipIt("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs,pathToFolder );//default location where zippe file will always be
//    }

//    private void EncryptFolder(String saveAs){
//        String key = "This is a secret";
//        File encryptedFile = new File("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs+".encrypted");
//        File inputFile = new File("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs);
//        EncryptFiles encryptMyFolder = new EncryptFiles();
//        encryptMyFolder.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
//    }

//    private void StoreInCloud(String saveAs){
//        List<String> list = new ArrayList<String>();
//        try{
//            CloudStorageHelper.uploadFile("longtermstorageedhr", "C:\\EDHRHOME\\ArchivedFiles\\"+saveAs+".encrypted");
//        }
//        catch(Exception ee){
//            ee.printStackTrace();
//        }
//    }

//    private List<String> CloudContents(){
//        List<String> list = new ArrayList<String>();
//        try{
//            list = CloudStorageHelper.listBucket("longtermstorageedhr");
//        }
//        catch(Exception ee){
//            ee.printStackTrace();
//        }
//        return list;
//    }


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

    private void getJobInfo(NewJobEntry job)
    {
        job.setCustomerName( job.getCustomerName());
        job.setJobNumber( JobNumField.getText() );
        job.setPartName( partNames.getSelectedItem().toString().replace(".pdf", "").replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
        job.setTechniaclDrawing( partNames.getSelectedItem().toString() );
        job.setPartSop( sopNames.getSelectedItem().toString() );
        job.setDepartment(depNames.getSelectedItem().toString() );
        job.setBatchQty( batchQtyField.getText() );
        job.setActive("false");
        job.setQtyMade(0);
        job.setQtyScrap(0);
        job.setBatchNumber(1234);
        job.setMachineID( 1 );
        saveNewJobToDB(job);
    }

    private class Worker extends SwingWorker< List<String>, String> {
        //get paths and filename to save as sent into the string worker
        private Worker(NewJobEntry job) {
            this.job = job;
        }

        @Override
        public  List<String> doInBackground() {
            setProgress(0);
            Integer jobNum = 0;
            List<String> list = new ArrayList<String>();
            try {
                //setProgress(5);
                int i = 5;
                process(i);
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                    System.exit( 1 );
                }
                System.out.println("job numeber search");
                try{
                    jobNum = database.findMaxJobId();
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
                jobNum++;

                //setProgress(25);
                i = 25;
                process(i);
//                EncryptFolder(saveAs);
                i = 45;
                process(i);
                //setProgress(45);
                i = 70;
                process(i);
                //setProgress(70);
//                StoreInCloud(saveAs);
                i = 80;
                process(i);
                //setProgress(85);
                i = 100;
                process(i);
                //setProgress(100);
//                list = CloudContents();

            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            return list;
        }

        //        @Override
        public void process(Integer chunks)
        {
            // define what the event dispatch thread
            // will do with the intermediate results received
            // while the thread is executing
            int val = chunks;
            setProgress(val);
            System.out.println("val"+val);
        }

        @Override
        public void done() {
            try{
                List<String> list = get();
                taskOutput.append("\nArchive Contents = \n");
                for (String number : list) {
                    taskOutput.append(number+ "\n");
                }
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
            selectWorksOrder.setEnabled(true);
        }
        private String pathToFolder;
        private String saveAs;
        private NewJobEntry job;
    }

    private class Worker2 extends SwingWorker< List<String>, String> {
        //get paths and filename to save as sent into the string worker
        private Worker2(NewJobEntry job) {
            this.job = job;
        }

        @Override
        public  List<String> doInBackground() {
            setProgress(0);
            Integer jobNum = 0;
            List<String> list = new ArrayList<String>();
            try {
                //setProgress(5);
                int i = 5;
                process(i);
                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch ( Exception exception ) {
                    exception.printStackTrace();
                    System.exit( 1 );
                }
                System.out.println("job numeber search");
                try{
                    jobNum = database.findMaxJobId();
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
                jobNum++;

                //setProgress(25);
                i = 25;
                process(i);
//                EncryptFolder(saveAs);
                i = 45;
                process(i);
                //setProgress(45);
                i = 70;
                process(i);
                //setProgress(70);
//                StoreInCloud(saveAs);
                i = 80;
                process(i);
                //setProgress(85);
                i = 100;
                process(i);
                //setProgress(100);
//                list = CloudContents();

            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
            return list;
        }

        //        @Override
        public void process(Integer chunks)
        {
            // define what the event dispatch thread
            // will do with the intermediate results received
            // while the thread is executing
            int val = chunks;
            setProgress(val);
            System.out.println("val"+val);
        }

        @Override
        public void done() {
            try{
                List<String> list = get();
                taskOutput.append("\nArchive Contents = \n");
                for (String number : list) {
                    taskOutput.append(number+ "\n");
                }
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
            selectWorksOrder.setEnabled(true);
        }
        private String pathToFolder;
        private String saveAs;
        private NewJobEntry job;
    }
}

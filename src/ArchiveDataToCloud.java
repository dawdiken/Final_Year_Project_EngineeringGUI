import java.awt.*;
import java.awt.event.*;
import javax.crypto.Cipher;
import javax.swing.*;
import java.beans.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDataToCloud extends JPanel
        implements ActionListener,
        PropertyChangeListener {

    private ProgressMonitor progressMonitor;
    private JButton selectWorksOrder;
    private JTextArea taskOutput;
    private Worker worker;

    public ArchiveDataToCloud() {
        super(new BorderLayout());

        selectWorksOrder = new JButton("Click to choose a Works order");
        selectWorksOrder.setActionCommand("start");
        selectWorksOrder.addActionListener(this);

        taskOutput = new JTextArea(20, 80);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        add(selectWorksOrder, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<String> list = new ArrayList<String>();
        list = CloudContents();
        taskOutput.append("Archive Contents = \n");
        for (String number : list) {
            taskOutput.append(number+ "\n");
        }
    }

    public void actionPerformed(ActionEvent evt) {
        progressMonitor = new ProgressMonitor(ArchiveDataToCloud.this,
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

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Save Works Order to the cloud");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComponent newContentPane = new ArchiveDataToCloud();
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
                    worker = new Worker(pathToFolder,saveAs);
                    worker.addPropertyChangeListener(this);
                    worker.execute();
                    selectWorksOrder.setEnabled(false);
                }
            }
        }
    }

    private void ZippFolder(String pathToFolder, String saveAs){
        ZipUtils appZip = new ZipUtils();
        appZip.generateFileList(new File(pathToFolder),pathToFolder);
        appZip.zipIt("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs,pathToFolder );//default location where zippe file will always be
    }

    private void EncryptFolder(String saveAs){
        String key = "This is a secret";
        File encryptedFile = new File("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs+".encrypted");
        File inputFile = new File("C:\\EDHRHOME\\ArchivedFiles\\"+saveAs);
        EncryptFiles encryptMyFolder = new EncryptFiles();
        encryptMyFolder.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
    }

    private void StoreInCloud(String saveAs){
        List<String> list = new ArrayList<String>();
        try{
            CloudStorageHelper.uploadFile("longtermstorageedhr", "C:\\EDHRHOME\\ArchivedFiles\\"+saveAs+".encrypted");
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
    }

    private List<String> CloudContents(){
        List<String> list = new ArrayList<String>();
        try{
            list = CloudStorageHelper.listBucket("longtermstorageedhr");
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
        return list;
    }

    private class Worker extends SwingWorker< List<String>, String> {
        //get paths and filename to save as sent into the string worker
        private Worker(String pathToFolder, String saveAs) {
            this.pathToFolder = pathToFolder;
            this.saveAs = saveAs;
        }

        @Override
        public  List<String> doInBackground() {
            setProgress(0);
            List<String> list = new ArrayList<String>();
            try {
                //setProgress(5);
                int i = 5;
                process(i);
                ZippFolder(pathToFolder,saveAs);
                //setProgress(25);
                i = 25;
                process(i);
                EncryptFolder(saveAs);
                i = 45;
                process(i);
                //setProgress(45);
                i = 70;
                process(i);
                //setProgress(70);
                StoreInCloud(saveAs);
                i = 80;
                process(i);
                //setProgress(85);
                i = 100;
                process(i);
                //setProgress(100);
                list = CloudContents();

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
    }
}

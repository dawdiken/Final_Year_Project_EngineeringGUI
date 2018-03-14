/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;
import javax.crypto.Cipher;
import javax.swing.*;
import java.beans.*;
import java.io.File;

public class ArchiveDataToCloud extends JPanel
        implements ActionListener,
        PropertyChangeListener {

    private ProgressMonitor progressMonitor;
    private JButton selectWorksOrder;
    private JTextArea taskOutput;
    private Worker worker;

    private class Worker extends SwingWorker<String, String> {
        //get paths and filename to save as sent into the string worker
        public Worker(String pathToFolder, String saveAs) {
            this.pathToFolder = pathToFolder;
            this.saveAs = saveAs;
        }

        @Override
        public String doInBackground() {
            setProgress(0);
            try {
                setProgress(5);
                String zippedAs = ZippFolder(pathToFolder,saveAs);
                setProgress(25);
                String encryptAs = EncryptFolder(saveAs);
                setProgress(45);
                setProgress(65);
                String pushToCloud = StoreInCloud(saveAs);
                setProgress(85);
                setProgress(100);
            } catch (Exception ignore) {}
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            selectWorksOrder.setEnabled(true);
        }
        private String pathToFolder;
        private String saveAs;
    }

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

    }


    /**
     * Invoked when the user presses the start button.
     */
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


        if ("progress" == evt.getPropertyName() ) {
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new ArchiveDataToCloud();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public void SendToCloud() {
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

    private String ZippFolder(String pathToFolder, String saveAs){
        ZipUtils appZip = new ZipUtils();
        appZip.generateFileList(new File(pathToFolder));
        appZip.zipIt("C:\\EDHRHOME\\"+saveAs);//default location where zippe file will always be
        return null;
    }

    private String EncryptFolder(String saveAs){
        String key = "This is a secret";
        File encryptedFile = new File("C:\\EDHRHOME\\"+saveAs+".encrypted");
        File inputFile = new File("C:\\EDHRHOME\\"+saveAs);
        EncryptFiles encryptMyFolder = new EncryptFiles();
        encryptMyFolder.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
        return null;
    }

    private String StoreInCloud(String saveAs){
        try{
            CloudStorageHelper.uploadFile("longtermstorageedhr", "C:\\EDHRHOME\\"+saveAs+".encrypted");
        }
        catch(Exception ee){
            System.out.println(ee);
        }
        return null;
    }
}

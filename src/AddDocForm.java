import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddDocForm  extends JInternalFrame {
    private EngineeringDataAccess database;

    public AddDocForm(){
        Loader l = new Loader();
        int choice = 1;
        SwingWorker work = l.createWorker(choice, null);
        work.execute();
        NewJobEntry job = new NewJobEntry();

        String[] choices = {"SOP", "Technical Drawing"};
        final JComboBox<String> cb2 = new JComboBox<String>(choices);
        JLabel label1 = new JLabel("Document type:");

        cb2.setMaximumSize(cb2.getPreferredSize());
        JPanel myPanel = new JPanel();
        myPanel.add(label1);
        myPanel.add(cb2);
        try {
            database = new DataBaseAccess();
        }
        // detect problems with database connection
        catch ( Exception exception ) {
            exception.printStackTrace();
        }

        ArrayList<String> customerNames = database.findCustomer();
        String[] result = {};
        String result2 = "";
        String[] result3 = {};
        for (int i = 0; i < customerNames.size() ; i++) {
            result = customerNames.toArray(new String[]{});
        }
        try{
           result2 = work.get().toString().replace("[", "").replace("]", "");
        }
        catch (Exception exception){
            exception.printStackTrace();
        }

        String[] ary = result2.split(",");

        JComboBox<String> cb3 = new JComboBox<String>(ary);
        JLabel label2 = new JLabel("Customer Name:");

        cb3.setMaximumSize(cb3.getPreferredSize()); // added code
        myPanel.add(label2);
        myPanel.add(cb3);

        JLabel label = new JLabel( "Drag and Drop:" );
        label.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        myPanel.add( label );

        JTextArea field = new JTextArea( 5, 30 );
        field.setBorder(
                BorderFactory.createMatteBorder( 2, 2, 2, 2 , Color.black) );

        myPanel.add( field );

        new FileDrop( System.out, field, /*dragBorder,*/ new FileDrop.Listener()
        {
            public void filesDropped( java.io.File[] files )
            {
                for( int i = 0; i < files.length; i++ )
                {
                    String path = "";
                    try
                    {
                        field.setText("");
                        field.append( files[i].getCanonicalPath() + "\n" );
                        field.setBorder(
                                BorderFactory.createMatteBorder( 2, 2, 2, 2 , Color.green) );
                        path = files[i].getCanonicalPath();
                    }   // end try
                    catch( IOException e ) {
                        e.printStackTrace();
                    }
                    ViewFileDropped v1 = new ViewFileDropped();
                    v1.viewFileDropped(path);
                }
            }
        });
        //final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\New-file-icon.png");
        final ImageIcon icon = new ImageIcon("C:\\EDHRHOME\\Icons\\New-file-icon.png");
        int jpane = JOptionPane.showConfirmDialog(null, myPanel,
                "Drag And Drop New Documents", JOptionPane.OK_CANCEL_OPTION, 1, icon );
        if (jpane == JOptionPane.OK_OPTION) {
            String cust_name = cb3.getSelectedItem().toString().trim();
            String document_type = cb2.getSelectedItem().toString().trim();

            //Quick easy way to get the file name and type from the file path
            File f = new File(field.getText().trim());
            String fileName = f.getName();

            //set values for the job object to be passed to database.newDocument function
            int table = 0;
            job.setCustomerName(cust_name);
            job.setDropPath(field.getText());
            try{
                if(document_type.equals("Technical Drawing")){
                    //set values for the job object to be passed to database.newDocument function
                    table = 1;//flag to decide which table to write the document to (sop or technical drawing)
                    job.setTechniaclDrawing(fileName);
                    try{
                        String pathtoUpload = job.getDropPath().trim();
                        CloudStorageHelper.uploadFile("vision_fyp", pathtoUpload);
                        DimensionVisionAPI getDim = new DimensionVisionAPI();
                        getDim.dimensionVisionAPI(fileName, job);
                    }
                    catch (Exception ee){
                        ee.printStackTrace();
                    }
                    database.newDocument(job ,table);
                }
               else if (document_type.equals("SOP")){
                    table = 2;//flag to decide which table to write the document to (sop or technical drawing)
                    job.setPartSop(fileName);
                    JOptionPane.showMessageDialog (null, "Document saved to database", "Success", JOptionPane.INFORMATION_MESSAGE);
                    database.newDocument(job ,table);
                }
            }
            catch (DataAccessException ee){
                ee.printStackTrace();
            }
        }
    }
}
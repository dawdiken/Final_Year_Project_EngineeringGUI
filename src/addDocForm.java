import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class addDocForm  extends JInternalFrame {
    private EngineeringDataAccess database;
    //private NewJobEntry job;

    public addDocForm(){
        NewJobEntry job = new NewJobEntry();

        String[] choices = {"SOP", "Technical Drawing"};
        final JComboBox<String> cb2 = new JComboBox<String>(choices);
        JLabel label1 = new JLabel("Document type:");

        cb2.setMaximumSize(cb2.getPreferredSize()); // added code
        //cb2.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        //cb.setVisible(true); // Not needed
        JPanel myPanel = new JPanel();
        myPanel.add(label1);
        myPanel.add(cb2);
        try {
            database = new DataBaseAccess();
        }
        // detect problems with database connection
        catch ( Exception exception ) {
            exception.printStackTrace();
            System.exit( 1 );
        }
//        Loader l = new Loader();
//        SwingWorker work = l.createWorker();
        //work.execute();
//        try{
//            ArrayList<String> customerNames2;
//            String[] result2 = {};
//
//            System.out.println("this is work "+ work.get().toString());
//        }
//        catch( Exception ee){
//            System.out.println(ee);
//
//
//        }


        ArrayList<String> customerNames = database.findCustomer();
        String[] result = {};
        String result2 = "";
        String[] result3 = {};
        //System.out.println("where is the customers" + customerNames.get(2).getCustomerName().toString());
        for (int i = 0; i < customerNames.size() ; i++) {
            result = customerNames.toArray(new String[]{});
        }
        try{
          // result2 = work.get().toString();
        }
        catch (Exception ee){
            System.out.println(ee);
        }

        String[] ary = result2.split(",");

        System.out.println("result 2" + result2);
        System.out.println("result " + result.toString());
        System.out.printf("ary = "+ ary);

        //String[] choices = {"SOP", "Technical Drawing"};
        JComboBox<String> cb3 = new JComboBox<String>(result);
        JLabel label2 = new JLabel("Customer Name:");

        cb3.setMaximumSize(cb3.getPreferredSize()); // added code
        //cb2.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        //cb.setVisible(true); // Not needed
        myPanel.add(label2);
        myPanel.add(cb3);

//        JTextField xField = new JTextField(10);
//        xField.setText("Administrator");
//        JPasswordField yField = new JPasswordField(10);
//        //JPanel myPanel = new JPanel();
//        myPanel.add(new JLabel("Doument Type:"));
//        myPanel.add(xField);
//        myPanel.add(xField);
//
//        myPanel.add(Box.createHorizontalStrut(10)); // a spacer
//        myPanel.add(new JLabel("Password:"));
//        myPanel.add(yField);
        //final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\workIcon.png");


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
                        path = files[i].getCanonicalPath();
                    }   // end try
                    catch( java.io.IOException e ) {
                        System.out.println("Drop failed/n" + e);
                    }

                    ViewFileDropped v1 = new ViewFileDropped();
                    v1.ViewFileDropped(path);
                }   // end for: through each dropped file
            }   // end filesDropped
        }); // end FileDrop.Listener

        final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\New-file-icon.png");
        int jpane = JOptionPane.showConfirmDialog(null, myPanel,
                "Drag And Drop New Documents", JOptionPane.OK_CANCEL_OPTION, 1, icon );
        if (jpane == JOptionPane.OK_OPTION) {
            String cust_name = cb3.getSelectedItem().toString().trim();
            String document_type = cb2.getSelectedItem().toString().trim();

            System.out.println("documetnt TYPE!!!!"+ document_type);

            //Quick easy way to get the file name and type from the file path
            File f = new File(field.getText().trim());
            String fileName = f.getName();

            //set values for the job object to be passed to database.newTechDrawing function
            int table = 0;
            try{
                if(document_type == "Technical Drawing"){
                    //set values for the job object to be passed to database.newTechDrawing function
                    table = 1;//flag to decide which table to write the document to (sop or technical drawing)
                    job.setCustomerName(cust_name);
                    job.setDropPath(field.getText());
                    job.setTechniaclDrawing(fileName);
                    database.newTechDrawing(job ,table );
                }
               else if (document_type == "SOP"){
                    table = 2;//flag to decide which table to write the document to (sop or technical drawing)
                    job.setCustomerName(cust_name);
                    job.setDropPath(field.getText());
                    job.setPartSop(fileName);
                    System.out.println("sOPPPPP");
                    database.newTechDrawing(job ,table );
                }
            }
            catch (DataAccessException ee){
                System.out.println(ee);
            }
        }
    }
}
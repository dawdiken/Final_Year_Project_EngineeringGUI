// Java core packages
import java.util.*;
import java.awt.*;

// Java extension packages
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class NewJobFrame extends JInternalFrame {
    //database connection
    private EngineeringDataAccess database;

    // HashMap to store JTextField references for quick access
    private HashMap fields;

    // current AddressBookEntry set by AddressBook application
    private NewJobEntry job;

    // panels to organize GUI
    private JPanel leftPanel, rightPanel, middlePanel,bottomPanel;
//    private JComboBox jcomp1;
//    private JPanel leftPanel_2, rightPanel_2;

    // static integers used to determine new window positions
    // for cascading windows
    private static int xOffset = 0, yOffset = 0;

    // static Strings that represent name of each text field.
    // These are placed on JLabels and used as keys in
    // HashMap fields.
    private static final String
            JOB_NUMBER = "Job number:",
            CUSTOMER_NAME = "Customer Name:",
            CUSTOMER_PART = "Customer Part:",
            PART_SOP = "Part SOP:",
            TECH_DRAWING = "Technical Drawing:",
            BATCH_QTY = "Batch QTY:",
            DEPARTMENT = "Department:",
            DRAG_DROP = "Drag and Drop:";



    private int rowCount = 12;
    private int height = 500;
    private Container container;

    // construct GUI
    public NewJobFrame(int id, String custName)
    {
        super( "Creat a new job for customer = " + custName , true, true );

        fields = new HashMap();

        leftPanel = new JPanel();
        leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        middlePanel = new JPanel();
        middlePanel.setLayout( new GridLayout( rowCount, 100, 0, 5 ) );
        rightPanel = new JPanel();
        rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        bottomPanel = new JPanel();
        bottomPanel.setLayout( new GridLayout( 50, 1, 0, 5 ) );

//        leftPanel_2 = new JPanel();
//        leftPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
//        rightPanel_2 = new JPanel();
//        rightPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

        createOption(CUSTOMER_NAME, 1, custName);
        createOption( CUSTOMER_PART, 2, null);
        createOption( PART_SOP, 3, custName);
        createOption( TECH_DRAWING, 3, custName);
        createOption( DEPARTMENT , 4, null);
        createRow(JOB_NUMBER);
        createRow( BATCH_QTY);
        createTable(JOB_NUMBER);
        //createDragDrop( DRAG_DROP );

        container = getContentPane();
        container.add( leftPanel, BorderLayout.WEST );
        container.add( middlePanel, BorderLayout.CENTER );
        container.add( rightPanel, BorderLayout.EAST );
        container.add( bottomPanel,400,400 );

        setBounds( xOffset, yOffset, 600, height );
        xOffset = ( xOffset + 30 ) % 300;
        yOffset = ( yOffset + 30 ) % 300;
    }


//     set AddressBookEntry then use its properties to
//     place data in each JTextField
    public void setAddressBookEntry( NewJobEntry entry )
    {
        job = entry;

//        setField( FIRST_NAME, job.getCustomerName() );
//        setField( CUSTOMER_NAME, job.getPartName() );
        //setField( JOB_NUMBER, job.getJobNumber() );
        setOption( CUSTOMER_NAME, job.getCustomerName() );
        setOption( CUSTOMER_PART, job.getPartName() );
        setOption( PART_SOP, job.getPartSop() );
        setOption( DEPARTMENT, job.getDepartment() );
        setField( BATCH_QTY, job.getBatchQty() );
        //setField( DRAG_DROP, job.getBatchQty() );
//        setField( PHONE, job.getPhoneNumber() );
////        setField( PHONE_1, job.getPhoneNumber() );
//        setField( EMAIL, job.getEmailAddress() );
    }
//
//    // store AddressBookEntry data from GUI and return
//    // AddressBookEntry
    public NewJobEntry getAddressBookEntry()
    {
        //job.setJobId();
        job.setJobNumber( getField( JOB_NUMBER ) );
        job.setActive("false");

        job.setCustomerName( getOption( CUSTOMER_NAME ) );
        job.setDepartment( getOption( DEPARTMENT ) );
        job.setPartName( getOption( CUSTOMER_PART ) );
        job.setPartSop( getOption( PART_SOP ) );
        job.setTechniaclDrawing( getOption( TECH_DRAWING ) );
        job.setBatchNumber(1234);
        job.setBatchQty( getField( BATCH_QTY ) );
        job.setMachineID(1);
        job.setQtyMade(0);
        job.setQtyScrap(0);
        //job.setDropPath( getDropped( DRAG_DROP ) );

        return job;
    }

    // set text in JTextField by specifying field's
    // name and value
    private void setField( String fieldName, String value )
    {
        JTextField field =
                ( JTextField ) fields.get( fieldName );

        field.setText( value );
    }

    private void setOption( String fieldName, String value )
    {

        System.out.printf("TESTTTTTT");
        JComboBox field =
                ( JComboBox ) fields.get( fieldName );
        Object obj = field.getSelectedItem();
        System.out.println("obj= " +obj);
        String name = obj.toString();
//        String name = field.getSelectedItem().toString();
//        String text = mySpinner.getSelectedItem().toString();

//        return field.getSelectedItem().toString();
        field.setSelectedItem( value );
    }

    // get text in JTextField by specifying field's name
    private String getField( String fieldName )
    {
        JTextField field =
                ( JTextField ) fields.get( fieldName );

        return field.getText();
    }

    private String getDropped( String fieldName )
    {
        JTextArea field =
                ( JTextArea ) fields.get( fieldName );

        return field.getText();
    }
    private String getOption( String fieldName )
    {
        JComboBox field =
                ( JComboBox ) fields.get( fieldName );
        Object obj = field.getSelectedItem();
        System.out.println("obj= " +obj);
        String name = obj.toString();
//        String name = field.getSelectedItem().toString();
//        String text = mySpinner.getSelectedItem().toString();

        return field.getSelectedItem().toString();
//        return name;
    }
    // utility method used by constructor to create one row in
    // GUI containing JLabel and JTextField
    private void createRow( String name )
    {

        JLabel label = new JLabel( name, SwingConstants.RIGHT );
        label.setBorder(
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        leftPanel.add( label );

        JTextField field = new JTextField( );

        Integer jobNum = 0;
        //String jobnn = "";
        if (name.equals(JOB_NUMBER)){
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
            catch (Exception ee){
                System.out.println(ee);
            }
            System.out.println("jobNum" + jobNum);
            jobNum++;
            field.setText(jobNum.toString());
            field.setEditable(false);
            //field.setText("why wont you work");
        }
        middlePanel.add( field );
        fields.put( name, field );
    }

    private void createTable( String name )
    {

        final String[] columnNames = { "First Name", "Last Name", "Sport",
                "# of Years", "Vegetarian" };

        final Object[][] data = {
                { "Mary", "Campione", "Snowboarding", new Integer(5),
                        new Boolean(false) },
                { "Alison", "Huml", "Rowing", new Integer(3), new Boolean(true) },
                { "Kathy", "Walrath", "Knitting", new Integer(2), new Boolean(false) },
                { "Sharon", "Zakhour", "Speed reading", new Integer(20),
                        new Boolean(true) },
                { "Philip", "Milne", "Pool", new Integer(10), new Boolean(false) } };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Ignore extra messages.
                if (e.getValueIsAdjusting())
                    return;

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                }
            }
        });

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel.
        add(scrollPane);
        bottomPanel.add( scrollPane );
        //fields.put( name, field );
    }

//    private void createDragDrop( String name )
//    {
//        JLabel label = new JLabel( name, SwingConstants.RIGHT );
//        label.setBorder(
//                BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
//        leftPanel.add( label );
//
//        JTextArea field = new JTextArea(  );
//        fields.put( name, field );
//        field.setBorder(
//                BorderFactory.createMatteBorder( 1, 1, 1, 1 , Color.black) );
//
//        middlePanel.add( field );
//
//
//        new FileDrop( System.out, field, /*dragBorder,*/ new FileDrop.Listener()
//        {
//            public void filesDropped( java.io.File[] files )
//            {
//                for( int i = 0; i < files.length; i++ )
//                {
//                    String path = "";
//                    try
//                    {
//                        field.setText("");
//                        field.append( files[i].getCanonicalPath() + "\n" );
//                        path = files[i].getCanonicalPath();
//                    }   // end try
//                    catch( java.io.IOException e ) {
//                        System.out.println("Drop failed/n" + e);
//                    }
//
//                    ViewFileDropped v1 = new ViewFileDropped();
//                    v1.ViewFileDropped(path);
//                }   // end for: through each dropped file
//            }   // end filesDropped
//        }); // end FileDrop.Listener
//    }

    private void createOption( String name, int option, String custName )
    {
        int choice = 0;
        switch (option){
            case 1:
                String[] choices = { custName };
                final JComboBox<String> cb = new JComboBox<String>(choices);

                cb.setMaximumSize(cb.getPreferredSize()); // added code
                cb.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb);

                JLabel label = new JLabel( name, SwingConstants.RIGHT );
                label.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label );



//                JButton btn = new JButton("OK");
//                btn.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
//                rightPanel.add(btn);


                fields.put( name, cb );
                break;
                case 2:
                    String[] choices2 = { "Job 1", "Job 2", "Job 3", "Job 4",
                            "Job 5", "Job 6" };
                    final JComboBox<String> cb2 = new JComboBox<String>(choices2);

                    cb2.setMaximumSize(cb2.getPreferredSize()); // added code
                    cb2.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                    //cb.setVisible(true); // Not needed
                    middlePanel.add(cb2);

                    JLabel label1 = new JLabel( name, SwingConstants.RIGHT );
                    label1.setBorder(
                            BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                    leftPanel.add( label1 );
//
//                    JButton btn1 = new JButton("OK");
//                    btn1.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
//                    rightPanel.add(btn1);

    //        frame.setVisible(true); // added code
    //        rightPanel.add( jcomp1 );

                    fields.put( name, cb2 );
                    break;
            case 3:
                if (name.equals(PART_SOP)){
                    choice = 2;//flag to tell the swing worker which table to read from
                }
                else if (name.equals(TECH_DRAWING)){
                    choice = 3;//flag to tell the swing worker which table to read from
                }

                Loader l = new Loader();
                System.out.println("passed in" + custName.toString());
                SwingWorker work = l.createWorker(choice, custName);
                work.execute();
                String result2 = "";
                try{
                    ArrayList<String> customerNames2;

                    System.out.println("this is work "+ work.get().toString());
                }
                catch( Exception ee){
                    System.out.println(ee);

                }
                try{
                    result2 = work.get().toString().replace("[", "").replace("]", "");
                }
                catch (Exception ee){
                    System.out.println(ee);
                }
                String[] ary = result2.split(",");

//                String[] choices3 = { "SOP 1", "SOP 2", "SOP 3", "SOP 4",
//                        "SOP 5", "SOP 6" };
                final JComboBox<String> cb3 = new JComboBox<String>(ary);

                cb3.setMaximumSize(cb3.getPreferredSize()); // added code
                cb3.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb3);

                JLabel label3 = new JLabel( name, SwingConstants.RIGHT );
                label3.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label3 );

//                JButton btn3 = new JButton("OK");
//                btn3.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
//                rightPanel.add(btn3);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

                fields.put( name, cb3);
                break;
            case 4:
                String[] choices4 = { "TURNING", "MILLING", "PASAVATION", "LASER MARKING", "QUALITY"};
                final JComboBox<String> cb4 = new JComboBox<String>(choices4);

                cb4.setMaximumSize(cb4.getPreferredSize()); // added code
                cb4.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb4);

                JLabel label4 = new JLabel( name, SwingConstants.RIGHT );
                label4.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label4 );

//                JButton btn4 = new JButton("OK");
//                btn4.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
//                rightPanel.add(btn4);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

                fields.put( name, cb4);
                break;
        }
    }


    private String[] findCustomers (){
        try {
            database = new DataBaseAccess();
        }
        // detect problems with database connection
        catch ( Exception exception ) {
            exception.printStackTrace();
            System.exit( 1 );
        }



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
        return result;
    }
}  // end class AddressBookEntryFrame

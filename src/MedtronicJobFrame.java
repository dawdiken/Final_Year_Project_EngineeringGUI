// Java core packages
import java.util.*;
import java.awt.*;

// Java extension packages
import javax.swing.*;

public class MedtronicJobFrame extends JInternalFrame {

    // HashMap to store JTextField references for quick access
    private HashMap fields;

    // current AddressBookEntry set by AddressBook application
    private NewJobEntry job;

    // panels to organize GUI
    private JPanel leftPanel, rightPanel, middlePanel;
//    private JComboBox jcomp1;
//    private JPanel leftPanel_2, rightPanel_2;

    // static integers used to determine new window positions
    // for cascading windows
    private static int xOffset = 0, yOffset = 0;

    // static Strings that represent name of each text field.
    // These are placed on JLabels and used as keys in
    // HashMap fields.
    private static final String
            JOB_NUMBER = "Job numer",
            CUSTOMER_NAME = "Customer Name",
            CUSTOMER_PART = "Customer Part",
            PART_SOP = "Part SOP:",
            BATCH_QTY = "Batch QTY:",
            DEPARTMENT = "Department";
//            EIRCODE = "Eircode",
//            ADDRESS1_1 = "Address 1(2)",
//            ADDRESS2_1 = "Address 2(2)",
//            CITY_1 = "City(2)",
//            STATE_1 = "State(2)",
//            EIRCODE_1 = "Eircode(2)",
//            ADDRESS1_2 = "Address 1(3)",
//            ADDRESS2_2 = "Address 2(3)",
//            CITY_2 = "City(3)",
//            STATE_2 = "State(3)",
//            EIRCODE_2 = "Eircode(3)",
//            PHONE = "Phone", EMAIL = "Email",
//            PHONE_1 = "Phone(2)", EMAIL_1 = "Email(2)",
//            PHONE_2 = "Phone(3)", EMAIL_2 = "Email(3)";


    private int rowCount = 12;
    private int height = 500;
    private Container container;

    // construct GUI
    public MedtronicJobFrame()
    {
        super( "New Job customer: MEDTRONIC", true, true );

        fields = new HashMap();

        leftPanel = new JPanel();
        leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        middlePanel = new JPanel();
        middlePanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        rightPanel = new JPanel();
        rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

//        leftPanel_2 = new JPanel();
//        leftPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
//        rightPanel_2 = new JPanel();
//        rightPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

//        createRow( FIRST_NAME , 1);

        createOption(CUSTOMER_NAME, 1);
        createOption( CUSTOMER_PART, 2);
        createOption( PART_SOP, 3);
        createOption( DEPARTMENT , 4);
        createRow(JOB_NUMBER);
        createRow( BATCH_QTY);

//        createRow( EIRCODE , 1);
//        createRow( PHONE , 1);
//        createRow( EMAIL , 1);

        container = getContentPane();
        container.add( leftPanel, BorderLayout.WEST );
        container.add( middlePanel, BorderLayout.CENTER );
        container.add( rightPanel, BorderLayout.EAST );


        setBounds( xOffset, yOffset, 300, height );
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
        setField( JOB_NUMBER, job.getJobNumber() );
        setOption( CUSTOMER_NAME, job.getCustomerName() );
        setOption( CUSTOMER_PART, job.getPartName() );
        setOption( PART_SOP, job.getPartSop() );
        setOption( DEPARTMENT, job.getDepartment() );
        setField( BATCH_QTY, job.getBatchQty() );
//        setField( PHONE, job.getPhoneNumber() );
////        setField( PHONE_1, job.getPhoneNumber() );
//        setField( EMAIL, job.getEmailAddress() );
    }
    //
//    // store AddressBookEntry data from GUI and return
//    // AddressBookEntry
    public NewJobEntry getAddressBookEntry()
    {
        System.out.println("TEST11111");
        job.setCustomerName( getOption( CUSTOMER_NAME ) );
        System.out.println("test2222222222");
        job.setPartName( getOption( CUSTOMER_PART ) );
        System.out.println("test3333333333");
        job.setPartSop( getOption( PART_SOP ) );
        System.out.println("Test44444");
        job.setDepartment( getOption( DEPARTMENT ) );
        job.setJobNumber( getField( JOB_NUMBER ) );
        job.setBatchQty( getField( BATCH_QTY ) );
//        job.setState( getField( STATE ) );
//        job.setZipcode( getField(EIRCODE) );
//        job.setPhoneNumber( getField( PHONE ) );
////        job.setPhoneNumber( getField( PHONE_1 ) );
//        job.setEmailAddress( getField( EMAIL ) );

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
    private String getOption( String fieldName )
    {

        System.out.printf("TESTTTTTT");
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

        JTextField field = new JTextField( 30 );
        middlePanel.add( field );

        fields.put( name, field );
    }

    private void createOption( String name, int option )
    {
        switch (option){
            case 1:
                String[] choices = { "Customer 1", "Customer 2", "Customer 3", "Customer 4",
                        "Customer 5", "Customer 6" };
                final JComboBox<String> cb = new JComboBox<String>(choices);

                cb.setMaximumSize(cb.getPreferredSize()); // added code
                cb.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb);

                JLabel label = new JLabel( name, SwingConstants.RIGHT );
                label.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label );



                JButton btn = new JButton("OK");
                btn.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
                rightPanel.add(btn);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

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

                JButton btn1 = new JButton("OK");
                btn1.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
                rightPanel.add(btn1);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

                fields.put( name, cb2 );
                break;
            case 3:
                String[] choices3 = { "SOP 1", "SOP 2", "SOP 3", "SOP 4",
                        "SOP 5", "SOP 6" };
                final JComboBox<String> cb3 = new JComboBox<String>(choices3);

                cb3.setMaximumSize(cb3.getPreferredSize()); // added code
                cb3.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb3);

                JLabel label3 = new JLabel( name, SwingConstants.RIGHT );
                label3.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label3 );

                JButton btn3 = new JButton("OK");
                btn3.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
                rightPanel.add(btn3);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

                fields.put( name, cb3);
                break;
            case 4:
                String[] choices4 = { "TURNING", "MILLING", "PASAVATION", "LASER MARKING"};
                final JComboBox<String> cb4 = new JComboBox<String>(choices4);

                cb4.setMaximumSize(cb4.getPreferredSize()); // added code
                cb4.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
                //cb.setVisible(true); // Not needed
                middlePanel.add(cb4);

                JLabel label4 = new JLabel( name, SwingConstants.RIGHT );
                label4.setBorder(
                        BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
                leftPanel.add( label4 );

                JButton btn4 = new JButton("OK");
                btn4.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
                rightPanel.add(btn4);

                //        frame.setVisible(true); // added code
                //        rightPanel.add( jcomp1 );

                fields.put( name, cb4);
                break;
        }
    }
}  // end class AddressBookEntryFrame

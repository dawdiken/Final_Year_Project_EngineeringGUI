// Java core packages
import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;

// Java extension packages
import javax.swing.*;

public class UserEntryFrame extends JInternalFrame {

    // HashMap to store JTextField references for quick access
    private HashMap fields;

    // reference to database access object
    private AddressBookDataAccess database;



    // current AddressBookEntry set by AddressBook application
    private NewJobEntry job;

    // panels to organize GUI
    private JPanel leftPanel, rightPanel;

    private JButton addUser;

    // static integers used to determine new window positions
    // for cascading windows
    private static int xOffset = 0, yOffset = 0;

    // static Strings that represent name of each text field.
    // These are placed on JLabels and used as keys in
    // HashMap fields.
    private static final String FIRST_NAME = "New User Name;",
            LAST_NAME = "New User Password:",
            ADDRESS1 = "Address 1", ADDRESS2 = "Address 2", CITY = "City", STATE = "State", EIRCODE = "Eircode",
            ADDRESS1_1 = "Address 1(2)", ADDRESS2_1 = "Address 2(2)", CITY_1 = "City(2)", STATE_1 = "State(2)", EIRCODE_1 = "Eircode(2)",
            ADDRESS1_2 = "Address 1(3)", ADDRESS2_2 = "Address 2(3)", CITY_2 = "City(3)", STATE_2 = "State(3)", EIRCODE_2 = "Eircode(3)",
            PHONE = "Phone", EMAIL = "Email",
            PHONE_1 = "Phone(2)", EMAIL_1 = "Email(2)",
            PHONE_2 = "Phone(3)", EMAIL_2 = "Email(3)";

    private int newAddressClickCount = 0;
    private int addPhoneNumbersClickCount = 0;
    private int addEmailAddressClickCount = 0;
    private int rowCount = 9;
    private int height = 300;
    private Container container;

    // construct GUI
    public UserEntryFrame()
    {
        super( "Add User", true, true );

        // create database connection
        try {
            database = new CloudscapeDataAccess();
        }

        // detect problems with database connection
        catch ( Exception exception ) {
            exception.printStackTrace();
            System.exit( 1 );
        }

        fields = new HashMap();

        leftPanel = new JPanel();
        leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        rightPanel = new JPanel();
        rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

        createRow( FIRST_NAME );
        createRow( LAST_NAME );
        addButton();


        container = getContentPane();
        container.add( leftPanel, BorderLayout.WEST );
        container.add( rightPanel, BorderLayout.CENTER );

        setBounds( xOffset, yOffset, 300, height );
        xOffset = ( xOffset + 30 ) % 300;
        yOffset = ( yOffset + 30 ) % 300;
    }



    // set AddressBookEntry then use its properties to
    // place data in each JTextField
    public void setAddressBookEntry( NewJobEntry entry )
    {
        job = entry;

        setField( FIRST_NAME, job.getUserName() );
        setField( LAST_NAME, job.getPassword() );

    }

//


        // store AddressBookEntry data from GUI and return
    // AddressBookEntry
    public NewJobEntry getAddressBookEntry()
    {
        job.setUserName( getField( FIRST_NAME ) );
        job.setPassword( getField( LAST_NAME ) );

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

    // get text in JTextField by specifying field's name
    private String getField( String fieldName )
    {
        JTextField field =
                ( JTextField ) fields.get( fieldName );

        return field.getText();
    }

    // utility method used by constructor to create one row in
    // GUI containing JLabel and JTextField
    private void createRow( String name) {

        JLabel label = new JLabel(name, SwingConstants.RIGHT);
        label.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JTextField field = new JTextField(30);

        leftPanel.add(label);
        rightPanel.add(field);

        fields.put(name, field);

    }

    public void addButton() {

//        JLabel label = new JLabel(name, SwingConstants.RIGHT);
        JButton addButt = new JButton("Add User");
        addButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("worked");
                job = getAddressBookEntry();
                System.out.println(job.getUserName());
                String username = job.getUserName();
                String passw = job.getPassword();
                System.out.println(passw);

                ArrayList<NewJobEntry> person = database.findPerson(username,
                        passw );
                if ( person != null ) {

                    // create window to display AddressBookEntry

                    String alreadyUsername = person.get(0).getUserName();
                    JOptionPane.showMessageDialog( container,
                            " Username " + alreadyUsername + " already exists. \n Please try another username.");
                }
                else {
                    try{
                        System.out.println(database.newUser(job));
                    }
                    catch (Exception j){
                        System.out.println(j);
                    }


                }
            }
        });

        addButt.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        JTextField field = new JTextField(30);
        rightPanel.add(addButt);
//        rightPanel.add(label);
    }
}  // end class AddressBookEntryFrame

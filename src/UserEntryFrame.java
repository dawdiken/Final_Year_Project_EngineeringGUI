import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class UserEntryFrame extends JInternalFrame {

    // HashMap to store JTextField references for quick access
    private HashMap fields;

    // reference to database access object
    private EngineeringDataAccess database;

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
    private static final String USER_NAME = "New User Name;", PASSWORD = "New User Password:";

    private int rowCount = 9;
    private int height = 300;
    private Container container;

    // construct GUI
    public UserEntryFrame()
    {
        super( "Add User", true, true );

        // create database connection
        try {
            database = new DataBaseAccess();
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
        createRow( USER_NAME );
        createRow( PASSWORD );
        JButton addButt = new JButton("Add User");
        addButt.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        rightPanel.add(addButt);
        addButt.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          addButton();
                                      }
        });
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

        setField( USER_NAME, job.getUserName() );
        setField( PASSWORD, job.getPassword() );

    }

    // store AddressBookEntry data from GUI and return
    // AddressBookEntry
    private NewJobEntry getAddressBookEntry()
    {
        job.setUserName( getField( USER_NAME ) );
        job.setPassword( getField( PASSWORD ) );

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

    private void addButton() {

                job = getAddressBookEntry();
                System.out.println(job.getUserName());
                String username = job.getUserName();
                String passw = job.getPassword();
                System.out.println(passw);

                ArrayList<NewJobEntry> person = database.findPerson(username,
                        passw );
                if ( person != null ) {
                    String alreadyUsername = person.get(0).getUserName();
                    JOptionPane.showMessageDialog( container,
                            " Username " + alreadyUsername + " already exists. \n Please try another username.");
                }
                else {
                    try{
                        database.newUser(job);
//                        System.out.println(database.newUser(job));
                        JOptionPane.showMessageDialog( container,
                                "New user added.");
                    }
                    catch (Exception j){
                        j.printStackTrace();
                    }
                }
            }
}

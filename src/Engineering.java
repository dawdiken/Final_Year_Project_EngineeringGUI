

// Java core packages

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Java extension packages
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.ColorUIResource;

public class Engineering extends JFrame {

    // reference for manipulating multiple document interface
    private JDesktopPane desktop;

    // reference to database access object
    private EngineeringDataAccess database;

    // references to Actions
    Action newAction, saveAction, deleteAction, LoginAction, LogOutAction, AddUserAction,
            searchAction, exitAction, addAddressAction, addPhonesAction, savePhone, newDocumentAction;

    // set up database connection and GUI
    public Engineering() {
        super( "Engineering Portal" );

//         create database connection
        try {
            database = new DataBaseAccess();
        }
        // detect problems with database connection
        catch ( Exception exception ) {
            exception.printStackTrace();
            System.exit( 1 );
        }

        // database connection successful, create GUI
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        JMenu fileMenu = new JMenu( "File" );
        fileMenu.setMnemonic( 'F' );


        // Set up actions for common operations. Private inner
        // classes encapsulate the processing of each action.
        newAction = new NewAction();
        newAction.setEnabled( true );    // disabled by default
        saveAction = new SaveAction();
        saveAction.setEnabled( false );    // disabled by default
        deleteAction = new DeleteAction();
        deleteAction.setEnabled( false );  // disabled by default
        LoginAction = new LoginAction();
        LoginAction.setEnabled( true );  // disabled by default
        LogOutAction = new LogOutAction();
        LogOutAction.setEnabled( false );  // disabled by default
        newDocumentAction = new newDocumentAction();
        newDocumentAction.setEnabled( true );    // disabled by default
        AddUserAction = new AddUserAction();
        AddUserAction.setEnabled( true );  // disabled by default
//        searchAction = new SearchAction();
//        exitAction = new ExitAction();
//        savePhone = new SavePhoneNum();
//
        addAddressAction = new AddAddressAction();
        addAddressAction.setEnabled( false );  // disabled by default
//        addPhonesAction = new AddPhonesAction();
        // addEmail = new addEmail();

        // add actions to tool bar
        toolBar.add( newAction);
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( saveAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( deleteAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( searchAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( newDocumentAction);
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( addAddressAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( addPhonesAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( savePhone );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( LoginAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( LogOutAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( AddUserAction );

        // add actions to File menu
        fileMenu.add( newAction );
        fileMenu.add( saveAction );
        fileMenu.add( deleteAction );
        fileMenu.addSeparator();
        fileMenu.add( searchAction );
        fileMenu.addSeparator();
        fileMenu.add( exitAction );
        //fileMenu.addSeparator();
        //fileMenu.add( addAddressAction );

        // set up menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add( fileMenu );
        setJMenuBar( menuBar );

        // set up desktop
        desktop = new JDesktopPane();

        // get the content pane to set up GUI
        Container c = getContentPane();
        c.add( toolBar, BorderLayout.EAST );
        c.add( desktop, BorderLayout.CENTER );

        //set a nice default colour for all JOptionPane/ComboBox panes throughout the program
        UIManager UI=new UIManager();
        UI.put("OptionPane.background",new ColorUIResource(255,240,153));
        UI.put("Panel.background",new ColorUIResource(255,240,153));
        UI.put("ComboBox.selectionBackground", new ColorUIResource(Color.green));
        UI.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));

        // register for windowClosing event in case user
        // does not select Exit from File menu to terminate
        // application
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing( WindowEvent event )
                    {
                        shutDown();
                    }
                }
        );

        // set window size and display window
        Toolkit toolkit = getToolkit();
        Dimension dimension = toolkit.getScreenSize();

        // center window on screen
        setBounds( 100, 100, dimension.width - 200,
                dimension.height );

        setVisible( true );

    }  // end Engineering constructor

    // close database connection and terminate program
    private void shutDown() {
        database.close();   // close database connection
        System.exit( 0 );   // terminate program
    }

    // create a new NewJobEntryFrame and register listener
    private NewJobFrame createNewJobFrame(int id, String custName) {
        NewJobFrame frame = new NewJobFrame(id, custName);
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.addInternalFrameListener(
                new InternalFrameAdapter() {

                    // internal frame becomes active frame on desktop
                    public void internalFrameActivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( true );
                        deleteAction.setEnabled( true );
                    }

                    // internal frame becomes inactive frame on desktop
                    public void internalFrameDeactivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( false );
                        deleteAction.setEnabled( false );
                    }
                }  // end InternalFrameAdapter anonymous inner class
        ); // end call to addInternalFrameListener

        return frame;
    }  // end method createAddressBookEntryFrame


    // create a new NewJobEntryFrame and register listener
    private addDocForm createAddDocFrame() {
        addDocForm frame = new addDocForm();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.addInternalFrameListener(
                new InternalFrameAdapter() {
                    // internal frame becomes active frame on desktop
                    public void internalFrameActivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( true );
                    }

                    // internal frame becomes inactive frame on desktop
                    public void internalFrameDeactivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( false );
                    }
                }  // end InternalFrameAdapter anonymous inner class
        ); // end call to addInternalFrameListener
        return frame;
    }  // end method createAddressBookEntryFrame



    // create a new UserEntryFrame and register listener
    private UserEntryFrame createUserEntryFrame() {
        UserEntryFrame frame = new UserEntryFrame();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.addInternalFrameListener(
                new InternalFrameAdapter() {

                    // internal frame becomes active frame on desktop
                    public void internalFrameActivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( true );
                        //deleteAction.setEnabled( true );
                    }

                    // internal frame becomes inactive frame on desktop
                    public void internalFrameDeactivated(
                            InternalFrameEvent event )
                    {
                        saveAction.setEnabled( false );
                        //deleteAction.setEnabled( false );
                    }
                }  // end InternalFrameAdapter anonymous inner class
        ); // end call to addInternalFrameListener

        return frame;
    }  // end method createAddressBookEntryFrame

    // create a new AddressBookEntryFrame and register listener
//    private PhoneNumEntryFrame createPhoneNumEntryFrame() {
//        PhoneNumEntryFrame frame = new PhoneNumEntryFrame();
//        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
//        frame.addInternalFrameListener(
//                new InternalFrameAdapter() {
//
//                    // internal frame becomes active frame on desktop
//                    public void internalFrameActivated(
//                            InternalFrameEvent event )
//                    {
//                        saveAction.setEnabled( true );
//                        deleteAction.setEnabled( true );
//                    }
//
//                    // internal frame becomes inactive frame on desktop
//                    public void internalFrameDeactivated(
//                            InternalFrameEvent event )
//                    {
//                        saveAction.setEnabled( false );
//                        deleteAction.setEnabled( false );
//                    }
//                }  // end InternalFrameAdapter anonymous inner class
//        ); // end call to addInternalFrameListener
//
//        return frame;
//    }  // end method createAddressBookEntryFrame

    // method to launch program execution
    public static void main( String args[] )
    {
        try {
            // select Look and Feel
//            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            // start application
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
//                    new LoginFrame();
                    new Engineering();
                }
            });
//            new Engineering();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Private inner class defines action that enables
    // user to input new entry. User must "Save" entry
    // after inputting data.
    private class NewAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public NewAction()
        {
            putValue( NAME, "New Job" );
            putValue( SHORT_DESCRIPTION, "New Job" );
            putValue( LONG_DESCRIPTION,
                    "Add a new address book entry" );
            putValue( MNEMONIC_KEY, new Integer( 'N' ) );
        }

        // display window in which user can input entry
        public void actionPerformed( ActionEvent e )
        {

           ArrayList<String> customerNames = database.findCustomer();
            String[] result = {};
            //System.out.println("where is the customers" + customerNames.get(2).getCustomerName().toString());
            for (int i = 0; i < customerNames.size() ; i++) {
                result = customerNames.toArray(new String[]{});
            }
            final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\wrench-128.png");

            JFrame frame = new JFrame("Input Dialog Example 3");
            String customerName = (String) JOptionPane.showInputDialog(frame,
                    "Please select a customer?",
                    "Customer",
                    JOptionPane.QUESTION_MESSAGE,
                    icon,
                    result,
                    result[0]);

            // favoritePizza will be null if the user clicks Cancel
            System.out.printf("customerName is %s.\n", customerName);            // create new internal window

            if (customerName.equalsIgnoreCase("Boston Scientific")) {
                int id = 1;
                NewJobFrame entryFrame =
                        createNewJobFrame(id, customerName);
//                entryFrame.setAddressBookEntry(
//                        new NewJobEntry() );


            // set new AddressBookEntry in window
            entryFrame.setAddressBookEntry(
                    new NewJobEntry(id) );

                // display window
                desktop.add( entryFrame );
                entryFrame.setVisible( true );
            }
            else if (customerName.equalsIgnoreCase("Medtronic")){
                int id = 2;
                NewJobFrame entryFrame =
                        createNewJobFrame(id, customerName);
//                entryFrame.setAddressBookEntry(
//                        new NewJobEntry() );


                // set new AddressBookEntry in window
                entryFrame.setAddressBookEntry(
                        new NewJobEntry(id) );

                // display window
                desktop.add( entryFrame );
                entryFrame.setVisible( true );
            }
            else{
                System.out.println("no customer here");
            }



        }

    }  // end inner class NewAction

//     inner class defines an action that can save new or
//     updated entry
    private class SaveAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public SaveAction()
        {
            putValue( NAME, "Save" );
            putValue( SHORT_DESCRIPTION, "Save" );
            putValue( LONG_DESCRIPTION,
                    "Save an address book entry" );
            putValue( MNEMONIC_KEY, new Integer( 'S' ) );
        }

        // save new entry or update existing entry
        public void actionPerformed( ActionEvent e )
        {
            // get currently active window
            NewJobFrame currentFrame =
                    ( NewJobFrame ) desktop.getSelectedFrame();

            // obtain AddressBookEntry from window
            NewJobEntry person =
                    currentFrame.getAddressBookEntry();
//            System.out.printf(person.getBatchQty());
            System.out.println("Batch qty = " + person.getBatchQty());
            System.out.println("drop path = " + person.getDropPath());

            try {
                database.savePerson(person);
            }
            // detect problems deleting person
            catch ( DataAccessException exception ) {
                JOptionPane.showMessageDialog( desktop, exception,
                        "Save failed", JOptionPane.ERROR_MESSAGE );
                exception.printStackTrace();
            }
                // close current window and dispose of resources
                currentFrame.dispose();
        }  // end method actionPerformed

    }  // end inner class SaveAction


    // Private inner class defines action that enables
    // user to input new entry. User must "Save" entry
    // after inputting data.
    private class newDocumentAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public newDocumentAction()
        {
            putValue( NAME, "New Documents" );
            putValue( SHORT_DESCRIPTION, "New Doc" );
            putValue( LONG_DESCRIPTION,
                    "Add a new document to the data base" );
            putValue( MNEMONIC_KEY, new Integer( 'D' ) );
        }

        public void actionPerformed( ActionEvent e )
        {
            //addDocForm newDocEntry = new addDocForm();
            addDocForm entryFrame =
                    createAddDocFrame();
//            JTextField xField = new JTextField(10);
//            xField.setText("Administrator");
//            JPasswordField yField = new JPasswordField(10);
//            JPanel myPanel = new JPanel();
//            myPanel.add(new JLabel("Admin Accounnt Name:"));
//            myPanel.add(xField);
//            myPanel.add(xField);
//
//            myPanel.add(Box.createHorizontalStrut(10)); // a spacer
//            myPanel.add(new JLabel("Password:"));
//            myPanel.add(yField);
//            int lastName = JOptionPane.showConfirmDialog(null, myPanel,
//                    "Please Log In", JOptionPane.OK_CANCEL_OPTION);
//            if (lastName == JOptionPane.OK_OPTION) {
//                System.out.println("x value: " + xField.getText());
//                System.out.println("y value: " + yField.getText());
//            }
//            String userName = xField.getText().toString();
//            String password = yField.getText().toString();
//            System.out.println(password);
//
////            String lastName =
////                    JOptionPane.showInputDialog( desktop, myPanel,
////                            "Enter last name" );
//
//
////            String lastName =
////                    JOptionPane.showInputDialog( desktop,
////                            "Enter last name" );
////            loggedIn =   new LoginFrame();
//
//            // if last name was input, search for it; otherwise,
//            // do nothing
//
//
//            // Execute search. If found, AddressBookEntry
//            // is returned containing data.
//            ArrayList<NewJobEntry> person = database.findPerson( userName,
//                    password );
//
//            System.out.printf("Person" + person);
//            if ( person != null ) {
//                // create window to display AddressBookEntry
//                System.out.printf("Person" + person);
//                JOptionPane.showMessageDialog( desktop,
//                        "Log in succesfull.  Hello " + userName);
////                LoginAction.setEnabled(false);  // disabled by default
////                newAction.setEnabled(true);
//////                    saveAction.setEnabled( true );
//////                    deleteAction.setEnabled( true );
////                addAddressAction.setEnabled(true);
////                LogOutAction.setEnabled(true);  // disabled by default
//                UserEntryFrame entryFrame =
//                        createUserEntryFrame();
//                entryFrame.setAddressBookEntry(
//                        new NewJobEntry() );
//
//
//                // set new AddressBookEntry in window
//                entryFrame.setAddressBookEntry(
//                        new NewJobEntry() );
//
//                // display window
//                desktop.add( entryFrame );
//                entryFrame.setVisible( true );
//            }
//            else
//                JOptionPane.showMessageDialog( desktop,
//                        "User:\n" + userName +
//                                "\" not found!" );

        }

    }  // end inner class AddNewUserAction



//    // inner class defines action that deletes entry
    private class DeleteAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public DeleteAction()
        {
            putValue( NAME, "Delete" );
            putValue( SHORT_DESCRIPTION, "Delete" );
            putValue( LONG_DESCRIPTION,
                    "Delete an address book entry" );
            putValue( MNEMONIC_KEY, new Integer( 'D' ) );
        }

        // delete entry
        public void actionPerformed( ActionEvent e )
        {
            // get currently active window
            NewJobFrame currentFrame =
                    ( NewJobFrame ) desktop.getSelectedFrame();

//            // get AddressBookEntry from window
//            AddressBookEntry person =
//                    currentFrame.getAddressBookEntry();

            // If personID is 0, this is new entry that has not
            // been inserted. Therefore, delete is not necessary.
            // Display message and return.
//            if ( person.getPersonID() == 0 ) {
//                JOptionPane.showMessageDialog( desktop,
//                        "New entries must be saved before they can be " +
//                                "deleted. \nTo cancel a new entry, simply " +
//                                "close the window containing the entry" );
//                return;
//            }
//
//            // delete person
//            try {
//                database.deletePerson( person );
//
//                // display message indicating success
//                JOptionPane.showMessageDialog( desktop,
//                        "Deletion successful" );
//            }
//
//            // detect problems deleting person
//            catch ( DataAccessException exception ) {
//                JOptionPane.showMessageDialog( desktop, exception,
//                        "Deletion failed", JOptionPane.ERROR_MESSAGE );
//                exception.printStackTrace();
//            }

            // close current window and dispose of resources
            currentFrame.dispose();

        }  // end method actionPerformed

    }  // end inner class DeleteAction
//
//    // inner class defines action that locates entry
//    private class SearchAction extends AbstractAction {
//
//        // set up action's name, icon, descriptions and mnemonic
//        public SearchAction()
//        {
//            putValue( NAME, "Search" );
//            //          putValue( SMALL_ICON, new ImageIcon(
////                    getClass().getResource( "images/Find24.png" ) ) );
//            putValue( SHORT_DESCRIPTION, "Search" );
//            putValue( LONG_DESCRIPTION,
//                    "Search for an address book entry" );
//            putValue( MNEMONIC_KEY, new Integer( 'r' ) );
//        }
//
//        // locate existing entry
//        public void actionPerformed( ActionEvent e )
//        {
//            String lastName =
//                    JOptionPane.showInputDialog( desktop,
//                            "Enter last name" );
//
//            // if last name was input, search for it; otherwise,
//            // do nothing
//            if ( lastName != null ) {
//
//                // Execute search. If found, AddressBookEntry
//                // is returned containing data.
//                ArrayList<AddressBookEntry> person = database.findPerson(
//                        lastName );
//
//
//                if ( person != null ) {
//
//                    // create window to display AddressBookEntry
//
//                    System.out.printf("Person" + person);
//
//
//                    // set AddressBookEntry to display
//                    for (int i = 0; i <person.size() ; i++) {
//                        AddressBookEntryFrame entryFrame =
//                                createAddressBookEntryFrame();
//                        entryFrame.setAddressBookEntry( person.get(i) );
//                        desktop.add( entryFrame );
//                        entryFrame.setVisible( true );
//                    }
//
//
//                    // display window
//
//                }
//                else
//                    JOptionPane.showMessageDialog( desktop,
//                            "Entry with last name \"" + lastName +
//                                    "\" not found in address book" );
//
//            }  // end "if ( lastName == null )"
//
//        }  // end method actionPerformed
//
//    }  // end inner class SearchAction

    // inner class defines action that locates entry
    private class LoginAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public LoginAction()
        {
            putValue( NAME, "Log In" );
            //          putValue( SMALL_ICON, new ImageIcon(
//                    getClass().getResource( "images/Find24.png" ) ) );
            putValue( SHORT_DESCRIPTION, "Log In" );
            putValue( LONG_DESCRIPTION,
                    "Log In" );
            putValue( MNEMONIC_KEY, new Integer( 'r' ) );
        }

        // locate existing entry
        public void actionPerformed( ActionEvent e )
        {
            JTextField xField = new JTextField(10);
            JPasswordField yField = new JPasswordField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Username:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(10)); // a spacer
            myPanel.add(new JLabel("Password:"));
            myPanel.add(yField);
            int lastName = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Log In", JOptionPane.OK_CANCEL_OPTION);
            if (lastName == JOptionPane.OK_OPTION) {
                System.out.println("x value: " + xField.getText());
                System.out.println("y value: " + yField.getText());
            }
            String userName = xField.getText().toString();
            String password = yField.getText().toString();
            System.out.println(password);

//            String lastName =
//                    JOptionPane.showInputDialog( desktop, myPanel,
//                            "Enter last name" );


//            String lastName =
//                    JOptionPane.showInputDialog( desktop,
//                            "Enter last name" );
//            loggedIn =   new LoginFrame();

            // if last name was input, search for it; otherwise,
            // do nothing


                // Execute search. If found, AddressBookEntry
                // is returned containing data.
                ArrayList<NewJobEntry> person = database.findPerson( userName,
                        password );

                 System.out.printf("Person" + person);
                if ( person != null ) {
                    // create window to display AddressBookEntry
                    System.out.printf("Person" + person);
                    JOptionPane.showMessageDialog( desktop,
                            "Log in succesfull.  Hello " + userName);
                    LoginAction.setEnabled(false);  // disabled by default
                    newAction.setEnabled(true);
//                    saveAction.setEnabled( true );
//                    deleteAction.setEnabled( true );
                    addAddressAction.setEnabled(true);
                    LogOutAction.setEnabled(true);  // disabled by default


                    // set AddressBookEntry to display
//                    for (int i = 0; i < person.size(); i++) {
//                        AddressBookEntryFrame entryFrame =
//                                createAddressBookEntryFrame();
//                        entryFrame.setAddressBookEntry(person.get(i));
//                        desktop.add(entryFrame);
//                        entryFrame.setVisible(true);
//                    }
                    // display window
                }
                else
                    JOptionPane.showMessageDialog( desktop,
                            "User:\n" + userName +
                                    "\" not found!" );

             // end "if ( lastName == null )"

        }  // end method actionPerformed

    }  // end inner class SearchAction

    private class LogOutAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public LogOutAction()
        {
            putValue( NAME, "Log Out" );
            //          putValue( SMALL_ICON, new ImageIcon(
//                    getClass().getResource( "images/Find24.png" ) ) );
            putValue( SHORT_DESCRIPTION, "Log Out" );
            putValue( LONG_DESCRIPTION,
                    "Log Out" );
            putValue( MNEMONIC_KEY, new Integer( 'O' ) );
        }

        // perform log out action
        public void actionPerformed( ActionEvent e )
        {
            JOptionPane.showMessageDialog( desktop,
                        "User: Logged out!" );
            newAction.setEnabled(false);
            saveAction.setEnabled( false );
            deleteAction.setEnabled( false );
            addAddressAction.setEnabled(false);
            LogOutAction.setEnabled(false);  // disabled by default
            LoginAction.setEnabled(true);  // disabled by default

        }  // end method actionPerformed

    }  // end inner class SearchAction

    private class AddUserAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public AddUserAction()
        {
            putValue( NAME, "Add User" );
            putValue( SHORT_DESCRIPTION, "Add User" );
            putValue( LONG_DESCRIPTION,
                    "Add new Engineering User" );
            putValue( MNEMONIC_KEY, new Integer( 'A' ) );
        }

        // save new entry or update existing entry
        public void actionPerformed( ActionEvent e )
        {
            JTextField xField = new JTextField(10);
            xField.setText("Administrator");
            JPasswordField yField = new JPasswordField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Admin Accounnt Name:"));
            myPanel.add(xField);
            myPanel.add(xField);

            myPanel.add(Box.createHorizontalStrut(10)); // a spacer
            myPanel.add(new JLabel("Password:"));
            myPanel.add(yField);
            int lastName = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Log In", JOptionPane.OK_CANCEL_OPTION);
            if (lastName == JOptionPane.OK_OPTION) {
                System.out.println("x value: " + xField.getText());
                System.out.println("y value: " + yField.getText());
            }
            String userName = xField.getText().toString();
            String password = yField.getText().toString();
            System.out.println(password);

//            String lastName =
//                    JOptionPane.showInputDialog( desktop, myPanel,
//                            "Enter last name" );


//            String lastName =
//                    JOptionPane.showInputDialog( desktop,
//                            "Enter last name" );
//            loggedIn =   new LoginFrame();

            // if last name was input, search for it; otherwise,
            // do nothing


            // Execute search. If found, AddressBookEntry
            // is returned containing data.
            ArrayList<NewJobEntry> person = database.findPerson( userName,
                    password );

            System.out.printf("Person" + person);
            if ( person != null ) {
                // create window to display AddressBookEntry
                System.out.printf("Person" + person);
                JOptionPane.showMessageDialog( desktop,
                        "Log in succesfull.  Hello " + userName);
//                LoginAction.setEnabled(false);  // disabled by default
//                newAction.setEnabled(true);
////                    saveAction.setEnabled( true );
////                    deleteAction.setEnabled( true );
//                addAddressAction.setEnabled(true);
//                LogOutAction.setEnabled(true);  // disabled by default
                UserEntryFrame entryFrame =
                        createUserEntryFrame();
                entryFrame.setAddressBookEntry(
                        new NewJobEntry() );


                // set new AddressBookEntry in window
                entryFrame.setAddressBookEntry(
                        new NewJobEntry() );

                // display window
                desktop.add( entryFrame );
                entryFrame.setVisible( true );
            }
            else
                JOptionPane.showMessageDialog( desktop,
                        "User:\n" + userName +
                                "\" not found!" );

        }

    }  // end inner class AddNewUserAction
//
//     inner class defines action that closes connection to
//     database and terminates program
    private class ExitAction extends AbstractAction {

        // set up action's name, descriptions and mnemonic
        public ExitAction()
        {
            putValue( NAME, "Exit" );
            putValue( SHORT_DESCRIPTION, "Exit" );
            putValue( LONG_DESCRIPTION, "Terminate the program" );
            putValue( MNEMONIC_KEY, new Integer( 'x' ) );
        }

        // terminate program
        public void actionPerformed( ActionEvent e )
        {
            shutDown();  // close database connection and terminate
        }

    }  // end inner class ExitAction
//
//    private class AddAddressAction extends AbstractAction {
//
//        // set up action's name, icon, descriptions and mnemonic
//        public AddAddressAction()
//        {
//            putValue( NAME, "AddAddress" );
//            putValue( SHORT_DESCRIPTION, "Add Address" );
//            putValue( LONG_DESCRIPTION,
//                    "Add another address" );
//            putValue( MNEMONIC_KEY, new Integer( 'A' ) );
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    // get currently active window
//                    AddressBookEntryFrame currentFrame =
//                            ( AddressBookEntryFrame ) desktop.getSelectedFrame();
//
//                    // obtain AddressBookEntry from window
//                    AddressBookEntry person =
//                            currentFrame.getAddressBookEntry();
//
////                    currentFrame.addAddresses();
//                }
//            });
//        }
//    }

    private class AddAddressAction extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        public AddAddressAction()
        {
            putValue( NAME, "AddAddress" );
            putValue( SHORT_DESCRIPTION, "Add Address" );
            putValue( LONG_DESCRIPTION,
                    "Add another address" );
            putValue( MNEMONIC_KEY, new Integer( 'A' ) );
        }

        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // get currently active window
                    NewJobFrame currentFrame =
                            ( NewJobFrame ) desktop.getSelectedFrame();

                    // obtain AddressBookEntry from window
                    NewJobEntry job =
                            currentFrame.getAddressBookEntry();

//                    System.out.println(job.getBatchQty());

//                    currentFrame.addAddresses();
                    // create new internal window
//                    NewJobFrame entryFrame =
//                            createNewJobFrame();

//            // set new AddressBookEntry in window
//            entryFrame.setAddressBookEntry(
//                    new AddressBookEntry() );

                    // display window
//                    desktop.add( entryFrame );
//                    entryFrame.setVisible( true );
                }
            });
        }
    }

//
//    private class AddPhonesAction extends AbstractAction {
//        public AddPhonesAction()
//        {
//            putValue( NAME, "Add Phone Num" );
//            //          putValue( SMALL_ICON, new ImageIcon(
////                    getClass().getResource( "images/Find24.png" ) ) );
//            putValue( SHORT_DESCRIPTION, "Add Phone Num" );
//            putValue( LONG_DESCRIPTION,
//                    "Search for an address book entry and Add Phone Num" );
//            putValue( MNEMONIC_KEY, new Integer( 'r' ) );
//        }
//
//        // locate existing entry
//        public void actionPerformed( ActionEvent e )
//        {
//            String lastName =
//                    JOptionPane.showInputDialog( desktop,
//                            "Enter last name" );
//
//            // if last name was input, search for it; otherwise,
//            // do nothing
//            if ( lastName != null ) {
//
//                // Execute search. If found, AddressBookEntry
//                // is returned containing data.
//                ArrayList<AddressBookEntry> person = database.findPerson(
//                        lastName );
//
//
//                if ( person != null ) {
//
//                    // create window to display AddressBookEntry
//
//                    System.out.printf("Person" + person);
//
//
//                    // set AddressBookEntry to display
//                    for (int i = 0; i <person.size() ; i++) {
//                        PhoneNumEntryFrame entryFrame =
//                                createPhoneNumEntryFrame();
//                        entryFrame.setAddressBookEntry( person.get(i) );
//                        desktop.add( entryFrame );
//                        entryFrame.setVisible( true );
//                    }
//
//
////                    need to add actioner listener which
////                            calls this
////                    System.out.println(database.newPerson(person));
////                    but not new person
//
//                }
//                else
//                    JOptionPane.showMessageDialog( desktop,
//                            "Entry with last name \"" + lastName +
//                                    "\" not found in address book" );
//
//            }  // end "if ( lastName == null )"
//
//        }  // end method actionPerformed
//
//    }  // end inner class SearchAction
//
//    // inner class defines an action that can save new or
//    // updated entry
//    private class SavePhoneNum extends AbstractAction {
//
//        // set up action's name, icon, descriptions and mnemonic
//        public SavePhoneNum()
//        {
//            putValue( NAME, "Save New Num" );
//            putValue( SHORT_DESCRIPTION, "Save New Num" );
//            putValue( LONG_DESCRIPTION,
//                    "Save new phone nnum" );
//            putValue( MNEMONIC_KEY, new Integer( 'N' ) );
//        }
//
//        // save new entry or update existing entry
//        public void actionPerformed( ActionEvent e )
//        {
//            // get currently active window
//            PhoneNumEntryFrame currentFrame =
//                    ( PhoneNumEntryFrame ) desktop.getSelectedFrame();
//
//            // obtain AddressBookEntry from window
//            AddressBookEntry person =
//                    currentFrame.getAddressBookEntry();
//
//
//            try {
//
//                // Get personID. If 0, this is a new entry;
//                // otherwise an update must be performed.
//                int personID = person.getPersonID();
//                System.out.println("personID" +personID);
//
//                // determine string for message dialogs
//                String operation =
//                        (personID == 0) ? "Insertion" : "Update";
//
//                // insert or update entry
//
//                System.out.println(database.newNumber(person));
//
//                // display success message
//                JOptionPane.showMessageDialog(desktop,
//                        operation + " successful");
//            }  // end try
//
//            // detect database errors
//            catch (Exception exception) {
//                JOptionPane.showMessageDialog(desktop, exception,
//                        "DataAccessException",
//                        JOptionPane.ERROR_MESSAGE);
//                exception.printStackTrace();
//            }
//
//            // close current window and dispose of resources
//            currentFrame.dispose();
//
//        }  // end method actionPerformed
//
//    }  // end inner class SaveAction
//
//    // utility method to check the valildity of the eircode
//    private boolean isValid(String input) {
//        if(input.length() == 8 &&
//                input.charAt(0) != ' ' &&
//                input.charAt(1) != ' ' &&
//                input.charAt(2) != ' ' &&
//                input.charAt(3) == ' ' &&
//                input.charAt(4) != ' ' &&
//                input.charAt(5) != ' ' &&
//                input.charAt(6) != ' ' &&
//                input.charAt(7) != ' ')
//            return true;
//        else
//            return false;
//    }
}
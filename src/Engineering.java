

// Java core packages

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
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
    private Action LoginAction, LogOutAction, AddUserAction,
            newDocumentAction, viewAllJobsInDB, storeFinishedJobsInBucket, newWorkOrder;

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

        CreateDirectory makeHome = new CreateDirectory();
        makeHome.CreateDirectory();

        // database connection successful, create GUI
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        JMenu fileMenu = new JMenu( "File" );
        fileMenu.setMnemonic( 'F' );

        LoginAction = new LoginAction();
        LoginAction.setEnabled( true );  // disabled by default
        LogOutAction = new LogOutAction();
        LogOutAction.setEnabled( false );  // disabled by default
        newDocumentAction = new newDocumentAction();
        newDocumentAction.setEnabled( false );    // disabled by default
        AddUserAction = new AddUserAction();
        AddUserAction.setEnabled( false );  // disabled by default
        viewAllJobsInDB = new viewAllJobsInDB();
        viewAllJobsInDB.setEnabled( false );  // disabled by default
        storeFinishedJobsInBucket = new storeFinishedJobsInBucket();
        storeFinishedJobsInBucket.setEnabled( false );  // disabled by default
        newWorkOrder = new newJobGui();
        newWorkOrder.setEnabled( false );  // disabled by default

        // add actions to tool bar
        toolBar.add( newWorkOrder );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( newDocumentAction);
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( LoginAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( LogOutAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( AddUserAction );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( viewAllJobsInDB );
        toolBar.add( new JToolBar.Separator() );
        toolBar.add( storeFinishedJobsInBucket );
        toolBar.add( new JToolBar.Separator() );

//        fileMenu.add( saveAction );
        fileMenu.addSeparator();

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
        UI.put("OptionPane.background",new ColorUIResource( 0xCADBFF));
        UI.put("Panel.background",new ColorUIResource( 0xCADBFF));
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


    // create a new createAddDocFrame and register listener
    private addDocForm createAddDocFrame() {
        addDocForm frame = new addDocForm();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        return frame;
    }  // end method createAddDocFrame

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
                    new Engineering();
                }
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }


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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    addDocForm entryFrame =
                            createAddDocFrame();
                }
            });
        }

    }  // end inner class AddNewUserAction


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
            String userName = xField.getText().toString();
            String password = yField.getText().toString();
            HandleLogIn(userName,password);

//            ArrayList<NewJobEntry> person = database.findPerson( userName,
//                    password );
//            if ( person != null ) {
//                JOptionPane.showMessageDialog( desktop,
//                        "Log in succesfull.  Hello " + userName);
//
//                if (person.get(0).getRole().equals("ENGINEERING")){
//                    LoginAction.setEnabled(false);
//                    newWorkOrder.setEnabled(true);
//                    LogOutAction.setEnabled(true);
//                    newDocumentAction.setEnabled(true);
//                    viewAllJobsInDB.setEnabled(true);
//                    storeFinishedJobsInBucket.setEnabled(true);
//                    AddUserAction.setEnabled(true);
//                }
//                else {
//                    LoginAction.setEnabled(false);
//                    LogOutAction.setEnabled(true);
//                    System.out.println("in here ");
//                    ArrayList<String> jobList = database.findJobsByDept(person.get(0).getRole());
//
//                    SwingUtilities.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            OpperatorGui asd = new OpperatorGui();
//                        }
//                    });
//                }
//            }
//            else
//                JOptionPane.showMessageDialog( desktop,
//                        "User:\n" + userName +
//                                "\" not found!" );

            }  // end method actionPerformed

    }

    private void HandleLogIn(String userName, String password){

        ArrayList<NewJobEntry> person = database.findPerson( userName,
                password );
        if ( person != null ) {
            JOptionPane.showMessageDialog( desktop,
                    "Log in succesfull.  Hello " + userName);

            if (person.get(0).getRole().equals("ENGINEERING")){
                LoginAction.setEnabled(false);
                newWorkOrder.setEnabled(true);
                LogOutAction.setEnabled(true);
                newDocumentAction.setEnabled(true);
                viewAllJobsInDB.setEnabled(true);
                storeFinishedJobsInBucket.setEnabled(true);
                AddUserAction.setEnabled(true);
            }
            else {
                LoginAction.setEnabled(false);
                LogOutAction.setEnabled(true);
                System.out.println("in here ");
                ArrayList<String> jobList = database.findJobsByDept(person.get(0).getRole());


                //ArrayList<String> customerNames = database.findCustomer();
                String[] result = {};
                for (int i = 0; i < jobList.size() ; i++) {
                    result = jobList.toArray(new String[]{});
                }
                final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\wrench-128.png");
                JFrame frame1 = new JFrame("Pick Customer");
                String jobNumber = (String) JOptionPane.showInputDialog(frame1,
                        "Please select a job?",
                        "Work Order",
                        JOptionPane.QUESTION_MESSAGE,
                        icon,
                        result,
                        result[0]);




                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
//                        OpperatorGui asd = new OpperatorGui(jobNumber,userName);
                    }
                });
            }
        }
        else
            JOptionPane.showMessageDialog( desktop,
                    "User:\n" + userName +
                            "\" not found!" );
    }


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
//            saveAction.setEnabled( false );
            newWorkOrder.setEnabled( false );
            LogOutAction.setEnabled(false);  // disabled by default
            LoginAction.setEnabled(true);  // disabled by default
            newDocumentAction.setEnabled(false);
            viewAllJobsInDB.setEnabled(false);
            storeFinishedJobsInBucket.setEnabled(false);
            AddUserAction.setEnabled(false);
        }  // end method actionPerformed

    }  // end inner class

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
            String userName = xField.getText().toString();
            String password = yField.getText().toString();

            ArrayList<NewJobEntry> person = database.findPerson( userName,
                    password );

            if ( person != null ) {
                JOptionPane.showMessageDialog( desktop,
                        "Log in succesfull.  Hello " + userName);
                LoginAction.setEnabled(false);  // disabled by default
                LogOutAction.setEnabled(true);  // disabled by default
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        UserEntryFrame entryFrame =  new UserEntryFrame();
                        entryFrame.setUserInfo(
                                new NewJobEntry() );
                        // display window
                        desktop.add( entryFrame );
                        entryFrame.setVisible( true );
                    }
                });
            }
            else
                JOptionPane.showMessageDialog( desktop,
                        "User:\n" + userName +
                                "\" not found!" );

        }

    }  // end inner class AddNewUserAction

    private class viewAllJobsInDB extends AbstractAction {
        // set up action's name, icon, descriptions and mnemonic
        private viewAllJobsInDB()
        {
            putValue( NAME, "View works orders" );
            putValue( SHORT_DESCRIPTION, "View works orders" );
            putValue( LONG_DESCRIPTION,
                    "View works orders in data base" );
            putValue( MNEMONIC_KEY, new Integer( 'w' ) );
        }
        // save new entry or update existing entry
        public void actionPerformed( ActionEvent e )
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TableFromDatabase();
                }
            });
        }

    }  // end

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

    private class storeFinishedJobsInBucket extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        private storeFinishedJobsInBucket()
        {
            putValue( NAME, "Archive finished work" );
            putValue( SHORT_DESCRIPTION, "Archive finished works orders" );
            putValue( LONG_DESCRIPTION,
                    "Store finished works orders in long term storage" );
            putValue( MNEMONIC_KEY, new Integer( 'A' ) );
        }
        // save new entry or update existing entry
        public void actionPerformed( ActionEvent e )
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ArchiveDataToCloud().createAndShowGUI();
                }
            });
        }
    }  // end inner class

    private class newJobGui extends AbstractAction {

        // set up action's name, icon, descriptions and mnemonic
        private newJobGui()
        {
            putValue( NAME, "New work order" );
            putValue( SHORT_DESCRIPTION, "New work order" );
            putValue( LONG_DESCRIPTION,
                    "New work order" );
            putValue( MNEMONIC_KEY, new Integer( 'N' ) );
        }
        // save new entry or update existing entry
        public void actionPerformed( ActionEvent e )
        {
            ArrayList<String> customerNames = database.findCustomer();
            String[] result = {};
            for (int i = 0; i < customerNames.size() ; i++) {
                result = customerNames.toArray(new String[]{});
            }
            final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\wrench-128.png");
            JFrame frame1 = new JFrame("Pick Customer");
            String customerName = (String) JOptionPane.showInputDialog(frame1,
                    "Please select a customer?",
                    "Customer",
                    JOptionPane.QUESTION_MESSAGE,
                    icon,
                    result,
                    result[0]);

            NewJobEntry job = new NewJobEntry();
            job.setCustomerName(customerName);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                        new NewWorkGui(job).createAndShowGUI(job);
                }
            });
        }
    }  // end inner class
}
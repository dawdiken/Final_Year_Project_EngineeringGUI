import javax.swing.*;
import java.awt.*;

public class addDocForm  extends JInternalFrame {
    public addDocForm(){


        String[] choices2 = { "Job 1", "Job 2", "Job 3", "Job 4",
                "Job 5", "Job 6" };
        final JComboBox<String> cb2 = new JComboBox<String>(choices2);

        cb2.setMaximumSize(cb2.getPreferredSize()); // added code
        //cb2.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        //cb.setVisible(true); // Not needed
        JPanel myPanel = new JPanel();
        myPanel.add(cb2);

        JTextField xField = new JTextField(10);
        xField.setText("Administrator");
        JPasswordField yField = new JPasswordField(10);
        //JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Doument Type:"));
        myPanel.add(xField);
        myPanel.add(xField);

        myPanel.add(Box.createHorizontalStrut(10)); // a spacer
        myPanel.add(new JLabel("Password:"));
        myPanel.add(yField);
        //final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\workIcon.png");
        final ImageIcon icon = new ImageIcon("C:\\Users\\G00070718\\Desktop\\project_gui\\Final_Year_Project_EngineeringGUI\\src\\New-file-icon.png");




        int lastName = JOptionPane.showConfirmDialog(null, myPanel,
                "Drag And Drop New Documents", JOptionPane.OK_CANCEL_OPTION, 1, icon );
        if (lastName == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + xField.getText());
            System.out.println("y value: " + yField.getText());
        }
        String userName = xField.getText().toString();
        String password = yField.getText().toString();
        System.out.println(password);

    }
}


//    private void createOption( String name, int option, String custName )
//    {
//
//
//            String[] choices2 = { "Job 1", "Job 2", "Job 3", "Job 4",
//                    "Job 5", "Job 6" };
//            final JComboBox<String> cb2 = new JComboBox<String>(choices2);
//
//            cb2.setMaximumSize(cb2.getPreferredSize()); // added code
//            cb2.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
//            //cb.setVisible(true); // Not needed
//            middlePanel.add(cb2);
//
//            JLabel label1 = new JLabel( name, SwingConstants.RIGHT );
//            label1.setBorder(
//                    BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
//            leftPanel.add( label1 );
//
//            JButton btn1 = new JButton("OK");
//            btn1.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
//            rightPanel.add(btn1);
//
//            //        frame.setVisible(true); // added code
//            //        rightPanel.add( jcomp1 );
//
//            fields.put( name, cb2 );
//    }
//
//
//
//
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

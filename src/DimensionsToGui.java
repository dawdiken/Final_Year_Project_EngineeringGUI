import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DimensionsToGui extends JFrame
{
    public DimensionsToGui(String fileName)
    {
        //headers for the table
        String[] columns = new String[] {
                "Dimension Number", "Dimension", "Tolerance"
        };

        DimensionVisionAPI getMeString = new DimensionVisionAPI();
        System.out.println("herererere1");
        System.out.println(fileName);
        String[] data2 = getMeString.DimensionVisionAPI(fileName);
//        for (int i = 0; i <data2.length ; i++) {
//            //strParts[i].replace("\\\\", "");
//            //strParts[i].substring(0,strParts.length-1);
//            System.out.println(data2[i]);
//        }
        Object [][] dataNEw = new Object [data2.length][data2.length];
        for(int i = 0; i < data2.length; i++) {
            dataNEw[i][0] = i+1;
            dataNEw[i][1] = data2[i];
            dataNEw[i][2] = "+/- 0.10";
        }

        //create table with data
        for(int i = 0; i < data2.length; i++) {
            System.out.println(data2[i]);
        }
        System.out.println();
        JTable table = new JTable(dataNEw, columns);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(String.class, centerRenderer);
        table.setDefaultRenderer(Integer.class, centerRenderer);

        //add the table to the frame
        this.add(new JScrollPane(table));

        this.setTitle("Dimensions returned from the Technical Drawing");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
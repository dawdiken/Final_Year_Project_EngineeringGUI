import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DimensionsToGui extends JFrame
{
    public DimensionsToGui(String [] dimensionsValues)
    {
        //headers for the table
        String[] columns = new String[] {
                "Dimension Number", "Dimension", "Tolerance"
        };

        Object [][] dataNew = new Object [dimensionsValues.length][dimensionsValues.length];
        for(int i = 0; i < dimensionsValues.length; i++) {
            dataNew[i][0] = i+1;
            dataNew[i][1] = dimensionsValues[i];
            dataNew[i][2] = "+/- 0.10";
        }

        //create table with data
        JTable table = new JTable(dataNew, columns);
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
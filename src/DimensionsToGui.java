import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;

public class DimensionsToGui extends JFrame
{
    public DimensionsToGui(ArrayList<String>  dimensionsValues)
    {
        //headers for the table
        String[] columns = new String[] {
                "Dimension Number", "Dimension", "Tolerance"
        };

        Object [][] dataNew = new Object [dimensionsValues.size()][dimensionsValues.size()];
        for(int i = 0; i < dimensionsValues.size(); i++) {
            dataNew[i][0] = i+1;
            dataNew[i][1] = dimensionsValues.get(i);
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
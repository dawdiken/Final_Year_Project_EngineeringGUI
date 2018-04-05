import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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
        for (int i = 0; i <data2.length ; i++) {
            //strParts[i].replace("\\\\", "");
            //strParts[i].substring(0,strParts.length-1);
            System.out.println(data2[i]);
        }
        System.out.println("herererererereeeeeeeeeeeeeeeeeeee");
        System.out.println("lenght of data size"+data2.length);
        Object [][] dataNEw = new Object [data2.length][data2.length];
        //Object [][] dataNEw = {};
        for(int i = 0; i < data2.length; i++) {
            dataNEw[i][0] = i+1;
            dataNEw[i][1] = data2[i];
            dataNEw[i][2] = "+/- 0.10";
        }
        System.out.println("test herere");
//        //actual data for the table in a 2d array
//        Object[][] data = new Object[][] {
//                {1, data2[10], "+/- 0.10" },
//                {2, data2[1], "+/- 0.10" },
//                {3, data2[2], "+/- 0.10" },
//                {4, data2[3], "+/- 0.10" },
//                {5, data2[10], "+/- 0.10" },
//                {6, data2[11], "+/- 0.10" },
//                {7, data2[12], "+/- 0.10" },
//                {8, data2[13], "+/- 0.10" },
//                {9, data2[14], "+/- 0.10" },
//                {10, data2[15], "+/- 0.10" },
//                {11, data2[20], "+/- 0.10" },
//                {12, data2[23], "+/- 0.10" },
//                {13, data2[24], "+/- 0.10" },
//                {14, data2[25], "+/- 0.10" },
//        };
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

//    public void run(){
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new DimensionsToGui();
//            }
//        });
//    }

//    public static void main(String[] args)
//    {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new TableExample();
//            }
//        });
//    }
}
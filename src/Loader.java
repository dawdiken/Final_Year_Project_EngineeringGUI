import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Loader {


    /**
     * Creates SwingWorker
     */
    public SwingWorker createWorker(int choice , String custName) {
        return new SwingWorker<ArrayList<String>, ArrayList<String>>() {

            private EngineeringDataAccess database;

            @Override
            protected ArrayList<String> doInBackground() throws Exception {
                String customerName = custName;
                System.out.println("doInBackground" + customerName);

                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch (Exception exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }
                ArrayList<String> customerNames = new ArrayList<>();
                if (choice == 1){
                    customerNames = database.findCustomer();
                }
                else if (choice == 2){
                    System.out.println("custName" + custName);
                    customerNames = database.findSop(custName);
                    System.out.println("here not customers");
                }



                return customerNames;
            }

//            @Override
//            protected void process(List<Integer> chunks) {
//                // Get Info
//                for (int number : chunks) {
//                    System.out.println("Found even number: " + number);
//                }
//            }

            @Override
            protected void done() {
                ArrayList<String> chunks = new ArrayList<>();
                try {
                    chunks = get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //System.out.println("swing worker return = "+ chunks.get(0).toString());
            }
        };
    } // End of Method: createWorker()
}


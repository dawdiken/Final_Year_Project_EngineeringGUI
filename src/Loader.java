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
                    customerNames = database.findSop(custName);
                }
                else if (choice == 3){
                    customerNames = database.findTechDrawing(custName);
                }
                return customerNames;
            }

            @Override
            protected void done() {
                ArrayList<String> chunks = new ArrayList<>();
                try {
                    chunks = get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    } // End of Method: createWorker()
}


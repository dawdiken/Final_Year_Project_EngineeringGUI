import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Loader {


    /**
     * Creates an Example SwingWorker
     */
    public SwingWorker createWorker() {
        return new SwingWorker<ArrayList<String>, ArrayList<String>>() {

            private EngineeringDataAccess database;

            @Override
            protected ArrayList<String> doInBackground() throws Exception {


                try {
                    database = new DataBaseAccess();
                }
                // detect problems with database connection
                catch (Exception exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }
                ArrayList<String> customerNames = database.findCustomer();
                String[] result = {};
                //System.out.println("where is the customers" + customerNames.get(2).getCustomerName().toString());
                for (int i = 0; i < customerNames.size(); i++) {
                    result = customerNames.toArray(new String[]{});
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
                System.out.println("swing worker return = "+ chunks.get(2).toString());
            }
        };
    } // End of Method: createWorker()
}


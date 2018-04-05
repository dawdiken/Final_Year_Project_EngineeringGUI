import java.io.File;

public class CreateDirectory {

    public void CreateDirectory(){

        File file = new File("C:\\EDHRHOME");
        File file1 = new File("C:\\EDHRHOME\\ArchivedFiles");
        File file2 = new File("C:\\EDHRHOME\\FinishedWorksOrders");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            }
            if (file1.mkdir()) {
                System.out.println("Directory is created!");
            }
            if (file2.mkdir()) {
                System.out.println("Directory is created!");
            }
            else {
                System.out.println("Failed to create directory!");
            }
        }
        else {
            System.out.println("Home directory already exists");
        }
    }
}
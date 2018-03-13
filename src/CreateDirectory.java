import java.io.File;

public class CreateDirectory {

    public void CreateDirectory(){

        File file = new File("C:\\EDHRHOME");
        if (!file.exists()) {
            if (file.mkdir()) {
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
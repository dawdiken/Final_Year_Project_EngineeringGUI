import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ArchiveToCloud {

   public String ArchiveToCloud() {

       String pathToFolder = "";
       JFileChooser jfc = new JFileChooser("C:\\EDHRHOME\\FinishedWorksOrders");
       jfc.setDialogTitle("Choose Works Order to Archive: ");
       jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

       int returnValue = jfc.showDialog(null, "Select Folder");
       if (returnValue == JFileChooser.APPROVE_OPTION) {
           if (jfc.getSelectedFile().isDirectory()) {
               System.out.println("You selected the directory: " + jfc.getSelectedFile());
               pathToFolder = jfc.getSelectedFile().toString();
           }
       }
       return pathToFolder;
   }
}
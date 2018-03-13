import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class ArchiveToCloud {

   public void ArchiveToCloud() {

       String pathToFolder = "";
       JFileChooser jfc = new JFileChooser("C:\\EDHRHOME\\FinishedWorksOrders");
       jfc.setDialogTitle("Choose Works Order to Archive: ");
       jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

       int returnValue = jfc.showDialog(null, "Select Folder");
       if (returnValue == JFileChooser.APPROVE_OPTION) {
           if (jfc.getSelectedFile().isDirectory()) {
               System.out.println("You selected the directory: " + jfc.getSelectedFile());
               pathToFolder = jfc.getSelectedFile().toString();
               System.out.println("pathtofolder" + pathToFolder);
               //get the file name so you can use it to save the save the file to the cloud using its name
               File f = new File(pathToFolder);
               System.out.println(f.getName());
               String saveAs = f.getName();

               //check that you are only trying to zip and push finished works orders to the cloud
               if (pathToFolder.contains("C:\\EDHRHOME\\FinishedWorksOrders\\")){
                   System.out.println("successsssssss");
                   ZipUtils appZip = new ZipUtils();
                   appZip.generateFileList(new File(pathToFolder));
                   appZip.zipIt("C:\\EDHRHOME\\"+saveAs);//default location where zippe file will always be
                   try{
                       CloudStorageHelper.uploadFile("longtermstorageedhr", "C:\\EDHRHOME\\"+saveAs);
                   }
                   catch(Exception ee){
                       System.out.println(ee);
                   }
               }
           }
       }
   }
}
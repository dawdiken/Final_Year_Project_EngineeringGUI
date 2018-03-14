import javax.crypto.Cipher;
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
               String saveAs = jfc.getSelectedFile().getName().toString();
               System.out.println("pathtofolder2" + saveAs);

               //check that you are only trying to zip and push finished works orders to the cloud
               if (pathToFolder.contains("C:\\EDHRHOME\\FinishedWorksOrders\\")){
                   System.out.println("successsssssss");
                   String zippedAs = ZippFolder(pathToFolder,saveAs);
                   String encryptAs = EncryptFolder(saveAs);
                   String pushToCloud = StoreInCloud(saveAs);
               }
           }
       }
   }

   private String ZippFolder(String pathToFolder, String saveAs){
       ZipUtils appZip = new ZipUtils();
       appZip.generateFileList(new File(pathToFolder));
       appZip.zipIt("C:\\EDHRHOME\\"+saveAs);//default location where zippe file will always be
       return null;
   }

   private String EncryptFolder(String saveAs){
       String key = "This is a secret";
       File encryptedFile = new File("C:\\EDHRHOME\\"+saveAs+".encrypted");
       File inputFile = new File("C:\\EDHRHOME\\"+saveAs);
       Crypto encryptMyFolder = new Crypto();
       encryptMyFolder.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
       return null;
   }

   private String StoreInCloud(String saveAs){
       try{
           CloudStorageHelper.uploadFile("longtermstorageedhr", "C:\\EDHRHOME\\"+saveAs+".encrypted");
       }
       catch(Exception ee){
           System.out.println(ee);
       }
       return null;
   }
}
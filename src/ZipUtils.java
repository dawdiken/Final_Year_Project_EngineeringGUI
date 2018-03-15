import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private List <String> fileList;

    public ZipUtils() {
        fileList = new ArrayList < String > ();
    }

    public void zipIt(String zipFile, String pathToFolder) {
        byte[] buffer = new byte[1024];
        String source = new File(pathToFolder).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: this.fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(pathToFolder + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node, String source) {
        //zip file only
        if (node.isFile()) {
            System.out.println(node.toString());
            fileList.add(generateZipEntry(node.toString(), source));
        }
        //zip directory and check for sub folders and directories
        if (node.isDirectory()) {
            String[] subNote = node.list();
                for (String filename : subNote) {
                    generateFileList(new File(node, filename), source);//done like this to zip files within folders
                }
        }
    }

    private String generateZipEntry(String file, String source) {
        return file.substring(source.length() + 1, file.length());
    }
}
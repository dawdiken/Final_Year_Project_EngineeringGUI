import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class DimensionVisionAPI {
    private static final String TARGET_URL =
            "https://vision.googleapis.com/v1/images:annotate?";
    private static final String API_KEY =
            "key=AIzaSyBpUPfVsfVn2SIgYL4xwfYfLUe0wHzsEbM";
    public void dimensionVisionAPI(String FileName, NewJobEntry job) {

        try{
            URL serverUrl = new URL(TARGET_URL + API_KEY);
            URLConnection urlConnection = serverUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setDoOutput(true);
            BufferedWriter httpRequestBodyWriter = new BufferedWriter(new
                    OutputStreamWriter(httpConnection.getOutputStream()));
            httpRequestBodyWriter.write
                    ("{\n" +
                            "  \"requests\": [\n" +
                            "    {\n" +
                            "      \"image\": {\n" +
                            "        \"source\": {\n" +
                            "          \"gcsImageUri\": \"gs://vision_fyp/"+FileName+"\"\n" +
                            "        }\n" +
                            "      },\n" +
                            "      \"features\": [\n" +
                            "        {\n" +
                            "          \"type\": \"TEXT_DETECTION\"\n" +
                            "        }\n" +
                            "      ]\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}");
            httpRequestBodyWriter.close();
            String response = httpConnection.getResponseMessage();
            System.out.println(response);

            if (httpConnection.getInputStream() == null) {
                System.out.println("No stream");
            }

            Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());

            String textFromResponse = "";
            while (httpResponseScanner.hasNext()) {
                String line = httpResponseScanner.nextLine();
                if(line.contains("text") && line.length()> 100 ) {
                    textFromResponse = line;
                }
            }

            String[] strParts = textFromResponse.split("\\\\n");
            httpResponseScanner.close();
            Gson gson=new GsonBuilder().create();
            String jsonArray=gson.toJson(strParts);
            job.setDimension(jsonArray);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new DimensionsToGui(strParts);
                }
            });
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
    }
}

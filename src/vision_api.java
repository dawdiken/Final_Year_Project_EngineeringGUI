import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.spec.ECField;
import java.util.Arrays;
import java.util.Scanner;
import org.json.*;

public class vision_api {
    private static final String TARGET_URL =
            "https://vision.googleapis.com/v1/images:annotate?";
    private static final String API_KEY =
            "key=AIzaSyBpUPfVsfVn2SIgYL4xwfYfLUe0wHzsEbM";
    public static void main(String[] args) {

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
                            "          \"gcsImageUri\": \"gs://vision_fyp/idler.jpg\"\n" +
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


            //gs://vision_fyp/fig_30.jpeg
            httpRequestBodyWriter.close();
            String response = httpConnection.getResponseMessage();
            System.out.println(response);

            if (httpConnection.getInputStream() == null) {
                System.out.println("No stream");
                return;
            }

            Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());

            String resp = "";
            String respt = "";
            while (httpResponseScanner.hasNext()) {

                String line = httpResponseScanner.nextLine();
                if(line.contains("text") && line.length()> 100 ){
                    //System.out.println(line);
                    respt += line;
                    System.out.println(line.length());
                }
                resp += line;



                //System.out.println(line);  //  alternatively, print the line of response
            }
            //JSONObject myResponse = new JSONObject(resp.toString());
            System.out.println(respt);
            //String[] lines = respt.split("\\r?\\n");
            String strParts22 = respt.replace("\\\\", "");
            String[] strParts = strParts22.split("n");


//            String lines[] = String.split("\\r?\\n", -1);
            //System.out.println("ipAddress- "+strParts.getString("text"));
            String[] strParts2 = {};
            for (int i = 0; i <strParts.length ; i++) {
                strParts[i].replace("\\\\", "");
                //strParts[i].substring(0,strParts.length-1);
                System.out.println(strParts[i].toString());
            }




//            JSONArray jsonarray = new JSONArray(resp);
//            for (int i = 0; i < jsonarray.length(); i++) {
//                JSONObject jsonobject = jsonarray.getJSONObject(i);
//                String name = jsonobject.getString("textAnnotations :");
//                //String url = jsonobject.getString("text");
//                System.out.println(name);
//                //System.out.println(url);
//            }




            httpResponseScanner.close();

//            Object obj = parser.parse(s);
//            Json array = (JSONArray)obj;
//
//            System.out.println("The 2nd element of array");
//            System.out.println(array.get(1));
//            System.out.println();
//
//            JSONObject obj2 = (JSONObject)array.get(1);
//            System.out.println("Field \"1\"");
//            System.out.println(obj2.get("1"));


        }
        catch(Exception ee){
            System.out.println(ee);
        }





    }



}

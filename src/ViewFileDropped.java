public class ViewFileDropped {

    public void ViewFileDropped(String pathto) {

        try {
            String command = "rundll32 url.dll,FileProtocolHandler " + pathto;
            System.out.println(command);
            Process p = Runtime
                    .getRuntime()
                    .exec(command);
            p.waitFor();

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

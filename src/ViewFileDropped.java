public class ViewFileDropped {

    public void viewFileDropped(String pathto) {
        try {
            String command = "rundll32 url.dll,FileProtocolHandler " + pathto;
            System.out.println(command);
            Process p = Runtime
                    .getRuntime()
                    .exec(command);
            p.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        myFTP ftp = new myFTP();
        ftp.connect("speedtest.tele2.net", 21);
        ftp.getResponse();

        ftp.sendResponse("USER anonymous");
        ftp.sendResponse("PASS  ");
        ftp.getCurrentDirectory();

        ftp.setPassiveMode();
        //ftp.ListFiles();
        ftp.downloadFile("512KB.zip");
        
    }

}

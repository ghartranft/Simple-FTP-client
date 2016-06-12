
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        myFTP ftp = new myFTP();
        ftp.connect("speedtest.tele2.net", 21);
        ftp.getResponse();

        ftp.sendResponse("USER anonymous");
        ftp.sendResponse("PASS  ");
        ftp.getCurrentDirectory();
        ftp.changeDirectory("/upload");
        ftp.sendResponse("TYPE I");
        ftp.setPassiveMode();
        // ftp.ListFiles();
        //ftp.downloadFile("512KB.zip");
        ftp.uploadFile("output.zip");
        ftp.setPassiveMode();
        ftp.sendResponse("QUIT");

    }

}

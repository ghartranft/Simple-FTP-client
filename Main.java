import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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

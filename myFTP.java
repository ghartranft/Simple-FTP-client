import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class myFTP {

    Socket ftp;
    BufferedReader in;
    PrintWriter out;

    Socket dataSocket;
    BufferedReader datain;
    PrintWriter dataout;

    public void connect(String url, int port) throws IOException {
        ftp = new Socket(url, port);
        in = new BufferedReader(new InputStreamReader(ftp.getInputStream()));
        out = new PrintWriter(ftp.getOutputStream(), true);

    }

    public void getResponse() {
        getResponse(this.in);
    }

    private String getResponse(BufferedReader input) {
        String response = null;
        try {

            do {
                response = input.readLine();
                System.out.println(response);
            } while (input.ready());

        } catch (IOException ex) {
            System.out.println("ERROR! Nothing to read?");
            Logger.getLogger(myFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    private void sendResponse(String data, Socket sock, BufferedReader in, PrintWriter out) {
        out.println(data);
        getResponse(in);
    }

    public void sendResponse(String data) {
        sendResponse(data, ftp, in, out);
    }

    public void close() {
        try {
            ftp.close();
        } catch (IOException ex) {
            System.out.println("Could not close!");
        }
    }

    public int getPort() {
        return ftp.getPort();

    }

    public void getCurrentDirectory() {
        sendResponse("PWD");
    }

    public void changeDirectory(String path) {
        sendResponse("CWD " + path);
    }

    public void setPassiveMode() {
        try {

            out.println("PASV");
            String response = in.readLine();
            
            int start = response.indexOf("(");
            response = response.substring(start + 1, response.length() - 2);

            System.out.println(response + "dongs");
            
           
            String[] parsedIP = response.split(",");
            String IP = parsedIP[0] + "." + parsedIP[1] + "." + parsedIP[2] + "." + parsedIP[3];
            int port = (Integer.parseInt(parsedIP[4]) * 256) + Integer.parseInt(parsedIP[5]);

            System.out.println(IP);
            System.out.println(port);

            dataSocket = new Socket(IP, port);
            datain = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            dataout = new PrintWriter(dataSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(myFTP.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }

    }

    public void ListFiles() throws IOException {
        // setPassiveMode();
        sendResponse("LIST");
        getResponse(datain);

    }

    public void downloadFile(String file) throws IOException {
        setPassiveMode();
        sendResponse("RETR " + file);

        BufferedInputStream input = new BufferedInputStream(dataSocket.getInputStream());
        OutputStream output = new FileOutputStream("D:\\test\\output");
        byte[] buffer = new byte[4096];
        int readbytes = 0;
        int c = -1;
        while ((c = input.read(buffer, 0, buffer.length)) != -1) {
            output.write(buffer, 0, c);
            output.flush();
        }

    }
}

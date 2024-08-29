import java.io.*;

public class ProgramServer {
    
    public static void main(String[] args) throws IOException {
    	System.out.println("Server start...");
        Server server = new Server();
        try {
        	server.ServerWork();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
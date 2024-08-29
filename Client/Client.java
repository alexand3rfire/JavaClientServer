import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket; 	//сокет для общения
    private static BufferedReader reader; 	// нам нужен ридер читающий с консоли, иначе как
    private static BufferedReader in; 		// поток чтения из сокета
    private static BufferedWriter out; 		// поток записи в сокет

    public void ClientWork() throws IOException {
        try {
            clientSocket = new Socket("127.0.0.1", 8080);
            reader = new BufferedReader(new InputStreamReader(System.in));   //переменная для чтения с клавиатуры клиента
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String word = "";
            do {
                System.out.print("Что отправить серверу? >");
            	word = reader.readLine(); 	// для чтения из потока клавиатуры
                
                out.write(word + "\n"); 	// отправка запроса на сервер
                out.flush();    			// очистка буфера отправки
                String serverWord = "";
                String str = "";			// чтение с сервера
                do {
                	str = in.readLine();
                	/*if (str.equals("")) {
                		break;
                	}*/
                	
                	serverWord += str + "\n";
                } while (!str.equals(""));                
                System.out.println(serverWord); // получив - выводим на экран
            } while (word != "");

        } catch (Exception e) {
            System.err.println(e);
        } finally { 						// по окончании в любом случае необходимо закрыть сокет и потоки
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;


public class Server {
        private static Socket clientSocket; 	//сокет
        private static ServerSocket server; 	// серверсокет
        private static BufferedReader in; 		// поток чтения из сокета
        private static BufferedWriter out; 		// поток записи в сокет
        
        public static String serverWord;
        public boolean isEnd = false;

        public void ServerWork() throws IOException, SQLException {
            try {
                server = new ServerSocket(8080); 				// серверсокет прослушивает порт 8080
                while (isEnd != true) {
                    clientSocket = server.accept(); 			// прием клиентского сокета
                    Runnable task = ThreadClient(clientSocket);	// запуск обработки в отдельном потоке
                    Thread thread = new Thread(task);
                    thread.start();                   
                }
                
            } catch (IOException e) {
                System.err.println(e);
            } finally {
            	in.close();
                out.close();
            	clientSocket.close();
                server.close();
            }
        }

        public Runnable ThreadClient(Socket client) throws IOException, SQLException {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String word = "";
            do {
            	serverWord = "";
	            word = in.readLine(); 		// ждём пока от клиента не поступит строка
	
	            System.out.println(word);
	            
	            if (word.equals("Data")) {
		            StudentFunction studentFunction = new StudentFunction();
		            serverWord = studentFunction.ServerFunction();
		            serverWord += "\n\n";
	            } else if (word.equals("File")) {
		            File file = new File("a.txt");
		            FileReader inFile = new FileReader(file);
		            BufferedReader bufFile= new BufferedReader(inFile);
		            String line;		
		            while ((line = bufFile.readLine()) != null) {
		                serverWord += line + "\n";
		            }
		            serverWord += "\n";
		            bufFile.close();
	            } else if (word.equals("DB")) {
		            Database dbs = new Database();
		            ResultSet resultSet = dbs.getStudents();
		            while(resultSet.next()){
		                int id = resultSet.getInt(1);
		                String name = resultSet.getString(2);
		                String secondname = resultSet.getString(3);
		                String subject = resultSet.getString(4);
		                String group = resultSet.getString(5);
		                Float rate = resultSet.getFloat(6);
		                serverWord += id + ": " + name + " " + secondname + " - " + rate + " (" + subject + " " + group + ")\n";
		            }
		            serverWord += "\n";
		
	            } else if (word.equals("Rate")) {
		            Database dbs = new Database();
		            ResultSet resultSet = dbs.getRates();
		            while(resultSet.next()){
		                String name = resultSet.getString(1);
		                String subject = resultSet.getString(2);
		                Integer rate = resultSet.getInt(3);
		                serverWord += name + " " + subject + " - " + rate + ")\n";
		            }
		            serverWord += "\n";
	            } else if (word.equals("FileS")) {
		            /* Посимвольное чтение файла */
		            Reader ch = new FileReader("file.bin");
		            BufferedReader bufReadChar = new BufferedReader(ch);
		            int sym = bufReadChar.read();
		            while (sym != -1) {
		            	serverWord += (char)sym;
		            	sym = bufReadChar.read();
		            }
		            System.out.println("File char readed.");
		            ch.close();
		            serverWord += "\n";		            
	            } else if (word.equals("FileBinary")) {
		            /* Бинарное (двоичное) чтение файла */
		            byte[] binFile;
		            try (FileInputStream fin = new FileInputStream("file.bin")) {
		            	binFile = new byte[fin.available()];
		                //System.out.printf("File size: %d bytes \n", fin.available());
		                int i = -1;
		                int index = 0;
		                while((i = fin.read()) != -1){
		                    binFile[index] = (byte)i;
		                    index++;
		                }
		                System.out.println("File binary readed.");
		                serverWord = new String(binFile, StandardCharsets.UTF_8);
		                serverWord += "\n";
		            } catch(IOException ex) {                  
		                System.out.println(ex.getMessage());
		            }
	            } else if (word.equals("EXIT")) {
	            	serverWord = "Выход из программы\n\n";
	            	isEnd = true;
	            } else {
	            	serverWord = "Неверный запрос\n\n";
	            }
	            System.out.print(serverWord);
	            out.write(serverWord);
                out.flush();
	            
            } while (!word.equals("END") || !word.equals("EXIT"));
            
            out.write("Пока!\n\n");
            out.flush();
            
            in.close();
            out.close();
            client.close();
            return null;			
        }

        public String getDataDb() {
            String url = "jdbc:mysql://localhost/";
            String user = "root";
            String pass = "root";
            String dbname = "javadb";
            
            String result = "";
            Statement statement = null;
            ResultSet resultSet = null;
            Connection conn = null;
            String sqlCommand = "";
            try{
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                conn = DriverManager.getConnection(url + dbname, user, pass);
                System.out.println("Соединение успешно установлено!");
                
                sqlCommand = "SELECT * FROM students; ";
                statement = conn.createStatement();
                resultSet = statement.executeQuery(sqlCommand);
                result = "";
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String secondname = resultSet.getString(3);
                    String subject = resultSet.getString(4);
                    String group = resultSet.getString(5);
                    Float rate = resultSet.getFloat(6);
                    result += id + ". " + name + " " + secondname + " - " + rate + " (" + subject + ", " + group + ")\n"; 
                    System.out.printf("%d. %s %s - %f (%s, %s) \n", id, name, secondname, rate, subject, group);
                }
            }
            catch(Exception ex){
                System.out.println("Ошибка подключения...");
                System.out.println(ex);
            }

            return result;
        }
}
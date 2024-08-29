import java.sql.*;

public class Database {
    String url = "jdbc:mysql://localhost/";
    String user = "root";
    String pass = "root";
    String dbname = "javadb";
	public Database() {

    }
	
    public ResultSet getStudents() {
        ResultSet resultSet = null;
		 try{
             Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             Connection conn = DriverManager.getConnection(url + dbname, user, pass);
             System.out.println("Соединение успешно установлено!");
             
             String sqlCommand = "SELECT * FROM students; ";
             Statement statement = conn.createStatement();
             resultSet = statement.executeQuery(sqlCommand);
             
             while(resultSet.next()){
            	 int id = resultSet.getInt(1);
                 String name = resultSet.getString(2);
                 String secondname = resultSet.getString(3);
                 String subject = resultSet.getString(4);
                 String group = resultSet.getString(5);
                 Float rate = resultSet.getFloat(6);
                 System.out.printf("%d. %s %s - %f (%s, %s) \n", id, name, secondname, rate, subject, group);
             }
         }
         catch(Exception ex){
             System.out.println("Ошибка подключения...");
             System.out.println(ex);
         }

         return resultSet;
	}
    
    public ResultSet getRates() {
    	ResultSet resultSet = null;
		 try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection conn = DriverManager.getConnection(url + dbname, user, pass);
            System.out.println("Соединение успешно установлено!");
            
            String sqlCommand = "SELECT St.firstname + ' ' + St.secondname AS 'ФИО', \r\n"
            		+ "		Sb.title AS 'Предмет', \r\n"
            		+ "		R.rate AS 'Оценка'\r\n"
            		+ "FROM Rates AS R, \r\n"
            		+ "     Subjects AS Sb, \r\n"
            		+ "	 Students AS St \r\n"
            		+ "WHERE R.student_id = St.id AND R.subject_id = Sb.id ";
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlCommand);
            
            while(resultSet.next()){
                String name = resultSet.getString(1);
                String subject = resultSet.getString(2);
                Integer rate = resultSet.getInt(3);
                System.out.printf("%s %s - %d\n", name, subject, rate);
            }
        }
        catch(Exception ex) {
            System.out.println("Ошибка подключения...");
            System.out.println(ex);
        }

        return resultSet;
    }

}

import java.util.ArrayList;
import java.util.Scanner;
public class ProgramStudent {
    
    public static void main(String[] args) {
        ArrayList<StudentFunction> list1 = new ArrayList<StudentFunction>();
        Scanner input = new Scanner(System.in);
        System.out.println("Введите данные студентов");
        for (int i = 0, j = 0; i == j; i++, j++) {
        	StudentFunction st = new StudentFunction();
        	System.out.print("Имя: ");
            st.name = input.nextLine();
            System.out.print("Фамилия: ");
            st.surname = input.nextLine();
            System.out.print("Группа: ");
            st.number = input.nextLine();
            System.out.print("Предмет: ");
            st.subject = input.nextLine();
            System.out.print("Оценка: ");
            st.rate = input.nextLine();
            if (st.rate != "") {
            	list1.add(st);
            } else {
            	j = j - 1;
            }            
        }
        //input.close();

        for (StudentFunction student : list1) {
            System.out.println(student.name);
            System.out.println(student.surname);
            System.out.println(student.number);
            System.out.println(student.subject);
            System.out.println(student.rate);
        }

        Client client;
		try {
			client = new Client();
			client.ClientWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
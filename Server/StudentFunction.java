import java.util.ArrayList;
import java.util.Scanner;

public class StudentFunction {
    public String name = "Имя";
    public String surname = "Фамилия";
    public String number = "Номер группы";
    public String subject = "Предмет";
    public String rate = "Оценка";

    public String ServerFunction() {
        ArrayList<StudentFunction> list1 = new ArrayList<StudentFunction>();
        Scanner input = new Scanner(System.in);

        for (int i = 0, j = 0; i == j; i++, j++) {
            StudentFunction st = new StudentFunction();
            System.out.println("Введите имя студента");
            st.name = input.nextLine();
            System.out.println("Введите фамилию");
            st.surname = input.nextLine();
            System.out.println("Введите группу");
            st.number = input.nextLine();
            System.out.println("Введите предмет");
            st.subject = input.nextLine();
            System.out.println("Введите оценку");
            st.rate = input.nextLine();
            if (st.rate != "") {
            	list1.add(st);
            } else {
            	j = j - 1;
            }            
        }
        //input.close();
        
        String serverWord = "";
        
        for (StudentFunction student : list1) {
            serverWord += student.name + "\n";
            serverWord += student.surname  + "\n";
            serverWord += student.number + "\n";
            serverWord += student.subject + "\n";
            serverWord += student.rate + "\n";
        }

        return serverWord;
    }
}
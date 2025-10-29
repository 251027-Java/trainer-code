import java.util.Scanner;

public class LetterGrade {
    public static void main(){
        Scanner scanner = new Scanner(System.in);
        double grade;
        try {
            grade = scanner.nextDouble();
        } catch (Exception e){
            System.out.println("That wasn't a number!");
            grade = 0;
        }
        System.out.println(grade);



    }
}

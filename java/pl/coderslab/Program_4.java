package pl.coderslab;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;
import pl.coderslab.utils.GetConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Program_4 {

    public static void main(String[] args) {
        try {
            Connection connection = GetConnection.getConnection();
            Scanner scan = new Scanner(System.in);
            printMenu();
            String choice = scan.nextLine();
            while (!choice.equals("quit")) {
                if (choice.equals("add")) {
                    Program_1.printAllUsers(connection);
                    System.out.println("Podaj id użytkownika:");
                    User user = User.loadById(connection,takeId());
                    Program_2.printAllExercises(connection);
                    System.out.println("Podaj id zadania:");
                    Exercise exercise = Exercise.loadById(connection,takeId());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    Solution solution = new Solution(formatter.format(date),exercise,user);
                    solution.saveToDb(connection);

                } else if (choice.equals("view")) {
                    System.out.println("Podaj id użytkownika");
                    System.out.println(Arrays.toString(Solution.loadAllByUserId(connection,takeId())));
                }
                printMenu();
                choice = scan.nextLine();
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int takeId() {
        Scanner scanner = new Scanner(System.in);
        boolean gotInt = false;
        String stringId = "aaa";
        while (!gotInt) {
            stringId = scanner.nextLine();
            try {
                int id = Integer.parseInt(stringId);
                gotInt = true;
            } catch (Exception e) {
                System.out.println("Podaj id, a nie: " + stringId);
            }
        }
        return Integer.parseInt(stringId);
    }

    private static void printMenu() {
        System.out.println("Wybierz jedną z opcji:\n" +
                "add - przypisywanie zadań do użytkowników,\n" +
                "view - przeglądanie rozwiązań danego użytkownika,\n" +
                "quit - zakończenie programu.");
    }
}

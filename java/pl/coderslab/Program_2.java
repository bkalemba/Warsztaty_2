package pl.coderslab;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.User;
import pl.coderslab.models.UserGroup;
import pl.coderslab.utils.GetConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Program_2 {
    public static void main(String[] args) {
        try {
            Connection connection = GetConnection.getConnection();
            Scanner scan = new Scanner(System.in);
            printAllExercises(connection);
            printMenu();
            String choice = scan.nextLine();
            while (!choice.equals("quit")) {
                if (choice.equals("add")) {
                    Exercise exercise = new Exercise();
                    System.out.println("Podaj title:");
                    exercise.setTitle(scan.nextLine());
                    System.out.println("Podaj description:");
                    exercise.setDescription(scan.nextLine());
                    exercise.saveToDb(connection);
                    printAllExercises(connection);

                } else if (choice.equals("edit")) {
                    System.out.println("Podaj id zadania");
                    Exercise exercise = Exercise.loadById(connection,takeId());
                    System.out.println("Podaj title:");
                    exercise.setTitle(scan.nextLine());
                    System.out.println("Podaj description:");
                    exercise.setDescription(scan.nextLine());
                    exercise.saveToDb(connection);
                    printAllExercises(connection);

                } else if (choice.equals("delete")) {
                    System.out.println("Podaj id zadania");
                    Exercise exercise = Exercise.loadById(connection,takeId());
                    exercise.delete(connection);
                    printAllExercises(connection);

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
                "add - dodanie zadania,\n" +
                "edit - edycja zadania,\n" +
                "delete - usuwanie zadania,\n" +
                "quit - zakończenie programu.");
    }

    protected static void printAllExercises(Connection connection) throws SQLException {
        System.out.println(Arrays.toString(Exercise.loadAll(connection)));
    }
}

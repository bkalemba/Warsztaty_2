package pl.coderslab;

import pl.coderslab.models.User;
import pl.coderslab.models.UserGroup;
import pl.coderslab.utils.GetConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Program_1 {
    public static void main(String[] args) {
        try {
            Connection connection = GetConnection.getConnection();
            Scanner scan = new Scanner(System.in);
            printAllUsers(connection);
            printMenu();
            String choice = scan.nextLine();
            while (!choice.equals("quit")) {
                if (choice.equals("add")) {
                    User user = new User();
                    System.out.println("Podaj username:");
                    user.setUsername(scan.nextLine());
                    System.out.println("Podaj password:");
                    user.setPassword(scan.nextLine());
                    System.out.println("Podaj email:");
                    user.setEmail(scan.nextLine());
                    System.out.println("Podaj id grupy użytkownika:");
                    user.setUserGroup(UserGroup.loadById(connection, takeId()));
                    user.saveToDb(connection);
                    printAllUsers(connection);

                } else if (choice.equals("edit")) {
                    System.out.println("Podaj id użytkownika");
                    User user = User.loadById(connection,takeId());
                    System.out.println("Podaj username:");
                    user.setUsername(scan.nextLine());
                    System.out.println("Podaj password:");
                    user.setPassword(scan.nextLine());
                    System.out.println("Podaj email:");
                    user.setEmail(scan.nextLine());
                    System.out.println("Podaj id grupy użytkownika:");
                    user.setUserGroup(UserGroup.loadById(connection, takeId()));
                    user.saveToDb(connection);
                    printAllUsers(connection);

                } else if (choice.equals("delete")) {
                    System.out.println("Podaj id użytkownika");
                    User user = User.loadById(connection,takeId());
                    user.delete(connection);
                    printAllUsers(connection);

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
                "add - dodanie użytkownika,\n" +
                "edit - edycja użytkownika,\n" +
                "delete - usuwanie użytkownia,\n" +
                "quit - zakończenie programu.");
    }

    protected static void printAllUsers(Connection connection) throws SQLException {
        System.out.println(Arrays.toString(User.loadAll(connection)));
    }
}

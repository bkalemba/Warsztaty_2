package pl.coderslab;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.UserGroup;
import pl.coderslab.utils.GetConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Program_3 {
    public static void main(String[] args) {
        try {
            Connection connection = GetConnection.getConnection();
            Scanner scan = new Scanner(System.in);
            printAllUserGroups(connection);
            printMenu();
            String choice = scan.nextLine();
            while (!choice.equals("quit")) {
                if (choice.equals("add")) {
                    UserGroup userGroup = new UserGroup();
                    System.out.println("Podaj name:");
                    userGroup.setName(scan.nextLine());
                    userGroup.saveToDb(connection);
                    printAllUserGroups(connection);

                } else if (choice.equals("edit")) {
                    System.out.println("Podaj id grupy");
                    UserGroup userGroup = UserGroup.loadById(connection,takeId());
                    System.out.println("Podaj name:");
                    userGroup.setName(scan.nextLine());
                    userGroup.saveToDb(connection);
                    printAllUserGroups(connection);

                } else if (choice.equals("delete")) {
                    System.out.println("Podaj id grupy");
                    UserGroup userGroup = UserGroup.loadById(connection,takeId());
                    userGroup.delete(connection);
                    printAllUserGroups(connection);

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
                "add - dodanie grupy,\n" +
                "edit - edycja grupy,\n" +
                "delete - usuwanie grupy,\n" +
                "quit - zakończenie programu.");
    }

    private static void printAllUserGroups(Connection connection) throws SQLException {
        System.out.println(Arrays.toString(UserGroup.loadAll(connection)));
    }
}

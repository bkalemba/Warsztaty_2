package pl.coderslab;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.utils.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Program_User {
    private static int userId;

    public static void main(String[] args) {
        System.out.println("Podaj swoje id");
        userId = takeId();
        try {
            Connection connection = GetConnection.getConnection();
            printMenu();
            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();
            while (!choice.equals("quit")) {
                if (choice.equals("add")) {
                    System.out.println(Arrays.toString(loadAllWithoutSolution(connection, userId)));
                    System.out.println("Podaj id zadania do którego chcesz dodać rozwiązanie:");
                    Solution solution = loadSolutionByExerciseId(connection, takeId());
                    try {
                        solution.getUpdated().equals(null);
                        System.out.println("Podałeś już rozwiązanie tego zadania");

                    } catch (NullPointerException e) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        System.out.println("Podaj rozwiązanie:");
                        solution.setDescription(scan.nextLine());
                        solution.setUpdated(formatter.format(date));
                        solution.saveToDb(connection);
                    }

                } else if (choice.equals("view")) {

                    System.out.println(Arrays.toString(loadAllSolutionByUser(connection, userId)));
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
                "add - dodanie rozwiązania,\n" +
                "view - przeglądanie swoich rozwiązań,\n" +
                "quit - zakończenie programu.");
    }

    private static Exercise[] loadAllWithoutSolution(Connection connection, int userId) throws SQLException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "select distinct exercise_id from solution WHERE users_id = ? AND updated IS NULL;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Exercise exercise = Exercise.loadById(connection, resultSet.getInt("exercise_id"));
            exercises.add(exercise);
        }
        Exercise[] uArray = new Exercise[exercises.size()];
        uArray = exercises.toArray(uArray);
        return uArray;
    }

    private static Solution loadSolutionByExerciseId(Connection connection, int exerciseId) throws SQLException {
        String sql = "select id from solution where users_id = ? and exercise_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, exerciseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Solution.loadById(connection, resultSet.getInt("id"));
        }
        return null;
    }

    private static Solution[] loadAllSolutionByUser(Connection connection, int userId) throws SQLException {
        List<Solution> solutions = new ArrayList<>();
        String sql = "select * from solution WHERE users_id = ? AND updated IS NOT NULL;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution solution = Solution.loadById(connection, resultSet.getInt("id"));
            solutions.add(solution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }
}

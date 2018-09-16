package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {

    }

    public Solution(String created, Exercise exercise, User user){
        this.created = created;
        this.exercise = exercise;
        this.user = user;
    }

    public Solution(String created, String updated, String description, Exercise exercise, User user) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveToDb(Connection connection) throws SQLException {
        if (id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?,?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise.getId());
            preparedStatement.setInt(5, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, exercise.getId());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.setInt(6, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    static public Solution loadById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadById(conn, resultSet.getInt("users_id"));
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAll(Connection conn) throws SQLException {
        List<Solution> solutions = new ArrayList<>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    static public Solution[] loadAllByUserId(Connection conn,int id) throws SQLException {
        List<Solution> solutions = new ArrayList<>();
        String sql = "SELECT * FROM solution WHERE users_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    static public Solution[] loadAllExerciseId(Connection conn,int id) throws SQLException {
        List<Solution> solutions = new ArrayList<>();
        String sql = "SELECT * FROM solution WHERE exercise_id = ? ORDER BY created DESC";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}';
    }
}

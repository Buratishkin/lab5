package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSQUserDAO implements UserDAO {
  @Override
  public String[] logIn(String user, String password) throws SQLException {
    String sql = "SELECT password, salt FROM users where name = ?";

    if (checkUser(user)) {
      try (Connection connection = DBManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, user);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          return new String[] {resultSet.getString("password"), resultSet.getString("salt")};
        }
      } catch (SQLException e) {
        throw new SQLException("Ошибка при выполнении запроса: " + e.getMessage());
      }
    } else {
      throw new SQLException("Такого пользователя не существует");
    }
    return new String[] {""};
  }

  @Override
  public void registration(String user, String hash, String salt) throws SQLException {
    String sql =
            """
            INSERT INTO users (
            name, password, salt)
            VALUES (?, ?, ?)""";

    if (checkUser(user)) {
      throw new IllegalArgumentException("Такой пользователь уже существует");
    } else {
      try (Connection connection = DBManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, user);
        statement.setString(2, hash);
        statement.setString(3, salt);

        if (statement.executeUpdate() == 0) throw new SQLException("Запрос не выполнен");
      } catch (SQLException e) {
        throw new SQLException("Ошибка при выполнении запроса: " + e.getMessage());
      }
    }
  }

  private boolean checkUser(String user) throws SQLException {
    String sql = "SELECT name FROM users";
    try (Connection connection = DBManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        if (resultSet.getString("name").equals(user)) return true;
      }
    } catch (SQLException e) {
      throw new SQLException("Ошибка при создании соединения: " + e.getMessage());
    }
    return false;
  }

  @Override
  public int getUserId(String userName) throws SQLException {
    String sql = "SELECT id FROM users WHERE name = ?";

    try (Connection connection = DBManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, userName);
      ResultSet resultSet = statement.executeQuery();
      if (checkUser(userName)) {
        int id = 0;
        if (resultSet.next()) {
          id = resultSet.getInt("id");
        }
        return id;
      } else {
        throw new SQLException("Не существует пользователя " + userName);
      }
    } catch (Exception e) {
      throw new SQLException("При нахождении id пользователя возникла ошибка: " + e.getMessage());
    }
  }
}
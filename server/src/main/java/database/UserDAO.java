package database;

import java.sql.SQLException;

public interface UserDAO {
  String[] logIn(String user, String password) throws SQLException;

  void registration(String user, String password, String salt) throws SQLException;

  int getUserId(String userName) throws SQLException;
}
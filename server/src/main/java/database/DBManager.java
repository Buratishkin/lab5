package database;

import java.sql.*;

public class DBManager {
  private static final String URL = System.getenv("DBURL");
  private static final String NAME = System.getenv("DBNAME");
  private static final String PASSWORD = System.getenv("DBPASSWORD");

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, NAME, PASSWORD);
  }
}

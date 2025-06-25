package database;

import classes.City;
import classes.Coordinates;
import classes.Human;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PSQLCollectionDAO implements CollectionDAO {
  @Override
  public synchronized void remove(String user, int id) {
    try {
      checkOwner(user, id);
      String sql = "DELETE FROM collection WHERE id = " + id;
      Connection connection = DBManager.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql);
      if (statement.executeUpdate() == 0) {
        throw new SQLException("Объект не был удален");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public synchronized void update(City updatedCity, String user, int id) {
    try {
      int ownerId = checkOwner(user, id);
      String sql =
          """
            UPDATE collection SET
              name = ?, coordinateX = ?, coordinateY = ?, creationDate = ?,
              area = ?, population = ?, metersAboveSeaLevel = ?, climate = ?,
              government = ?, standardOfLiving = ?, governorName = ?, governorAge = ?
            WHERE id = ? AND userId = ?""";
      Connection connection = DBManager.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.setString(1, updatedCity.getName());
      statement.setFloat(2, updatedCity.getCoordinates().getX());
      statement.setLong(3, updatedCity.getCoordinates().getY());
      statement.setDate(4, Date.valueOf(updatedCity.getCreationDate()));
      statement.setFloat(5, updatedCity.getArea());
      statement.setInt(6, updatedCity.getPopulation());
      statement.setFloat(7, updatedCity.getMetersAboveSeaLevel());
      statement.setString(8, updatedCity.getClimate().toString());
      statement.setString(9, updatedCity.getGovernment().toString());
      statement.setString(10, updatedCity.getStandardOfLiving().toString());
      statement.setString(11, updatedCity.getGovernor().getName());
      statement.setInt(12, updatedCity.getGovernor().getAge());
      statement.setInt(13, id);
      statement.setInt(14, ownerId);

      updatedCity.setId(id);
      updatedCity.setOwnerId(ownerId);

      if (statement.executeUpdate() == 0) {
        throw new SQLException("Город не был обновлен");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public synchronized List<City> getCities() {
    List<City> cities = new ArrayList<>();
    String sql = "SELECT * FROM collection";
    try (Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        City city = new City();
        city.setOwnerId(resultSet.getInt("userId"));
        city.setId(resultSet.getInt("id"));
        city.setName(resultSet.getString("name"));
        city.setCoordinates(
            new Coordinates(resultSet.getFloat("coordinateX"), resultSet.getLong("coordinateY")));
        city.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
        city.setArea(resultSet.getFloat("area"));
        city.setPopulation(resultSet.getInt("population"));
        city.setMetersAboveSeaLevel(resultSet.getFloat("metersAboveSeaLevel"));

        city.setClimate(Climate.valueOf(resultSet.getString("climate")));
        city.setGovernment(Government.valueOf(resultSet.getString("government")));
        city.setStandardOfLiving(StandardOfLiving.valueOf(resultSet.getString("standardOfLiving")));

        Human governor =
            new Human(resultSet.getString("governorName"), resultSet.getInt("governorAge"));
        city.setGovernor(governor);

        cities.add(city);
      }

    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Возникла ошибка при получении коллекции из бд: " + e.getMessage());
    }
    return cities;
  }

  @Override
  public synchronized City add(City city) {
    String sql =
        """
                INSERT INTO collection (
                name, coordinateX, coordinateY, creationDate, area, population, metersAboveSeaLevel,
                climate, government, standardOfLiving, governorName, governorAge, userId)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    try (Connection connection = DBManager.getConnection();
        PreparedStatement statement =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, city.getName());
      statement.setFloat(2, city.getCoordinates().getX());
      statement.setLong(3, city.getCoordinates().getY());
      statement.setDate(4, Date.valueOf(city.getCreationDate().toString()));
      statement.setFloat(5, city.getArea());
      statement.setInt(6, city.getPopulation());
      statement.setFloat(7, city.getMetersAboveSeaLevel());
      statement.setString(8, city.getClimate().name());
      statement.setString(9, city.getGovernment().name());
      statement.setString(10, city.getStandardOfLiving().name());
      statement.setString(11, city.getGovernor().getName());
      statement.setInt(12, city.getGovernor().getAge());
      statement.setInt(13, city.getOwnerId());

      if (statement.executeUpdate() == 0) throw new SQLException("Город не был добавлен");
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        city.setId(generatedKeys.getInt(1));
      } else {
        throw new SQLException("Не удалось получить id города");
      }
      return city;
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private synchronized int checkOwner(String user, int id) throws SQLException {
    String sql =
        """
        SELECT collection.userId FROM collection
        INNER JOIN users ON users.id = collection.userId
        where collection.id = ? and
            users.name = ?""";
    try (Connection connection = DBManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      statement.setString(2, user);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) return resultSet.getInt("userId");
      else throw new SQLException();
    } catch (Exception e) {
      throw new SQLException("Пользователь " + user + " не владеет городом с id = " + id);
    }
  }

  @Override
  public synchronized void clear() {
    String sql = "DELETE FROM collection";
    try (Connection connection = DBManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.executeUpdate();
    } catch (Exception e) {
      throw new IllegalArgumentException("Возникла ошибка при попытке соединения с бд");
    }
  }
}

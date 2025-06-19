package database;

import classes.City;
import java.util.List;

public interface CollectionDAO {
  void remove(String user, int id);

  void update(City updatedCity, String user, int id);

  City add(City city);

  List<City> getCities();

  void clear();
}

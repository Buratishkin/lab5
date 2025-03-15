package managers;

import classes.City;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

/** Менеджер коллекцией городов */
public class CityManager {
  private final TreeSet<City> cities = new TreeSet<>();
  private final HashMap<Integer, City> citiesMap = new HashMap<>();
  private final HashMap<City, Integer> reverseCitiesMap = new HashMap<>();
  private final TreeSet<Integer> citiesId = new TreeSet<>();
  private City lastCity;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;

  /** Конструктор */
  public CityManager() {
    createDateTime = LocalDateTime.now();
    updateDateTime = LocalDateTime.now();
  }

  /**
   * Добавляет City в коллекцию
   *
   * @param city город, который добавляем
   */
  public void addCity(City city) {
    cities.add(city);
    citiesMap.put(city.getId(), city);
    reverseCitiesMap.put(city, city.getId());
    citiesId.add(city.getId());
  }

  /** Очищает коллекцию */
  public void clear() {
    cities.clear();
  }

  /**
   * Возвращает количество городов в коллекции
   *
   * @return количество городов в коллекции
   */
  public int citySize() {
    int size = 0;
    for (City city : cities) {
      size++;
    }
    return size;
  }

  /**
   * Удаляет город из коллекции
   *
   * @param city город, который удаляется
   */
  public void removeCity(City city) {
    cities.remove(city);
    citiesId.remove(city.getId());
    reverseCitiesMap.remove(city);
    citiesMap.remove(city.getId());
  }

  /**
   * Проверяет содержаться ли город с id в коллекции
   *
   * @param id города, который проверяется
   * @return true, если город есть в коллекции; false - нет
   */
  public boolean contains(int id) {
    for (City city : cities) {
      if (city.getId() == id) return true;
    }
    return false;
  }

  /**
   * Возвращает id города
   *
   * @param city город
   * @return id города
   */
  public int getId(City city) {
    return reverseCitiesMap.get(city);
  }

  /**
   * Получает город по его id
   *
   * @param id id города
   * @return город
   */
  public City getById(int id) {
    return citiesMap.get(id);
  }

  /**
   * Возвращает элементы коллекции в виде строк
   *
   * @return строки элементов
   */
  public String toString() {
    StringBuilder line = new StringBuilder();
    for (City city : cities) {
      line.append(city.toString() + "\n");
    }
    return line.toString();
  }

  /** Возвращает последний город, который пытались добавить в коллекцию */
  public City getLastCity() {
    return lastCity;
  }

  /**
   * Устанавливает значение для последнего город, который пытались добавить в коллекцию
   *
   * @param lastCity последний город, который пытались добавить в коллекцию
   */
  public void setLastCity(City lastCity) {
    this.lastCity = lastCity;
  }

  /**
   * Возвращает множество городов
   *
   * @return множество городов
   */
  public TreeSet<City> getCities() {
    TreeSet<City> sortedCities = new TreeSet<>(Comparator.comparingInt(City::getId));
    sortedCities.addAll(cities);
    return sortedCities;
  }

  /**
   * Возвращает дату создания коллекции
   *
   * @return createDateTime
   */
  public String getCreateDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return createDateTime.format(formatter);
  }

  /**
   * Возвращает множество id всех городов коллекции
   *
   * @return множество id всех городов коллекции
   */
  public TreeSet<Integer> getCitiesId() {
    return citiesId;
  }

  /**
   * Возвращает дату изменения коллекции
   *
   * @return updateDateTime
   */
  public String getUpdateDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return updateDateTime.format(formatter);
  }

  /**
   * Обновляет дату изменения коллекции
   *
   * @param updateDateTime новая дата изменения коллекции
   */
  public void setUpdateDateTime(LocalDateTime updateDateTime) {
    this.updateDateTime = updateDateTime;
  }
}

package service;

import java.util.TreeSet;
import managers.CityManager;

/** Класс для генерации id */
public class IdCreator {
  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public IdCreator(CityManager cityManager) {
    this.cityManager = cityManager;
  }

  private final CityManager cityManager;
  private static final TreeSet<Integer> freeId = new TreeSet<>();

  /**
   * Возвращает наименьший свободный id
   *
   * @return id
   */
  public int getId() {
    if (freeId.isEmpty()) {
      return cityManager.getCitiesId().getLast() + 1;
    } else {
      int id = freeId.getFirst();
      freeId.remove(id);
      return id;
    }
  }

  /**
   * Удаляет из массива занятые id
   *
   * @param id id
   */
  public void delId(int id) {
    freeId.add(id);
  }

  /**
   * Добавляет в массив свободные id
   *
   * @param cityManager менеджер коллекций
   */
  public void addFreeId(CityManager cityManager) {
    try {
      for (int i = 1; i < cityManager.getCitiesId().getLast(); i++) {
        if (!cityManager.getCitiesId().contains(i)) freeId.add(i);
      }
    } catch (Exception e) {
      System.out.println("Коллекция пуста.");
    }
  }

  public void clearId() {
    freeId.clear();
  }
}

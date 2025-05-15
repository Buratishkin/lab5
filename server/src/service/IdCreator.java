package service;

import interfaces.Identifiable;
import managers.CollectionManager;

import java.util.TreeSet;

/** Класс для генерации id */
public class IdCreator<T extends Comparable<T> & Identifiable> {
  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public IdCreator(CollectionManager<T> collectionManager) {
    this.collectionManager = collectionManager;
  }

  private final CollectionManager<T> collectionManager;
  private static final TreeSet<Integer> freeId = new TreeSet<>();

  /**
   * Возвращает наименьший свободный id
   *
   * @return id
   */
  public int getId() {
    if (freeId.isEmpty()) {
      return collectionManager.getObjectsId().last() + 1;
    } else {
      int id = freeId.first();
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

  /** Добавляет в массив свободные id */
  public void addFreeId() {
    try {
      for (int i = 1; i < collectionManager.getObjectsId().last(); i++) {
        if (!collectionManager.getObjectsId().contains(i)) freeId.add(i);
      }
    } catch (Exception e) {
      System.out.println("Коллекция пуста");
    }
  }

  public void clearId() {
    freeId.clear();
  }
}

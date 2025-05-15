package managers;

import interfaces.Identifiable;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class CollectionManager<T extends Comparable<T> & Identifiable> {
  protected final TreeSet<T> objects = new TreeSet<>();
  protected final HashMap<Integer, T> objectsMap = new HashMap<>();
  protected final HashMap<T, Integer> reverseObjectsMap = new HashMap<>();
  protected final TreeSet<Integer> objectsId = new TreeSet<>();
  private T lastElement;
  private final LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;

  public CollectionManager() {
    createDateTime = LocalDateTime.now();
    updateDateTime = LocalDateTime.now();
  }

  public Method getObjectMethod(T element, String methodName) {
    try {
      return element.getClass().getDeclaredMethod(methodName);
    } catch (Exception e) {
      throw new RuntimeException("У " + element.getClass() + " нет метода " + methodName + ".");
    }
  }

  public Object invokeObjectMethod(Method objectMethod, T element, Object... args) {
    try {
      return objectMethod.invoke(element, args);
    } catch (Exception e) {
      throw new RuntimeException(
          "Ошибка при вызове метода "
              + objectMethod.getName()
              + " у объекта "
              + element
              + " с параметрами "
              + args
              + ".");
    }
  }

  public void addElement(T element) {
    Method getIdMethod = getObjectMethod(element, "getId");
    int id = (int) invokeObjectMethod(getIdMethod, element);
    objects.add(element);
    objectsMap.put(id, element);
    reverseObjectsMap.put(element, id);
    objectsId.add(id);
  }

  public void removeElement(T element) {
    Method getIdMethod = getObjectMethod(element, "getId");
    int id = (int) invokeObjectMethod(getIdMethod, element);

    objects.remove(element);
    objectsMap.remove(id);
    reverseObjectsMap.remove(element);
    objectsId.remove(id);
  }

  public T getById(int id) {
    return objectsMap.get(id);
  }

  public int getId(T element) {
    return reverseObjectsMap.get(element);
  }

  public boolean contains(int id) {
    Method getIdMethod = getObjectMethod(getById(id), "getId");

    for (T element : objects) {
      int elementId = (int) invokeObjectMethod(getIdMethod, element);
      if (elementId == id) return true;
    }
    return false;
  }

  public T getLastElement() {
    return lastElement;
  }

  public void setLastElement(T lastElement) {
    this.lastElement = lastElement;
  }

  public TreeSet<Integer> getObjectsId() {
    return objectsId;
  }

  public String toString() {
    StringBuilder line = new StringBuilder();
    for (T element : objects) {
      line.append(element.toString() + "\n");
    }
    return line.toString();
  }

  public void clearCollection() {
    objects.clear();
    objectsMap.clear();
    reverseObjectsMap.clear();
    objectsId.clear();
  }

  public int objectsSize() {
    return objects.size();
  }

  public TreeSet<T> getElements() {
    TreeSet<T> sortedElements = new TreeSet<>(Comparator.comparingInt(T::getId));
    sortedElements.addAll(objects);
    return sortedElements;
  }

  public String getUpdateDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return updateDateTime.format(formatter);
  }

  public String getCreateDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return createDateTime.format(formatter);
  }

  public void setUpdateDateTime(LocalDateTime updateDateTime) {
    this.updateDateTime = updateDateTime;
  }
}

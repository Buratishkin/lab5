package commands;

import classes.City;
import exceptions.DuplicateElementException;
import interfaces.Identifiable;
import managers.CollectionManager;
import network.Request;

/** Добавляет новый элемент в коллекцию */
public class AddCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {
  private final CollectionManager<T> collectionManager;
  /**
   * Конструктор
   *
   * @param collectionManager коллекция городов
   */
  public AddCommand(
      CollectionManager<T> collectionManager) {
    super("add", "Добавляет новый элемент в коллекцию.");
    this.collectionManager = collectionManager;

  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    try {
      City newElement = request.getCity();
      if (newElement == null) throw new IllegalArgumentException("Город не может быть null");
      if (!collectionManager.contains(newElement.getId())) {
        collectionManager.addElement((T) newElement);
        return "Город добавлен";
      } else throw new DuplicateElementException("Город с таким id уже существует");
    } catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }

  @Override
  public boolean isElementable() {
    return true;
  }
}

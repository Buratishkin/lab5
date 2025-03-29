package commands;

import interfaces.Identifiable;
import managers.CollectionManager;
import service.IdCreator;

/** Удаляет элемент из коллекции по его id */
public class RemoveByIdCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final IdCreator<T> idCreator;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public RemoveByIdCommand(CollectionManager<T> collectionManager, IdCreator<T> idCreator) {
    super("remove_by_id", "Удаляет элемент из коллекции по его id.");
    this.collectionManager = collectionManager;
    this.idCreator = idCreator;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    int id;
    try {
      id = Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + arg + " не является числом.");
    }
    if (!collectionManager.contains(id)) {
      throw new IllegalArgumentException("В коллекции нет объекта с индексом " + id);
    } else {
      idCreator.delId(id);
      collectionManager.removeElement(collectionManager.getById(id));
      System.out.println("Город удален.");
    }
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

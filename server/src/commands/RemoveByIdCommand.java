package commands;

import interfaces.Identifiable;
import managers.CollectionManager;
import service.IdCreator;

/** Удаляет элемент из коллекции по его id */
public class RemoveByIdCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final IdCreator<T> idCreator;
  private Integer argument;

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
    try {
      argument = Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + argument + " не является числом.");
    }
    try {
      collectionManager.contains(argument);
      idCreator.delId(argument);
      collectionManager.removeElement(collectionManager.getById(argument));
      System.out.println("Город удален.");
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "В коллекции нет объекта с индексом "
              + argument
              + ".\nЧтобы узнать какие элементы есть в коллекции напишите show.");
    }
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }

  @Override
  public boolean isElementable() {
    return true;
  }
}

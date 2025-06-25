package commands;

import database.CollectionDAO;
import interfaces.Identifiable;
import managers.CollectionManager;
import network.Request;

/** Очищает коллекцию */
public class ClearCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final CollectionDAO collectionDAO;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public ClearCommand(CollectionManager<T> collectionManager, CollectionDAO collectionDAO) {
    super("clear", "Очищает коллекцию.");
    this.collectionManager = collectionManager;
    this.collectionDAO = collectionDAO;
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    collectionManager.clearCollection();
    collectionDAO.clear();
    return "Коллекция очищена";
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

package commands;

import interfaces.Identifiable;
import managers.CollectionManager;
import service.IdCreator;

/** Очищает коллекцию */
public class ClearCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final IdCreator<T> idCreator;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public ClearCommand(CollectionManager<T> collectionManager, IdCreator<T> idCreator) {
    super("clear", "Очищает коллекцию.");
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
    collectionManager.clearCollection();
    idCreator.clearId();
    System.out.println("Коллекция очищена");
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

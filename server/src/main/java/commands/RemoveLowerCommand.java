package commands;

import interfaces.Identifiable;
import managers.CollectionManager;
import managers.CommandManager;
import network.Request;

/** Удаляет из коллекции все элементы, меньшие, чем заданный */
public class RemoveLowerCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final CommandManager commandManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public RemoveLowerCommand(CollectionManager<T> collectionManager, CommandManager commandManager) {
    super("remove_lower", "Удаляет из коллекции все элементы, меньшие, чем заданный.");
    this.collectionManager = collectionManager;
    this.commandManager = commandManager;
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    RemoveByIdCommand<T> removeByIdCommand =
        (RemoveByIdCommand<T>) commandManager.getCommand("remove_by_id");
    T newElement = (T) request.getCity();
    int oldSize = collectionManager.objectsSize();
    for (T element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == 1) {
        removeByIdCommand.execute(new Request(null, Integer.toString(element.getId()), null));
      }
    }
    collectionManager.removeElement(newElement);
    return ("Количество элементов удаленных командой remove_lower: "
        + (oldSize - collectionManager.objectsSize() + 1));
  }

  @Override
  public boolean isElementable() {
    return true;
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }
}

package commands;

import classes.City;
import interfaces.Identifiable;
import managers.CollectionManager;
import managers.CommandManager;
import network.Request;

/** Удаляет из коллекции все элементы, превышающие заданный */
public class RemoveGreaterCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<City> collectionManager;
  private final CommandManager commandManager;

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   */
  public RemoveGreaterCommand(
      CollectionManager<City> collectionManager, CommandManager commandManager) {
    super("remove_greater", "Удаляет из коллекции все элементы, превышающие заданный.");
    this.commandManager = commandManager;
    this.collectionManager = collectionManager;
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
    City newElement = request.getCity();
    int oldSize = collectionManager.objectsSize();
    for (City element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == -1) {
        removeByIdCommand.execute(
            new Request(
                null,
                Integer.toString(element.getId()),
                null,
                request.getUserName(),
                request.getPassword()));
      }
    }
    return ("Количество элементов удаленных командой remove_greater: "
        + (oldSize - collectionManager.objectsSize()));
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

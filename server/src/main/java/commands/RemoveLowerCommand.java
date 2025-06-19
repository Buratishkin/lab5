package commands;

import classes.City;
import interfaces.Identifiable;
import managers.CollectionManager;
import managers.CommandManager;
import network.Request;

/** Удаляет из коллекции все элементы, меньшие, чем заданный */
public class RemoveLowerCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<City> collectionManager;
  private final CommandManager commandManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public RemoveLowerCommand(
      CollectionManager<City> collectionManager, CommandManager commandManager) {
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
    City newElement = request.getCity();
    int oldSize = collectionManager.objectsSize();
    for (City element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == 1) {
        removeByIdCommand.execute(
            new Request(
                null,
                Integer.toString(element.getId()),
                null,
                request.getUserName(),
                request.getPassword()));
      }
    }
    return ("Количество элементов удаленных командой remove_lower: "
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

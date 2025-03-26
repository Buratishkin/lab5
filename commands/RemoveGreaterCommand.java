package commands;

import exceptions.DuplicateElementException;
import interfaces.Elementable;
import interfaces.Identifiable;
import managers.CollectionManager;
import managers.CommandManager;

/** Удаляет из коллекции все элементы, превышающие заданный */
public class RemoveGreaterCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand implements Elementable {

  private final CollectionManager<T> collectionManager;
  private final CommandManager commandManager;
  private boolean consoleMode = true;

  public void setConsoleMode(boolean consoleMode) {
    this.consoleMode = consoleMode;
  }

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   */
  public RemoveGreaterCommand(CollectionManager<T> collectionManager, CommandManager commandManager) {
    super("remove_greater", "Удаляет из коллекции все элементы, превышающие заданный.");
    this.commandManager = commandManager;
    this.collectionManager = collectionManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    AddCommand<T> addCommand = (AddCommand<T>) commandManager.getCommand("add");
    RemoveByIdCommand<T> removeByIdCommand = (RemoveByIdCommand<T>) commandManager.getCommand("remove_by_id");

    int oldSize = collectionManager.objectsSize();
    try {
      addCommand.execute(arg);
    } catch (DuplicateElementException e) {
      System.out.println(e.getMessage());
    }
    T newElement = collectionManager.getLastElement();
    for (T element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == -1) {
        removeByIdCommand.execute(Integer.toString(collectionManager.getId(element)));
      }
    }
    System.out.println(
        "Количество элементов удаленных командой remove_greater: "
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

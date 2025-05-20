package commands;

import exceptions.DuplicateElementException;
import interfaces.Identifiable;
import interfaces.ScriptCommand;
import managers.CollectionManager;
import managers.CommandManager;

/** Удаляет из коллекции все элементы, превышающие заданный */
public class RemoveGreaterCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand
    implements ScriptCommand {

  private final CollectionManager<T> collectionManager;
  private final CommandManager commandManager;
  private boolean scriptMode = false;
  private String argument;

  @Override
  public void setScriptMode(boolean scriptMode) {
    this.scriptMode = scriptMode;
  }

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   */
  public RemoveGreaterCommand(
      CollectionManager<T> collectionManager, CommandManager commandManager) {
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
    argument = arg;
    AddCommand<T> addCommand = (AddCommand<T>) commandManager.getCommand("add");
    RemoveByIdCommand<T> removeByIdCommand =
        (RemoveByIdCommand<T>) commandManager.getCommand("remove_by_id");

    int oldSize = collectionManager.objectsSize();
    try {
      addCommand.setScriptMode(scriptMode);
      addCommand.execute(argument);
    } catch (DuplicateElementException e) {
      System.out.println(e.getMessage());
    }
    T newElement = collectionManager.getLastElement();
    for (T element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == -1) {
        removeByIdCommand.execute(Integer.toString(collectionManager.getId(element)));
      }
    }
    collectionManager.removeElement(newElement);
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

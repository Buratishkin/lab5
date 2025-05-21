package commands;

import exceptions.DuplicateElementException;
import interfaces.Identifiable;
import interfaces.ScriptCommand;
import managers.CollectionManager;
import managers.CommandManager;

/** Удаляет из коллекции все элементы, меньшие, чем заданный */
public class RemoveLowerCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand
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
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    AddCommand<T> addCommand = (AddCommand<T>) commandManager.getCommand("add");
    RemoveByIdCommand<T> removeByIdCommand =
        (RemoveByIdCommand<T>) commandManager.getCommand("remove_by_id");

    argument = arg;
    int oldSize = collectionManager.objectsSize();
    try {
      addCommand.setScriptMode(scriptMode);
      addCommand.execute(arg);
    } catch (DuplicateElementException e) {
      return (e.getMessage());
    }
    T newElement = collectionManager.getLastElement();
    for (T element : collectionManager.getElements()) {
      if (newElement.compareTo(element) == 1) {
        removeByIdCommand.execute(Integer.toString(collectionManager.getId(element)));
      }
    }
    collectionManager.removeElement(newElement);
    return (
        "Количество элементов удаленных командой remove_lower: "
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

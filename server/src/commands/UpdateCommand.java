package commands;

import interfaces.Identifiable;
import interfaces.ScriptCommand;
import managers.CollectionManager;
import network.Request;

/** Обновляет значение элемента коллекции, id которого равен заданному. */
public class UpdateCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand
    implements ScriptCommand {

  private final CollectionManager<T> collectionManager;
  private boolean scriptMode = false;

  @Override
  public void setScriptMode(boolean scriptMode) {
    this.scriptMode = scriptMode;
  }

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public UpdateCommand(CollectionManager<T> collectionManager) {
    super("update", "Обновляет значение элемента коллекции, id которого равен заданному.");
    this.collectionManager = collectionManager;
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    int argument = 0;
    try {
      argument = Integer.parseInt(request.getArgument());
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + argument + " не является числом.");
    }

      if (collectionManager.contains(argument)) {
        collectionManager.removeElement(collectionManager.getById(argument));
        collectionManager.addElement((T) request.getCity());
        return "Город обновлён.";
      } else return "В коллекции нет элемента с индексом " + argument;
  }

  @Override
  public boolean isElementable() {
    return true;
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }
}

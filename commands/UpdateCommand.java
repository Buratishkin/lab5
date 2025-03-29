package commands;

import interfaces.Argumentable;
import interfaces.Elementable;
import interfaces.Identifiable;
import interfaces.ScriptCommand;
import io.InputManager;
import managers.*;

/** Обновляет значение элемента коллекции, id которого равен заданному. */
public class UpdateCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand implements ScriptCommand {

  private final InputManager<T> inputManager;
  private final CollectionManager<T> collectionManager;
  private boolean scriptMode = false;

  @Override
  public void setScriptMode(boolean scriptMode){
    this.scriptMode = scriptMode;
  }

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public UpdateCommand(CollectionManager<T> collectionManager, InputManager<T> inputManager) {
    super("update", "Обновляет значение элемента коллекции, id которого равен заданному.");
    this.collectionManager = collectionManager;
    this.inputManager = inputManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    int id;
    try {
      id = Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + arg + " не является числом.");
    }
    if (!collectionManager.contains(id)) {
      throw new IllegalArgumentException(
          "В коллекции нет объекта с индексом "
              + id
              + ".\nЧтобы узнать какие элементы есть в коллекции напишите show.");
    } else {
      collectionManager.removeElement(collectionManager.getById(id));
      collectionManager.addElement(inputManager.inputObject());
      System.out.println("Город обновлён.");
    }
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

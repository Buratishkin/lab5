package commands;

import exceptions.DuplicateElementException;
import interfaces.Identifiable;
import interfaces.ScriptCommand;
import io.InputManager;
import managers.CollectionManager;
import service.IdCreator;

/** Добавляет новый элемент в коллекцию */
public class AddCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand implements ScriptCommand {
  private final CollectionManager<T> collectionManager;
  private final IdCreator<T> idCreator;
  private final InputManager<T> inputManager;
  private boolean scriptMode = false;

  @Override
  public void setScriptMode(boolean scriptMode){
    this.scriptMode = scriptMode;
  }

  /**
   * Конструктор
   *
   * @param collectionManager коллекция городов
   */
  public AddCommand(CollectionManager<T> collectionManager, IdCreator<T> idCreator, InputManager<T> inputManager) {
    super("add", "Добавляет новый элемент в коллекцию.");
    this.collectionManager = collectionManager;
    this.idCreator = idCreator;
    this.inputManager = inputManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    inputManager.getDataReader().setScriptMode(scriptMode);
    T newELement = inputManager.inputObject();
    int oldSize = collectionManager.objectsSize();
    collectionManager.addElement(newELement);
    if (oldSize == collectionManager.objectsSize()) {
      idCreator.delId(newELement.getId());
      throw new DuplicateElementException("Элемент не добавлен, так как он уже есть в коллекции.");
    } else {
      collectionManager.setLastElement(newELement);
      System.out.println("Город добавлен.");
    }
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

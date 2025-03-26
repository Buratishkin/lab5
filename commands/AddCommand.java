package commands;

import exceptions.DuplicateElementException;
import interfaces.Elementable;
import interfaces.Identifiable;
import managers.CollectionManager;
import io.InputManager;
import service.IdCreator;

/** Добавляет новый элемент в коллекцию */
public class AddCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand implements Elementable {
  private final CollectionManager<T> collectionManager;
  private final IdCreator<T> idCreator;
  private final InputManager<T> inputManager;
  private boolean consoleMode = true;
              
  public void setConsoleMode(boolean consoleMode){
    this.consoleMode = consoleMode;
  }

  /**
   * Конструктор
   *
   * @param collectionManager коллекция городов
   */
  public AddCommand(CollectionManager<T> collectionManager, IdCreator<T> idCreator, InputManager<T> inputManager, boolean isConsoleRead) {
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
    T newELement = inputManager.inputObject(consoleMode);
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

package commands;

import classes.City;
import database.CollectionDAO;
import interfaces.Identifiable;
import manager.ValidationManager;
import managers.CollectionManager;
import network.Request;

/** Обновляет значение элемента коллекции, id которого равен заданному. */
public class UpdateCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<City> collectionManager;
  private final ValidationManager validationManager;
  private final CollectionDAO collectionDAO;
  private int argument;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public UpdateCommand(
      CollectionManager<City> collectionManager,
      ValidationManager validationManager,
      CollectionDAO collectionDAO) {
    super("update", "Обновляет значение элемента коллекции, id которого равен заданному.");
    this.collectionManager = collectionManager;
    this.validationManager = validationManager;
    this.collectionDAO = collectionDAO;
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    argument = validationManager.validateInt(request.getArgument(), false);
    if (collectionManager.contains(collectionManager.getById(argument))) {
      collectionDAO.update(request.getCity(), request.getUserName(), argument);
      collectionManager.removeById(argument);
      collectionManager.addElement(request.getCity());
      return "Город обновлён.";
    }
    throw new IllegalArgumentException("В коллекции нет элемента с id = " + argument);
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

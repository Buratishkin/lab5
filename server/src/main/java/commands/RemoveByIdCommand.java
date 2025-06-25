package commands;

import classes.City;
import database.CollectionDAO;
import exceptions.ValidateException;
import interfaces.Identifiable;
import manager.ValidationManager;
import managers.CollectionManager;
import network.Request;

/** Удаляет элемент из коллекции по его id */
public class RemoveByIdCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<City> collectionManager;
  private final ValidationManager validationManager;
  private final CollectionDAO collectionDAO;
  private Integer argument;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public RemoveByIdCommand(
      CollectionManager<City> collectionManager,
      ValidationManager validationManager,
      CollectionDAO collectionDAO) {
    super("remove_by_id", "Удаляет элемент из коллекции по его id.");
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
    try {
      if (collectionManager.containsById(argument)) {
        collectionDAO.remove(request.getUserName(), argument);
        collectionManager.removeById(argument);
      } else throw new IllegalArgumentException("В коллекции нет элемента с id = " + argument);

      return "Город удален.";
    } catch (ValidateException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (Exception e) {
      throw new IllegalArgumentException("При удалении города произошла ошибка: " + e.getMessage());
    }
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }

  @Override
  public boolean isElementable() {
    return true;
  }
}

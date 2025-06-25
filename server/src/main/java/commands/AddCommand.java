package commands;

import classes.City;
import database.CollectionDAO;
import database.UserDAO;
import exceptions.DuplicateElementException;
import interfaces.Identifiable;
import managers.CollectionManager;
import network.Request;

/** Добавляет новый элемент в коллекцию */
public class AddCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {
  private final CollectionManager<City> collectionManager;
  private final CollectionDAO psqlCityDAO;
  private final UserDAO psqlUserDAO;

  /**
   * Конструктор
   *
   * @param collectionManager коллекция городов
   */
  public AddCommand(
      CollectionManager<City> collectionManager, CollectionDAO psqlCityDAO, UserDAO psqlUserDAO) {
    super("add", "Добавляет новый элемент в коллекцию.");
    this.collectionManager = collectionManager;
    this.psqlCityDAO = psqlCityDAO;
    this.psqlUserDAO = psqlUserDAO;
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    try {
      City newElement = request.getCity();
      if (newElement == null) throw new IllegalArgumentException("Город не может быть null");
      if (!collectionManager.contains(newElement)) {
        newElement.setOwnerId(psqlUserDAO.getUserId(request.getUserName()));
        collectionManager.addElement(psqlCityDAO.add(newElement));
        return "Город добавлен";
      } else throw new DuplicateElementException("Такой город уже существует");
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "При добавлении города возникла ошибка: " + e.getMessage());
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

package commands;

import classes.City;
import exceptions.DuplicateElementException;
import interfaces.Elementable;
import managers.CityManager;
import managers.InputManager;
import service.IdCreator;

/** Добавляет новый элемент в коллекцию */
public class AddCommand extends AbstractCommand implements Elementable {

  private final CityManager cityManager;
  private final IdCreator idCreator;

  /**
   * Конструктор
   *
   * @param cityManager коллекция городов
   */
  public AddCommand(CityManager cityManager) {
    super("add", "Добавляет новый элемент в коллекцию.");
    this.cityManager = cityManager;
    idCreator = new IdCreator(cityManager);
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    InputManager inputManager = new InputManager();
    City newCity = inputManager.createCity(idCreator.getId());
    int oldSize = cityManager.citySize();
    cityManager.addCity(newCity);
    if (oldSize == cityManager.citySize()) {
      idCreator.delId(newCity.getId());
      throw new DuplicateElementException("Элемент не добавлен, так как он уже есть в коллекции.");
    } else {
      cityManager.setLastCity(newCity);
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

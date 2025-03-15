package commands;

import classes.City;
import exceptions.DuplicateElementException;
import interfaces.Elementable;
import managers.CityManager;

/** Удаляет из коллекции все элементы, превышающие заданный */
public class RemoveGreaterCommand extends AbstractCommand implements Elementable {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public RemoveGreaterCommand(CityManager cityManager) {
    super("remove_greater", "Удаляет из коллекции все элементы, превышающие заданный.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    AddCommand addCommand = new AddCommand(cityManager);
    RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand(cityManager);

    int oldSize = cityManager.citySize();
    try {
      addCommand.execute(arg);
    } catch (DuplicateElementException e) {
      System.out.println(e.getMessage());
    }
    City newCity = cityManager.getLastCity();
    for (City city : cityManager.getCities()) {
      if (newCity.compareTo(city) == -1) {
        removeByIdCommand.execute(Integer.toString(cityManager.getId(city)));
      }
    }
    System.out.println(
        "Количество элементов удаленных командой remove_greater: "
            + (oldSize - cityManager.citySize() + 1));
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

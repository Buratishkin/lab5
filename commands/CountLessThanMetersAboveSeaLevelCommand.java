package commands;

import classes.City;
import interfaces.Argumentable;
import managers.CityManager;

/** Выводит количество элементов, значение поля metersAboveSeaLevel которых меньше заданного */
public class CountLessThanMetersAboveSeaLevelCommand extends AbstractCommand
    implements Argumentable {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public CountLessThanMetersAboveSeaLevelCommand(CityManager cityManager) {
    super(
        "count_less_than_meters_above_sea_level",
        "Выводит количество элементов, значение поля metersAboveSeaLevel которых меньше заданного.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды.
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    float metersAboveSeaLevel = 0;
    int counter = 0;
    try {
      metersAboveSeaLevel = Float.parseFloat(arg);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + arg + " не является числом.");
    }
    for (City city : cityManager.getCities()) {
      if (city.getMetersAboveSeaLevel() < metersAboveSeaLevel) {
        counter++;
      }
    }
    System.out.println(
        "Количество элементов значение уровня воды, которых меньше "
            + metersAboveSeaLevel
            + ": "
            + counter);
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }

  @Override
  public boolean isElementable() {
    return false;
  }
}

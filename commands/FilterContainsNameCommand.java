package commands;

import classes.City;
import interfaces.Argumentable;
import managers.CityManager;

/** Выводит элементы, значение поля name которых содержит заданную подстроку */
public class FilterContainsNameCommand extends AbstractCommand implements Argumentable {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public FilterContainsNameCommand(CityManager cityManager) {
    super(
        "filter_contains_name",
        "Выводит элементы, значение поля name которых содержит заданную подстроку.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    String name = arg;
    int flag = 0;
    for (City city : cityManager.getCities()) {
      if (city.getName().contains(name)) {
        flag++;
        if (flag == 1) {
          System.out.println("Элементы с подстрокой " + name + ":");
        }
        System.out.println(city.toString());
      }
    }
    if (flag == 0) System.out.println("Нет элементов с подстрокой " + name + ".");
  }

  @Override
  public boolean isElementable() {
    return false;
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }
}

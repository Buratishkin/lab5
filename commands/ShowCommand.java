package commands;

import classes.City;
import java.util.Comparator;
import java.util.TreeSet;
import managers.CityManager;

/** Выводит в стандартный поток вывода все элементы коллекции в строковом представлении. */
public class ShowCommand extends AbstractCommand {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public ShowCommand(CityManager cityManager) {
    super(
        "show",
        "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    System.out.println("Элементы коллекции:");
    int ind = 1;
    StringBuilder line = new StringBuilder();
    TreeSet<City> cities = new TreeSet<>(Comparator.comparingInt(City::getId));
    cities.addAll(cityManager.getCities());
    for (City city : cities) {
      if (ind == cities.size()) {
        line.append(city.toString());
      } else {
        line.append(city.toString() + "\n");
        ind++;
      }
    }
    System.out.println(line);
  }

  @Override
  public boolean isElementable() {
    return false;
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }
}

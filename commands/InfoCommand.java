package commands;

import classes.City;
import managers.CityManager;

/** Выводит в стандартный поток вывода информацию о коллекции */
public class InfoCommand extends AbstractCommand {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public InfoCommand(CityManager cityManager) {
    super("info", "Выводит в стандартный поток вывода информацию о коллекции.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    float[] avgStats = getAvgStats();
    System.out.println("Информация о коллекции:");
    System.out.println("    Тип: TreeSet<City>");
    System.out.println("    Количество элементов: " + cityManager.citySize());
    System.out.println("    Дата создания: " + cityManager.getCreateDateTime());
    System.out.println("    Дата последнего изменения: " + cityManager.getUpdateDateTime());
    System.out.println("    Среднее значение population: " + avgStats[0]);
    System.out.println("    Среднее значение area: " + avgStats[1]);
    System.out.println("    Среднее значение metersAboveSeaLevel: " + avgStats[2]);
  }

  /**
   * Считает средние значение полей(population, area, metersAboveSeaLevel) элементов коллекции
   *
   * @return массив значений
   */
  private float[] getAvgStats() {
    float[] avgStats = new float[3];
    for (City city : cityManager.getCities()) {
      avgStats[0] += city.getPopulation();
      avgStats[1] += city.getArea();
      avgStats[2] += city.getMetersAboveSeaLevel();
    }
    avgStats[0] = avgStats[0] / cityManager.citySize();
    avgStats[1] = avgStats[1] / cityManager.citySize();
    avgStats[2] = avgStats[2] / cityManager.citySize();
    return avgStats;
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

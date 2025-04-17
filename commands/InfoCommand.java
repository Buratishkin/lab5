package commands;

import interfaces.Identifiable;
import java.lang.reflect.Method;
import managers.CollectionManager;

/** Выводит в стандартный поток вывода информацию о коллекции */
public class InfoCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public InfoCommand(CollectionManager<T> collectionManager) {
    super("info", "Выводит в стандартный поток вывода информацию о коллекции.");
    this.collectionManager = collectionManager;
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
    System.out.println("    Количество элементов: " + collectionManager.objectsSize());
    System.out.println("    Дата создания: " + collectionManager.getCreateDateTime());
    System.out.println("    Дата последнего изменения: " + collectionManager.getUpdateDateTime());
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
    for (T element : collectionManager.getElements()) {
      Method getPopulation = collectionManager.getObjectMethod(element, "getPopulation");
      int population = (int) collectionManager.invokeObjectMethod(getPopulation, element);
      Method getAreaMethod = collectionManager.getObjectMethod(element, "getArea");
      float area = (float) collectionManager.invokeObjectMethod(getAreaMethod, element);
      Method getMetersMethod = collectionManager.getObjectMethod(element, "getMetersAboveSeaLevel");
      float meters = (float) collectionManager.invokeObjectMethod(getMetersMethod, element);

      avgStats[0] += population;
      avgStats[1] += area;
      avgStats[2] += meters;
    }
    avgStats[0] = avgStats[0] / collectionManager.objectsSize();
    avgStats[1] = avgStats[1] / collectionManager.objectsSize();
    avgStats[2] = avgStats[2] / collectionManager.objectsSize();
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

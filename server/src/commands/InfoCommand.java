package commands;

import com.sun.nio.sctp.AbstractNotificationHandler;
import interfaces.Identifiable;
import managers.CollectionManager;

import java.lang.reflect.Method;

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
  public String execute(String arg) {
    float[] avgStats = getAvgStats();
    StringBuilder line = new StringBuilder();
    line.append("Информация о коллекции:");
    line.append("\n    Тип: TreeSet<City>");
    line.append("\n    Количество элементов: " + collectionManager.objectsSize());
    line.append("\n    Дата создания: " + collectionManager.getCreateDateTime());
    line.append("\n    Дата последнего изменения: " + collectionManager.getUpdateDateTime());
    line.append("\n    Среднее значение population: " + avgStats[0]);
    line.append("\n    Среднее значение area: " + avgStats[1]);
    line.append("\n    Среднее значение metersAboveSeaLevel: " + avgStats[2]);
    return line.toString();
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

package commands;

import exceptions.ValidateException;
import interfaces.Identifiable;
import java.lang.reflect.Method;
import managers.CollectionManager;
import managers.ValidationManager;

/** Выводит количество элементов, значение поля metersAboveSeaLevel которых меньше заданного */
public class CountLessThanMetersAboveSeaLevelCommand<T extends Comparable<T> & Identifiable>
    extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final ValidationManager validationManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public CountLessThanMetersAboveSeaLevelCommand(
      CollectionManager<T> collectionManager, ValidationManager validationManager) {
    super(
        "count_less_than_meters_above_sea_level",
        "Выводит количество элементов, значение поля metersAboveSeaLevel которых меньше заданного.");
    this.collectionManager = collectionManager;
    this.validationManager = validationManager;
  }

  /**
   * Выполнение команды.
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    float metersAboveSeaLevel;
    int counter = 0;

    try {
      metersAboveSeaLevel = validationManager.validateFloat(arg, false);
    } catch (ValidateException e) {
      System.out.println(e.getMessage());
      return;
    }

    for (T element : collectionManager.getElements()) {
      Method getIdMethod = collectionManager.getObjectMethod(element, "getMetersAboveSeaLevel");
      float meters = (float) collectionManager.invokeObjectMethod(getIdMethod, element);
      if (meters < metersAboveSeaLevel) {
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

package commands;

import exceptions.ValidateException;
import interfaces.Identifiable;
import managers.CollectionManager;
import managers.ValidationManager;

import java.lang.reflect.Method;

/** Выводит количество элементов, значение поля metersAboveSeaLevel которых меньше заданного */
public class CountLessThanMetersAboveSeaLevelCommand<T extends Comparable<T> & Identifiable>
    extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final ValidationManager validationManager;
  private Float argument;

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
  public String execute(String arg) {
    int counter = 0;

    try {
      argument = validationManager.validateFloat(arg, false);
    } catch (ValidateException e) {
      return e.getMessage();
    }

    for (T element : collectionManager.getElements()) {
      Method getIdMethod = collectionManager.getObjectMethod(element, "getMetersAboveSeaLevel");
      float meters = (float) collectionManager.invokeObjectMethod(getIdMethod, element);
      if (meters < argument) {
        counter++;
      }
    }

    return (
        "Количество элементов значение уровня воды, которых меньше "
            + argument
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

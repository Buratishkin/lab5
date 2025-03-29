package commands;

import interfaces.Identifiable;
import managers.CollectionManager;

import java.lang.reflect.Method;

/** Выводит элементы, значение поля name которых содержит заданную подстроку */
public class FilterContainsNameCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public FilterContainsNameCommand(CollectionManager<T> collectionManager) {
    super(
        "filter_contains_name",
        "Выводит элементы, значение поля name которых содержит заданную подстроку.");
    this.collectionManager = collectionManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    int flag = 0;
    for (T element : collectionManager.getElements()) {
      Method getNameMethod = collectionManager.getObjectMethod(element, "getName");
      String elementName = (String) collectionManager.invokeObjectMethod(getNameMethod, element);

      if (elementName.contains(arg)) {
        flag++;
        if (flag == 1) {
          System.out.println("Элементы с подстрокой " + arg + ":");
        }
        System.out.println(element.toString());
      }
    }
    if (flag == 0) System.out.println("Нет элементов с подстрокой " + arg + ".");
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

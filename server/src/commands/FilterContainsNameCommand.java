package commands;

import interfaces.Identifiable;
import managers.CollectionManager;
import network.Request;

import java.lang.reflect.Method;

/** Выводит элементы, значение поля name которых содержит заданную подстроку */
public class FilterContainsNameCommand<T extends Comparable<T> & Identifiable>
    extends AbstractCommand {

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
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
    int flag = 0;
    StringBuilder line = new StringBuilder();
    for (T element : collectionManager.getElements()) {
      Method getNameMethod = collectionManager.getObjectMethod(element, "getName");
      String elementName = (String) collectionManager.invokeObjectMethod(getNameMethod, element);

      if (elementName.contains(request.getArgument())) {
        flag++;
        if (flag == 1) {
          line.append("Элементы с подстрокой " + request.getArgument() + ":\n");
        }
        line.append(element.toString() + "\n");
      }
    }

    if (flag == 0) return  "Нет элементов с подстрокой " + request.getArgument() + ".";
    else return line.toString();
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

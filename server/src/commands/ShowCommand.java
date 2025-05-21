package commands;

import interfaces.Identifiable;
import managers.CollectionManager;

import java.util.Comparator;
import java.util.TreeSet;

/** Выводит в стандартный поток вывода все элементы коллекции в строковом представлении. */
public class ShowCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public ShowCommand(CollectionManager<T> collectionManager) {
    super(
        "show",
        "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.");
    this.collectionManager = collectionManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    StringBuilder line = new StringBuilder();
    line.append("Элементы коллекции:\n");
    int ind = 1;

    TreeSet<T> elements = new TreeSet<>(Comparator.comparingInt(T::getId));
    elements.addAll(collectionManager.getElements());
    for (T element : elements) {
      if (ind == elements.size()) {
        line.append(element.toString());
      } else {
        line.append(element.toString() + "\n");
        ind++;
      }
    }
    return line.toString();
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

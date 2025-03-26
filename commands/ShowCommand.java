package commands;

import java.util.Comparator;
import java.util.TreeSet;

import interfaces.Identifiable;
import managers.CollectionManager;

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
  public void execute(String arg) {
    System.out.println("Элементы коллекции:");
    int ind = 1;
    StringBuilder line = new StringBuilder();
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

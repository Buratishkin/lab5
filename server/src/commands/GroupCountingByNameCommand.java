package commands;

import interfaces.Identifiable;
import managers.CollectionManager;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Группирует элементы коллекции по значению поля name. Выводит количество элементов в каждой группе
 */
public class GroupCountingByNameCommand<T extends Comparable<T> & Identifiable>
    extends AbstractCommand {

  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public GroupCountingByNameCommand(CollectionManager<T> collectionManager) {
    super(
        "group_counting_by_name",
        "Группирует элементы коллекции по значению поля name. Выводит количество элементов в каждой группе.");
    this.collectionManager = collectionManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    ArrayList<String> vowelsGroup = new ArrayList<>();
    ArrayList<String> consonantsGroup = new ArrayList<>();
    ArrayList<String> digitsGroup = new ArrayList<>();
    ArrayList<String> specialsGroup = new ArrayList<>();

    String regexConsonants = "[bcdfghjklmnpqrstvwxyz]"; // согласные
    String regexDigits = "[0-9]"; // цифры
    String regexVowels = "[aeiou]"; // гласные
    String regexSpecials = "[^a-z0-9]"; // остальное

    for (T element : collectionManager.getElements()) {
      Method getNameMethod = collectionManager.getObjectMethod(element, "getName");
      String name = (String) collectionManager.invokeObjectMethod(getNameMethod, element);

      Character firstChar = name.toLowerCase().charAt(0);
      String firstCharStr = firstChar.toString();

      if (firstCharStr.matches(regexVowels)) vowelsGroup.add(name);
      else if (firstCharStr.matches(regexConsonants)) consonantsGroup.add(name);
      else if (firstCharStr.matches(regexDigits)) digitsGroup.add(name);
      else if (firstCharStr.matches(regexSpecials)) specialsGroup.add(name);
    }
    StringBuilder line = new StringBuilder();

    line.append("Города, начинающиеся с гласной буквы:\n");
    line.append(vowelsGroup);
    line.append("\nГорода, начинающиеся с согласной буквы:\n");
    line.append(consonantsGroup);
    line.append("\nГорода, начинающиеся с цифры:\n");
    line.append(digitsGroup);
    line.append("\nГорода, начинающиеся со специального символа:");
    line.append(specialsGroup);
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

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
  public void execute(String arg) {
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

    System.out.println("Города, начинающиеся с гласной буквы:");
    System.out.println(vowelsGroup);
    System.out.println("Города, начинающиеся с согласной буквы:");
    System.out.println(consonantsGroup);
    System.out.println("Города, начинающиеся с цифры:");
    System.out.println(digitsGroup);
    System.out.println("Города, начинающиеся со специального символа:");
    System.out.println(specialsGroup);
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

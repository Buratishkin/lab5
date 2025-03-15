package commands;

import classes.City;
import java.util.ArrayList;
import managers.CityManager;

/**
 * Группирует элементы коллекции по значению поля name. Выводит количество элементов в каждой группе
 */
public class GroupCountingByNameCommand extends AbstractCommand {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public GroupCountingByNameCommand(CityManager cityManager) {
    super(
        "group_counting_by_name",
        "Группирует элементы коллекции по значению поля name. Выводит количество элементов в каждой группе.");
    this.cityManager = cityManager;
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

    String regexConsonants = "[bcdfghjklmnpqrstvwxyz]"; // Учитываем все согласные
    String regexDigits = "[0-9]"; // Цифры
    String regexVowels = "[aeiou]"; // Гласные
    String regexSpecials = "[^a-z0-9]"; // Всё, что не буква и не цифра

    for (City city : cityManager.getCities()) {
      Character firstChar = city.getName().toLowerCase().charAt(0);
      String firstCharStr = firstChar.toString();

      if (firstCharStr.matches(regexVowels)) vowelsGroup.add(city.getName());
      else if (firstCharStr.matches(regexConsonants)) consonantsGroup.add(city.getName());
      else if (firstCharStr.matches(regexDigits)) digitsGroup.add(city.getName());
      else if (firstCharStr.matches(regexSpecials)) specialsGroup.add(city.getName());
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

package commands;

import interfaces.Argumentable;
import interfaces.Elementable;
import managers.CityManager;
import managers.InputManager;

/** Обновляет значение элемента коллекции, id которого равен заданному. */
public class UpdateCommand extends AbstractCommand implements Argumentable, Elementable {

  private final InputManager inputManager = new InputManager();
  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public UpdateCommand(CityManager cityManager) {
    super("update", "Обновляет значение элемента коллекции, id которого равен заданному.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    int id = -1;
    try {
      id = Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Переданный аргумент " + arg + " не является числом.");
    }
    if (!cityManager.contains(id)) {
      throw new IllegalArgumentException(
          "В коллекции нет объекта с индексом "
              + id
              + ".\nЧтобы узнать какие элементы есть в коллекции напишите show.");
    } else {
      cityManager.removeCity(cityManager.getById(id));
      cityManager.addCity(inputManager.createCity(id));
      System.out.println("Город обновлён.");
    }
  }

  @Override
  public boolean isElementable() {
    return true;
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }
}

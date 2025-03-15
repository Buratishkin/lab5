package commands;

import interfaces.Argumentable;
import managers.CityManager;
import service.IdCreator;

/** Удаляет элемент из коллекции по его id */
public class RemoveByIdCommand extends AbstractCommand implements Argumentable {

  private final CityManager cityManager;
  private final IdCreator idCreator;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public RemoveByIdCommand(CityManager cityManager) {
    super("remove_by_id", "Удаляет элемент из коллекции по его id.");
    this.cityManager = cityManager;
    idCreator = new IdCreator(cityManager);
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
      throw new IllegalArgumentException("В коллекции нет объекта с индексом " + id);
    } else {
      idCreator.delId(id);
      cityManager.removeCity(cityManager.getById(id));
      System.out.println("Город удален.");
    }
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }

  @Override
  public boolean isElementable() {
    return true;
  }
}

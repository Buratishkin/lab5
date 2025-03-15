package commands;

import managers.CityManager;
import service.IdCreator;

/** Очищает коллекцию */
public class ClearCommand extends AbstractCommand {

  private final CityManager cityManager;
  private final IdCreator idCreator;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public ClearCommand(CityManager cityManager) {
    super("clear", "Очищает коллекцию.");
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
    cityManager.clear();
    idCreator.clearId();
    System.out.println("Коллекция очищена");
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }

  @Override
  public boolean isElementable() {
    return true;
  }
}

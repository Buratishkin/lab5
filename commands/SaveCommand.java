package commands;

import managers.CityManager;
import managers.EnvManager;
import managers.FileManager;

/** Сохраняет коллекцию в файл */
public class SaveCommand extends AbstractCommand {

  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public SaveCommand(CityManager cityManager) {
    super("save", "Сохраняет коллекцию в файл.");
    this.cityManager = cityManager;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("Не передано имя файла.");
    }
    FileManager fileManager = new FileManager(EnvManager.getEnv(), cityManager);
    try {
      fileManager.saveCollection();
    } catch (Exception e) {
      System.out.println("Возникла ошибка: " + e.getMessage());
    }
    System.out.println("Коллекция сохранена в файл.");
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

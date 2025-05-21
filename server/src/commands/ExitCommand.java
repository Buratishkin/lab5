package commands;

/** Завершает программу без сохранения в файл */
public class ExitCommand extends AbstractCommand {
  /** Конструктор */
  public ExitCommand() {
    super("exit", "Завершает программу без сохранения в файл.");
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    System.exit(0);
    return "Завершение программы";
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }

  @Override
  public boolean isElementable() {
    return false;
  }
}

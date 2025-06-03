package commands;

import network.Request;

/** Завершает программу без сохранения в файл */
public class ExitCommand extends AbstractCommand {
  /** Конструктор */
  public ExitCommand() {
    super("exit", "Завершает программу без сохранения в файл.");
  }

  /**
   * Выполнение команды
   *
   * @param request аргумент
   */
  @Override
  public String execute(Request request) {
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

package commands;

import java.io.IOException;
import managers.CommandManager;
import network.Request;

/** Выводит в стандартный поток вывода информацию о коллекции */
public class HelpCommand extends AbstractCommand {

  private final CommandManager commandManager;

  /**
   * Конструктор
   *
   * @param commandManager менеджер команд
   */
  public HelpCommand(CommandManager commandManager) {
    super("help", "Выводит в стандартный поток вывода информацию о коллекции.");
    this.commandManager = commandManager;
  }

  /** Выполнение команды */
  @Override
  public String execute(Request request) throws IOException {
    StringBuilder line = new StringBuilder();
    line.append("Список команд:");
    for (AbstractCommand command : commandManager.getCommands().values()) {
      line.append("\n    " + command.toString());
    }
    line.append("\n    exit: Завершает программу без сохранения в файл.");
    line.append("\n    history: Выводит последние 11 команд");
    return line.toString();
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

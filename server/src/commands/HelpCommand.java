package commands;

import managers.CommandManager;

import utils.Console;

import java.io.IOException;

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

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) throws IOException {
    StringBuilder line = new StringBuilder();
    line.append("Список команд:\n");
    for (AbstractCommand command : commandManager.getCommands().values()) {
      line.append("    " + command.toString() + "\n");
    }
    System.out.println(line);
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

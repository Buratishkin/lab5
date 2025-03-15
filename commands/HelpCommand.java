package commands;

import managers.CommandManager;

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
  public void execute(String arg) {
    System.out.println("Список команд:");
    for (AbstractCommand command : commandManager.getCommands().values()) {
      System.out.println("    " + command.toString());
    }
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

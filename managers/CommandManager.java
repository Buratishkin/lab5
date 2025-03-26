package managers;

import commands.AbstractCommand;
import java.util.HashMap;

/** Менеджер для хранения всех команд */
public class CommandManager {
  /** Словарь для хранения команд */
  private final HashMap<String, AbstractCommand> commands = new HashMap<>();

  /**
   * Добавление команды в словарь
   *
   * @param name название команды
   * @param command команда
   */
  public void addInCommands(String name, AbstractCommand command) {
    commands.put(name, command);
  }

  /**
   * Возвращает словарь команд
   *
   * @return словарь команд
   */
  public HashMap<String, AbstractCommand> getCommands() {
    return commands;
  }

  public AbstractCommand getCommand(String name){
    return commands.get(name);
  }
}

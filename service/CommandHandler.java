package service;

import commands.AbstractCommand;
import commands.HistoryCommand;
import exceptions.CommandException;
import java.time.LocalDateTime;
import java.util.Scanner;
import managers.CityManager;
import managers.CommandManager;

/** Класс для работы с командами */
public class CommandHandler {
  private boolean mode = false;
  private static final Scanner scanner = new Scanner(System.in);
  private HistoryCommand historyCommand = new HistoryCommand();
  private final CityManager cityManager;

  /**
   * Конструктор
   *
   * @param cityManager менеджер коллекций
   */
  public CommandHandler(CityManager cityManager) {
    this.cityManager = cityManager;
  }

  /**
   * Метод для ввода команды
   *
   * @return введенную команду
   */
  private String enterCommand() {
    System.out.println("Введите команду:");
    return scanner.nextLine();
  }

  /**
   * Ставит режим выполнения: false - пользовательский ввод, true - выполнение скрипта
   *
   * @param mode мод
   */
  public void setMode(boolean mode) {
    this.mode = mode;
  }

  /**
   * Определение команды и её выполнение
   *
   * @param commandManager менеджер команд
   * @param line команда из скрипта или пустая строка
   */
  public void run(CommandManager commandManager, String line) {
    String input;
    if (line.isEmpty()) input = enterCommand();
    else input = line;
    String[] parts = input.trim().split("\\s+");

    if (parts.length == 0 || parts[0].isEmpty()) {
      System.out.println("Вы ничего не ввели. Попробуйте ещё раз.");
      return;
    }

    String commandName = parts[0].trim().toLowerCase();

    try {
      if (commandManager.getCommands().containsKey(commandName)) {
        AbstractCommand currentCommand = commandManager.getCommands().get(commandName);
        historyCommand.addInHistory(commandName);
        if (currentCommand.isElementable()) cityManager.setUpdateDateTime(LocalDateTime.now());
        if (currentCommand.isArgumentable()) {
          if (parts.length < 2) throw new CommandException("Не передан аргумент для команды.");
          else currentCommand.execute(parts[1]);
        } else currentCommand.execute(parts[0]);
      } else {
        System.out.println(
            "Такой команды не существует. Попробуйте ещё раз.\nЧтобы посмотреть список команд, напишите help.");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      if (mode) run(commandManager, "");
    }
  }
}

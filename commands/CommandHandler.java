package commands;

import exceptions.CommandException;
import interfaces.Identifiable;
import interfaces.ScriptCommand;
import io.DataReader;
import java.time.LocalDateTime;
import java.util.Scanner;
import main.Main;
import managers.CollectionManager;
import managers.CommandManager;

/** Класс для работы с командами */
public class CommandHandler<T extends Comparable<T> & Identifiable> {
  private boolean scriptMode = false;
  private final CommandManager commandManager;
  private Scanner scanner;
  private Scanner defaultScanner;
  private final HistoryCommand historyCommand = new HistoryCommand();
  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public CommandHandler(
      CollectionManager<T> collectionManager, Scanner scanner, CommandManager commandManager) {
    this.collectionManager = collectionManager;
    defaultScanner = scanner;
    this.scanner = scanner;
    this.commandManager = commandManager;
  }

  public void setDefaultScanner(Scanner scanner) {
    defaultScanner = scanner;
  }

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Метод для ввода команды
   *
   * @return введенную команду
   */
  private String enterCommand() {
    System.out.println("Введите команду:");
    try {
      if (scanner.hasNextLine()) {
        return scanner.nextLine();
      } else {
        System.out.println("Обнаружен EOF");
        scanner = Main.resetScanner(this);
        return "";
      }
    } catch (Exception e) {
      System.out.println("Возникла ошибка: " + e.getMessage());
      return "";
    }
  }

  /**
   * Ставит режим выполнения: false - пользовательский ввод, true - выполнение скрипта
   *
   * @param scriptMode мод
   */
  public void setMode(boolean scriptMode) {
    this.scriptMode = scriptMode;
  }

  /**
   * Определение команды и её выполнение
   *
   * @param line команда из скрипта или пустая строка
   */
  public void run(String line) {
    String input;
    if (line.isEmpty()) input = enterCommand();
    else input = line;
    String[] parts = input.trim().split("\\s+");

    if (parts.length == 0 || parts[0].isEmpty()) {
      throw new IllegalArgumentException("Вы ничего не ввели. Попробуйте ещё раз.");
    }

    String commandName = parts[0].trim().toLowerCase();

    try {
      if (commandManager.getCommands().containsKey(commandName)) {
        AbstractCommand currentCommand = commandManager.getCommands().get(commandName);
        historyCommand.addInHistory(commandName);

        if (currentCommand.isElementable() || commandName.contains("remove")) {
          collectionManager.setUpdateDateTime(LocalDateTime.now());
        }

        if (currentCommand instanceof ScriptCommand) {
          ((ScriptCommand) currentCommand).setScriptMode(scriptMode);
          if (scriptMode) {
            DataReader.setCurrentScanner(scanner);
            DataReader.setScriptMode(true);
          } else DataReader.setCurrentScanner(defaultScanner);
        }

        if (currentCommand.isArgumentable()) {
          if (parts.length < 2) throw new CommandException("Не передан аргумент для команды.");
          else currentCommand.execute(parts[1]);
        } else currentCommand.execute(parts[0]);

      } else {
        System.out.println(
            "Команды \""
                + commandName
                + "\" не существует. Попробуйте ещё раз.\nЧтобы посмотреть список команд, напишите help.");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

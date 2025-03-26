package commands;

import exceptions.CommandException;
import java.time.LocalDateTime;
import java.util.Scanner;

import interfaces.Identifiable;
import managers.CollectionManager;
import managers.CommandManager;

/** Класс для работы с командами */
public class CommandHandler<T extends Comparable<T> & Identifiable> {
  private boolean consoleMode = true;
  private static final Scanner scanner = new Scanner(System.in);
  private final HistoryCommand historyCommand = new HistoryCommand();
  private final CollectionManager<T> collectionManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public CommandHandler(CollectionManager<T> collectionManager) {
    this.collectionManager = collectionManager;
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
   * @param consoleMode мод
   */
  public void setMode(boolean consoleMode) {
    this.consoleMode = consoleMode;
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
        if (currentCommand.isElementable()) {
          collectionManager.setUpdateDateTime(LocalDateTime.now());
        }
        if (commandName.contains("remove")) collectionManager.setUpdateDateTime(LocalDateTime.now());
        if (currentCommand.isArgumentable()) {
          if (parts.length < 2) throw new CommandException("Не передан аргумент для команды.");
          else currentCommand.execute(parts[1]);
        } else currentCommand.execute(parts[0]);
      } else {
        System.out.println(
            "Команды \"" + commandName + "\" не существует. Попробуйте ещё раз.\nЧтобы посмотреть список команд, напишите help.");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

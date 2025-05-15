package commands;

import interfaces.Identifiable;
import io.FileManager;
import main.Main;
import managers.CommandManager;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/** Считываются и исполняются скрипт из указанного файла */
public class ExecuteScriptCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {
  private final CommandManager commandManager;
  private final Set<String> pastFiles = new HashSet<>();
  private final CommandHandler<T> commandHandler;
  private static String lostLine = "";

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   * @param commandHandler обработчик команд
   */
  public ExecuteScriptCommand(CommandManager commandManager, CommandHandler<T> commandHandler) {
    super("execute_script", "Считываются и исполняются скрипт из указанного файла.");
    this.commandManager = commandManager;
    this.commandHandler = commandHandler;
  }

  /**
   * Выполнение команды
   *
   * @param fileName аргумент
   */
  @Override
  public void execute(String fileName) {
    File file = new File(fileName);

    if (!FileManager.canRead(file)) {
      return;
    }

    if (pastFiles.contains(file.getAbsolutePath())) {
      System.out.println(
          "В скрипте обнаружена рекурсия. Повторно вызывается файл: " + file.getAbsolutePath());
      return;
    }

    try (Scanner scanner = new Scanner(file)) {
      getHelp();
      pastFiles.add(file.getAbsolutePath());
      String line;
      commandHandler.setMode(true);
      commandHandler.setScanner(scanner);
      while (scanner.hasNext() || !lostLine.isEmpty()) {
        if (!lostLine.isEmpty()) {
          line = lostLine;
          lostLine = "";
        } else line = scanner.nextLine().trim();
        if (line.isEmpty()) continue;
        commandHandler.run(line);
      }
      commandHandler.setMode(false);
      commandHandler.setScanner(Main.getScanner());
    } catch (Exception e) {
      System.out.println("При выполнении скрипта возникла ошибка: " + e.getMessage());
    }
  }

  public static void setLostLine(String lostLine) {
    ExecuteScriptCommand.lostLine = lostLine;
  }

  public void getHelp() {
    System.out.println(
        "Структура скрипта:"
            + "\n    Каждая команда начинается с новой строки. Для получения списка существующих команд напишите help."
            + "\n    Если команда добавляет элемент в коллекцию, то перед значениями полей должно быть 4 пробела."
            + "\n    Если значение полей меньше количества, нужных для создания класса, то недостающие поля нужно будет вводить вручную."
            + "\n    Если больше - лишние поля просто отбрасываются."
            + "\nЕсли возникает рекурсия - выполнение скрипта прекращается.");
  }

  @Override
  public boolean isArgumentable() {
    return true;
  }

  @Override
  public boolean isElementable() {
    return false;
  }
}

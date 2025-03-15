package commands;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import managers.CommandManager;
import managers.FileManager;
import service.CommandHandler;

/** Считываются и исполняются скрипт из указанного файла */
public class ExecuteScriptCommand extends AbstractCommand {
  private final CommandManager commandManager;
  private Set<String> pastFiles = new HashSet<>();
  private final CommandHandler commandHandler;

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   * @param commandHandler обработчик команд
   */
  public ExecuteScriptCommand(CommandManager commandManager, CommandHandler commandHandler) {
    super("execute_script", "Считываются и исполняются скрипт из указанного файла.");
    this.commandManager = commandManager;
    this.commandHandler = commandHandler;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public void execute(String arg) {
    String fileName = arg;
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
      pastFiles.add(file.getAbsolutePath());
      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        if (line.isEmpty()) break;
        commandHandler.run(commandManager, line);
      }
      commandHandler.setMode(true);
    } catch (Exception e) {
      System.out.println("При выполнении скрипта возникла ошибка: " + e.getMessage());
    }
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

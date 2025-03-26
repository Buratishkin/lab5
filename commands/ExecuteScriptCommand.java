package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import interfaces.Identifiable;
import io.InputManager;
import managers.CommandManager;
import io.FileManager;

/** Считываются и исполняются скрипт из указанного файла */
public class ExecuteScriptCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {
  private final CommandManager commandManager;
  private final Set<String> pastFiles = new HashSet<>();
  private final CommandHandler<T> commandHandler;
  private final InputManager<T> inputManager;

  /**
   * Конструктор
   *
   * @param commandManager менеджер коллекций
   * @param commandHandler обработчик команд
   */
  public ExecuteScriptCommand(CommandManager commandManager, CommandHandler<T> commandHandler, InputManager<T> inputManager) {
    super("execute_script", "Считываются и исполняются скрипт из указанного файла.");
    this.commandManager = commandManager;
    this.commandHandler = commandHandler;
    this.inputManager = inputManager;
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

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      pastFiles.add(file.getAbsolutePath());
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isEmpty()) continue;
        //надо как-то CityInputManager переделать, чтобы если заполнение класса из скрипта прекращалось, то мы заполняли его из терминала
        //надо смотреть сколько не строк в reader, если в нем столько же, сколько надо для inputManager, то просто делаем inputObject,
        //если меньше, то остальные заполняются с помощью ручного ввода, если больше, то остальные отбрасываются
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

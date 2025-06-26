package commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import classes.City;
import io.CityInputManager;
import service.ColorConsole;

public class ExecuteScriptCommand {
  private final CityInputManager cityInputManager;
  public ExecuteScriptCommand(CityInputManager cityInputManager){
    this.cityInputManager = cityInputManager;
  }

  public LinkedHashMap<String, City> execute(String[] parts) throws IOException {
    if (parts.length < 2) throw new IllegalArgumentException("Не передано название файла");

    LinkedHashMap<String, City> queueScriptCommand = new LinkedHashMap<>();

    String fileName = parts[1];
    Path path = Paths.get(fileName);
    canRead(path);
    Scanner scanner = new Scanner(path);
    String line;

    String command = "";
    LinkedList<String> args = new LinkedList<>();
    boolean isReadArgs = false;
    try {
      while (scanner.hasNextLine()){
        line = scanner.nextLine();
        if (line.startsWith("    ")){
          if (!isReadArgs){
            isReadArgs = true;
            args.clear();
          } args.add(line.trim());
        } else {
          if (isReadArgs){
            queueScriptCommand.put(command, cityInputManager.inputScriptCity(args));
          } else if (CommandHandler.CITY_COMMANDS.contains(line)) {
            command = line;
            continue;
          }
          command = line;
          queueScriptCommand.put(line, null);
          isReadArgs = false;
        }
      }
      if (CommandHandler.CITY_COMMANDS.contains(command)){
        queueScriptCommand.put(command, cityInputManager.inputScriptCity(args));
      } else{
        queueScriptCommand.put(command, null);
      }

    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }

    System.out.println(ColorConsole.PURPLE + "Команды в скрипте:");
    for (String cmnd : queueScriptCommand.keySet()) {
      System.out.println(cmnd + ": " + queueScriptCommand.get(cmnd));
    }
    System.out.print(ColorConsole.RESET);
    return queueScriptCommand;
  }

  public void canRead(Path path) throws NoSuchFileException, FileNotFoundException {
    if (!Files.exists(path)) {
      throw new NoSuchFileException("Файл " + path.getFileName() + " не существует");
    }
    if (!Files.isReadable(path)) {
      throw new FileNotFoundException("Нет прав на чтение файла " + path.getFileName());
    }
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
}

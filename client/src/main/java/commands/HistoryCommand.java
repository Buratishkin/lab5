package commands;

import java.util.ArrayList;
import java.util.List;
import service.ColorConsole;

/** Выводит последние 11 команд (без их аргументов) */
public class HistoryCommand {

  static List<String> historyCommands = new ArrayList<>();

  public void addInHistory(String command) {
    historyCommands.add(command);
  }

  public static void execute() {
    StringBuilder line = new StringBuilder();
    line.append("История команд:\n");
    if (historyCommands.size() > 11) {
      historyCommands =
          historyCommands.subList(historyCommands.size() - 11, historyCommands.size());
    }
    for (int i = 0; i < historyCommands.size(); i++) {
      line.append(String.format("%d. %s\n", i + 1, historyCommands.get(i)));
    }
    line.append(String.format("%d. %s", historyCommands.size() + 1, "history"));
    System.out.println(
        ColorConsole.GREEN + "Результат выполнения команды history:" + ColorConsole.RESET);
    System.out.println(line);
  }
}

package commands;

import java.util.LinkedList;
import java.util.List;
import service.ColorConsole;

/** Выводит последние 11 команд (без их аргументов) */
public class HistoryCommand {

  List<String> historyCommands = new LinkedList<>();

  public void addInHistory(String command) {
    historyCommands.add(command);
  }

  public void execute() {
    StringBuilder line = new StringBuilder();
    line.append("История команд:\n");
    if (historyCommands.size() > 11) {
      historyCommands =
          historyCommands.subList(historyCommands.size() - 11, historyCommands.size());
    }
    for (int i = 1; i < historyCommands.size(); i++) {
      line.append(String.format("%d. %s\n", i, historyCommands.get(i)));
    }
    line.append(String.format("%d. %s", historyCommands.size(), "history"));
    historyCommands.add("history");
    System.out.println(
        ColorConsole.GREEN + "Результат выполнения команды history:" + ColorConsole.RESET);
    System.out.println(line);
  }
}

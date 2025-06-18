package commands;

import java.util.ArrayList;
import java.util.List;

/** Выводит последние 11 команд (без их аргументов) */
public class HistoryCommand extends AbstractCommand {

  static List<String> historyCommands = new ArrayList<>();

  public HistoryCommand() {
    super("history", "Выводит последние 11 команд (без их аргументов)");
  }

  public void addInHistory(String command) {
    historyCommands.add(command);
  }

  public static List<String> getHistoryCommands() {
    return historyCommands;
  }

  /**
   * Выполнение команд
   *
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    StringBuilder line = new StringBuilder();
    line.append("История команд:");
    if (historyCommands.size() > 11) {
      historyCommands =
          historyCommands.subList(historyCommands.size() - 11, historyCommands.size());
    }
    for (int i = 0; i < historyCommands.size(); i++) {
      line.append(String.format("%d. %s\n", i + 1, historyCommands.get(i)));
    }
    return line.toString();
  }

  @Override
  public boolean isElementable() {
    return false;
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }
}

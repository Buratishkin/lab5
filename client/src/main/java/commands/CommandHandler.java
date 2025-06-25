package commands;

import classes.City;
import io.CityDataReader;
import io.CityInputManager;
import java.io.IOException;
import java.util.*;

import manager.PasswordManager;
import manager.ValidationManager;
import network.Request;

public class CommandHandler {
  private final Scanner scanner;
  private boolean scriptMode = false;
  private final ValidationManager validationManager = new ValidationManager();
  private final CityDataReader cityDataReader;
  private final CityInputManager cityInputManager;
  static final List<String> CITY_COMMANDS =
      new ArrayList<>(List.of("add", "update", "remove_lower", "remove_greater"));
  private final ExecuteScriptCommand executeScript = new ExecuteScriptCommand();
  private LinkedList<String> queueScriptCommand;
  private final HistoryCommand historyCommand;
  private String userName;
  private String password;

  public CommandHandler(Scanner scanner, HistoryCommand historyCommand) {
    this.scanner = scanner;
    this.cityDataReader = new CityDataReader(scanner, validationManager);
    this.cityInputManager = new CityInputManager(cityDataReader, validationManager);
    this.historyCommand = historyCommand;
  }

  public List<Request> run() throws IOException {
    ArrayList<Request> requests = new ArrayList<>();
    System.out.println("Введите команду:");
    String[] parts = scanner.nextLine().split(" ");

    if (parts[0].equals("execute_script")) {
      queueScriptCommand = executeScript.execute(parts);
      scriptMode = true;
      historyCommand.addInHistory(parts[0]);
    }

    if (!scriptMode) {
      createRequest(parts, requests, false);
    } else {
      for (String command : queueScriptCommand) {
        createRequest(command.split(" "), requests, false);
      }
    }

    return requests;
  }

  private void createRequest(String[] parts, List<Request> requests, boolean isAuthorize) {
    City city = null;
    if (parts[0].equals("exit")) {
      System.out.println("Завершение работы клиента");
      System.exit(0);
    }
    if (parts[0].equals("history")) {
      historyCommand.execute();
    }
    if (CITY_COMMANDS.contains(parts[0])) {
      if (parts[0].equals("update"))
        cityInputManager.setCustomId(
            validationManager.validateInt(parts.length < 2 ? null : parts[1], false));

      city = cityInputManager.inputObject();
    }

    if (isAuthorize) {
      requests.add(new Request(parts[0].toLowerCase().trim(), null, city, userName, password));
    } else if (parts.length < 2)
      requests.add(new Request(parts[0].toLowerCase().trim(), null, city, userName, password));
    else
      requests.add(
          new Request(parts[0].toLowerCase().trim(), parts[1].trim(), city, userName, password));
  }

  public List<Request> authorize() {
    String type;
    List<Request> requests = new ArrayList<>();
    if (!scanner.nextLine().trim().toLowerCase().equals("yes")) {
      type = "registration";
    } else {
      type = "login";
    }
    userName =
        validationManager.customValidate(
            System.console(),
            "Введите имя пользователя: ",
            "Ошибка для имени пользователя: ",
            0,
            false);
    password = validationManager.customValidate(
            System.console(), "Введите пароль: ", "Ошибка для пароля: ", 16, true);

    String[] parts = {type, null, userName, password};
    createRequest(parts, requests, true);
    return requests;
  }
}

package commands;

import classes.City;
import io.CityDataReader;
import io.CityInputManager;
import manager.ValidationManager;
import network.Request;

import java.io.IOException;
import java.util.*;

public class CommandHandler {
    private final Scanner scanner;
    private boolean scriptMode = false;
    private final ValidationManager validationManager = new ValidationManager();
    private final CityDataReader cityDataReader;
    private final CityInputManager cityInputManager;
    static final List<String> CITY_COMMANDS = new ArrayList<>(List.of("add", "update", "remove_lower", "remove_greater"));
    private final ExecuteScriptCommand executeScript = new ExecuteScriptCommand();
    private LinkedList<String> queueScriptCommand;

    public CommandHandler(Scanner scanner){
        this.scanner = scanner;
        this.cityDataReader  = new CityDataReader(scanner, validationManager);
        this.cityInputManager = new CityInputManager(cityDataReader, validationManager);
    }

    public List<Request> run() throws IOException {
        ArrayList<Request> requests = new ArrayList<>();
        System.out.println("Введите команду:");
        String[] parts = scanner.nextLine().split(" ");

        if (parts[0].equals("execute_script")){
            queueScriptCommand = executeScript.execute(parts);
            scriptMode = true;
        }

        if (!scriptMode) {
            createRequest(parts, requests);
        } else{
            for (String command : queueScriptCommand){
                createRequest(command.split(" "), requests);
            }
        }

        return requests;
    }

    private void createRequest(String[] parts, List<Request> requests){
        City city = null;
        if (parts[0].equals("exit")){
            System.out.println("Завершение работы клиента");
            System.exit(0);
        }
        if (parts[0].equals("history")){
            HistoryCommand.execute();
        }
        if (CITY_COMMANDS.contains(parts[0])) {
            if (parts[0].equals("update"))
                cityInputManager.setCustomId(validationManager.validateInt(parts.length < 2 ? null : parts[1], false));

            city = cityInputManager.inputObject();
        }

        if (parts.length < 2) requests.add(new Request(parts[0].toLowerCase().trim(), null, city));
        else requests.add(new Request(parts[0].toLowerCase().trim(), parts[1].trim(), city));
    }
}

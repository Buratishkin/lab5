package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import storage.CityManager;
/**
 * Обработчик команд.
 */
public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final CityManager cityManager;
    private final Scanner scanner;

    public CommandHandler(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;

        // Регистрация команд
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand(cityManager));
        commands.put("show", new ShowCommand(cityManager));
        commands.put("insert", new InsertCommand(cityManager, scanner));
        commands.put("update", new UpdateCommand(cityManager, scanner));
        commands.put("remove_key", new RemoveKeyCommand(cityManager, scanner));
        commands.put("clear", new ClearCommand(cityManager));
        commands.put("save", new SaveCommand(cityManager, "collection.xml")); // Укажите имя файла
        commands.put("execute_script", new ExecuteScriptCommand(this, scanner));
        commands.put("exit", new ExitCommand());
        commands.put("remove_greater", new RemoveGreaterCommand(cityManager, scanner));
        commands.put("replace_if_lowe", new ReplaceIfLoweCommand(cityManager, scanner));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(cityManager, scanner));
        commands.put("group_counting_by_id", new GroupCountingByIdCommand(cityManager));
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(cityManager, scanner));
        commands.put("print_ascending", new PrintAscendingCommand(cityManager));
    }

    /**
     * Обрабатывает команду.
     *
     * @param input имя команды.
     */
    public void handleCommand(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length==0) return;
        String commandName=parts[0];
        Command command= commands.get(commandName);
        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("Неизвестная команда. Введите help для справки.");
        }
    }
}
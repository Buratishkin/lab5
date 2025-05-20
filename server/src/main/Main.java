package main;

import classes.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.*;
import commands.CommandHandler;
import io.*;
import io.Reader;
import io.Writer;
import java.util.Scanner;
import managers.*;
import service.IdCreator;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EnvManager envManager = new EnvManager(scanner);
        envManager.manageEnv();

        CollectionManager<City> collectionManager = new CollectionManager<>();

        ObjectMapper objectMapper = new ObjectMapper();
        FileManager<City> fIleManager =
                new FileManager<>(envManager.getEnv(), collectionManager, objectMapper, City.class);

        Writer writer =
                new XMLWriter<>(envManager.getEnv(), collectionManager, objectMapper, City.class);
        Reader reader =
                new XMLReader<>(envManager.getEnv(), collectionManager, objectMapper, City.class);

        fIleManager.convertListToCollection(reader.readFromFile());

        IdCreator<City> idCreator = new IdCreator<>(collectionManager);
        idCreator.addFreeId();

        ValidationManager validationManager = new ValidationManager();

        CityDataReader dataReader = new CityDataReader(scanner, validationManager);

        InputManager<City> inputManager =
                new CityInputManager(dataReader, idCreator, validationManager);

        CommandManager commandManager = new CommandManager();
        CommandHandler<City> commandHandler =
                new CommandHandler<>(collectionManager, scanner, commandManager);

        commandManager.addInCommands(
                "add", new AddCommand<>(collectionManager, idCreator, inputManager));
        commandManager.addInCommands("clear", new ClearCommand<>(collectionManager, idCreator));
        commandManager.addInCommands(
                "count_less_than_meters_above_sea_level",
                new CountLessThanMetersAboveSeaLevelCommand<>(collectionManager, validationManager));
        commandManager.addInCommands("exit", new ExitCommand());
        commandManager.addInCommands(
                "execute_script", new ExecuteScriptCommand<>(commandManager, commandHandler));
        commandManager.addInCommands(
                "filter_contains_name", new FilterContainsNameCommand<>(collectionManager));
        commandManager.addInCommands(
                "group_counting_by_name", new GroupCountingByNameCommand<>(collectionManager));
        commandManager.addInCommands("help", new HelpCommand(commandManager));
        commandManager.addInCommands("history", new HistoryCommand());
        commandManager.addInCommands("info", new InfoCommand<>(collectionManager));
        commandManager.addInCommands(
                "remove_by_id", new RemoveByIdCommand<>(collectionManager, idCreator));
        commandManager.addInCommands(
                "remove_greater", new RemoveGreaterCommand<>(collectionManager, commandManager));
        commandManager.addInCommands(
                "remove_lower", new RemoveLowerCommand<>(collectionManager, commandManager));
        commandManager.addInCommands("save", new SaveCommand<>(collectionManager, fIleManager, writer));
        commandManager.addInCommands("show", new ShowCommand<>(collectionManager));
        commandManager.addInCommands("update", new UpdateCommand<>(collectionManager, inputManager));

        while (true) {
            try {
                commandHandler.setMode(false);
                commandHandler.run("");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static Scanner resetScanner(CommandHandler commandHandler) {
        scanner = new Scanner(System.in);
        commandHandler.setScanner(scanner);
        commandHandler.setDefaultScanner(scanner);
        System.out.println("Сканнер пересоздан.");
        return scanner;
    }
}

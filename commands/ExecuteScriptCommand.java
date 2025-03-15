package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта из файла.
 */
public class ExecuteScriptCommand implements Command {
    private final CommandHandler commandHandler;
    private final Scanner scanner;

    public ExecuteScriptCommand(CommandHandler commandHandler, Scanner scanner) {
        this.commandHandler = commandHandler;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Введите имя файла скрипта: ");
        String fileName = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String command = fileScanner.nextLine().trim();
                if (!command.isEmpty()) {
                    System.out.println("Выполнение команды: " + command);
                    commandHandler.handleCommand(command);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + fileName);
        }
    }
}
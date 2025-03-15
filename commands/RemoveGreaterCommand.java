package commands;

import city.City;
import io.InputHandler;
import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для удаления элементов, превышающих заданный.
 */
public class RemoveGreaterCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public RemoveGreaterCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        InputHandler inputHandler = new InputHandler(scanner);
        City city = inputHandler.inputCity();

        cityManager.getCollection().values().removeIf(c -> c.compareTo(city) > 0);
        System.out.println("Элементы, превышающие заданный, успешно удалены.");
    }
}
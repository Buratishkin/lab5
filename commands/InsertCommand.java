package commands;

import city.City;
import io.InputHandler;
import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class InsertCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public InsertCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        InputHandler inputHandler = new InputHandler(scanner);
        City city = inputHandler.inputCity();
        cityManager.addCity(city);
        System.out.println("Элемент успешно добавлен.");
    }
}
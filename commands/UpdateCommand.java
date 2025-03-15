package commands;

import city.City;
import io.InputHandler;
import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для обновления элемента коллекции по id.
 */
public class UpdateCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public UpdateCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Введите id города для обновления: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (!cityManager.getCollection().containsKey(id)) {
            System.out.println("Город с id " + id + " не найден.");
            return;
        }

        InputHandler inputHandler = new InputHandler(scanner);
        City newCity = inputHandler.inputCity();
        newCity.setId(id); // Сохраняем старый id
        cityManager.updateCity(id, newCity);
        System.out.println("Город с id " + id + " успешно обновлен.");
    }
}
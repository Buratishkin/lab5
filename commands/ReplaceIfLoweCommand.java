package commands;

import city.City;
import io.InputHandler;
import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для замены значения по ключу, если новое значение меньше старого.
 */
public class ReplaceIfLoweCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public ReplaceIfLoweCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Введите id города для замены: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (!cityManager.getCollection().containsKey(id)) {
            System.out.println("Город с id " + id + " не найден.");
            return;
        }

        InputHandler inputHandler = new InputHandler(scanner);
        City newCity = inputHandler.inputCity();

        City oldCity = cityManager.getCollection().get(id);
        if (newCity.compareTo(oldCity) < 0) {
            newCity.setId(id);
            cityManager.updateCity(id, newCity);
            System.out.println("Город с id " + id + " успешно заменен.");
        } else {
            System.out.println("Новое значение не меньше старого. Замена не выполнена.");
        }
    }
}
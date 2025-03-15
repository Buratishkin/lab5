package commands;

import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для вывода элементов, имя которых начинается с заданной подстроки.
 */
public class FilterStartsWithNameCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public FilterStartsWithNameCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Введите подстроку: ");
        String prefix = scanner.nextLine();

        cityManager.getCollection().values().stream()
                .filter(city -> city.getName().startsWith(prefix))
                .forEach(System.out::println);
    }
}
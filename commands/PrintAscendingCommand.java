package commands;

import storage.CityManager;

/**
 * Команда для вывода элементов коллекции в порядке возрастания.
 */
public class PrintAscendingCommand implements Command {
    private final CityManager cityManager;

    public PrintAscendingCommand(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        cityManager.getAllCities().stream()
                .sorted()
                .forEach(System.out::println);
    }
}
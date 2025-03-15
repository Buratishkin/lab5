package commands;

import storage.CityManager;

/**
 * Команда для вывода всех элементов коллекции.
 */
public class ShowCommand implements Command {
    private final CityManager cityManager;

    public ShowCommand(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        if (cityManager.getCollectionSize() == 0) {
            System.out.println("Коллекция пуста.");
        } else {
            cityManager.getAllCities().forEach(System.out::println);
        }
    }
}
package commands;

import storage.CityManager;

/**
 * Команда для вывода информации о коллекции.
 */
public class InfoCommand implements Command {
    private final CityManager cityManager;

    public InfoCommand(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Тип коллекции: " + cityManager.getCollection().getClass().getSimpleName());
        System.out.println("Количество элементов: " + cityManager.getCollectionSize());
        System.out.println("Дата инициализации: " + java.time.LocalDateTime.now());
    }
}
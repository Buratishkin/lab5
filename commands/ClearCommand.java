package commands;

import storage.CityManager;

/**
 * Команда для очистки коллекции.
 */
public class ClearCommand implements Command {
    private final CityManager cityManager;

    public ClearCommand(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        cityManager.clearCollection();
        System.out.println("Коллекция успешно очищена.");
    }
}
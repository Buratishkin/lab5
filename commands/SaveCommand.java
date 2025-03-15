package commands;

import io.XmlWriter;
import storage.CityManager;

/**
 * Команда для сохранения коллекции в файл.
 */
public class SaveCommand implements Command {
    private final CityManager cityManager;
    private final String fileName;

    public SaveCommand(CityManager cityManager, String fileName) {
        this.cityManager = cityManager;
        this.fileName = fileName;
    }

    @Override
    public void execute(String[] args) {
        try {
            XmlWriter.writeCitiesToFile(fileName, cityManager.getAllCities());
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }
}
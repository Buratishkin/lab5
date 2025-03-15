package commands;

import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для удаления элемента коллекции по ключу.
 */
public class RemoveKeyCommand implements Command {
    private final CityManager cityManager;

    public RemoveKeyCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length==2) {
            try {
                int id = Integer.parseInt(args[1]);
                if (cityManager.getCollection().containsKey(id)) {
                    cityManager.removeCity(id);
                    System.out.println("Город с id " + id + " успешно удален.");
                } else {
                    System.out.println("Город с id " + id + " не найден.");
                }
            } catch (NumberFormatException e) {
                System.out.println("id должен быть целым числом");
            }
        }
    }
}
package commands;

import storage.CityManager;

import java.util.Scanner;

/**
 * Команда для удаления элементов, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand implements Command {
    private final CityManager cityManager;
    private final Scanner scanner;

    public RemoveGreaterKeyCommand(CityManager cityManager, Scanner scanner) {
        this.cityManager = cityManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Введите ключ: ");
        int key = Integer.parseInt(scanner.nextLine());

        cityManager.getCollection().keySet().removeIf(k -> k > key);
        System.out.println("Элементы с ключами больше " + key + " успешно удалены.");
    }
}
package io;

import city.City;
import city.Coordinates;
import city.Human;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс для обработки пользовательского ввода.
 */
public class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Запрашивает у пользователя ввод данных для создания нового города.
     *
     * @return объект города.
     */
    public City inputCity() {
        System.out.println("Введите данные города:");

        System.out.print("Название: ");
        String name = scanner.nextLine();

        System.out.println("Введите координаты:");
        Coordinates coordinates = inputCoordinates();

        System.out.print("Площадь: ");
        Long area = Long.parseLong(scanner.nextLine());

        System.out.print("Население: ");
        Integer population = Integer.parseInt(scanner.nextLine());

        System.out.print("Высота над уровнем моря: ");
        Float metersAboveSeaLevel = Float.parseFloat(scanner.nextLine());

        System.out.println("Климат (доступные значения: " + Arrays.toString(Climate.values()).replaceAll("[\\[\\]]","") + "):");
        Climate climate = Climate.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Тип правительства (доступные значения: " + Arrays.toString(Government.values()).replaceAll("[\\[\\]]","") + "):");
        Government government = Government.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Уровень жизни (доступные значения: " + Arrays.toString(StandardOfLiving.values()).replaceAll("[\\[\\]]","") + "):");
        StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Введите данные губернатора:");
        Human governor = inputHuman();

        // Поля id и creationDate генерируются автоматически
        return new City(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    /**
     * Запрашивает у пользователя ввод координат.
     *
     * @return объект координат.
     */
    private Coordinates inputCoordinates() {
        System.out.print("Координата X (максимум 36): ");
        double x = Double.parseDouble(scanner.nextLine());
        System.out.print("Координата Y: ");
        int y = Integer.parseInt(scanner.nextLine());
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает у пользователя ввод данных губернатора.
     *
     * @return объект человека.
     */
    private Human inputHuman() {
        System.out.print("Рост губернатора: ");
        float height = Float.parseFloat(scanner.nextLine());
        return new Human(height);
    }
}

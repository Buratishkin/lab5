package io;

import classes.Coordinates;
import classes.Human;
import managers.ValidationManager;

import java.util.Scanner;

public class CityDataReader extends DataReader{
    private final ValidationManager validationManager;
    public CityDataReader(Scanner scanner, ValidationManager validationManager){
        super(scanner);
        this.validationManager = validationManager;
    }

    public Coordinates readCoordinates(boolean isConsoleRead){
        float coordinateX = readAnything(
                "Введите значение координаты x (число с плавающей точкой, не больше 590):",
                "Введено неправильное значение для координаты x",
                input -> validationManager.validateCondition(validationManager.validateFloat(input, false), 4, 590.0f),
                isConsoleRead
        );
        long coordinatesY = readAnything(
                "Введите значение координаты y (целое число)",
                "Введено неправильное значение для координаты y",
                input -> validationManager.validateLong(input, false),
                isConsoleRead
        );
        return (Coordinates) validationManager.validateObject(new Coordinates(coordinateX, coordinatesY), false);
    }

    public Human readHuman(boolean isConsoleRead){
        String name = readAnything(
                "Введите имя мэра:",
                "Введено неправильное значение для имени мэра",
                input -> validationManager.validateString(input, false),
                isConsoleRead
        );
        int age = readAnything(
                "Введите возраст мэра(целое число, больше 0):",
                "Введено неправильное значение для возраста мэра",
                input -> validationManager.validateCondition(validationManager.validateInt(input, false), 1, 0),
                isConsoleRead
        );
        return (Human) validationManager.validateObject(new Human(name, age), false);
    }
}

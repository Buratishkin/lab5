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

    public Coordinates readCoordinates(){
        float coordinateX = readAnything(
                "значение координаты x (число с плавающей точкой, не больше 590): ",
                "Введено неправильное значение для координаты x",
                input -> validationManager.validateCondition(validationManager.validateFloat(input, false), 4, 590.0f)
        );
        long coordinatesY = readAnything(
                "значение координаты y (целое число): ",
                "Введено неправильное значение для координаты y",
                input -> validationManager.validateLong(input, false)
        );
        return (Coordinates) validationManager.validateObject(new Coordinates(coordinateX, coordinatesY), false);
    }

    public Human readHuman(){
        String name = readAnything(
                "имя мэра: ",
                "Введено неправильное значение для имени мэра",
                input -> validationManager.validateString(input, false)
        );
        int age = readAnything(
                "возраст мэра(целое число, больше 0): ",
                "Введено неправильное значение для возраста мэра",
                input -> validationManager.validateCondition(validationManager.validateInt(input, false), 1, 0)
        );
        return (Human) validationManager.validateObject(new Human(name, age), false);
    }
}

package io;

import classes.City;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import managers.ValidationManager;
import service.DateCreator;
import service.IdCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CityInputManager implements InputManager<City> {
    private final CityDataReader cityDataReader;
    private final IdCreator<City> idCreator;
    private final ValidationManager validationManager;
    private final List<String> fields = new ArrayList<>(Arrays.asList("id", "name", "coordinateX", "coordinateY", "creationDate", "area",
            "population", "metersAboveSeaLevel", "climate" ,"government", "standardOfLiving", "humanName", "humanAge"));

    public CityInputManager(CityDataReader cityDataReader, IdCreator<City> idCreator, ValidationManager validationManager){
        this.cityDataReader = cityDataReader;
        this.idCreator = idCreator;
        this.validationManager = validationManager;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public City inputObject(boolean isConsoleRead) {
        return new City(
                idCreator.getId(),
                cityDataReader.readAnything(
                        "Введите название города:",
                        "Введено неправильное значение для name. Попробуйте ещё раз",
                        input -> validationManager.validateString(input, false),
                        isConsoleRead
                ),
                cityDataReader.readCoordinates(isConsoleRead),
                DateCreator.getDate(),
                cityDataReader.readAnything(
                        "Введите площадь(число с плавающей точкой, больше 0):",
                        "Введено неправильное значение для area. Попробуйте ещё раз",
                        input -> validationManager.validateCondition(validationManager.validateFloat(input, false), 1, 0.0f),
                        isConsoleRead
                ),
                cityDataReader.readAnything(
                        "Введите численность(целое число, больше 0):",
                        "Введено неправильное значение population. Попробуйте ещё раз",
                        input -> validationManager.validateCondition(validationManager.validateInt(input, false), 1, 0),
                        isConsoleRead
                ),
                cityDataReader.readAnything(
                        "Введите высоту над уровнем моря:",
                        "Введено неправильное значение для metersAboveSeaLevel. Попробуйте ещё раз",
                        input -> validationManager.validateFloat(input, true),
                        isConsoleRead
                ),
                cityDataReader.readAnything(
                        "Выберите один из климатов: " + Climate.valuesToString() +
                                "\nИндекс может быть от 1 до " + Climate.values().length,
                        "Введено неправильное значение для Climate. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(Climate.class, input),
                        isConsoleRead
                ),
                cityDataReader.readAnything(
                        "Выберите одно из правительств: " + Government.valuesToString() +
                                "\nИндекс может быть от 1 до " + Government.values().length,
                        "Введено неправильное значение для Government. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(Government.class, input),
                        isConsoleRead
                ),
                cityDataReader.readAnything(
                        "Выберите один из уровней жизни: " + StandardOfLiving.valuesToString() +
                                "\nИндекс может быть от 1 до " + StandardOfLiving.values().length,
                        "Введено неправильное значение для StandardOfLiving. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(StandardOfLiving.class, input),
                        isConsoleRead
                ),
                cityDataReader.readHuman(isConsoleRead)
        );
    }
}

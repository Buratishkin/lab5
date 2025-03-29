package io;

import classes.City;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import managers.ValidationManager;
import service.DateCreator;
import service.IdCreator;

public class CityInputManager implements InputManager<City> {
    private final CityDataReader cityDataReader;
    private final IdCreator<City> idCreator;
    private final ValidationManager validationManager;

    public CityInputManager(CityDataReader cityDataReader, IdCreator<City> idCreator, ValidationManager validationManager){
        this.cityDataReader = cityDataReader;
        this.idCreator = idCreator;
        this.validationManager = validationManager;
    }

    public CityDataReader getDataReader() {
        return cityDataReader;
    }

    @Override
    public City inputObject() {
        boolean scriptMode = cityDataReader.getScriptMode();
        cityDataReader.setScriptMode(scriptMode);

        return new City(
                idCreator.getId(),
                cityDataReader.readAnything(
                        "название города: ",
                        "Введено неправильное значение для name. Попробуйте ещё раз",
                        input -> validationManager.validateString(input, false)
                ),
                cityDataReader.readCoordinates(),
                DateCreator.getDate(),
                cityDataReader.readAnything(
                        "площадь(число с плавающей точкой, больше 0): ",
                        "Введено неправильное значение для area. Попробуйте ещё раз",
                        input -> validationManager.validateCondition(validationManager.validateFloat(input, false), 1, 0.0f)
                ),
                cityDataReader.readAnything(
                        "численность(целое число, больше 0): ",
                        "Введено неправильное значение population. Попробуйте ещё раз",
                        input -> validationManager.validateCondition(validationManager.validateInt(input, false), 1, 0)
                ),
                cityDataReader.readAnything(
                        "высоту над уровнем моря: ",
                        "Введено неправильное значение для metersAboveSeaLevel. Попробуйте ещё раз",
                        input -> validationManager.validateFloat(input, true)
                ),
                cityDataReader.readAnything(
                        "Climate: " + Climate.valuesToString() +
                                "\nИндекс может быть от 1 до " + Climate.values().length + " ",
                        "Введено неправильное значение для Climate. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(Climate.class, input)
                ),
                cityDataReader.readAnything(
                        "Government: " + Government.valuesToString() +
                                "\nИндекс может быть от 1 до " + Government.values().length + " ",
                        "Введено неправильное значение для Government. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(Government.class, input)
                ),
                cityDataReader.readAnything(
                        "StandardOfLiving: " + StandardOfLiving.valuesToString() +
                                "\nИндекс может быть от 1 до " + StandardOfLiving.values().length + " ",
                        "Введено неправильное значение для StandardOfLiving. Попробуйте ещё раз",
                        input -> validationManager.validateEnum(StandardOfLiving.class, input)
                ),
                cityDataReader.readHuman()
        );
    }
}

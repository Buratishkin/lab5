package io;

import classes.City;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import exceptions.ValidateException;
import manager.ValidationManager;
import service.Creator;
import service.DateCreator;

public class CityInputManager implements InputManager<City> {
  private final CityDataReader cityDataReader;
  private final Creator<City> idCreator;
  private final ValidationManager validationManager;
  private int customId = 0;

  public CityInputManager(
      CityDataReader cityDataReader, Creator<City> idCreator, ValidationManager validationManager) {
    this.cityDataReader = cityDataReader;
    this.idCreator = idCreator;
    this.validationManager = validationManager;
  }

  public CityInputManager(CityDataReader cityDataReader, ValidationManager validationManager) {
    this.cityDataReader = cityDataReader;
    this.idCreator = null;
    this.validationManager = validationManager;
  }

  @Override
  public void setCustomId(int id) {
    if (id > 0) this.customId = id;
    else throw new ValidateException("id должен быть натуральным");
  }

  public CityDataReader getDataReader() {
    return cityDataReader;
  }

  @Override
  public City inputObject() {
    boolean scriptMode = CityDataReader.getScriptMode();
    CityDataReader.setScriptMode(scriptMode);

    City city =
        new City(
            cityDataReader.readAnything(
                "название города: ",
                "Введено неправильное значение для name. Попробуйте ещё раз",
                input -> validationManager.validateString(input, false)),
            cityDataReader.readCoordinates(),
            DateCreator.getDate(),
            cityDataReader.readAnything(
                "площадь(число с плавающей точкой, больше 0): ",
                "Введено неправильное значение для area. Попробуйте ещё раз",
                input ->
                    validationManager.validateCondition(
                        validationManager.validateFloat(input, false), 1, 0.0f)),
            cityDataReader.readAnything(
                "численность(целое число, больше 0): ",
                "Введено неправильное значение population. Попробуйте ещё раз",
                input ->
                    validationManager.validateCondition(
                        validationManager.validateInt(input, false), 1, 0)),
            cityDataReader.readAnything(
                "высоту над уровнем моря: ",
                "Введено неправильное значение для metersAboveSeaLevel. Попробуйте ещё раз",
                input -> validationManager.validateFloat(input, false)),
            cityDataReader.readAnything(
                "Climate: "
                    + Climate.valuesToString()
                    + "\nИндекс может быть от 1 до "
                    + Climate.values().length
                    + " ",
                "Введено неправильное значение для Climate. Попробуйте ещё раз",
                input -> validationManager.validateEnum(Climate.class, input)),
            cityDataReader.readAnything(
                "Government: "
                    + Government.valuesToString()
                    + "\nИндекс может быть от 1 до "
                    + Government.values().length
                    + " ",
                "Введено неправильное значение для Government. Попробуйте ещё раз",
                input -> validationManager.validateEnum(Government.class, input)),
            cityDataReader.readAnything(
                "StandardOfLiving: "
                    + StandardOfLiving.valuesToString()
                    + "\nИндекс может быть от 1 до "
                    + StandardOfLiving.values().length
                    + " ",
                "Введено неправильное значение для StandardOfLiving. Попробуйте ещё раз",
                input -> validationManager.validateEnum(StandardOfLiving.class, input)),
            cityDataReader.readHuman());
    customId = 0;
    return city;
  }
}

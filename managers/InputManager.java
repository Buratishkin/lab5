package managers;

import static service.DateCreator.getDate;

import classes.City;
import classes.Coordinates;
import classes.Human;
import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import exceptions.ValidateException;
import java.util.Scanner;

/** Менеджер для взаимодействия с пользовательским вводом */
public class InputManager {
  private final ValidationManager variableValidator = new ValidationManager();
  private static final Scanner scanner = new Scanner((System.in));

  /**
   * Создание City на основе пользовательского ввода
   *
   * @param id id города
   * @return новый город
   */
  public City createCity(int id) {
    return new City(
        id,
        inputName(),
        inputCoordinates(),
        getDate(),
        inputArea(),
        inputPopulation(),
        inputMetersAboveSea(),
        inputClimate(),
        inputGovernment(),
        inputStandardOfLiving(),
        inputHuman());
  }

  /**
   * Принимает и обрабатывает значения для поля name в классе City
   *
   * @return название города
   */
  public String inputName() {
    while (true) {
      System.out.println("Введите название города:");
      try {
        return variableValidator.validateCityName(scanner.nextLine().trim());
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для name. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (Exception e) {
        System.out.println("Введен неправильный тип данных для name. Попробуйте ещё раз.");
        System.out.println("Вводится тип String.");
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля coordinates в классе City
   *
   * @return Coordinates
   */
  public Coordinates inputCoordinates() {
    while (true) {
      System.out.println("Введите координаты (сначала float: x, потом Long: y):");
      try {
        float x = variableValidator.validateCoordinatesX(scanner.nextLine().trim());
        Long y = variableValidator.validateLong(scanner.nextLine().trim());
        return variableValidator.validateCoordinates(new Coordinates(x, y));
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для Coordinates. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля area в классе City
   *
   * @return площадь
   */
  public float inputArea() {
    while (true) {
      System.out.println("Введите площадь:");
      try {
        return variableValidator.validateArea(scanner.nextLine().trim());
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для area. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля population в классе City
   *
   * @return численность
   */
  public int inputPopulation() {
    while (true) {
      System.out.println("Введите численность:");
      try {
        return variableValidator.validatePopulation(scanner.nextLine().trim());
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для population. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля metersAboveSeaLevel в классе City
   *
   * @return высота над уровнем моря
   */
  public float inputMetersAboveSea() {
    while (true) {
      System.out.println("Введите высоту над уровнем моря:");
      try {
        return variableValidator.validateFloat(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля government в классе City
   *
   * @return Government
   */
  public Government inputGovernment() {
    while (true) {
      System.out.println("Выберите одно из правительств: " + Government.valuesToString());
      System.out.println("Индекс может быть от 1 до " + Government.values().length);
      try {
        return variableValidator.validateEnum(convertEnumToInt(Government.class));
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для Government. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        System.out.println("Введен неправильный тип данных для Government. Попробуйте ещё раз.");
        System.out.println("Вводится тип int");
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля Climate в классе City
   *
   * @return Climate
   */
  public Climate inputClimate() {
    while (true) {
      System.out.println("Выберите один из климатов: " + Climate.valuesToString());
      System.out.println("Индекс может быть от 1 до " + Climate.values().length);
      try {
        return variableValidator.validateEnum(convertEnumToInt(Climate.class));
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для Climate. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        System.out.println("Введен неправильный тип данных для Climate. Попробуйте ещё раз.");
        System.out.println("Вводится тип int");
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля Climate в классе City
   *
   * @return Climate
   */
  public StandardOfLiving inputStandardOfLiving() {
    while (true) {
      System.out.println("Выберите один из уровней жизни: " + StandardOfLiving.valuesToString());
      System.out.println("Индекс может быть от 1 до " + StandardOfLiving.values().length);
      try {
        return variableValidator.validateEnum(convertEnumToInt(StandardOfLiving.class));
      } catch (ValidateException e) {
        System.out.println(
            "Введено неправильное значение для StandardOfLiving. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Принимает и обрабатывает значения для поля Human в классе City
   *
   * @return Human
   */
  public Human inputHuman() {
    while (true) {
      System.out.println("Введите имя и возраст мэра:");
      try {
        String name = variableValidator.validateGovernorName(scanner.nextLine().trim());
        Integer age = variableValidator.validateAge(scanner.nextLine().trim());
        return variableValidator.validateHuman(new Human(name, age));
      } catch (ValidateException e) {
        System.out.println("Введено неправильное значение для Human. Попробуйте ещё раз.");
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Конвертирует элементы enum в числовое представление
   *
   * @param enumClass enum
   * @return массив элементов enum
   */
  public <T extends Enum<T>> T convertEnumToInt(Class<T> enumClass) {
    int num = variableValidator.validateInt(scanner.nextLine().trim());
    T[] values = enumClass.getEnumConstants();
    if (num >= 1 && num <= values.length) {
      System.out.println("Выбрано: " + values[num - 1]);
      return values[num - 1];
    }
    throw new IllegalArgumentException("Введен неверный индекс. Попробуйте ещё раз.");
  }
}

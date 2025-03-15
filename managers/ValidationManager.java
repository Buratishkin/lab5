package managers;

import classes.City;
import classes.Coordinates;
import classes.Human;
import exceptions.ValidateException;
import java.time.LocalDate;

/** Менеджер для ограничения переменных */
public class ValidationManager {
  /**
   * Проверяет правильное заполнение города
   *
   * @param city город
   * @param cityManager менеджер коллекций
   * @return true, если все правильно, иначе false
   */
  public boolean isValidateCity(City city, CityManager cityManager) {
    return cityManager.getById(city.getId()) == null
        && isValidateCityName(city.getName())
        && isValidateCoordinates(city.getCoordinates())
        && isValidateCreationDate(city.getCreationDate())
        && isValidateArea(city.getArea())
        && isValidatePopulation(city.getPopulation())
        && isValidateEnum(city.getClimate())
        && isValidateEnum(city.getGovernment())
        && isValidateEnum(city.getStandardOfLiving())
        && isValidateHuman(city.getGovernor());
  }

  /**
   * Проверяет правильность имени города
   *
   * @param name имя
   * @return true, если правильное имя, иначе false
   */
  private boolean isValidateCityName(String name) {
    try {
      validateCityName(name);
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в имени города: " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность координат
   *
   * @param coordinates координаты
   * @return true, если правильно, иначе false
   */
  private boolean isValidateCoordinates(Coordinates coordinates) {
    try {
      validateCoordinatesX(Float.toString(coordinates.getX()));
      validateCoordinates(coordinates);
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в координатах: " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность даты создания
   *
   * @param localDate дата создания
   * @return true, если правильно, иначе false
   */
  private boolean isValidateCreationDate(LocalDate localDate) {
    try {
      validateLocalDate(localDate);
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в времени создания: " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность площади
   *
   * @param area площадь
   * @return true, если правильно, иначе false
   */
  private boolean isValidateArea(float area) {
    try {
      validateArea(Float.toString(area));
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в значении площади: " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность численности
   *
   * @param population численность
   * @return true, если правильно, иначе false
   */
  private boolean isValidatePopulation(int population) {
    try {
      validateArea(Integer.toString(population));
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в значении численности: " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность enum
   *
   * @param enumValue элемент enum
   * @return true, если правильно, иначе false
   */
  private <T extends Enum<T>> boolean isValidateEnum(T enumValue) {
    try {
      validateEnum(enumValue);
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в значении " + enumValue.getClass() + ": " + e.getMessage());
    }
    return false;
  }

  /**
   * Проверяет правильность человека=)
   *
   * @param human человек
   * @return true, если правильно, иначе false
   */
  private boolean isValidateHuman(Human human) {
    try {
      validateAge(Integer.toString(human.getAge()));
      validateGovernorName(human.getName());
      validateHuman(human);
      return true;
    } catch (ValidateException e) {
      System.out.println("Ошибка в: " + e.getMessage());
    }
    return false;
  }

  /**
   * Ограничения на имя Human: не может быть null или пустым
   *
   * @param name имя
   * @return имя, если пройдена валидация
   */
  public String validateGovernorName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidateException("Имя должно быть не пустым.");
    }
    return name;
  }

  /**
   * Ограничение на возраст Human: должен быть больше нуля
   *
   * @param num возраст
   * @return возраст, если пройдена валидация
   */
  public int validateAge(String num) {
    int age = validateInt(num);
    if (age <= 0) {
      throw new ValidateException("Возраст должен быть большим нуля.");
    }
    return age;
  }

  /**
   * Ограничение на координату х: не может быть больше 590
   *
   * @param num х
   * @return х, если пройдена валидация
   */
  public float validateCoordinatesX(String num) {
    float x = validateFloat(num);
    if (x > 590) {
      throw new ValidateException("Максимальное значение координаты x - 590.");
    }
    return x;
  }

  /**
   * Ограничения на название города: не может быть null или пустым
   *
   * @param name название города
   * @return название города, если пройдена валидация
   */
  public String validateCityName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidateException("Название города должно быть не пустым.");
    }
    return name;
  }

  /**
   * Ограничение на время создания: не может быть null.
   *
   * @param localDate время создания
   * @return время создания, если пройдена валидация
   */
  public LocalDate validateLocalDate(LocalDate localDate) {
    if (localDate == null) {
      throw new ValidateException("Значение не может быть равно null.");
    }
    return localDate;
  }

  /**
   * Ограничение на Coordinates: не может быть null.
   *
   * @param coordinates координаты
   * @return координаты, если пройдена валидация
   */
  public Coordinates validateCoordinates(Coordinates coordinates) {
    if (coordinates == null) {
      throw new ValidateException("Значение не может быть равно null");
    }
    return coordinates;
  }

  /**
   * Ограничения на площадь: не может быть null или должна быть больше нуля
   *
   * @param num площадь
   * @return площадь, если пройдена валидация
   */
  public Float validateArea(String num) {
    Float area = validateFloat(num);
    if (area <= 0) {
      throw new ValidateException("Численность должна быть больше нуля.");
    } else if (area == null) {
      throw new ValidateException("Значение не может быть равно null.");
    }
    return area;
  }

  /**
   * Ограничения на численность: не может быть null и должна быть больше нуля
   *
   * @param num численность
   * @return численность, если пройдена валидация
   */
  public Integer validatePopulation(String num) {
    Integer population = validateInt(num);
    if (population <= 0) {
      throw new ValidateException("Численность населения должна быть больше нуля.");
    } else if (population == null) {
      throw new ValidateException("Значение не может быть равно null.");
    }
    return population;
  }

  /**
   * Ограничение на Human: не может быть null
   *
   * @param governor человек
   * @return человека, если пройдена валидация
   */
  public Human validateHuman(Human governor) {
    if (governor == null) {
      throw new ValidateException("Значение не может быть равно null.");
    }
    return governor;
  }

  /**
   * Ограничения на элемент enum: не может быть null
   *
   * @param enumValue элемент enum
   * @return элемент enum, если пройдена валидация
   */
  public <T extends Enum<T>> T validateEnum(T enumValue) {
    if (enumValue == null) {
      throw new ValidateException("Значение не может быть равно null.");
    }
    return enumValue;
  }

  /**
   * Преобразует строку в число типа int
   *
   * @param num число
   * @return число типа int, если пройдена валидация
   */
  public int validateInt(String num) {
    try {
      return Integer.parseInt(num);
    } catch (Exception e) {
      throw new NumberFormatException("Введено не число типа int.");
    }
  }

  /**
   * Преобразует строку в число типа float
   *
   * @param num число
   * @return число типа float, если пройдена валидация
   */
  public float validateFloat(String num) {
    try {
      return Float.parseFloat(num);
    } catch (Exception e) {
      throw new NumberFormatException("Введено не число типа float.");
    }
  }

  /**
   * Преобразует строку в число типа long
   *
   * @param num число
   * @return число типа long, если пройдена валидация
   */
  public long validateLong(String num) {
    try {
      return Long.parseLong(num);
    } catch (Exception e) {
      throw new NumberFormatException("Введено не число типа long.");
    }
  }
}

package classes;

import java.io.Serializable;

/** Класс человека */
public class Human implements Serializable {
  private String name; // Поле не может быть null, Строка не может быть пустой
  private Integer age; // Значение поля должно быть меньше 0

  /**
   * Конструктор
   *
   * @param name имя
   * @param age возраст
   */
  public Human(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public Human() {}

  public String toString() {
    return String.format("[имя = %s, возраст = %d]", name, age);
  }

  /**
   * Возвращает возраст
   *
   * @return возраст
   */
  public Integer getAge() {
    return age;
  }

  /**
   * Возвращает имя
   *
   * @return имя
   */
  public String getName() {
    return name;
  }
}

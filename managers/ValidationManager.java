package managers;

import exceptions.ValidateException;
import java.util.List;
import java.util.function.BiPredicate;

public class ValidationManager {

  /**
   * Преобразует строку в число типа int
   *
   * @param num число
   * @param isNull может ли число быть null
   * @return число типа int, если пройдена валидация
   */
  public int validateInt(String num, boolean isNull) {
    emptyCheck(num);
    nullCheck(num, isNull);
    try {
      return Integer.parseInt(num);
    } catch (Exception e) {
      throw new ValidateException("Передано не число типа int.");
    }
  }

  /**
   * Преобразует строку в число типа float
   *
   * @param num число
   * @param isNull может ли число быть null
   * @return число типа float, если пройдена валидация
   */
  public float validateFloat(String num, boolean isNull) {
    nullCheck(num, isNull);
    emptyCheck(num);
    try {
      return Float.parseFloat(num);
    } catch (Exception e) {
      throw new ValidateException("Передано не число типа float.");
    }
  }

  /**
   * Проверяет, может ли объект быть null
   *
   * @param obj объект
   * @param isNull может ли число быть null
   * @return не null объект
   */
  public Object validateObject(Object obj, boolean isNull) {
    nullCheck(obj, isNull);
    return obj;
  }

  /**
   * Преобразует строку в число типа long
   *
   * @param num число
   * @param isNull может ли число быть null
   * @return число типа long, если пройдена валидация
   */
  public long validateLong(String num, boolean isNull) {
    emptyCheck(num);
    nullCheck(num, isNull);
    try {
      return Long.parseLong(num);
    } catch (Exception e) {
      throw new ValidateException("Передано не число типа long.");
    }
  }

  /**
   * Проверяет, строку на empty и null, если требуется
   *
   * @param str строка
   * @param isNull может ли быть null
   * @return строку, если пройдена проверка
   */
  public String validateString(String str, boolean isNull) {
    nullCheck(str, isNull);
    emptyCheck(str);
    return str;
  }

  /**
   * Проверяет, принадлежит ли элемент enum самому enum'у
   *
   * @param enumClass enum
   * @param numString номер элемента enum
   * @return элемент enum, если пройдена валидация
   */
  public <T extends Enum<T>> T validateEnum(Class<T> enumClass, String numString) {
    int num = validateInt(numString, false);
    T[] values = enumClass.getEnumConstants();
    if (num >= 1 && num <= values.length) {
      System.out.println("Выбрано: " + values[num - 1]);
      return values[num - 1];
    }
    throw new ValidateException("Введен неверный индекс. Попробуйте ещё раз.");
  }

  public boolean nullCheck(Object cls, boolean isNull) {
    if (!isNull && cls == null) {
      throw new ValidateException("Значение не может быть null.");
    }
    return true;
  }

  public void emptyCheck(String str) {
    if (str.isEmpty()) {
      throw new ValidateException("Передана пустая строка.");
    }
  }

  /**
   * Проверяет условие для числового типа
   *
   * @param value число, которое будем сравнивать
   * @param signCode код знака
   * @param compareTo число, с которым будем сравнивать
   * @return value, если условие было выполнено
   */
  public <T extends Number & Comparable<T>> T validateCondition(
      T value, int signCode, T compareTo) {
    List<BiPredicate<T, T>> conditions =
        List.of(
            (a, b) -> a.compareTo(b) == 0, // 0: "="
            (a, b) -> a.compareTo(b) > 0, // 1: ">"
            (a, b) -> a.compareTo(b) < 0, // 2: "<"
            (a, b) -> a.compareTo(b) >= 0, // 3: ">="
            (a, b) -> a.compareTo(b) <= 0, // 4: "<="
            (a, b) -> a.compareTo(b) != 0 // 5: "!="
            );

    if (signCode < 0 || signCode >= conditions.size()) {
      throw new ValidateException("Нет знака для кода: " + signCode);
    }

    BiPredicate<T, T> condition = conditions.get(signCode);

    if (!condition.test(value, compareTo)) {
      throw new ValidateException("Условие не выполнено для: " + value);
    }

    return value;
  }
}

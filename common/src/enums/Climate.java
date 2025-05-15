package enums;

import java.util.Arrays;

/** Enum для климата */
public enum Climate {
  RAIN_FOREST,
  HUMIDSUBTROPICAL,
  SUBARCTIC,
  DESERT;

  /**
   * Преобразовывает элементы enum в строку
   *
   * @return элементы enum
   */
  public static String valuesToString() {
    return Arrays.toString(Climate.values());
  }
}

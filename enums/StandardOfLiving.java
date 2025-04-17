package enums;

import java.util.Arrays;

/** Enum для уровня жизни */
public enum StandardOfLiving {
  MEDIUM,
  LOW,
  VERY_LOW,
  ULTRA_LOW,
  NIGHTMARE;

  /**
   * Преобразовывает элементы enum в строку
   *
   * @return элементы enum
   */
  public static String valuesToString() {
    return Arrays.toString(StandardOfLiving.values());
  }
}

package enums;

import java.util.Arrays;

/** Enum для правительства */
public enum Government {
  ANARCHY,
  KLEPTOCRACY,
  PLUTOCRACY,
  THALASSOCRACY;

  /**
   * Преобразовывает элементы enum в строку
   *
   * @return элементы enum
   */
  public static String valuesToString() {
    return Arrays.toString(Government.values());
  }
}

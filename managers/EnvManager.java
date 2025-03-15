package managers;

/** Менеджер для взаимодействия с переменной окружения */
public class EnvManager {
  private static String env = System.getenv("collection");

  /** Возвращает переменную окружения */
  private static boolean isEnv() {
    return env != null && !env.isEmpty();
  }

  public static void setEnv(String newEnv) {
    env = System.getenv(newEnv);
  }

  public static void manageEnv() {
    if (isEnv()) {
      System.out.println("Задана стандартная переменная окружения: " + getEnv());
    } else {
      System.out.println(
          "Не обнаружена стандартная переменная окружения с именем collection\n"
              + "Переменная окружения не задана. Выполнение программы невозможно.");
      System.exit(0);
    }
  }

  /**
   * Возвращает путь переменной окружения
   *
   * @return путь переменной окружения
   */
  public static String getEnv() {
    return env;
  }
}

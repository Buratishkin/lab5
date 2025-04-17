package managers;

import java.util.Scanner;

/** Менеджер для взаимодействия с переменной окружения */
public class EnvManager {
  private String env;
  private final Scanner scanner;

  public EnvManager(Scanner scanner) {
    this.scanner = scanner;
  }

  public void setEnv(String newEnv, String message) {
    try {
      env = System.getenv(newEnv);
      if (env == null) {
        System.out.println(message);
        manageEnv();
      }
    } catch (Exception e) {
      System.out.println("Файл не доступен для чтения");
    }
  }

  public void manageEnv() {
    System.out.println(
        "Хотите ли вы задать свою переменную-окружения? Введите true, если да; иначе введите что угодно");
    boolean isCustomEnv = Boolean.parseBoolean(scanner.nextLine().trim());
    if (isCustomEnv) {
      System.out.println("Напишите имя переменной-окружения:");
      String customEnv = scanner.nextLine().trim();
      setEnv(
          customEnv,
          "Не обнаружена переменная-окружения с именем " + customEnv + ". Попробуйте ещё раз");
      System.out.println("Задана переменная-окружения " + getEnv() + ".");
    } else {
      setEnv(
          "collection",
          "Не обнаружена стандартная переменная-окружения с именем collection\n"
              + "Вам нужно будет задать собственную переменную-окружения");
      System.out.println("Задана стандартная переменную-окружения collection");
    }
  }

  /**
   * Возвращает путь переменной окружения
   *
   * @return путь переменной окружения
   */
  public String getEnv() {
    return env;
  }
}

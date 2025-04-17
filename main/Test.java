package main;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Test {
  private static Scanner scanner = new Scanner(System.in);
  private static boolean eofTriggered = false;

  public static void main(String[] args) {
    while (true) {
      try {
        if (!eofTriggered) {
          System.out.print("Введите команду: ");
        }
        String input = readLine();
        if (!input.isEmpty()) {
          System.out.println("Вы ввели: " + input);
          eofTriggered = false;
        }
      } catch (Exception e) {
        System.out.println("Ошибка: " + e.getMessage());
      }
    }
  }

  private static String readLine() {
    try {
      String line = scanner.nextLine();
      eofTriggered = false;
      return line;
    } catch (NoSuchElementException e) {
      if (!eofTriggered) {
        System.out.println("\nОбнаружен EOF. Пересоздаю сканнер...");
        eofTriggered = true;
      }
      resetScanner();
      return "";
    }
  }

  private static void resetScanner() {
    scanner = new Scanner(System.in);
  }
}

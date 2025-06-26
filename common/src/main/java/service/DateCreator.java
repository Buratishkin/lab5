package service;

import java.time.LocalDate;

/** Класс для генерации даты */
public class DateCreator {
  public synchronized static LocalDate getDate() {
    return LocalDate.now();
  }
}

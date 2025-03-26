package commands;

import interfaces.Argumentable;
import interfaces.Elementable;

/** Абстрактный класс для всех команд. */
public abstract class AbstractCommand implements Command, Elementable, Argumentable {
  private final String name;
  private final String description;

  /**
   * Конструктор
   *
   * @param name название команды
   * @param description описание команды
   */
  AbstractCommand(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Возвращает имя
   *
   * @return имя
   */
  public String getName() {
    return name;
  }

  /**
   * Возвращает описание
   *
   * @return описание
   */
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.format("%s: %s", getName(), getDescription());
  }
}

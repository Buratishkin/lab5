package classes;

import java.util.Objects;

/** Класс координат */
public class Coordinates {
  private float x; // Максимальное значение поля: 590
  private Long y; // Поле не может быть null

  /**
   * Конструктор для координат
   *
   * @param x х
   * @param y у
   */
  public Coordinates(float x, Long y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates() {}

  /**
   * Возвращает координату х
   *
   * @return х
   */
  public float getX() {
    return x;
  }

  /**
   * Возвращает координату у
   *
   * @return у
   */
  public Long getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("[%s, %s]", x, y);
  }

  public int compareTo(Coordinates other) {
    int result = Float.compare(this.x, other.x);
    if (result != 0) {
      return result;
    }

    return Long.compare(this.y, other.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coordinates that = (Coordinates) obj;
    return Float.compare(that.x, x) == 0 && Objects.equals(y, that.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}

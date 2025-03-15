package city;
import service.ValidationService;
/**
 * Класс, представляющий координаты города.
 */
public class Coordinates {
    private final double x; // Максимальное значение поля: 36
    private final int y;

    public Coordinates(double x, int y) {
        this.x = ValidationService.validateX(x);
        this.y = y;
    }


    // Геттеры и сеттеры
    public double getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "city.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
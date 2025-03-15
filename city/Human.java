package city;
import service.ValidationService;
/**
 * Класс, представляющий человека (губернатора).
 */
public class Human {
    private final Float height; // Значение поля должно быть больше 0

    public Human(Float height) {
        this.height = ValidationService.validateHeight(height);
    }


    public float getHeight() {
        return height;
    }

    // Геттеры и сеттеры

    @Override
    public String toString() {
        return "city.Human{" +
                "height=" + height +
                '}';
    }
}
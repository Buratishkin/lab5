package city;

import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import util.DateUtils;
import util.IdGenerator;

import java.time.LocalDateTime;

import service.ValidationService;

/**
 * Класс, представляющий город.
 */
public class City implements Comparable<City> {
    private int id;
    private String name;
    private final Coordinates coordinates;
    private final LocalDateTime creationDate;
    private final Long area;
    private final Integer population;
    private final Float metersAboveSeaLevel;
    private final Climate climate;
    private final Government government;
    private final StandardOfLiving standardOfLiving;
    private final Human governor;

    public City(String name, Coordinates coordinates, Long area, Integer population,
                Float metersAboveSeaLevel, Climate climate, Government government,
                StandardOfLiving standardOfLiving, Human governor) {
        this.id = IdGenerator.getAndIncrement(); // Автоматически увеличиваем ID
        this.creationDate = DateUtils.getCurrentDateTime(); // Текущая дата и время создания
        this.name = ValidationService.validateName(name);
        this.coordinates = ValidationService.validateCoordinates(coordinates);
        this.area = ValidationService.validateArea(area);
        this.population = ValidationService.validatePopulation(population);
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = ValidationService.validateClimate(climate);
        this.government = ValidationService.validateGovernment(government);
        this.standardOfLiving = ValidationService.validateStandardOfLiving(standardOfLiving);
        this.governor = governor;
    }
    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Long getArea() { return area; }
    public Integer getPopulation() { return population; }
    public Float getMetersAboveSeaLevel() { return metersAboveSeaLevel; }
    public Climate getClimate() { return climate; }
    public Government getGovernment() { return government; }
    public StandardOfLiving getStandardOfLiving() { return standardOfLiving; }
    public Human getGovernor() { return governor; }
    // Сеттер для ID
    public void setId(int id) {
        this.id = id;
    }
    // Сеттер для имени
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int compareTo(City other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "city.City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", climate=" + climate +
                ", government=" + government +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor +
                '}';
    }
}

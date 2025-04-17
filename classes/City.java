package classes;

import enums.Climate;
import enums.Government;
import enums.StandardOfLiving;
import interfaces.Identifiable;
import java.time.LocalDate;
import java.util.Objects;

/** Класс города */
public class City implements Comparable<City>, Identifiable {
  private int id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
  // Значение этого поля должно генерироваться автоматически
  private String name; // Поле не может быть null, Строка не может быть пустой
  private Coordinates coordinates; // Поле не может быть null
  private java.time.LocalDate
      creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
  // автоматически
  private Float area; // Значение поля должно быть больше 0, Поле не может быть null
  private Integer population; // Значение поля должно быть больше 0, Поле не может быть null
  private float metersAboveSeaLevel;
  private Climate climate; // Поле не может быть null
  private Government government; // Поле не может быть null
  private StandardOfLiving standardOfLiving; // Поле не может быть null
  private Human governor; // Поле не может быть null

  /**
   * Конструктор
   *
   * @param name название города
   * @param coordinates координаты
   * @param area площадь
   * @param population численность
   * @param metersAboveSeaLevel высота над уровнем моря
   * @param climate климат
   * @param government правительство
   * @param standardOfLiving уровень жизни
   * @param governor мэр
   */
  public City(
      int id,
      String name,
      Coordinates coordinates,
      LocalDate localDate,
      Float area,
      Integer population,
      float metersAboveSeaLevel,
      Climate climate,
      Government government,
      StandardOfLiving standardOfLiving,
      Human governor) {
    this.id = id;
    this.name = name;
    this.coordinates = coordinates;
    this.creationDate = localDate;
    this.area = area;
    this.population = population;
    this.metersAboveSeaLevel = metersAboveSeaLevel;
    this.climate = climate;
    this.government = government;
    this.standardOfLiving = standardOfLiving;
    this.governor = governor;
  }

  public City() {}

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public Float getArea() {
    return area;
  }

  public Integer getPopulation() {
    return population;
  }

  public Climate getClimate() {
    return climate;
  }

  public float getMetersAboveSeaLevel() {
    return metersAboveSeaLevel;
  }

  public Government getGovernment() {
    return government;
  }

  public StandardOfLiving getStandardOfLiving() {
    return standardOfLiving;
  }

  public Human getGovernor() {
    return governor;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public void setArea(Float area) {
    this.area = area;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  public void setMetersAboveSeaLevel(float metersAboveSeaLevel) {
    this.metersAboveSeaLevel = metersAboveSeaLevel;
  }

  public void setClimate(Climate climate) {
    this.climate = climate;
  }

  public void setGovernment(Government government) {
    this.government = government;
  }

  public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
    this.standardOfLiving = standardOfLiving;
  }

  public void setGovernor(Human governor) {
    this.governor = governor;
  }

  @Override
  public int compareTo(City other) {
    int result = Integer.compare(this.population, other.population);
    if (result != 0) {
      return result;
    }

    return this.coordinates.compareTo(other.coordinates);
  }

  public String toString() {
    return String.format(
        "id = %s, название_города = %s, координаты = %s, дата_создания = %s, площадь = %s, численность = %s, высота_над_уровнем_моря = %s, климат = %s, правительство = %s, уровень_жизни = %s, мэр = %s ",
        id,
        name,
        coordinates.toString(),
        creationDate,
        area,
        population,
        metersAboveSeaLevel,
        climate.toString(),
        government.toString(),
        standardOfLiving.toString(),
        governor.toString());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    City city = (City) obj;
    return Objects.equals(population, city.population)
        && Objects.equals(coordinates, city.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(population, coordinates);
  }
}

package managers;

import classes.City;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.util.List;
import java.util.Scanner;

/** Менеджер для работы с файлами */
public class FileManager {
  private final String inputFileName;
  private final CityManager cityManager;
  private final ValidationManager validationManager = new ValidationManager();

  /**
   * Конструктор
   *
   * @param inputFileName имя файла
   * @param cityManager менеджер коллекций
   */
  public FileManager(String inputFileName, CityManager cityManager) {
    this.inputFileName = inputFileName;
    this.cityManager = cityManager;
  }

  /** Сохраняет коллекцию в файл */
  public void saveCollection() {
    try {
      XmlMapper mapper = new XmlMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      String xmlResult =
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cityManager.getCities());
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName))) {
        writer.write(xmlResult);
        writer.flush();
      }
    } catch (Exception e) {
      System.out.println("Ошибка сохранения коллекции в файл: " + e.getMessage() + ".");
    }
  }

  public void fillCollection() {
    File file = new File(inputFileName);
    if (!canRead(file)) {
      return;
    }

    try {
      XmlMapper xmlMapper = new XmlMapper();
      xmlMapper.registerModule(new JavaTimeModule());
      String xml = fileToStringUsingScanner(file);
      List<City> to_check = xmlMapper.readValue(xml, new TypeReference<>() {});

      for (var city : to_check) {
        if (validationManager.isValidateCity(city, cityManager)) {
          cityManager.addCity(city);
        } else {
          System.out.println("Город с id " + city.getId() + " не прошла валидацию.");
        }
      }
    } catch (Exception e) {
      System.out.println("Ошибка чтения файла: " + e.getMessage() + ".");
    }
  }

  /**
   * Преобразовать файл в строку с использованием Scanner
   *
   * @param file файл для чтения
   * @return строка с содержимым файла
   * @throws IOException ошибка ввода/вывода
   */
  public String fileToStringUsingScanner(File file) throws IOException {
    try (Scanner scanner = new Scanner(new FileReader(file))) {
      scanner.useDelimiter("\\Z"); // Читаем весь файл как одну строку
      return scanner.next();
    }
  }

  /**
   * Проверить, можно ли читать файл
   *
   * @param file файл
   * @return true, если файл можно читать, иначе false
   */
  public static boolean canRead(File file) {
    if (!file.exists()) {
      System.out.println("Файл не существует: " + file.getAbsolutePath() + ".");
      return false;
    }
    if (!file.canRead()) {
      System.out.println("Нет прав на чтение файла: " + file.getAbsolutePath() + ".");
      return false;
    }
    return true;
  }
}

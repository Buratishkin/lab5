package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.Identifiable;
import java.io.*;
import java.util.*;
import managers.CollectionManager;

/** Менеджер для работы с файлами */
public class FileManager<T extends Comparable<T> & Identifiable> {
  protected final String inputFileName;
  protected final CollectionManager<T> collectionManager;
  protected final ObjectMapper objectMapper;
  protected final Class<T> tClass;

  /**
   * Конструктор
   *
   * @param inputFileName имя файла
   */
  public FileManager(
      String inputFileName,
      CollectionManager<T> collectionManager,
      ObjectMapper objectMapper,
      Class<T> tClass) {
    this.inputFileName = inputFileName;
    this.collectionManager = collectionManager;
    this.objectMapper = objectMapper;
    this.tClass = tClass;
  }

  public List<Map<String, Object>> convertCollectionToList(TreeSet<T> collection) {
    List<Map<String, Object>> collectionList = new ArrayList<>();
    for (T item : collection) {
      collectionList.add(convertObjectToMap(item));
    }
    return collectionList;
  }

  public Map<String, Object> convertObjectToMap(T object) {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    return objectMapper.convertValue(object, Map.class);
  }

  public T convertMapToObject(Map<String, Object> objectMap) {
    try {
      objectMapper.registerModule(new JavaTimeModule());
      return objectMapper.convertValue(objectMap, tClass);
    } catch (Exception e) {
      System.out.println("Не получилось создать объект класса " + tClass + ":\n" + e.getMessage());
    }
    return null;
  }

  public void convertListToCollection(List<Map<String, Object>> collectionList) {
    for (Map<String, Object> objectMap : collectionList) {
      collectionManager.addElement(convertMapToObject(objectMap));
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

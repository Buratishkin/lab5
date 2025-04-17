package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.Identifiable;
import java.io.File;
import java.util.List;
import java.util.Map;
import managers.CollectionManager;

public class XMLReader<T extends Comparable<T> & Identifiable> extends FileManager<T>
    implements Reader {
  public XMLReader(
      String inputFileName,
      CollectionManager<T> collectionManager,
      ObjectMapper objectMapper,
      Class<T> tClass) {
    super(inputFileName, collectionManager, objectMapper, tClass);
  }

  public List<Map<String, Object>> readFromFile() {
    File file = new File(inputFileName);
    if (!canRead(file)) {
      return null;
    }
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.registerModule(new JavaTimeModule());
    xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    try {
      return xmlMapper.readValue(file, List.class);
    } catch (Exception e) {
      throw new RuntimeException("Ошибка при чтении XML-файла", e);
    }
  }
}

package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.Identifiable;
import managers.CollectionManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLWriter<T extends Comparable<T> & Identifiable> extends FileManager<T>
    implements Writer {
  public XMLWriter(
      String inputFileName,
      CollectionManager<T> collectionManager,
      ObjectMapper objectMapper,
      Class<T> tClass) {
    super(inputFileName, collectionManager, objectMapper, tClass);
  }

  public void writeToFile(List<Map<String, Object>> collectionList) {
    try {
      XmlMapper mapper = new XmlMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

      Map<String, Object> root = new HashMap<>();
      root.put("item", collectionList);

      String xml =
          mapper.writerWithDefaultPrettyPrinter().withRootName("TreeSet").writeValueAsString(root);

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName))) {
        writer.write(xml);
      }
    } catch (Exception e) {
      System.out.println("Ошибка сохранения: " + e.getMessage());
    }
  }
}

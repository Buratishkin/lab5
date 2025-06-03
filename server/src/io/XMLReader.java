package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.Identifiable;

import java.io.File;
import java.util.List;
import java.util.Map;

public class XMLReader<T extends Comparable<T> & Identifiable> extends FileManager<T>
    implements Reader {
  private final FileManager<T> fileManager;

  public XMLReader(FileManager<T> fileManager){
    super(fileManager.inputFileName, fileManager.collectionManager, fileManager.objectMapper, fileManager.tClass);
    this.fileManager = fileManager;
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

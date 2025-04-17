package io;

import java.util.List;
import java.util.Map;

public interface Writer {
  void writeToFile(List<Map<String, Object>> collectionList);
}

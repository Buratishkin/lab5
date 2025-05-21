package commands;

import interfaces.Identifiable;
import io.FileManager;
import io.Writer;
import managers.CollectionManager;

/** Сохраняет коллекцию в файл */
public class SaveCommand<T extends Comparable<T> & Identifiable> extends AbstractCommand {

  private final CollectionManager<T> collectionManager;
  private final Writer writer;
  private final FileManager<T> fileManager;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public SaveCommand(
      CollectionManager<T> collectionManager, FileManager<T> fileManager, Writer writer) {
    super("save", "Сохраняет коллекцию в файл.");
    this.collectionManager = collectionManager;
    this.fileManager = fileManager;
    this.writer = writer;
  }

  /**
   * Выполнение команды
   *
   * @param arg аргумент
   */
  @Override
  public String execute(String arg) {
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("Не передано имя файла.");
    }
    try {
      writer.writeToFile(fileManager.convertCollectionToList(collectionManager.getElements()));
    } catch (Exception e) {
      return ("Возникла ошибка: " + e.getMessage());
    }
    return "Коллекция сохранена в файл.";
  }

  @Override
  public boolean isElementable() {
    return false;
  }

  @Override
  public boolean isArgumentable() {
    return false;
  }
}

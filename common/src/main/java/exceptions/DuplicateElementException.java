package exceptions;

/** Ошибка, возникающая при добавлении существующего элемента в коллекцию */
public class DuplicateElementException extends RuntimeException {
  public DuplicateElementException(String message) {
    super(message);
  }
}

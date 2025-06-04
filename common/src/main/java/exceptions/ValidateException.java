package exceptions;

/** Ошибка, возникающая при валидации переменной */
public class ValidateException extends RuntimeException {
  public ValidateException(String message) {
    super(message);
  }
}

package exceptions;

/** Ошибка, возникающая при работе с командой */
public class CommandException extends RuntimeException {
  public CommandException(String message) {
    super(message);
  }
}

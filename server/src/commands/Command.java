package commands;

import java.io.IOException;

/** Интерфейс для выполнения команд */
public interface Command {
  void execute(String arg) throws IOException;
}

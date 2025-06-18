package commands;

import java.io.IOException;

/** Интерфейс для выполнения команд */
public interface Command {
  String execute(String arg) throws IOException;
}

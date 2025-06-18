package commands;

import java.io.IOException;
import network.Request;

/** Интерфейс для выполнения команд */
public interface Command {
  String execute(Request request) throws IOException;
}

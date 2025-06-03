package commands;

import network.Request;

import java.io.IOException;

/** Интерфейс для выполнения команд */
public interface Command {
  String execute(Request request) throws IOException;
}

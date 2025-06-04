package network;

import classes.City;
import java.io.Serializable;

public class Request implements Serializable {
  private final String commandName;
  private final String arg;
  private final City city;

  public Request(String commandName, String arg, City city) {
    this.commandName = commandName;
    this.arg = arg;
    this.city = city;
  }

  public String getCommandName() {
    return commandName;
  }

  public String getArgument() {
    return arg;
  }

  public City getCity() {
    return city;
  }

  @Override
  public String toString() {
    return city == null
        ? "команда: " + commandName + " | аргументы: " + arg + " | город: null"
        : "команда: " + commandName + " | аргументы: " + arg + " | город: " + city.toString();
  }
}

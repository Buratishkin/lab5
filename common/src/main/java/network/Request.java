package network;

import classes.City;
import manager.PasswordManager;

import java.io.Serializable;

public class Request implements Serializable {
  private final String commandName;
  private final String arg;
  private final City city;
  private final String userName;
  private final String password;
  private final String salt;

  public Request(String commandName, String arg, City city, String userName, String password) {
    this.commandName = commandName;
    this.arg = arg;
    this.city = city;
    this.userName = userName;
    this.salt = PasswordManager.getSalt();
    this.password = PasswordManager.hash(password, salt);
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

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return city == null
        ? "команда: "
            + commandName
            + " | аргументы: "
            + arg
            + " | город: null"
            + "| пользователь: "
            + userName
        : "команда: "
            + commandName
            + " | аргументы: "
            + arg
            + " | город: "
            + city
            + "| пользователь: "
            + userName;
  }

  public String getSalt() {
    return salt;
  }
}

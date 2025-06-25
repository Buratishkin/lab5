package commands;

import database.UserDAO;
import manager.PasswordManager;
import network.Request;

public class LogInCommand implements AuthorizationCommand {
  private final UserDAO userDAO;

  public LogInCommand(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public String execute(Request request) throws IllegalArgumentException {
    String userName = request.getUserName();
    String password = request.getPassword();

    try {
      String[] user = userDAO.logIn(userName, password);
      if (PasswordManager.checkPassword(password, user[0], user[1])) return "Вход выполнен успешно";
      else throw new IllegalArgumentException("Введен неправильный пароль");
    } catch (Exception e) {
      throw new IllegalArgumentException("При входе возникла ошибка: " + e.getMessage());
    }
  }
}
package commands;

import database.UserDAO;
import manager.PasswordManager;
import network.Request;

public class RegistrationCommand implements AuthorizationCommand {
  private final UserDAO userDAO;

  public RegistrationCommand(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public String execute(Request request) throws IllegalArgumentException {
    String userName = request.getUserName();
    String password = request.getPassword();

    String salt = PasswordManager.getSalt();
    String hash = PasswordManager.hash(password, salt);

    try {
      userDAO.registration(userName, hash, salt);
      return "Регистрация завершена";
    } catch (Exception e) {
      throw new IllegalArgumentException("При регистрации возникла ошибка: " + e.getMessage());
    }
  }
}
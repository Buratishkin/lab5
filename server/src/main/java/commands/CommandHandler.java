package commands;

import database.UserDAO;
import exceptions.CommandException;
import interfaces.Identifiable;
import java.time.LocalDateTime;
import managers.CollectionManager;
import managers.CommandManager;
import network.Request;
import network.Response;

/** Класс для работы с командами */
public class CommandHandler<T extends Comparable<T> & Identifiable> {
  private final CommandManager commandManager;
  private final CollectionManager<T> collectionManager;
  private final UserDAO psqlUserDAO;

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public CommandHandler(
      CollectionManager<T> collectionManager, CommandManager commandManager, UserDAO userDAO) {
    this.collectionManager = collectionManager;
    this.commandManager = commandManager;
    this.psqlUserDAO = userDAO;
  }

  /**
   * Определение команды и её выполнение
   *
   * @param request команда из скрипта или пустая строка
   */
  public Response run(Request request) {
    String answerLine = "";
    try {
      if (request.getCommandName().isEmpty()) {
        throw new IllegalArgumentException("Вы ничего не ввели. Попробуйте ещё раз");
      }

      if (commandManager.getServerCommand(request.getCommandName())
          instanceof AuthorizationCommand) {
        try {
          AuthorizationCommand command =
              (AuthorizationCommand) commandManager.getServerCommand(request.getCommandName());
          answerLine = command.execute(request);
        } catch (Exception e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      } else if (commandManager.getCommands().containsKey(request.getCommandName())) {
        AbstractCommand currentCommand = commandManager.getCommands().get(request.getCommandName());

        if (currentCommand.isElementable() || request.getCommandName().contains("remove")) {
          collectionManager.setUpdateDateTime(LocalDateTime.now());
        }

        if (currentCommand.isArgumentable()
            && (request.getArgument() == null || request.getArgument().isEmpty())) {
          throw new CommandException("Не передан аргумент для команды");
        } else answerLine = currentCommand.execute(request);

      } else {
        throw new IllegalArgumentException(
            "Команды \""
                + request.getCommandName()
                + "\" не существует. Попробуйте ещё раз.\nЧтобы посмотреть список команд, напишите help");
      }
      return new Response(true, answerLine);
    } catch (Exception e) {
      return new Response(false, e.getMessage());
    }
  }
}

package commands;

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

  /**
   * Конструктор
   *
   * @param collectionManager менеджер коллекций
   */
  public CommandHandler(CollectionManager<T> collectionManager, CommandManager commandManager) {
    this.collectionManager = collectionManager;
    this.commandManager = commandManager;
  }

  /**
   * Определение команды и её выполнение
   *
   * @param request команда из скрипта или пустая строка
   */
  public Response run(Request request) {
    try {
      if (request.getCommandName().isEmpty()) {
        throw new IllegalArgumentException("Вы ничего не ввели. Попробуйте ещё раз");
      }

      if (commandManager.getCommands().containsKey(request.getCommandName())) {
        AbstractCommand currentCommand = commandManager.getCommands().get(request.getCommandName());

        if (currentCommand.isElementable() || request.getCommandName().contains("remove")) {
          collectionManager.setUpdateDateTime(LocalDateTime.now());
        }

        String answerLine;
        if (currentCommand.isArgumentable()
            && (request.getArgument() == null || request.getArgument().isEmpty())) {
          throw new CommandException("Не передан аргумент для команды");
        } else answerLine = currentCommand.execute(request);
        return new Response(true, answerLine);

      } else {
        throw new IllegalArgumentException(
            "Команды \""
                + request.getCommandName()
                + "\" не существует. Попробуйте ещё раз.\nЧтобы посмотреть список команд, напишите help");
      }
    } catch (Exception e) {
      return new Response(false, e.getMessage());
    }
  }
}

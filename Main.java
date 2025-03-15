import commands.*;
import managers.CityManager;
import managers.CommandManager;
import managers.EnvManager;
import managers.FileManager;
import service.CommandHandler;
import service.IdCreator;

public class Main {
  public static void main(String[] args) {
    EnvManager.manageEnv();

    CityManager cityManager = new CityManager();
    FileManager fIleManager = new FileManager(EnvManager.getEnv(), cityManager);
    fIleManager.fillCollection();

    IdCreator idCreator = new IdCreator(cityManager);
    idCreator.addFreeId(cityManager);

    CommandHandler commandHandler = new CommandHandler(cityManager);
    CommandManager commandManager = new CommandManager();

    commandManager.addInCommands("add", new AddCommand(cityManager));
    commandManager.addInCommands("clear", new ClearCommand(cityManager));
    commandManager.addInCommands(
        "count_less_than_meters_above_sea_level",
        new CountLessThanMetersAboveSeaLevelCommand(cityManager));
    commandManager.addInCommands("exit", new ExitCommand());
    commandManager.addInCommands(
        "execute_script", new ExecuteScriptCommand(commandManager, commandHandler));
    commandManager.addInCommands(
        "filter_contains_name", new FilterContainsNameCommand(cityManager));
    commandManager.addInCommands(
        "group_counting_by_name", new GroupCountingByNameCommand(cityManager));
    commandManager.addInCommands("help", new HelpCommand(commandManager));
    commandManager.addInCommands("history", new HistoryCommand());
    commandManager.addInCommands("info", new InfoCommand(cityManager));
    commandManager.addInCommands("remove_by_id", new RemoveByIdCommand(cityManager));
    commandManager.addInCommands("remove_greater", new RemoveGreaterCommand(cityManager));
    commandManager.addInCommands("remove_lower", new RemoveLowerCommand(cityManager));
    commandManager.addInCommands("save", new SaveCommand(cityManager));
    commandManager.addInCommands("show", new ShowCommand(cityManager));
    commandManager.addInCommands("update", new UpdateCommand(cityManager));

    while (true) {
      commandHandler.run(commandManager, "");
    }
  }
}

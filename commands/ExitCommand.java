package commands;

/**
 * Команда для завершения программы.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Завершение программы.");
        System.exit(0);
    }
}
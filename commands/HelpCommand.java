package commands;

/**
 * Команда для вывода справки по доступным командам.
 */
public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Доступные команды:");
        System.out.println("help - вывести справку по командам");
        System.out.println("info - вывести информацию о коллекции");
        System.out.println("show - вывести все элементы коллекции");
        System.out.println("insert null {element} - добавить новый элемент");
        System.out.println("update id {element} - обновить элемент по id");
        System.out.println("remove_key null - удалить элемент по ключу");
        System.out.println("clear - очистить коллекцию");
        System.out.println("save - сохранить коллекцию в файл");
        System.out.println("execute_script file_name - выполнить скрипт из файла");
        System.out.println("exit - завершить программу");
        System.out.println("remove_greater {element} - удалить элементы, превышающие заданный");
        System.out.println("replace_if_lowe null {element} - заменить значение, если новое меньше старого");
        System.out.println("remove_greater_key null - удалить элементы с ключами больше заданного");
        System.out.println("group_counting_by_id - сгруппировать элементы по id");
        System.out.println("filter_starts_with_name name - вывести элементы, название которых начинается с подстроки");
        System.out.println("print_ascending - вывести элементы в порядке возрастания");
    }
}
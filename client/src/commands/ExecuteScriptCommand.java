package commands;

import service.ColorConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ExecuteScriptCommand{
    public LinkedList<String> execute(String[] parts) throws IOException {
        if (parts.length < 2) throw new IllegalArgumentException("Не передано название файла");

        LinkedList<String> queueScriptCommand = new LinkedList<>();

        String fileName = parts[1];
        Path path = Paths.get(fileName);
        canRead(path);

        try {
            List<String> lines = Files.readAllLines(path);
            queueScriptCommand.addAll(lines);
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }

        System.out.println(ColorConsole.PURPLE + "Команды в скрипте:");
        for (String command : queueScriptCommand){
            System.out.println(command);
        }
        System.out.print(ColorConsole.RESET);
        return queueScriptCommand;
    }

    public void canRead(Path path) throws NoSuchFileException, FileNotFoundException{
        if (!Files.exists(path)) {
            throw new NoSuchFileException("Файл " + path.getFileName() + " не существует");
        }
        if (!Files.isReadable(path)) {
            throw new FileNotFoundException("Нет прав на чтение файла " + path.getFileName());
        }
    }

    public void getHelp() {
        System.out.println(
                "Структура скрипта:"
                        + "\n    Каждая команда начинается с новой строки. Для получения списка существующих команд напишите help."
                        + "\n    Если команда добавляет элемент в коллекцию, то перед значениями полей должно быть 4 пробела."
                        + "\n    Если значение полей меньше количества, нужных для создания класса, то недостающие поля нужно будет вводить вручную."
                        + "\n    Если больше - лишние поля просто отбрасываются."
                        + "\nЕсли возникает рекурсия - выполнение скрипта прекращается.");
    }
}

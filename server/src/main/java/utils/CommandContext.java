package utils;

import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class CommandContext {
    private Consumer<String> nextHandler; // Обработчик следующего ввода
    private SocketChannel clientChannel;
    private String commandName;

    public CommandContext(SocketChannel clientChannel, String commandName) {
        this.clientChannel = clientChannel;
        this.commandName = commandName;
    }

    public void setNextHandler(Consumer<String> nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleInput(String input) {
        if (nextHandler != null) {
            nextHandler.accept(input);
        }
    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }

    public String getCommandName() {
        return commandName;
    }
}
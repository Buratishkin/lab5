package client;

import commands.CommandHandler;
import commands.HistoryCommand;
import network.Request;
import network.Response;
import service.ColorConsole;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestClient {
    private final int PORT = 234;
    private final HistoryCommand historyCommand = new HistoryCommand();
    private final int BUFFER_SIZE = 65536;
    private final String HOST = "localhost";
    private String currentCommand = "";
    private boolean isAccess = false;
    private final Random rand = new Random();
    private SocketChannel socketChannel;
    private CommandHandler commandHandler = new CommandHandler(new Scanner(System.in), historyCommand);

    public static void main(String[] args) {
        new TestClient().connect();
    }

    public void connect() {
        try {
            socketChannel = SocketChannel.open();
            int random = rand.nextInt(10) + 1;
            socketChannel.connect(new InetSocketAddress(HOST, PORT));
            System.out.println("Подключено к серверу");
            System.out.println("Номер скрипта: " + random);

            while (!isAccess) {
                isAccess = authorize(commandHandler, socketChannel);
            }

            List<Request> requests = new ArrayList<>();
            try {
                requests = commandHandler.executeScript(random);
            } catch (Exception e) {
                System.out.println(
                        ColorConsole.RED
                                + "При создании запроса произошла ошибка: "
                                + e.getMessage()
                                + ColorConsole.RESET);
            }
            for (Request request : requests) {
                currentCommand = request.getCommandName();
                if (currentCommand.equals("history")) continue;

                System.out.println(
                        ColorConsole.YELLOW + "Отправка запроса: " + request + ColorConsole.RESET);

                sendRequest(request);

                Response response = receiveResponse();
                printResponse(response, request.getCommandName());
            }

        } catch (ConnectException e) {
            System.err.println("Сервер не запущен");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(request);
        }
        byte[] requestData = baos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(requestData);
        socketChannel.write(buffer);
    }

    public Response receiveResponse()
            throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        socketChannel.read(buffer);
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Response) ois.readObject();
        }
    }

    private void printResponse(Response response, String commandName) {
        if (response.isSuccess()) {
            System.out.println(
                    ColorConsole.GREEN
                            + "Результат выполнения команды "
                            + commandName
                            + ":\n"
                            + ColorConsole.RESET
                            + response.getMessage());
            historyCommand.addInHistory(currentCommand);
        } else {
            System.out.println(
                    ColorConsole.RED
                            + "При выполнении команды "
                            + commandName
                            + " возникла ошибка: "
                            + response.getMessage()
                            + ColorConsole.RESET);
        }
    }

    private boolean authorize(CommandHandler commandHandler, SocketChannel socketChannel) {
        try {
            String username = "qw";
            String password = "qw";
            commandHandler.setUserName(username);
            commandHandler.setPassword(password);
            List<Request> requests = new ArrayList<>();
            String[] parts = {"login"};
            commandHandler.createRequest(parts, requests, true, null);
            Request request = requests.get(0);

            sendRequest(request);
            Response response = receiveResponse();
            printResponse(response, request.getCommandName());

            return response.isSuccess();
        } catch (Exception e) {
            System.out.println("При авторизации возникла ошибка: " + e.getMessage());
            return false;
        }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}

package client;

import commands.CommandHandler;
import commands.HistoryCommand;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Scanner;

import manager.ValidationManager;
import network.Request;
import network.Response;
import service.ColorConsole;

public class Client {
  private static final String HOST = "localhost";
  private static int PORT;
  private static final int BUFFER_SIZE = 65536;
  private static final HistoryCommand historyCommand = new HistoryCommand();
  private static String currentCommand = "";
  private static final ValidationManager validationManager = new ValidationManager();

  public static void main(String[] args) {
    try (SocketChannel socketChannel = SocketChannel.open();
        Scanner scanner = new Scanner(System.in)) {
      while (PORT <= 0){
        try{
          System.out.println("Введите порт:");
          PORT = validationManager.validateInt(scanner.nextLine(), false);
        } catch (Exception e){
          System.out.println("Порт введен не верно: " + e.getMessage());
        }
      }
      CommandHandler commandHandler = new CommandHandler(scanner);
      socketChannel.connect(new InetSocketAddress(HOST, PORT));
      System.out.println("Подключено к серверу");

      while (true) {
        // Создаем запрос
        List<Request> requests;
        try {
          requests = commandHandler.run();
        } catch (Exception e) {
          System.out.println(
              ColorConsole.RED
                  + "При создании запроса произошла ошибка: "
                  + e.getMessage()
                  + ColorConsole.RESET);
          continue;
        }
        for (Request request : requests) {
          currentCommand = request.getCommandName();
          if (currentCommand.equals("history")) continue;

          System.out.println(
              ColorConsole.YELLOW + "Отправка запроса: " + request + ColorConsole.RESET);

          // Отправляем запрос
          sendRequest(socketChannel, request);

          // Получаем ответ
          Response response = receiveResponse(socketChannel);
          printResponse(response, request.getCommandName());
        }
      }
    } catch (ConnectException e) {
      System.err.println("Сервер не запущен");
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Ошибка: " + e.getMessage());
    }
  }

  private static void sendRequest(SocketChannel channel, Request request) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(request);
    }
    byte[] requestData = baos.toByteArray();
    ByteBuffer buffer = ByteBuffer.wrap(requestData);
    channel.write(buffer);
  }

  private static Response receiveResponse(SocketChannel channel)
      throws IOException, ClassNotFoundException {
    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    channel.read(buffer);
    buffer.flip();
    byte[] data = new byte[buffer.remaining()];
    buffer.get(data);

    try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais)) {
      return (Response) ois.readObject();
    }
  }

  private static void printResponse(Response response, String commandName) {
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
}

package server;

import classes.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.*;
import io.FileManager;
import io.XMLReader;
import io.XMLWriter;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import manager.ValidationManager;
import managers.CollectionManager;
import managers.CommandManager;
import managers.EnvManager;
import network.Request;
import network.Response;
import service.ColorConsole;
import service.IdCreator;

public class Server {
  private static int PORT;
  private static final int BUFFER_SIZE = 65536;
  private static CollectionManager<City> collectionManager;
  private CommandHandler<City> commandHandler;
  private CommandManager commandManager;
  private static XMLWriter<City> writer;
  private ValidationManager validationManager = new ValidationManager();

  public static void main(String[] args) {
    new Server().start();
  }

  public void start() {
    try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        Scanner scanner = new Scanner(System.in)) {

      while (PORT <= 0){
        try{
          System.out.println("Введите порт:");
          PORT = validationManager.validateInt(scanner.nextLine(), false);
        } catch (Exception e){
          System.out.println("Порт введен не верно: " + e.getMessage());
        }
      }

      serverChannel.bind(new InetSocketAddress(PORT));
      serverChannel.configureBlocking(false);
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);

      writer = startProcess();
      System.out.println("Сервер запущен на порту " + PORT);

      while (true) {
        selector.select(); // Блокируемся до готовности каналов
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> iter = selectedKeys.iterator();

        while (iter.hasNext()) {
          SelectionKey key = iter.next();
          iter.remove();

          if (key.isAcceptable()) {
            acceptClient(serverChannel, selector);
          } else if (key.isReadable()) {
            readFromClient(key);
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void acceptClient(ServerSocketChannel serverChannel, Selector selector)
      throws IOException {
    SocketChannel clientChannel = serverChannel.accept();
    clientChannel.configureBlocking(false);
    clientChannel.register(selector, SelectionKey.OP_READ);
    System.out.println("Подключен клиент: " + clientChannel.getRemoteAddress());
  }

  private void readFromClient(SelectionKey key) {
    SocketChannel channel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

    try {
      int bytesRead = channel.read(buffer);
      if (bytesRead == -1) {
        System.out.println("Клиент отключился: " + channel.getRemoteAddress());
        channel.close();
        return;
      }

      if (bytesRead > 0) {
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        Request request = deserializeRequest(data);
        System.out.println(ColorConsole.YELLOW + "Получен запрос: " + request + ColorConsole.RESET);

        Response response = commandHandler.run(request);

        sendResponse(channel, response);
        if (response.isSuccess())
          System.out.println(
              ColorConsole.GREEN
                  + "Команда выполнена, ответ отправлен клиенту"
                  + ColorConsole.RESET);
        else
          System.out.println(
              ColorConsole.RED
                  + "Во время выполнения команды возникла ошибка, ответ отправлен клиенту"
                  + ColorConsole.RESET);
      }
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Ошибка обработки запроса: " + e.getMessage());
      closeChannel(channel);
    }
  }

  private Request deserializeRequest(byte[] data) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais)) {
      return (Request) ois.readObject();
    }
  }

  private void sendResponse(SocketChannel channel, Response response) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(response);
    }
    byte[] responseData = baos.toByteArray();
    ByteBuffer buffer = ByteBuffer.wrap(responseData);
    channel.write(buffer);
  }

  private static void closeChannel(SocketChannel channel) {
    try {
      if (channel != null) {
        writer.writeToFile(
            writer.getFileManager().convertCollectionToList(collectionManager.getElements()));
        channel.close();
      }
    } catch (IOException e) {
      System.err.println("Ошибка при закрытии канала: " + e.getMessage());
    }
  }

  private XMLWriter<City> startProcess() {
    commandManager = new CommandManager();
    collectionManager = new CollectionManager<>();
    commandHandler = new CommandHandler<>(collectionManager, commandManager);
    ValidationManager validationManager = new ValidationManager();
    EnvManager envManager = new EnvManager();

    ObjectMapper objectMapper = new ObjectMapper();
    FileManager<City> fIleManager =
        new FileManager<>(
            System.getenv(envManager.getEnv()), collectionManager, objectMapper, City.class);

    XMLWriter<City> writer = new XMLWriter<>(fIleManager);
    XMLReader<City> reader = new XMLReader<>(fIleManager);

    fIleManager.convertListToCollection(reader.readFromFile());

    IdCreator<City> idCreator = new IdCreator<>(collectionManager);
    idCreator.addFreeId();

    commandManager.addInCommands("add", new AddCommand<>(collectionManager));
    commandManager.addInCommands("clear", new ClearCommand<>(collectionManager, idCreator));
    commandManager.addInCommands(
        "count_less_than_meters_above_sea_level",
        new CountLessThanMetersAboveSeaLevelCommand<>(collectionManager, validationManager));
    commandManager.addInCommands(
        "filter_contains_name", new FilterContainsNameCommand<>(collectionManager));
    commandManager.addInCommands(
        "group_counting_by_name", new GroupCountingByNameCommand<>(collectionManager));
    commandManager.addInCommands("help", new HelpCommand(commandManager));
    commandManager.addInCommands("info", new InfoCommand<>(collectionManager));
    commandManager.addInCommands(
        "remove_by_id", new RemoveByIdCommand<>(collectionManager, idCreator));
    commandManager.addInCommands(
        "remove_greater", new RemoveGreaterCommand<>(collectionManager, commandManager));
    commandManager.addInCommands(
        "remove_lower", new RemoveLowerCommand<>(collectionManager, commandManager));
    commandManager.addInCommands("show", new ShowCommand<>(collectionManager));
    commandManager.addInCommands("update", new UpdateCommand<>(collectionManager));
    return writer;
  }
}

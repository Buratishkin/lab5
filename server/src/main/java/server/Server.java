package server;

import classes.City;
import commands.*;
import database.CollectionDAO;
import database.PSQLCollectionDAO;
import database.PSQUserDAO;
import database.UserDAO;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;
import manager.ValidationManager;
import managers.CollectionManager;
import managers.CommandManager;
import network.Request;
import network.Response;
import service.ColorConsole;

public class Server {
  private static int PORT;
  private static final int BUFFER_SIZE = 65536;
  private static CollectionManager<City> collectionManager;
  private CommandHandler<City> commandHandler;
  private CommandManager commandManager;
  private ValidationManager validationManager = new ValidationManager();
  private boolean running;
  private ServerSocketChannel serverChannel;
  private Selector selector;

  private final ForkJoinPool readPool = new ForkJoinPool();
  private final ExecutorService processingPool = Executors.newCachedThreadPool();
  private final ForkJoinPool writePool = new ForkJoinPool();

  public static void main(String[] args) {
    new Server().start();
  }

  public void start() {
    try (Scanner scanner = new Scanner(System.in)) {

      while (PORT <= 0) {
        try {
          System.out.println("Введите порт:");
          PORT = validationManager.validateInt(scanner.nextLine(), false);
        } catch (Exception e) {
          System.out.println("Порт введен не верно: " + e.getMessage());
        }
      }

      serverChannel = ServerSocketChannel.open();
      selector = Selector.open();

      serverChannel.bind(new InetSocketAddress(PORT));
      serverChannel.configureBlocking(false);
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);

      startProcess();
      System.out.println("Сервер запущен на порту " + PORT);
      running = true;

      new Thread(this::selectorLoop).start();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void selectorLoop() {
    while (running) {
      try {
        selector.select();
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> iter = selectedKeys.iterator();
        while (iter.hasNext()) {
          SelectionKey key = iter.next();
          iter.remove(); // Критически важно!

          if (!key.isValid()) continue;

          if (key.isAcceptable()) {
            acceptClient();
          }

          if (key.isReadable()) {
            readPool.execute(() -> readFromClient(key));
          }
        }
      } catch (Exception e) {
        System.out.println("Возникла ошибка при принятии соединения: " + e.getMessage());
        running = false;
      }
    }
  }

  private void acceptClient() throws IOException {
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

        processingPool.execute(() -> executeRequest(channel, request));
      }
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Ошибка обработки запроса: " + e.getMessage());
      closeChannel(channel);
    }
  }

  private void executeRequest(SocketChannel channel, Request request) {
    Response response = commandHandler.run(request);
    writePool.execute(() -> sendResponse(channel, response));

  }

  private Request deserializeRequest(byte[] data) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais)) {
      return (Request) ois.readObject();
    }
  }

  private void sendResponse(SocketChannel channel, Response response) {
    if (response.isSuccess())
      System.out.println(
          ColorConsole.GREEN + "Команда выполнена, ответ отправлен клиенту" + ColorConsole.RESET);
    else
      System.out.println(
          ColorConsole.RED
              + "Во время выполнения команды возникла ошибка, ответ отправлен клиенту"
              + ColorConsole.RESET);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(response);

      byte[] responseData = baos.toByteArray();
      ByteBuffer buffer = ByteBuffer.wrap(responseData);
      channel.write(buffer);
    } catch (IOException e) {
      throw new RuntimeException("Во время отправки ответа возникла ошибка: " + e.getMessage());
    }
  }

  private static void closeChannel(SocketChannel channel) {
    try {
      if (channel != null) {
        channel.close();
      }
    } catch (IOException e) {
      System.err.println("Ошибка при закрытии канала: " + e.getMessage());
    }
  }

  private void startProcess() {
    commandManager = new CommandManager();
    collectionManager = new CollectionManager<>();
    CollectionDAO psqlCollectionDAO = new PSQLCollectionDAO();
    UserDAO psqlUserDAO = new PSQUserDAO();
    commandHandler = new CommandHandler<>(collectionManager, commandManager, psqlUserDAO);
    ValidationManager validationManager = new ValidationManager();

    psqlCollectionDAO.getCities().stream().forEach(collectionManager::addElement);

    commandManager.addInCommands(
        "add", new AddCommand<>(collectionManager, psqlCollectionDAO, psqlUserDAO));
    commandManager.addInCommands("clear", new ClearCommand<>(collectionManager, psqlCollectionDAO));
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
        "remove_by_id",
        new RemoveByIdCommand<>(collectionManager, validationManager, psqlCollectionDAO));
    commandManager.addInCommands(
        "remove_greater", new RemoveGreaterCommand<>(collectionManager, commandManager));
    commandManager.addInCommands(
        "remove_lower", new RemoveLowerCommand<>(collectionManager, commandManager));
    commandManager.addInCommands("show", new ShowCommand<>(collectionManager));
    commandManager.addInCommands(
        "update", new UpdateCommand<>(collectionManager, validationManager, psqlCollectionDAO));
    commandManager.addInServerCommands("registration", new RegistrationCommand(psqlUserDAO));
    commandManager.addInServerCommands("login", new LogInCommand(psqlUserDAO));
  }
}

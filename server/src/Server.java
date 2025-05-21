import utils.Console;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static final int BUFFER_SIZE = 1024;
    private static final int PORT = 6789;
    private static int clientCounter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(PORT));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port " + PORT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    handleAccept(key, selector);
                } else if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Client connected: " + clientChannel.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            try {
                int bytesRead = clientChannel.read(buffer);

                if (bytesRead == -1) {
                    System.out.println("Клиент отключился");
                    clientChannel.close();
                    return;
                }

                buffer.flip();
                String command = StandardCharsets.UTF_8.decode(buffer).toString().trim();

                System.out.println("Получено от клиента: " + command);

                String[] args = new String[1];
                HelpClass.main(args);
                try {
                    HelpClass.getCommandHandler().setMode(false);
                    String line = HelpClass.getCommandHandler().run(command);
                    responseToClient(clientChannel, line);
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("Потеря соединения с клиентом");
                clientChannel.close();
            }
    }

    private static void responseToClient(SocketChannel clientChanel, String line) throws IOException {
        ByteBuffer responseBuffer = StandardCharsets.UTF_8.encode(line + "\n");

        while (responseBuffer.hasRemaining()) {
            clientChanel.write(responseBuffer);
        }
        System.out.println("Данные отправлены клиенту");
    }
}
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    private static final int READ_TIMEOUT = 1000;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 6789);
            Scanner scanner = new Scanner(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            socket.setSoTimeout(READ_TIMEOUT);
            System.out.println("Подключено к серверу. Чтобы получить доступные команды, введите \"help\"");

            while (true) {
                System.out.print("Введите команду: ");
                String command = scanner.nextLine();

                out.write(command + "\n");
                out.flush();

                try {
                    while (true) {
                        String response = in.readLine();
                        if (response == null) {
                            System.out.println("Соединение с сервером закрыто");
                            return;
                        }
                        System.out.println(response);
                    }
                } catch (SocketTimeoutException e) {
                    System.out.print("");
                }
            }
        } catch (IOException e) {
            System.out.println("Потеря соединения с сервером: " + e.getMessage());
        }
    }
}
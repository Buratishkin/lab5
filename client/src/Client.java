import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6789);
             Scanner scanner = new Scanner(System.in);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("Подключено к серверу. Чтобы получить доступные команды, введите \"help\"");

            while (true) {
                System.out.print("Введите команду: ");
                String command = scanner.nextLine();

                // Отправка команды
                out.write(command);
                out.flush();

                // Чтение ответа (одна строка)
                String response;
                while ((response = in.readLine()) != null && in.ready()){
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Потеря соединения с сервером: " + e.getMessage());
        }
    }
}
package utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Console {
    private  SocketChannel clientChanel;
    private ByteBuffer buffer;

   public Console(SocketChannel clientChanel, ByteBuffer buffer){
       this.clientChanel = clientChanel;
       this.buffer = buffer;
   }

    public void responseToClient(String line) throws IOException {
        ByteBuffer responseBuffer = StandardCharsets.UTF_8.encode(line);

        while (responseBuffer.hasRemaining()) {
            clientChanel.write(responseBuffer);
        }
        System.out.println("Данные отправлены клиенту");
    }
}

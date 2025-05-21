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


}

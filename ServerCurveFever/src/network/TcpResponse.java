package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpResponse extends Thread {
    private ServerSocket serverSocket = new ServerSocket(1234);

    public TcpResponse() throws IOException {
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new TcpLogic(socket).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

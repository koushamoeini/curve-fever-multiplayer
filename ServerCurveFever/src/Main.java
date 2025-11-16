import network.TcpResponse;
import network.UdpResponse;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UdpResponse udpResponse=new UdpResponse();
        udpResponse.start();
        TcpResponse tcpResponse=new TcpResponse();
        tcpResponse.start();
    }
}
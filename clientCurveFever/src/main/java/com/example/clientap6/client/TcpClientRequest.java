package com.example.clientap6.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClientRequest {
    private Socket socket=new Socket(InetAddress.getLocalHost(),1234);
    private InputStreamReader inputStream=new InputStreamReader(socket.getInputStream());
    private OutputStreamWriter outputStream=new OutputStreamWriter(socket.getOutputStream());
    private BufferedReader bufferedReader= new BufferedReader(inputStream);
    private BufferedWriter bufferedWriter=new BufferedWriter(outputStream);
    private static TcpClientRequest instance;

    public TcpClientRequest() throws IOException {
        instance = this;
    }

    public static TcpClientRequest getInstance() throws IOException {
        if (instance == null)
            instance = new TcpClientRequest();
        return instance;
    }
    public synchronized String requestMethod(String str) throws Exception {
        bufferedWriter.write(str);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        return bufferedReader.readLine();
    }
}

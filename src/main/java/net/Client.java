package net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String IP;
    private int port;
    Socket socketClient;

    public Client(String ip, int port){
        this.IP = ip;
        this.port = port;
    }
    public void connect(String file) throws UnknownHostException, IOException{
        System.out.println("Підєднуємося до сервера");
        socketClient = new Socket(IP,port);
        System.out.println("З'єднання успішно встановлено");
        sendFile(socketClient,file);
    }
    public void sendFile(Socket socketClient, String files) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));

        writer.write(files);
        writer.newLine();
        writer.flush();
    }
    public void readResponseFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        System.out.println("Отримано відповідь від сервера2:");
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

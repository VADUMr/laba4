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
    public void connect() throws UnknownHostException, IOException{
        System.out.println("Підєднуємося до сервера");
        socketClient = new Socket(IP,port);
        System.out.println("З'єднання успішно встановлено");
    }
    public void connectToSendFile(File file) throws IOException {
        System.out.println("Підєднуємося до сервера");
        socketClient = new Socket(IP,port);
        System.out.println("З'єднання успішно встановлено");
        sendFile(file);
        readResponseFile();
    }
    public void sendFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));

        writer.write(file.getName());
        writer.newLine();
        writer.flush();

        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = fileReader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
            writer.flush();
        }
        fileReader.close();
    }
    public void readResponseFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        System.out.println("Отримано відповідь від сервера2:");
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    public void readResponse() throws IOException{
        BufferedReader serverOutput = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        System.out.println("Фідбек");
        String clientInput;
        while ((clientInput = serverOutput.readLine()) != null){
            System.out.println(clientInput);
        }
    }
}

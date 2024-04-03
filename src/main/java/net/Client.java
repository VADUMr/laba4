package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public void readResponse() throws IOException{
        String clientInput;
        BufferedReader serverOutput = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        System.out.println("Фідбек");

        while ((clientInput = serverOutput.readLine()) != null){
            System.out.println(clientInput);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Введіть порт");
        int port = Integer.parseInt((scan.nextLine()));

        System.out.println("Введіть ip");
        String ip = scan.nextLine();

        Client client = new Client(ip,port);
        try{
            client.connect();
            client.readResponse();
        }catch (UnknownHostException e){
            System.err.println("Шо ти ввів?");
        }catch (IOException e){
            System.err.println("Невдалося підключитися " + e);
        }
    }
}

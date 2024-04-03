package net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server {
    private ServerSocket serverSocket;
    private int port;

    public Server(int port){
        this.port = port;
    }
    public void start() throws  IOException{
        System.out.println("Запуск сервера під портом " + port);
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket client = serverSocket.accept();
            sendResponse(client);
        }
    }
    public void sendResponse(Socket client) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        writer.write("Ваш порт " + client.getPort() + " Ви підєдналися до порту " + client.getLocalPort()
                + " та IP адреси " + client.getLocalAddress());
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введіть порт для сервера");
        int port = Integer.parseInt(scan.nextLine());

        try{
            Server soketServer = new Server(port);
            soketServer.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String clientInput;
            if((clientInput = reader.readLine()) != null) {
                sendResponse(client);
            }else {
                handleClient(client);
            }
        }
    }
    public void handleClient(Socket client) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        System.out.println("dsfsdfsdf");

        // Отримуємо ім'я файлу від клієнта
        String filename = reader.readLine();
        File file = new File(filename);
        if (!file.exists()) {
            writer.write("Файл не знайдено");
            writer.newLine();
            writer.flush();
            return;
        }

        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        StringBuilder fileContent = new StringBuilder();
        String line;
        while ((line = fileReader.readLine()) != null) {
            fileContent.append(line);
        }
        fileReader.close();

        // Відправляємо клієнту розбитий вміст файлу
        int startPos = 0;
        for (int i = 0; i < 3; i++) {
            int endPos = Math.min(startPos + (i == 0 ? 8 : i == 1 ? 10 : 12), fileContent.length());
            writer.write(fileContent.substring(startPos, endPos));
            writer.newLine();
            writer.flush();
            startPos = endPos;

        }
    }
    public void sendResponse(Socket client) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        writer.write("Ваш порт " + client.getPort() + " Ви підєдналися до порту " + client.getLocalPort()
                + " та IP адреси " + client.getLocalAddress());
        writer.flush();
        writer.close();
    }
}

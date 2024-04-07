package net;

import javafx.application.Platform;

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
            serverWork(client);
        }
    }
    public void serverWork(Socket client) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ) {

            writer.write("З'єднання успішно встановлено");
            writer.newLine();
            writer.flush();

            writer.write("Ваш порт " + client.getPort() + " Ви підєдналися до порту " + client.getLocalPort()
                    + " та IP адреси " + client.getLocalAddress());
            writer.newLine();
            writer.flush();

            // Отримуємо ім'я файлу від клієнта
            String filename = reader.readLine();
            File file = new File(filename);
            if (!file.exists()) {
                writer.write("Файл не знайдено");
                writer.newLine();
                writer.flush();
                return;
            }

            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = fileReader.readLine()) != null) {
                    fileContent.append(line);
                }

                writer.write("Данні з файлу");
                writer.newLine();
                writer.flush();

                // Відправляємо клієнту розбитий вміст файлу
                int startPos = 0;
                for (int i = 0; i < 3; i++) {
                    int endPos = Math.min(startPos + (i == 0 ? 8 : i == 1 ? 10 : 12), fileContent.length());
                    writer.write(fileContent.substring(startPos, endPos));
                    writer.newLine();
                    writer.flush();
                    startPos = endPos;
                }
                client.close();
            }
        }
    }
}

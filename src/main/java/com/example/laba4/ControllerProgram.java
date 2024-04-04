package com.example.laba4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.Client;
import net.Server;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;


public class ControllerProgram {
    @FXML
    private Label exceptionServer;
    @FXML
    private Label exceptionSendFile;
    @FXML
    private Label exceptionClient;
    @FXML
    private TextArea events;
    @FXML
    private TextField portClient;
    @FXML
    private TextField portServer;
    @FXML
    private TextField ipClient;

    boolean serverStarted = false;
    boolean clientStarted = false;

    @FXML
    protected void sendFile() throws IOException {

        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберіть файл");

            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                System.out.println("Вибраний файл: " + selectedFile.getAbsolutePath());
                if (!serverStarted) {
                    System.out.println("Сервер не запущений");
                } else {
                    if (!clientStarted) {
                        System.out.println("Клієнт не коректний");
                    } else {
                        new Thread(() -> {
                        try {
                            int port = Integer.parseInt(portClient.getText());

                            String ip = ipClient.getText();
                            Client client = new Client(ip, port);

                            client.connectToSendFile(selectedFile);
                            client.readResponseFile();

                        } catch (IOException e) {
                            System.err.println("Невдалося підключитися " + e);
                        }
                        }).start();
                    }
                }
            } else {
                System.out.println("Файл не було вибрано.");
            }
        });

    }
    @FXML
    protected void startServer() throws IOException {
        new Thread(() -> {
            try {
                int port = Integer.parseInt(portServer.getText());
                if (port < 0 || port > 65535) {
                    Platform.runLater(() -> exceptionServer.setText("Порт повинен бути цілим числом від 0 до 65535"));
                    return;
                }
                Server socketServer = new Server(port);
                serverStarted = true;
                socketServer.start();

            } catch (NumberFormatException e) {
                Platform.runLater(() ->exceptionServer.setText("Некоректне значення порту. Введіть ціле число."));
            } catch (IOException e) {
                Platform.runLater(() ->exceptionServer.setText("Помилка при старті сервера: " + e.getMessage()));
            }
        }).start();
    }
    @FXML
    protected void startClient() throws IOException{
        new Thread(() -> {
            try {
                int port = Integer.parseInt(portClient.getText());
                if (port < 0 || port > 65535) {
                    exceptionClient.setText("Порт повинен бути цілим числом від 0 до 65535");
                    return;
                }
                String ip = ipClient.getText();
                Client client = new Client(ip, port);

                client.connect();
                clientStarted = true;
                client.readResponse();
            } catch (UnknownHostException e) {
                System.err.println("Шо за хост?");
            } catch (NumberFormatException e) {
                exceptionClient.setText("Некоректне значення порту. Введіть ціле число.");
            } catch (IOException e) {
                System.err.println("Невдалося підключитися " + e);
            }
    }).start();
    }
}
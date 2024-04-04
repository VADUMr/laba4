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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ControllerProgram {
    @FXML
    private Label exceptionServer;
    @FXML
    private Label exceptionClient;
    @FXML
    private Label exceptionFile;
    private TextArea events;
    @FXML
    private TextField portClient;
    @FXML
    private TextField portServer;
    @FXML
    private TextField ipClient;


    @FXML
    protected void sendFile() throws IOException {
        Platform.runLater(() -> {
                        new Thread(() -> {
                        try {
                            int port = Integer.parseInt(portClient.getText());
                            if (port < 0 || port > 65535) {
                                Platform.runLater(() -> exceptionClient.setText("Порт повинен бути цілим числом від 0 до 65535"));
                                return;
                            }
                            String ip = ipClient.getText();

                            String IP_ADDRESS_REGEX = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
                            Pattern pattern = Pattern.compile(IP_ADDRESS_REGEX);
                            Matcher matcher = pattern.matcher(ip);
                            if(matcher.matches()) {
                                Platform.runLater(() -> exceptionClient.setText("Клієнт ок"));

                                FileChooser fileChooser = new FileChooser();
                                fileChooser.setTitle("Оберіть файл");

                                File selectedFile = fileChooser.showOpenDialog(new Stage());

                                if (selectedFile != null) {
                                    System.out.println("Вибраний файл: " + selectedFile.getAbsolutePath());
                                    Client client = new Client(ip, port);

                                    client.connect(selectedFile.getAbsolutePath());
                                    client.readResponseFile();
                                }

                            }else{
                                Platform.runLater(() ->exceptionClient.setText("Некоректне значення порту клієнта. Введіть ціле число."));
                            }
                        } catch (NumberFormatException e) {
                            Platform.runLater(() ->exceptionClient.setText("Некоректне значення порту клієнта. Введіть ціле число."));
                        }catch (IOException e) {
                            Platform.runLater(() ->exceptionClient.setText("Невдалося підключитися " + e.getMessage()));
                        }
                        }).start();
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
                Platform.runLater(() -> exceptionServer.setText("Сервер ок"));
                Server socketServer = new Server(port);
                socketServer.start();

            } catch (NumberFormatException e) {
                Platform.runLater(() ->exceptionServer.setText("Некоректне значення порту. Введіть ціле число."));
            } catch (IOException e) {
                Platform.runLater(() ->exceptionServer.setText("Помилка при старті сервера: " + e.getMessage()));
            }
        }).start();
    }
}
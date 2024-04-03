package com.example.laba4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControllerProgram {
    @FXML
    private Label welcomeText;

    @FXML
    protected void choiseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Оберіть файл");

        // Вибір файлу
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Робота з вибраним файлом (якщо файл вибрано)
        if (selectedFile != null) {
            System.out.println("Вибраний файл: " + selectedFile.getAbsolutePath());
            // Тут ви можете зробити що завгодно з вибраним файлом
        } else {
            System.out.println("Файл не було вибрано.");
        }
    }
}
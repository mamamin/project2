package com.example.project2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        Parent nodes = FXMLLoader.load(getClass().getResource("Login page.fxml"));
        Scene scene = new Scene(nodes);
        stage.setScene(scene);
        stage.setTitle("Login page");
        Image icon = new Image("logo.png");
        stage.getIcons().add(icon);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
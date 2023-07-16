package com.frytech.AnvilCraft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/connection.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("AnvilCraft");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

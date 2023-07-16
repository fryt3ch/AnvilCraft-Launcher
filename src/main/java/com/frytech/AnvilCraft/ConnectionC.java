package com.frytech.AnvilCraft;

import com.jfoenix.controls.JFXClippedPane;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.net.URLConnection;

import static com.frytech.AnvilCraft.Main.primaryStage;

public class ConnectionC extends Methods implements Configuration, Dialog
{
    @FXML ImageView logo;
    @FXML AnchorPane pane;
    @FXML Text versionText;
    @FXML Label copyrightText;
    @FXML JFXClippedPane border, rollupBtn, exitBtn;

    @FXML
    void initialize()
    {
        Platform.runLater(() ->
        {
            copyrightText.setText(copyright);
            versionText.setText("Launcher " + version);

            loadControls(rollupBtn, exitBtn);
            DragListener.start(primaryStage, border);
        });

        retryConnection();
    }

    boolean isNetOk(String site)
    {
        try
        {
            URL url = new URL(site);
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (Exception e) { return false; }
    }

    void retryConnection()
    {
        new Thread(() ->
        {
            FadeTransition ft = new FadeTransition();
            ft.setNode(logo);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setDuration(Duration.millis(connectionAnimDuration));
            ft.setCycleCount(10);
            ft.setAutoReverse(true);
            ft.play();

            boolean webOk = isNetOk(webURL);
            boolean extraOk = isNetOk(extraURL);

            Platform.runLater(() ->
            {
                if (webOk)
                {
                    try
                    {
                        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/login.fxml"))));
                    }
                    catch (Exception e)
                    {
                        exceptionDlg(pane, e);
                    }
                }
                else if (extraOk)
                    warningDlg(pane, serverUnavailable, "random", 2);
                else
                    warningDlg(pane, noInternet, "random", 2);
            });
        }).start();
    }
}

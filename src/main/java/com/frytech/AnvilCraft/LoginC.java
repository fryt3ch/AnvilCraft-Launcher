package com.frytech.AnvilCraft;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static com.frytech.AnvilCraft.Main.primaryStage;

public class LoginC extends Methods implements Configuration, Dialog
{
    @FXML ImageView logo;
    @FXML Label forgotpassText, registrationText;
    @FXML AnchorPane pane;
    @FXML JFXClippedPane border, rollupBtn, exitBtn;

    @FXML void initialize()
    {
        Platform.runLater(() ->
        {
            //Проверка совпадения битности JRE и ОС, 0 - ok
            if (bitnessCheck() != 0) warningDlg(pane, differentBitness + bitness, "random", 1);

            //Создаем папку лаунчера и загружаем настройки
            createAppFolder(); loadSettings();

            logo.setOnMouseClicked(event -> gotosite(webURL));
            forgotpassText.setOnMouseClicked(event -> gotosite(forgotPassURL));
            registrationText.setOnMouseClicked(event -> gotosite(registrationURL));
            loadControls(rollupBtn, exitBtn);
            DragListener.start(primaryStage, border);
        });
    }

    void checkUpdate()
    {

    }

    void loadSettings()
    {
        try
        {
            initializeSettings();
            if (settingsState == 0)
                warningDlg(pane, invalidSettingsFile, "random", 0);
        }
        catch (Exception e) { exceptionDlg(pane, e); }
    }
}

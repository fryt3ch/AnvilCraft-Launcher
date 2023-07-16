package com.frytech.AnvilCraft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static com.frytech.AnvilCraft.Configuration.downloadJavaURL;
import static com.frytech.AnvilCraft.Main.primaryStage;
import static com.frytech.AnvilCraft.Methods.gotosite;

public interface Dialog
{
    String[] wHeader = {"Ну надо же..",
                        "Упс..",
                        "Бывает :(",
                        "Мда.."};
    String[] eHeader = {"Упс.. Сорри",
                        "Не баг, а фича",
                        "Ошибочка вышла :(",
                        "С кем не бывает"};

    default void warningDlg(AnchorPane pane, String btext, String htext, int type) //if htext = "random" -> randomize by wHeader
    {
        htext = htext.equals("random") ? wHeader[0 + (int) (Math.random() * wHeader.length)] : htext;

        StackPane sp = new StackPane();
        sp.setPrefWidth(pane.getWidth()); sp.setPrefHeight(pane.getHeight() - 30);
        sp.setLayoutY(30);
        pane.getChildren().add(sp);

        JFXDialogLayout content = new JFXDialogLayout();
        content.getStylesheets().add("/css/dialogs.css");

        Label header = new Label(htext);
        header.getStyleClass().add("header");
        content.setHeading(header);

        Text body = new Text(btext);
        body.getStyleClass().add("body");
        content.setBody(body);

        JFXDialog dialog = new JFXDialog(sp, content, JFXDialog.DialogTransition.CENTER, false);
        dialog.setStyle("-fx-background-color: rgba(0.0, 0.0, 0.0, 0.0);");

        switch (type)
        {
            case 0: //default dialog
            {
                JFXButton btn = new JFXButton("Хорошо");
                btn.setCursor(Cursor.HAND);
                btn.setOnMouseClicked(event ->
                {
                    dialog.close(); pane.getChildren().remove(sp);
                });
                content.setActions(btn);
                break;
            }
            case 1: //differentBitness
            {
                JFXButton btn = new JFXButton("Загрузить");
                btn.setCursor(Cursor.HAND);
                btn.getStyleClass().add("extra_button");
                btn.setOnMouseClicked(event -> gotosite(downloadJavaURL));
                JFXButton btn1 = new JFXButton("Закрыть");
                btn1.setOnMouseClicked(event ->
                {
                    dialog.close(); pane.getChildren().remove(sp);
                });
                content.setActions(btn, btn1);
                break;
            }
            case 2: //connectionTrouble
            {
                JFXButton btn = new JFXButton("Еще раз");
                btn.setCursor(Cursor.HAND);
                btn.getStyleClass().add("extra_button");
                btn.setOnMouseClicked(event ->
                {
                    try
                    {
                        dialog.close(); pane.getChildren().remove(sp);
                        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/connection.fxml"))));
                    }
                    catch (Exception e){}
                });
                JFXButton btn1 = new JFXButton("Выйти");
                btn1.setCursor(Cursor.HAND);
                btn1.setOnMouseClicked(event -> Platform.exit());
                content.setActions(btn, btn1);
                break;
            }
        }
        dialog.show();
    }

    default void exceptionDlg(AnchorPane pane, Exception etext)
    {
        int i = 0 + (int) (Math.random() * eHeader.length);

        StackPane sp = new StackPane();
        sp.setPrefWidth(pane.getWidth()); sp.setPrefHeight(pane.getHeight() - 30);
        sp.setLayoutY(30);
        pane.getChildren().add(sp);

        JFXDialogLayout content = new JFXDialogLayout();
        content.getStylesheets().add("/css/dialogs.css");

        Label header = new Label(eHeader[i]);
        header.getStyleClass().add("header");
        content.setHeading(header);

        JFXTextArea body = new JFXTextArea(etext.toString());
        body.getStyleClass().add("ebody");
        body.setEditable(false);
        content.setBody(body);

        JFXDialog dialog = new JFXDialog(sp, content, JFXDialog.DialogTransition.CENTER, false);
        dialog.setStyle("-fx-background-color: rgba(0.0, 0.0, 0.0, 0.0);");

        JFXButton btn = new JFXButton("Скопировать");
        btn.setCursor(Cursor.HAND);
        btn.getStyleClass().add("extra_button");
        btn.setOnMouseClicked(event -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(etext.toString()), null));

        JFXButton btn1 = new JFXButton("Продолжить");
        btn1.setCursor(Cursor.HAND);
        btn1.setOnMouseClicked(event ->
        {
            dialog.close(); pane.getChildren().remove(sp);
        });
        content.setActions(btn, btn1);

        dialog.show();
    }
}

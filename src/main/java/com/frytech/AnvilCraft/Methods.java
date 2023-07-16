package com.frytech.AnvilCraft;

import com.jfoenix.controls.JFXClippedPane;
import com.sun.javafx.PlatformUtil;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.*;
import java.net.URL;

import static com.frytech.AnvilCraft.Main.primaryStage;

public class Methods implements Configuration, Dialog
{
    static int bitness = 0;
    static int settingsState = 2;

    static String workingDirectory = "";

    static String login = "none";
    static String password = "none";
    static boolean rememberMe = false;
    static int jvmAllocatedMemory = 1024;
    static String jvmExtraFlags = "none";
    static String lastClient = "none";

    static String defaults = "{" +
            "\n\t\"login\": \"none\"," +
            "\n\t\"password\": \"none\"," +
            "\n\t\"rememberMe\": false," +
            "\n\t\"jvmAllocatedMemory\": 1024," +
            "\n\t\"jvmExtraFlags\": \"none\"," +
            "\n\t\"lastClient\": \"none\"" +
            "\n}";

    static String createAppFolder()
    {
        String path = "";

        if (System.getProperty("os.name").toLowerCase().contains("windows"))
            path = System.getenv("APPDATA") + "\\" + launcherFolder +"\\";
        else if (PlatformUtil.isLinux() || PlatformUtil.isUnix() || PlatformUtil.isMac())
            path = System.getProperty("user.home/" + launcherFolder + "/");
        else if (PlatformUtil.isMac())
            path = System.getProperty("user.home") + "/Library/Application Support/" + launcherFolder + "/";
        else Platform.exit();

        new File(path).mkdir();
        return workingDirectory = path;
    }

    static int initializeSettings() throws Exception //returns 0 - restored, 1 - OK, 2 - new file
    {
        String path = workingDirectory + settingsFile;
        File file = new File(path);

        if (file.exists())
        {
            try
            {
                parseJSON(path);
                return settingsState = 1;
            }
            catch (Exception e)
            {
                restoreDefaults(path, file);
                return settingsState = 0;
            }
        }
        else
        {
            restoreDefaults(path, file);
            return settingsState = 2;
        }
    }

    static void parseJSON(String file) throws Exception {
        Object json = new JSONParser().parse(new FileReader(file));
        JSONObject jo = (JSONObject) json;

        String login = (String) jo.get("login");
        if (login.equals(null)) throw new Exception();
        String password = (String) jo.get("password");
        if (password.equals(null)) throw new Exception();
        Boolean rememberMe = (Boolean) jo.get("rememberMe");
        if (rememberMe.equals(null)) throw new Exception();
        Long jvmAllocatedMemory = (Long) jo.get("jvmAllocatedMemory");
        Math.toIntExact(jvmAllocatedMemory);
        if (jvmAllocatedMemory.equals(null)) throw new Exception();
        String jvmExtraFlags = (String) jo.get("jvmExtraFlags");
        if (jvmExtraFlags.equals(null)) throw new Exception();
        String lastClient = (String) jo.get("lastClient");
        if (lastClient.equals(null)) throw new Exception();
    }

    static void restoreDefaults(String path, File file) throws Exception
    {
        if (!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(path);
        fw.write(defaults);
        fw.close();
    }

    static int bitnessCheck()
    {
        int javaArch = System.getProperty("sun.arch.data.model").contains("64") ? 64 : 32;
        int osArch = System.getProperty("os.arch").contains("64") ? 64 : 32;

        if (System.getProperty("os.name").toLowerCase().contains("windows"))
            osArch = System.getenv("ProgramFiles(x86)") != null ? 64 : 32;

        if (osArch != javaArch) return bitness = osArch;
        else return 0;
    }

    static void gotosite(String url){
        try
        {
            Desktop.getDesktop().browse(new URL(url).toURI());
        }
        catch (Exception e){}
    }

    static void loadControls(JFXClippedPane rollupbtn,JFXClippedPane exitbtn)
    {
        rollupbtn.setOnMouseClicked(event -> primaryStage.setIconified(true));
        exitbtn.setOnMouseClicked(event -> Platform.exit());
    }
}

class DragListener
{
    private static double xOffset, yOffset;

    static void start(Stage stage, JFXClippedPane pane) // based on lambda of mouse event handler
    {
        pane.setOnMousePressed(event ->
        {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        pane.setOnMouseDragged(event ->
        {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }
}

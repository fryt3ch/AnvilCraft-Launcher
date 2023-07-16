package com.frytech.AnvilCraft;

interface Configuration
{
    // Common props
    String launcherFolder = ".anvilcraft";
    String settingsFile = "settings.json";
    String webURL = "https://frytech.info/";
    String forgotPassURL = "https://anvilcraft.ru/forgotpass";
    String registrationURL = "https://anvilcraft.ru/registration";

    // Extra props
    String extraURL = "https://google.com";
    String downloadJavaURL = "https://java.com/ru/download/manual.jsp";
    int connectionAnimDuration = 450;

    // Lang props
    String differentBitness = "Разрядность вашей ОС не совпадает с разрядностью Java\nЭто может создать проблемы с выделением ОЗУ игре\nРекомендуем вам установить Java x";
    String invalidSettingsFile = "Ваш файл настроек был поврежден и мы восстановили его.\nУдачной игры на проекте AnvilCraft :)";
    String serverUnavailable = "На нашем сайте ведутся технические работы, попробуйте позже :)";
    String noInternet = "Похоже, что у вас проблемы с Интернетом.\nВозможно, на форуме вы найдете решение данной проблемы.";

    //Version
    String copyright = "© AnvilCraft Project, 2019 г.";
    String version = "v1.0-beta";
}

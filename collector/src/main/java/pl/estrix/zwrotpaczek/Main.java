package pl.estrix.zwrotpaczek;

import com.sun.javafx.application.LauncherImpl;

public class Main {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(AppMain.class, MyPreloader.class, args);
    }
}
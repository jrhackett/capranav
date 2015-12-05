package controller;

import com.sun.javafx.application.LauncherImpl;

/**
 * Created by Kurt on 12/4/15.
 */
public class Main {
    public static void main(String[] args) {


        LauncherImpl.launchApplication(Controller.class, myPreloader.class, args);


    }
}
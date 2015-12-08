package controller;

import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.FileFetch;

/**
 * Created by Kurt on 12/2/15.  Modified by Greg afterwards.
 */


public class myPreloader extends javafx.application.Preloader {
    private Stage preloaderStage;
    //private Display myDisplay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        primaryStage.initStyle(StageStyle.UNDECORATED);

        VBox loading = new VBox(20);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);

        Image goatImage = FileFetch.getImageFromFile("goatnav.png");
        ImageView goatView = new ImageView(goatImage);
        goatView.setFitHeight(309);
        goatView.setFitWidth(550);

        loading.getChildren().addAll(goatView);

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);


        primaryStage.setWidth(550);  //960 goatnav.png, 360 Preloader.gif
        primaryStage.setHeight(309);  //540 goatnav.png, 270 Preloader.gif
        primaryStage.setScene(scene);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == Type.BEFORE_START) {

            System.out.print("ASDASD");
            preloaderStage.hide();
        }
    }

}
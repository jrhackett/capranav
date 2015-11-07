package visuals;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Xenocidist on 11/7/15.
 */
public class Button extends StackPane{
   private static final double size = 30;

    public Button(String content) {
        Rectangle background = new Rectangle(size, size, Color.web("#638CA6", .3));
        background.setArcWidth(5);
        background.setArcHeight(5);
        background.setStroke(Color.web("#BFD4D9", .3));
        background.setStrokeWidth(2);

        Text buttonContent = new Text(content);
        buttonContent.setFont(Font.font("Sans Serif", FontWeight.BOLD, 24));
        buttonContent.setFill(Color.BLACK);

        this.getChildren().addAll(background, buttonContent);

        DropShadow ds = new DropShadow();
        ds.setOffsetX(.3);
        ds.setOffsetY(.3);
        ds.setColor(Color.LIGHTGRAY);
        this.setEffect(ds);

        this.setOnMouseEntered(e -> {
            background.setFill(Color.web("#638CA6", 1));
            background.setStroke(Color.web("#BFD4D9", 1));
            ds.setOffsetX(.5);
            ds.setOffsetY(.5);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
        this.setOnMouseExited(e -> {
            background.setFill(Color.web("#638CA6", .3));
            background.setStroke(Color.web("#BFD4D9", .3));
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
    }

    public Button(Node content, String translate){
        Rectangle background = new Rectangle(size, size, Color.web("#638CA6", .3));
        background.setArcWidth(5);
        background.setArcHeight(5);
        background.setStroke(Color.web("#BFD4D9", .3));
        background.setStrokeWidth(2);

        if(translate.equals("menu")) {
            content.setTranslateX(5);
            content.setTranslateY(9);
        } else {
            content.setTranslateX(0);
            content.setTranslateY(0);
        }

        this.getChildren().addAll(background, content);

        DropShadow ds = new DropShadow();
        ds.setOffsetX(.3);
        ds.setOffsetY(.3);
        ds.setColor(Color.LIGHTGRAY);
        this.setEffect(ds);

        this.setOnMouseEntered(e -> {
            background.setFill(Color.web("#638CA6", 1));
            background.setStroke(Color.web("#BFD4D9", 1));
            ds.setOffsetX(.5);
            ds.setOffsetY(.5);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
        this.setOnMouseExited(e -> {
            background.setFill(Color.web("#638CA6", .3));
            background.setStroke(Color.web("#BFD4D9", .3));
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
    }
}

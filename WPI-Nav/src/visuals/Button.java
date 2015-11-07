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
 * Class for Buttons - [custom not JavaFX]
 */
public class Button extends StackPane{

    /**
     * Constructor for button
     * @param content = string of content - 1 character.
     * @param size = size of the button.
     */
    public Button(String content, double size) {
        /* background of button */
        Rectangle background = new Rectangle(size, size, Color.web("#638CA6", .3));
        background.setArcWidth(5);
        background.setArcHeight(5);
        background.setStroke(Color.web("#BFD4D9", .3));
        background.setStrokeWidth(2);

        /* content */
        Text buttonContent = new Text(content);
        buttonContent.setFont(Font.font("Sans Serif", FontWeight.BOLD, 24));
        buttonContent.setFill(Color.BLACK);

        this.getChildren().addAll(background, buttonContent);

        /* effects: */

            /* shadow */
        DropShadow ds = new DropShadow();
        ds.setOffsetX(.3);
        ds.setOffsetY(.3);
        ds.setColor(Color.LIGHTGRAY);
        this.setEffect(ds);

            /* user interaction: */
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
        this.setOnMousePressed(e -> {
            background.setFill(Color.FORESTGREEN);
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.WHITE);
            this.setEffect(ds);
        });

        this.setOnMouseReleased(e -> {
            background.setFill(Color.web("#638CA6", .3));
            background.setStroke(Color.web("#BFD4D9", .3));
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
    }

    /**
     * Alternate constructor for Nodes [images / constructs]
     * @param content = non-text javafx content
     * @param translate = a BAD way of fixing individual buttons (moving around the content inside the button)
     * @param size = size of button
     */
    public Button(Node content, String translate, double size){
        /* background of button */
        Rectangle background = new Rectangle(size, size, Color.web("#638CA6", .3));
        background.setArcWidth(5);
        background.setArcHeight(5);
        background.setStroke(Color.web("#BFD4D9", .3));
        background.setStrokeWidth(2);

        /* quick/dirty fix for moving content to where I want it*/
        if(translate.equals("menu")) {
            content.setTranslateX(5);
            content.setTranslateY(9);
        } else {
            content.setTranslateX(0);
            content.setTranslateY(0);
        }

        this.getChildren().addAll(background, content);

        /* effects: */

            /* shadow */
        DropShadow ds = new DropShadow();
        ds.setOffsetX(.3);
        ds.setOffsetY(.3);
        ds.setColor(Color.LIGHTGRAY);
        this.setEffect(ds);

            /* user interaction: */
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

        this.setOnMousePressed(e -> {
            background.setFill(Color.FORESTGREEN);
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.WHITE);
            this.setEffect(ds);
        });

        this.setOnMouseReleased(e -> {
            background.setFill(Color.web("#638CA6", .3));
            background.setStroke(Color.web("#BFD4D9", .3));
            ds.setOffsetX(.3);
            ds.setOffsetY(.3);
            ds.setColor(Color.LIGHTGRAY);
            this.setEffect(ds);
        });
    }

}

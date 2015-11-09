package visuals;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class OptionsMenu extends AnchorPane {
    private static final double SPACING = 5;

    /**
     * This is going to be an extendable/hidden menu
     * @param WIDTH
     * @param HEIGHT
     */
    public OptionsMenu(double WIDTH, double HEIGHT){
        super();
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        VBox panel = createOptionPanel(WIDTH);
        this.getChildren().addAll(panel);

    }

    /**
     * Creates the checkboxes
     * @param WIDTH
     * @return
     */
    private VBox createOptionPanel(double WIDTH){
        VBox panel = new VBox();
        panel.setMaxWidth(WIDTH);
        panel.setMinHeight(WIDTH);
        panel.setSpacing(SPACING);

        /* handicap */
        final CheckBox cbh = new CheckBox("Handicap");
        cbh.setOnAction(event -> {
            //Controller.handicap();
            cbh.setTextFill(Color.GREEN);
        });

        /* weather */
        final CheckBox cbw = new CheckBox("Weather");
        cbw.setOnAction(event -> {
            //Controller.weather();
            cbh.setTextFill(Color.GREEN);
        });

        panel.getChildren().addAll(cbh, cbw);
        return panel;
    }

}


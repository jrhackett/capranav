package visuals;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class OptionsMenu extends AnchorPane {
    private static final double SPACING = 5;


    public OptionsMenu(double WIDTH, double HEIGHT){
        super();
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        VBox panel = createPanel(WIDTH);
        this.getChildren().addAll(panel);

    }

    private VBox createPanel(double WIDTH){
        VBox panel = new VBox();
        panel.setMaxWidth(WIDTH);
        panel.setMinHeight(WIDTH);
        panel.setSpacing(SPACING);

        final String[] names = new String[]{"Handicap", "Weather"};
        final CheckBox[] cbs = new CheckBox[names.length];

        for (int i = 0; i < names.length; i++) {
            final CheckBox cb = cbs[i] = new CheckBox(names[i]); /* cool trick i saw */
        }

        panel.getChildren().addAll(cbs);

        return panel;
    }

}


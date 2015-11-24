package visuals;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


class SlidingAnchorPane extends AnchorPane {

    private javafx.scene.control.Button controlButton = new javafx.scene.control.Button();

    public javafx.scene.control.Button getButton() {
        return controlButton;
    }

    SlidingAnchorPane(final double expandedWidth, final double edge, BooleanProperty prop, Node buttonval, Node... nodes) {


        this.setPrefWidth(expandedWidth + edge);
        this.setMinWidth(edge);

        this.controlButton = new javafx.scene.control.Button();
        controlButton.setGraphic(buttonval);
        controlButton.setAlignment(Pos.CENTER);
        this.getChildren().addAll(nodes);

        //TODO: functionality
        // apply the animations when the button is pressed.
        controlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // create an animation to hide sidebar.


                final Animation hideSidebar = new Transition() {
                    {
                        setCycleDuration(Duration.millis(250));
                    }

                    protected void interpolate(double t) {
                        final double S1 = 25.0 / 9.0;
                        final double S3 = 10.0 / 9.0;
                        final double S4 = 1.0 / 9.0;
                        t = ((t < 0.2) ? S1 * t * t : S3 * t - S4);
                        t = (t < 0.0) ? 0.0 : (t > 1.0) ? 1.0 : t;
                        final double curWidth = edge + expandedWidth * (1.0 - t);
                        setPrefWidth(curWidth);
                        //setTranslateX(-expandedWidth + curWidth);
                    }

                };
                hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        prop.setValue(!prop.getValue()); //this should flip the MISC_VISIBLE
                        //TODO change the look of bars here
                        //bars.getStyleClass().remove("hide-left");
                        //bars.getStyleClass().add("show-right");
                    }
                });

                final Animation showSidebar = new Transition() {
                    {
                        setCycleDuration(Duration.millis(250));
                    }

                    protected void interpolate(double t) {
                        final double S1 = 25.0 / 9.0;
                        final double S3 = 10.0 / 9.0;
                        final double S4 = 1.0 / 9.0;
                        t = ((t < 0.2) ? S1 * t * t : S3 * t - S4);
                        t = (t < 0.0) ? 0.0 : (t > 1.0) ? 1.0 : t;


                        final double curWidth = edge + expandedWidth * t;
                        setPrefWidth(curWidth);
                        //setTranslateX(-expandedWidth + curWidth);
                    }
                };
                showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        prop.setValue(!prop.getValue()); //this should flip the MISC_VISIBLE

                        //TODO change the look of bars here
                        //bars.getStyleClass().remove("hide-left");
                        //bars.getStyleClass().add("show-right");
                    }
                });

                if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                    if (prop.getValue()) {
                        hideSidebar.play();
                    } else {
                        //setVisible(true);
                        showSidebar.play();
                    }
                }
            }
        });
    }
}

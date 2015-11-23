package visuals;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


class SlidingVBox extends VBox {

    private javafx.scene.control.Button controlButton = new javafx.scene.control.Button();

    public javafx.scene.control.Button getButton() {
        return controlButton;
    }

    SlidingVBox(final double expandedWidth, Node buttonval, Node... nodes) {


        this.setPrefWidth(expandedWidth);
        this.setMinWidth(0);




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

                    protected void interpolate(double frac) {
                        final double curWidth = expandedWidth * (1.0 - frac);
                        setPrefWidth(curWidth);
                        setTranslateX(-expandedWidth + curWidth);
                    }
                };
                hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        setVisible(false);
                        //TODO change the look of bars here
                        //bars.getStyleClass().remove("hide-left");
                        //bars.getStyleClass().add("show-right");
                    }
                });

                final Animation showSidebar = new Transition() {
                    {
                        setCycleDuration(Duration.millis(250));
                    }

                    protected void interpolate(double frac) {
                        final double curWidth = expandedWidth * frac;
                        setPrefWidth(curWidth);
                        setTranslateX(-expandedWidth + curWidth);
                    }
                };
                showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //TODO change the look of bars here
                        //bars.getStyleClass().remove("hide-left");
                        //bars.getStyleClass().add("show-right");
                    }
                });

                if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                    if (isVisible()) {
                        hideSidebar.play();
                    } else {
                        setVisible(true);
                        showSidebar.play();
                    }
                }
            }
        });
    }
}

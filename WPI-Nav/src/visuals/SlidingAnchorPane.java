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


public class SlidingAnchorPane extends AnchorPane {
    Direction direction;
    double edge, expanded;

    private javafx.scene.control.Button controlButton = new javafx.scene.control.Button();

    public javafx.scene.control.Button getButton() {
        return controlButton;
    }

    public SlidingAnchorPane(){};

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

            final double curHeight;
            switch (direction){//note that this changes depending on if you want it closed or opened first
                case RIGHT:
                    break;
                case LEFT://to the right visible first, slides left
                    final double curWidth = edge + expanded * (1.0 - t);
                    setPrefWidth(curWidth);
                    break;
                case UP://down not visible, click down
                    curHeight = edge + expanded * t;
                    setPrefHeight(curHeight);
                    break;
                case DOWN:
                    curHeight = expanded * t;
                    setPrefHeight(curHeight);
                    break;

            }

        }

    };

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

            final double curHeight;

            switch (direction){//note that this changes depending on if you want it closed or opened first
                case RIGHT:
                    break;
                case LEFT://to the right visible first, slides left
                    final double curWidth = edge + expanded * t;
                    setPrefWidth(curWidth);
                    break;
                case UP://down not visible, click down
                    curHeight = edge + expanded * (1.0 - t);
                    setPrefHeight(curHeight);
                    break;
                case DOWN:
                    curHeight = expanded * (1.0 - t);
                    setPrefHeight(curHeight);
                    break;

            }
        }
    };

    public void playHidePane(){
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
            hideSidebar.play();
        }

    }

    public void playShowPane(){
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            showSidebar.play();
        }
    }

    public SlidingAnchorPane(final double expanded, final double edge, Direction direction, BooleanProperty prop, Node buttonval, Node... nodes) {
        this.direction = direction;
        this.edge = edge;
        this.expanded = expanded;

        switch (direction){
            case LEFT:
                this.setPrefWidth(expanded + edge);
                this.setMinWidth(edge);
                break;
            case UP:
                this.setPrefHeight(edge);
                this.setMinHeight(edge);
                break;
            case DOWN: //down is set up to not be visible at all then slide down
                this.setPrefHeight(0);
                this.setMinHeight(0);
                this.setMaxHeight(expanded);
        }


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

                hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        prop.setValue(!prop.getValue()); //this should flip the MISC_VISIBLE

                        //TODO change the look of bars here
                        //bars.getStyleClass().remove("hide-left");
                        //bars.getStyleClass().add("show-right");
                    }
                });


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

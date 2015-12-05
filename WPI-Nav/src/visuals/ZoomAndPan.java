package visuals;

/**
 * Created by Josh on 12/1/2015.
 */
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.Parent;


public class ZoomAndPan {

    private static final double MAX_SCALE = 15d;
    private static final double MIN_SCALE = 1d;
    private ScrollPane scrollPane = new ScrollPane();

    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

    //private final Group group = new Group();

    //@Override
    //public void start(Stage primaryStage) {
    public Parent createZoomPane(Group group) {
//        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
//        AnchorPane.setTopAnchor(scrollPane, 10.0d);
//        AnchorPane.setRightAnchor(scrollPane, 10.0d);
//        AnchorPane.setBottomAnchor(scrollPane, 10.0d);
//        AnchorPane.setLeftAnchor(scrollPane, 10.0d);

//        AnchorPane root = new AnchorPane();

        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(group);

        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        scrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        scrollPane.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

//        root.getChildren().add(scrollPane);
        //Scene scene = new Scene(root, 600, 400);
        //primaryStage.setScene(scene);
        //primaryStage.show();
        double zWidth = panAndZoomPane.getLayoutBounds().getWidth();
        double zHeight = panAndZoomPane.getLayoutBounds().getHeight();
        double vpH = scrollPane.getViewportBounds().getHeight();
        double vpW = scrollPane.getViewportBounds().getWidth();
        double initialScale = Math.min(vpH/zHeight, vpW/zWidth);
        double panXTrans = panAndZoomPane.getTranslateX();
        System.out.println(panXTrans);
        //panAndZoomPane.setPivot(0,0,1.063);
        return scrollPane;
    }
/**
 *
 *
 * Ignore everything below this line //TODO get rid of bad formatting later
 *
 *
 *
 *
 *
 */
    /**
     * Mouse drag context used for scene and nodes.
     */
    class DragContext {

        double mouseAnchorX;
        double mouseAnchorY;

        double translateAnchorX;
        double translateAnchorY;

    }

    /**
     * Listeners for making the scene's canvas draggable and zoomable
     */
    public class SceneGestures {

        private DragContext sceneDragContext = new DragContext();

        PanAndZoomPane panAndZoomPane;

        public SceneGestures( PanAndZoomPane canvas) {
            this.panAndZoomPane = canvas;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        public EventHandler<ScrollEvent> getOnScrollEventHandler() {
            return onScrollEventHandler;
        }

        private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                sceneDragContext.mouseAnchorX = event.getX();
                sceneDragContext.mouseAnchorY = event.getY();

                sceneDragContext.translateAnchorX = panAndZoomPane.getTranslateX();
                sceneDragContext.translateAnchorY = panAndZoomPane.getTranslateY();

            }

        };

        private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //double ZWidth = panAndZoomPane.getWidth();
                double ZWidth = panAndZoomPane.getLayoutBounds().getWidth();
                double ZHeight = panAndZoomPane.getLayoutBounds().getHeight();
                double vpH = scrollPane.getViewportBounds().getHeight();
                double vpW = scrollPane.getViewportBounds().getWidth();
                double scrollH = scrollPane.getLayoutBounds().getHeight();
                System.out.println("ScrollH " + scrollH + ", ZHeight "+ ZHeight + ", viewH " + vpH);
                double scrollW = scrollPane.getLayoutBounds().getWidth();
                System.out.println("ScrollW " + scrollW + ", ZWidth " + ZWidth + ", viewW" + vpW);
                //double minH = panAndZoomPane.getLayoutY();

                //System.out.println("maxh "+ maxH + " minh " + minH);
                double transX = sceneDragContext.translateAnchorX + event.getX() - sceneDragContext.mouseAnchorX;
                panAndZoomPane.setTranslateX(transX);
                System.out.println("Xtrans " + transX);
                double transY = sceneDragContext.translateAnchorY + event.getY() - sceneDragContext.mouseAnchorY;
                panAndZoomPane.setTranslateY(transY);
                System.out.println("Ytrans" +transY);

                event.consume();
            }
        };

        /**
         * Mouse wheel handler: zoom to pivot point
         */
        private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent event) {

                double delta = PanAndZoomPane.DEFAULT_DELTA;

                double scale = panAndZoomPane.getScale(); // currently we only use Y, same value is used for X
                double oldScale = scale;

                panAndZoomPane.setDeltaY(event.getDeltaY());
                if (panAndZoomPane.deltaY.get() < 0) {
                    scale /= delta;
                } else {
                    scale *= delta;
                }

                double newScale = clamp(scale,MIN_SCALE, MAX_SCALE);

                double f = (scale / oldScale)-1;

                double dx = (event.getX() - (panAndZoomPane.getBoundsInParent().getWidth()/2 + panAndZoomPane.getBoundsInParent().getMinX()));
                double dy = (event.getY() - (panAndZoomPane.getBoundsInParent().getHeight()/2 + panAndZoomPane.getBoundsInParent().getMinY()));

                if (newScale == scale){
                    panAndZoomPane.setPivot(f*dx, f*dy, newScale);
                }

                event.consume();

            }
        };

    }

    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
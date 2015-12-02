package visuals;

import controller.Controller;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import logic.IMap;
import logic.INode;
import logic.Path;

import java.util.ArrayList;
import java.util.HashMap;


public class MapDisplay extends Pane {
    /* constants */
    private double IMAGE_WIDTH = 700;//660
    private double IMAGE_HEIGHT = 525;//495


    /* Data Structures */
    private HashMap<Integer, Circle> id_circle;
    private Controller controller;
    private HashMap<Integer, ArrayList<Line>> lines; /* mapId to line */
    ArrayList<INode> idPath;

    /* Visuals */
    private Transition sts;
    private Transition ste;


    private Image mapImage;
    private ImageView mapView;

    private Color last = Color.TRANSPARENT;
    private Color lastStroke = Color.TRANSPARENT;

    private boolean HIGLIGHTED = false;

    private ArrayList<INode> path; //last set path

    //////////////////////// ICON CODE ///////////////////////////
    private HashMap<Integer, ImageView> id_ICON;


    /**
     * Constructor
     *
     * @param controller
     */
    public MapDisplay(Controller controller) {
        super();
        this.controller = controller;
        this.setStyle("-fx-background-color: #F5F5DC");
        this.mapView = new ImageView();
        this.setMaxWidth(IMAGE_WIDTH);
        this.setMaxHeight(IMAGE_HEIGHT);
        this.id_circle = new HashMap<>();
        this.path = new ArrayList<>();
        this.lines = new HashMap<>();
        this.idPath = new ArrayList<>();
        this.ste = new ScaleTransition();
        this.sts = new ScaleTransition();
    }


    /****************************************************************************************************************
     WORKING CIRCLE CODE
     ****************************************************************************************************************/


    /**
     * Draws the nodes given on the map
     *
     * @param nodes
     */
    public void drawNodes(HashMap<Integer, INode> nodes) {
        //this.id_circle = new HashMap<>(); <-- dont want this

        nodes.forEach((k, v) -> {
            if (id_circle.containsKey(k)) {
                System.out.println("Already Had Circle");
                Circle c = id_circle.get(k);
                this.getChildren().add(c);
            } else {
                Circle c = createCircle(v);
                id_circle.put(k, c);
                normal(c, v);
                this.getChildren().add(c);
            }
        });
    }

    /**
     * Given a MAP_NAME -> ask Controller for map name and nodes for the map [NEW VERSION]
     * Add image to map
     * Then add the Nodes
     *
     * @param map
     */
    public void setMap(IMap map) {

        this.getChildren().remove(0, this.getChildren().size());

        try {
            this.mapImage = new Image(getClass().getResourceAsStream("../images/floorPlans/" + map.getFilePath()));//
        } catch (NullPointerException e) {
            this.mapImage = new Image(getClass().getResourceAsStream("/images/floorPlans/" + map.getFilePath()));//, IMAGE_WIDTH, IMAGE_HEIGHT, true, true
        }

        this.mapView = new ImageView(mapImage);
        this.mapView.setFitHeight(IMAGE_HEIGHT);
        this.mapView.setFitWidth(IMAGE_WIDTH);

        this.getChildren().add(mapView);
        drawNodes(controller.getNodesOfMap(map.getID()));

        mapView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() >= 2) createTempLandmark(e);
        });
    }


    private void createTempLandmark(MouseEvent e) {
        //CREATE TEMPORARY POINT ->
        INode temp = controller.createTempLandmark(e.getX(), e.getY());
        Circle c = createCircle(temp);
        id_circle.put(temp.getID(), c);
        normal(c, temp);
        this.getChildren().add(c);
        controller.handleMapClick(temp);
    }

    public void softSelectAnimation(int id) {
        Circle c = id_circle.get(id); //TODO confirm we dont need to ensure its on the same map
        ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
        st.setByX(1.1f);
        st.setByY(1.1f);
        st.setCycleCount(4);
        st.setAutoReverse(true);
        st.play();
    }

    /**
     * default circle
     *
     * @param c
     */
    public void normal(Circle c, INode v) {
        //ultra jank <-- // TODO: 12/2/15

        try {
            if (v == null) {
                System.out.println("INode is null in normal");
            } else {
                if (!id_circle.containsKey(v.getID())) {
                    c = createCircle(v);
                    id_circle.put(v.getID(), c);
                }

                if (v != null && v.isTransition()) {
                    c.setFill(Color.YELLOW);
                } else {
                    System.out.println("TRYING TO TRANSPARENT");
                    c.setFill(Color.TRANSPARENT);
                }
                c.setStrokeWidth(0);
                c.setRadius(5);
                c.setOpacity(1);
                c.setEffect(null);
            }
        } catch (NullPointerException e){
            System.out.println("TRYING TO HIDE LAST AND THERE IS NO LAST TO HIDE");
        }
    }


    private Circle createCircle(INode v) {

        double x = v.getX();  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
        double y = v.getY();
        Circle circle = new Circle(x, y, 5);

        if (v.isTransition()) {
            circle.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.SECONDARY) && e.getClickCount() == 1) {
                    controller.handleEnterBuilding((logic.Transition) v);
                } else if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                    controller.handleMapClick(v);
                }
            });
        } else {
            circle.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.SECONDARY) && e.getClickCount() == 1) {
                    controller.showNodeImage();
                } else if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                    controller.handleMapClick(v);
                }
            });
        }

        if(v instanceof Path){

        }else {
            circle.setOnMouseEntered(e -> {
                controller.updateNodeInformation(v);
            });
        }

        return circle;
    }



    /**
     * highlights a circle
     *
     * @param c
     * @param color
     * @param colorStroke
     */
    private void highlight(Circle c, Color color, Color colorStroke) {
        c.setFill(color);
        c.setStroke(colorStroke);
        c.setStrokeWidth(1);
    }

    /**
     * Highlights the path of nodes.
     *
     * @param c
     */
    private void highlightPath(Circle c) {
        //c.setFill(Color.web("#00CCFF"));
        // c.setStroke(Color.web("#0018A8"));
        c.setRadius(5);
        highlight(c, Color.BLUE, Color.LIGHTBLUE);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        c.setEffect(ds);
    }


    public void changeBackOldPathNodes(){
        lines.forEach((k,v) -> {
           this.getChildren().removeAll(v);
        });

        id_circle.forEach((k,v) -> {
            normal(id_circle.get(k), controller.getNode(k));
            System.out.println("TRYING TO NORMAL");
        });

       /* for (INode i :idPath){
            normal(id_circle.get(i), i);
            System.out.println("TRYING TO NORMAL");
        }*/
    }

    /**
     * Show path of nodes
     */

    public void createPath(ArrayList<ArrayList<Instructions>> path) {

        changeBackOldPathNodes();


        idPath = new ArrayList<>(); //TODO AM I REMVOING THE OLD LINES
        lines = new HashMap<>();


        double coordX = path.get(0).get(0).getNode().getX();
        double coordY = path.get(0).get(0).getNode().getY();

        for (ArrayList<Instructions> list : path) {
            ArrayList<Line> lineArrayList = new ArrayList<>();
            for (Instructions i : list) {
                /** path nodes highlight blue **/
                highlightPath(id_circle.get(i.getNode().getID()));
                idPath.add(i.getNode());

                /** create all the lines and add them to a list **/
                Line line = new Line();
                line.setStartX(coordX);
                line.setStartY(coordY);
                coordX = i.getNode().getX();
                coordY = i.getNode().getY();
                line.setEndX(coordX);
                line.setEndY(coordY);
                line.setStroke(Color.web("#00CCFF", 0.7));
                line.setStrokeWidth(2);
                line.setStrokeDashOffset(5);
                line.getStrokeDashArray().addAll(2d, 7d);
                lineArrayList.add(line);
                System.out.println("lines created");
            }
            System.out.println("lines put into hashmap");
            lines.put(list.get(0).getNode().getMap_id(), lineArrayList);
        }


    }

    /**
     * Removes the old lines from the last map
     * Draw the new lines for this map
     *
     * @param mapIdOld
     * @param mapIdNew
     */
    public void showLines(int mapIdOld, int mapIdNew) {
    if (mapIdOld != -1) {
        this.getChildren().removeAll(lines.get(mapIdOld));
       // this.getChildren().removeAll(lines.get(mapIdOld));
    }
    else                this.getChildren().addAll(lines.get(mapIdNew));
    }


    /**
     * The color and effect for when a node is set as a destination
     *
     * @param id
     */
    public void setStartNode(int id, boolean START) {
        //NOW WE HAVE TO CHECK IF NODE IS ON THIS MAP
        //I think we should keep all circles
        Circle c;

        if (!id_circle.containsKey(id)){
            c = createCircle(controller.getNode(id));
        } else {
            c = id_circle.get(id);
        }

        c.setRadius(5);

        if (START) {
            highlight(c, Color.GREEN, Color.LIGHTGREEN);
            c.setRadius(5);


            if (sts != null) {
                sts.stop();
             /*   if((Circle)sts.getNode() == c){ return;}//do nothing _
                sts.*/
            }
            ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();
            sts = st;
        } else {
            highlight(c, Color.FIREBRICK, Color.RED);
            if (ste != null) ste.stop();
            c.setRadius(5);

            ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();
            ste = st;
        }
    }


    public void setEndNode(INode v) {
        if (id_circle.containsKey(v.getID())) {
            Circle c = id_circle.get(v.getID());
            c.setRadius(5);
            highlight(c, Color.FIREBRICK, Color.RED);
            id_circle.put(v.getID(), c);
        } else {
            Circle c = createCircle(v);
            c.setRadius(5);
            highlight(c, Color.FIREBRICK, Color.RED);
            id_circle.put(v.getID(), c);
        }
    }


    /**
     * remove node completely from map pane
     * (this is for temporary nodes)
     *
     * @param id
     */
    public void removeNode(int id) {
        if (id_circle.containsKey(id)) {
            //normal(id_circle.get(id), );//hideLast(id)
            this.getChildren().remove(id_circle.remove(id));
            id_circle.remove(id);
        }
    }

    public void hideLast(int id) {
        normal(id_circle.get(id), null);
    }


    /****************************************************************************************************************
                                                 ICON CODE
     ****************************************************************************************************************/


    /**
     * Creates an SVGPath icon (dependent on the node type)
     *
     * @param v
     * @return
     */
    private ImageView createNodeIcon(INode v) {

        double x = v.getX();  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
        double y = v.getY();

        ImageView icon = v.getIcon();
        //Circle circle = new Circle(x, y, 5);
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(.67);
        icon.setScaleY(.67);
        //normal(v);
        icon.setOnMouseClicked(e -> {
            controller.handleMapClick(v);
        });
        return icon;
    }


    /**
     * hides the icon
     *
     * @param c
     */
    public void hideICON(ImageView c) {
        c.setVisible(false);
        //c.setScaleX(.67);
        //c.setScaleY(.67);
        //c.setStrokeWidth(0);
        //c.setRadius(5);
        c.setOpacity(1);
        c.setEffect(null);
    }



/*    private void createTempLandmark(MouseEvent e){
        //CREATE TEMPORARY POINT ->
        INode temp = controller.createTempLandmark(e.getX(), e.getY());
        ImageView c = createNodeIcon(temp);
        id_ICON.put(temp.getID(), c);
        this.getChildren().add(c);
        controller.handleMapClick(temp);
    }*/


    /****************************************************************************************************************
     NOT UPDATED CODE BELOW
     ****************************************************************************************************************/

}
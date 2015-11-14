package MapBuilder;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visuals.Inputs;

/**
 * Created by Xenocidist on 11/12/15.
 */
public class MapBuilderDisplay extends HBox {
    /* constants */
    private static final double GAP = 5;

    /* variables */
    private double height;
    private double width;

    private double input_width;

    /* visual forms */
    VBox options;
    StackPane map_zone;

        /* options */
        VBox mapMenu;
        VBox nodeMenu;
        VBox edgeMenu;

        /* map */
        MapVisual mapvisual;




    /* controller */
    MapBuilderController controller;


    public MapBuilderDisplay(double height, double width, MapBuilderController controller) {
        super();
        this.height = height;
        this.width = width;
        this.input_width = width / 4 - this.GAP * 2;
        this.controller = controller;

        //basic hbox 1000 by 1000
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setMinSize(width / 4, height);

        //left:

        this.options = new VBox();
        options.setMaxWidth(width / 4);
        options.setMinWidth(width/4);
        options.setMaxHeight(height);
        options.setMinHeight(height);
        options.setStyle("-fx-background-color: #336699;");
        create_options();

        //right:
        this.map_zone = new StackPane();
       // map_zone.setMaxWidth(3  * width / 4);
        map_zone.setMinWidth(3  * width / 4);
        map_zone.setMaxHeight(height);
        map_zone.setMinHeight(height);
        map_zone.setAlignment(Pos.TOP_LEFT);
        map_zone.setStyle("-fx-background-color: #444444;");
        //Circle c = new Circle(15);
        //c.setFill(Color.RED);
        this.mapvisual = new MapVisual(controller);
        mapvisual.setAlignment(Pos.TOP_LEFT);
        map_zone.getChildren().addAll(mapvisual);




        this.getChildren().addAll(options, map_zone);
    }

    private void create_options() {
        //create
        createMapMenu();

        //MODE: [map] [Add Nodes] [Add Edges]
        //[Write]  //show only 1 at a time

        //show only one menu at a time


        options.getChildren().addAll(mapMenu);
    }

    private void createMapMenu(){
        this.mapMenu = new VBox();
        mapMenu.setStyle("-fx-background-color: #336699;");

        //ComboBox choose Map
        Inputs chooseMap = new Inputs("maps", input_width );
        chooseMap.setItems(chooseMap.getMaps(controller.getMaps()));
        chooseMap.setOnAction(e -> {
            logic.Map newMap = (logic.Map)chooseMap.getValue();
            controller.setCurrentMap(newMap.getID());
            mapvisual.setMap(newMap);
        });
        chooseMap.setTranslateY(GAP);

        //New Map
        Rectangle divide = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.RED);

        TextField name = new TextField("Enter New Map NAME");
        TextField path = new TextField("Enter New Map PATH");
        TextField ratio = new TextField("Enter New Map Pixel to Foot Ratio");
        Rectangle divide1 = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.GRAY);

        Button createButton = new Button("Create Map!");


        //setting max widths on visual forms
        name.setMaxWidth(input_width - GAP * 2);
        path.setMaxWidth(input_width - GAP * 2);
        ratio.setMaxWidth(input_width - GAP * 2);


        //general menu visuals:

        mapMenu.setSpacing(12);
        mapMenu.getChildren().addAll(chooseMap, divide, name, path, ratio, divide1, createButton);
        mapMenu.getChildren().forEach(n -> {
                    n.setTranslateX(GAP);
                }
        );

    }

    private void createAddNodeMenu(){
        //Current Node information

        //x !E
        //y !E
        //z  E
    }

    private void createEdgeNodeMenu(){
        //Selected NODE id / name

        //list of edges that are going to be added [GREEN TO BE ADDED]

    }

    private void createWriteButton(){
        //button that writes the current node and edges
    }

}


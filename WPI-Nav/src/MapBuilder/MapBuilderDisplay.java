package MapBuilder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import visuals.Inputs;

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
    public Inputs chooseMap;


    /* options */
    public VBox mapMenu;
    public VBox nodeMenu;
    public VBox edgeMenu;

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

        //menus
        this.edgeMenu = new VBox();
        this.nodeMenu = new VBox();
        this.mapMenu = new VBox();


        //basic hbox 1000 by 1000
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setMinSize(width / 4, height);

        //left:

        this.options = new VBox();
        options.setMaxWidth(width / 4);
        options.setMinWidth(width / 4);
        options.setMaxHeight(height);
        options.setMinHeight(height);
        options.setStyle("-fx-background-color: #336699;");
        create_options();
        System.out.println(width / 4);

        //right:
        this.map_zone = new StackPane();
        // map_zone.setMaxWidth(3  * width / 4);
        map_zone.setMinWidth(3 * width / 4);
        map_zone.setMaxHeight(height);
        map_zone.setMinHeight(height);
        map_zone.setAlignment(Pos.TOP_LEFT);
        map_zone.setStyle("-fx-background-color: #444444;");
        this.mapvisual = new MapVisual(controller);
        //mapvisual.setAlignment(Pos.TOP_LEFT);
        map_zone.getChildren().addAll(mapvisual);
        this.getChildren().addAll(options, map_zone);
    }

    private void create_options() {
        //create MODE buttons
        BorderPane background = new BorderPane();
        background.setId("background");

        Region spacer = new Region();
        spacer.setId("spacer");

        HBox modeButtons = new HBox();
        modeButtons.setId("segmented-button-bar");

        Button mapButton = new Button("MAP");
        mapButton.setId("first");
        mapButton.setOnAction(e -> {
            mapvisual.MAP = true;
            mapvisual.EDGE = false;
            mapvisual.NODE = false;
            options.getChildren().removeAll(nodeMenu, edgeMenu, mapMenu);
            options.getChildren().add(mapMenu);
        });

        Button nodeButton = new Button("NODE");
        nodeButton.setOnAction(e -> {
            mapvisual.MAP = false;
            mapvisual.EDGE = false;
            mapvisual.NODE = true;
            options.getChildren().removeAll(mapMenu, edgeMenu, nodeMenu);
            options.getChildren().add(nodeMenu);
        });

        Button edgeButton = new Button("EDGE");
        edgeButton.setId("last");
        edgeButton.setOnAction(e -> {
            mapvisual.MAP = false;
            mapvisual.EDGE = true;
            mapvisual.NODE = false;

            options.getChildren().removeAll(nodeMenu, mapMenu, edgeMenu);
            options.getChildren().add(edgeMenu);
        });


        modeButtons.getChildren().addAll(mapButton, nodeButton, edgeButton);
        modeButtons.setPadding(new Insets(10));

        ToolBar toolBar = new ToolBar(
                spacer,
                modeButtons
        );
        background.setTop(toolBar);


        //create menus
        createMapMenu();
        createNodeMenu();
        createEdgeMenu();


        options.setSpacing(25);
        options.getChildren().addAll(background, mapMenu);
    }

    private void createEdgeMenu() {
        this.edgeMenu = new VBox();
        edgeMenu.setId("menu");

        //status
        StackPane labelBox = new StackPane();
        Rectangle labelBackground = new Rectangle(input_width, 25);
        labelBackground.setFill(Color.LIGHTGRAY);
        Label status = new Label("Select a node, choose edge!");

        status.setFont(Font.font(17));
        status.setTextFill(Color.BLUE);
        labelBackground.setArcHeight(5);
        labelBackground.setArcWidth(5);
        labelBox.getChildren().addAll(labelBackground, status);
        labelBox.setTranslateY(GAP);

        //New Node Information
        Rectangle divide = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.RED);

        TextField name = new TextField("Enter Node Name");
        Rectangle divide1 = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.GRAY);

        //button to create the map -> writes the map to the JSON FILE
        Button saveButton = new Button("Write Nodes Information!");
        saveButton.setOnAction(e -> {
            System.out.println("save button go!");
        });

        //button to create the map -> writes the map to the JSON FILE
        Button deselectButton = new Button("Deselect Node!");
        deselectButton.setOnAction(e -> {
            System.out.println("deselect button go!");
        });

        //setting max widths on visual forms
        name.setMaxWidth(input_width - GAP * 2);

        //general menu visuals:
        edgeMenu.setSpacing(12);
        edgeMenu.getChildren().addAll(labelBox, divide, name, divide1, deselectButton, saveButton);
        edgeMenu.getChildren().forEach(n -> {
            n.setTranslateX(GAP);
        });
        labelBox.setTranslateX(0);
    }


    private void createNodeMenu() {
        this.nodeMenu = new VBox();
        nodeMenu.setId("menu");

        //status
        StackPane labelBox = new StackPane();
        Rectangle labelBackground = new Rectangle(input_width, 25);
        labelBackground.setFill(Color.LIGHTGRAY);
        Label status = new Label("Click to Add Nodes!");
        status.setFont(Font.font(17));
        status.setTextFill(Color.BLUE);
        labelBackground.setArcHeight(5);
        labelBackground.setArcWidth(5);
        labelBox.getChildren().addAll(labelBackground, status);
        labelBox.setTranslateY(GAP);


        //New Node Information
        Rectangle divide = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.RED);

        TextField name = new TextField("Enter Node Name");
        Rectangle divide1 = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.GRAY);

        //button to create the map -> writes the map to the JSON FILE
        Button saveButton = new Button("Save Nodes Information!");
        saveButton.setOnAction(e -> {
            System.out.println("save button go!");
        });

        //button to create the map -> writes the map to the JSON FILE
        Button deselectButton = new Button("Deselect Node!");
        deselectButton.setOnAction(e -> {
            System.out.println("deselect button go!");
        });

        //setting max widths on visual forms
        name.setMaxWidth(input_width - GAP * 2);

        //general menu visuals:
        nodeMenu.setSpacing(12);
        nodeMenu.getChildren().addAll(labelBox, divide, name, divide1, deselectButton, saveButton);
        nodeMenu.getChildren().forEach(n -> {
            n.setTranslateX(GAP);
        });
        labelBox.setTranslateX(0);
    }

    private void createMapMenu() {
        this.mapMenu = new VBox();
        mapMenu.setId("menu");

        //status
        StackPane labelBox = new StackPane();
        Rectangle labelBackground = new Rectangle(input_width, 25);
        labelBackground.setFill(Color.LIGHTGRAY);
        Label status = new Label("Create or Choose a Map!");
        status.setFont(Font.font(17));
        status.setTextFill(Color.BLUE);
        labelBackground.setArcHeight(5);
        labelBackground.setArcWidth(5);
        labelBox.getChildren().addAll(labelBackground, status);
        labelBox.setTranslateY(GAP);

        //ComboBox choose Map
        this.chooseMap = new Inputs("maps", input_width);
        chooseMap.setItems(chooseMap.getMaps(controller.getMaps()));
        chooseMap.setOnAction(e -> {
            logic.Map newMap = (logic.Map) chooseMap.getValue();
            controller.setCurrentMap(newMap.getID());
            mapvisual.setMap(newMap);
            status.setText("Map Loaded!");
            status.setTextFill(Color.GREEN);
        });

        //New Map
        Rectangle divide = new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.RED);

        TextField name =     new TextField("Enter New Map NAME");
        TextField path =     new TextField("Enter Map Name W/O Extenstion");
        TextField ratio =    new TextField("Enter New Map Pixel to Foot Ratio");
        Rectangle divide1 =  new Rectangle(input_width, 2);
        divide.setArcHeight(2);
        divide.setArcWidth(2);
        divide.setFill(Color.GRAY);

        //button to create the map -> writes the map to the JSON FILE
        Button createButton = new Button("Create Map!");
        createButton.setOnAction(e -> {
            Double ratioD = 1.0;
            try {
                ratioD = Double.parseDouble(ratio.getText());
            } catch (NumberFormatException n) {
                ratio.setText("Failed to convert double");
            }

            if (controller.createAndWriteNewMap(name.getText(), path.getText(), ratioD)) {
                status.setText("Map Created!");
                status.setTextFill(Color.GREEN);
                path.clear();
                ratio.clear();
                name.clear();
            } else {
                status.setText("Validation Failed!");
                status.setTextFill(Color.RED);
            }
        });


        //setting max widths on visual forms
        name.setMaxWidth(input_width - GAP * 2);
        path.setMaxWidth(input_width - GAP * 2);
        ratio.setMaxWidth(input_width - GAP * 2);


        //general menu visuals:
        mapMenu.setSpacing(12);
        mapMenu.getChildren().addAll(labelBox, chooseMap, divide, name, path, ratio, divide1, createButton);
        mapMenu.getChildren().forEach(n -> {
            n.setTranslateX(GAP);
        });
        labelBox.setTranslateX(0);

    }
}


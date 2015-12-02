package MapBuilder;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visuals.Inputs;

import java.util.Collection;

public class MapBuilderDisplay extends StackPane {
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
	HBox TopBar;

	/* map */
	MapVisual mapvisual;

	MapVisualSecondary mapvisualSecondary;
	public Inputs chooseMapTwo;


	/* controller */
	MapBuilderController controller;
	Label nodeInformation;
	Label edgeInformation;


	public MapBuilderDisplay(Stage stage, double width, double height, MapBuilderController controller) {
		super();
		this.height = height;
		this.width = width;
		this.input_width = width / 4 - this.GAP * 2;
		this.controller = controller;

		// basic hbox 1000 by 1000
		// this.setMaxHeight(height);
		// this.setMaxWidth(width);
		// this.setMinSize(width / 4, height);

		create_TopBar();

		// right:
		this.map_zone = new StackPane();
		// map_zone.setMaxWidth(3 * width / 4);
		//map_zone.setMinWidth(width * 1.0);
		//map_zone.setMaxWidth(width * 1.0);
		// map_zone.setMaxHeight(height);
		//map_zone.setMinHeight(height);
		//map_zone.setAlignment(Pos.TOP_LEFT);
		//map_zone.getChildren().addAll(mapvisual);


		this.mapvisual = new MapVisual(controller);
		this.mapvisualSecondary = new MapVisualSecondary(controller);

		//System.out.println("map_zone width = " + map_zone.getWidth());

		map_zone.prefWidthProperty().bind(this.widthProperty());

		HBox sections = new HBox();
		sections.setSpacing(50);
		sections.setStyle("-fx-background-color: #eeeeee;");
		sections.prefHeightProperty().bind(stage.heightProperty());
		sections.prefWidthProperty().bind(stage.widthProperty());

		sections.getChildren().addAll(mapvisual, mapvisualSecondary);



		VBox content = new VBox();

		content.getChildren().addAll(TopBar, sections);
		content.setAlignment(Pos.TOP_LEFT);
		content.prefHeightProperty().bind(stage.heightProperty());
		content.prefWidthProperty().bind(stage.widthProperty());
		this.getChildren().addAll(content);
	}

	private void create_TopBar() {
		// Initialize the topBar
		this.TopBar = new HBox();
		// this.TopBar = new HBox();
		TopBar.setId("top-bar");

		
		// TODO: Fix this to work with new classes
		
		
		// This makes the drop-down for selecting different maps
		this.chooseMap = new Inputs("maps", input_width, controller);
		
		chooseMap.setItems(chooseMap.convertMaps(controller.getMaps()));
		chooseMap.setOnAction(e -> {
			controller.deselectNode();
			controller.saveNodesToMaster();

			Collection<logic.IMap> mapSet = controller.getMaps().values();
			
			logic.IMap newMap = new logic.Floor();
			
			for(logic.IMap map : mapSet){
				if(map.getFilePath().equals(chooseMap.getValue())){
					newMap = (logic.IMap) map;
				}
			}

			controller.setCurrentMap(newMap.getID());
			mapvisual.setMap(newMap);
			chooseMapTwo.setVisible(true);
		});

		this.chooseMapTwo = new Inputs("maps", input_width, controller);

		chooseMapTwo.setItems(chooseMapTwo.convertMaps(controller.getMaps()));
		chooseMapTwo.setOnAction(e -> {
			controller.deselectNode();


			Collection<logic.IMap> mapSet = controller.getMaps().values();

			logic.IMap newMap = new logic.Floor();

			for(logic.IMap map : mapSet){
				if(map.getFilePath().equals(chooseMapTwo.getValue())){
					newMap = (logic.IMap) map;
				}
			}

			controller.setCurrentMapTwo(newMap.getID());
			mapvisualSecondary.setMap(newMap);
		});




		Button closeButton = new Button("X");
		closeButton.setOnAction(e -> {
			// Save the maps and nodes to the JSON files
			controller.mapsToFile();
			controller.nodesToFile();

			// Close the application
			Platform.exit();
		});


		final ToggleGroup group = new ToggleGroup();

		ToggleButton toggleButtonOn = new ToggleButton("Snapping On");
		ToggleButton toggleButtonOff = new ToggleButton("Snapping Off");

		toggleButtonOn.setToggleGroup(group);
		toggleButtonOff.setToggleGroup(group);

		toggleButtonOn.setSelected(true);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
								Toggle toggle, Toggle new_toggle) {
				if (new_toggle != null){
					if (toggleButtonOn.selectedProperty().getValue()){
						controller.SNAPPING = true;
					} else {
						controller.SNAPPING = false;
					}
				}

			}
		});


		nodeInformation = new Label();
		edgeInformation = new Label();

		chooseMapTwo.setVisible(false);
		chooseMap.setTranslateX(10);
		TopBar.setSpacing(15);
		TopBar.getChildren().addAll(chooseMap, chooseMapTwo, toggleButtonOn, toggleButtonOff, nodeInformation, edgeInformation, closeButton);
		
		/*AnchorPane.setRightAnchor(closeButton, 5.0);
		AnchorPane.setLeftAnchor(chooseMap, 5.0);
		AnchorPane.setLeftAnchor(chooseMapTwo, 600.0);*/

	}

	public void setNodeLabel(String s){
		nodeInformation.setText(s);
	}

	public void setEdgeLabel(String s){
		edgeInformation.setText(s);
	}

}



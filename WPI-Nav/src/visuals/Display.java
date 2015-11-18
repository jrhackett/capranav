package visuals;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;


public class Display {
	/* logger */
	private final Logger logger = LoggerFactory.getLogger(Display.class);

	/* constants */
	private static final double WIDTH_BUFFER = 15;
	private static final double HEIGHT_BUFFER = 15;
	private static final double GAP = 5;
	private static final double BUTTON_SIZE = 26;
	private static double TABLE_WIDTH;
	private static final double TABLE_HEIGHT = 150;
	private static final double INPUT_WIDTH = 160;

	private Double width;
	private Double height;

	/* variables */
	public Controller controller;
	private boolean MENU_VISIBLE = false;

    /* visuals */
	private Scene scene;
	private AnchorPane root;
	private VBox inputs;
	private	OptionsMenu options;
	private	Rectangle divide;
	//private Map map; //TODO CHANGE THIS

	private StackPane mapPane;
	public MapDisplay mapDisplay;
	private Inputs chooseMap;
	private TableView<Instructions> instructions;
	public Inputs start;
	public Inputs end;


	/**
	 * Basic constructor.
	 * @param width
	 * @param height
	 */
	public Display(Double width, Double height, Controller controller){
        root = new AnchorPane();
        this.width = width;
        this.height = height;
		this.controller = controller;
	}

	/**
	 * This sets up the GUI interface - both visuals and interactions.
	 * @return
	 */
	public Scene Init(){

		/* side - panel: inputs + divisor + options + divisor + buttons */
		VBox side_panel = new VBox();
		side_panel.setTranslateX(WIDTH_BUFFER);
		side_panel.setTranslateY(HEIGHT_BUFFER);
		side_panel.setSpacing(2 * GAP);

		/* button panel */
		StackPane button_panel = createButtonPane();

		/* stack pane of back ground and vbox of buttons */
		StackPane input_panel = createInputsPane();

		/* visual divide */
		this.divide = new Rectangle(INPUT_WIDTH + GAP + BUTTON_SIZE, 2);
		divide.setArcHeight(2);
		divide.setArcWidth(2);
		divide.setFill(Color.GRAY);
		divide.setVisible(false);

		/* options */
		this.options = new OptionsMenu(INPUT_WIDTH, 300);
		options.setVisible(false);
		//options.setTranslateX(WIDTH_BUFFER);
		//options.setTranslateY(HEIGHT_BUFFER + (MAX_INPUTS) * (BUTTON_SIZE + GAP) + 75); //this is some bad bad stuff

		/* map */
		this.mapPane = createMapPane();

		/*
		this.map = new Map( (width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER), this.controller);

		*/
		/* instructions */
		this.TABLE_WIDTH = (width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2);

		TableView<Instructions> instructions = createInstructionsTable();
		instructions.setTranslateX(width - TABLE_WIDTH - WIDTH_BUFFER);
		instructions.setTranslateY(height - TABLE_HEIGHT - HEIGHT_BUFFER);

		/* image */
		double dimension = width - (TABLE_WIDTH + 2 * WIDTH_BUFFER + GAP);
		ImageDisplay imageDisplay = new ImageDisplay(dimension);

		StackPane sp = new StackPane();
		sp.getChildren().add(imageDisplay);
		sp.setTranslateY(height - (HEIGHT_BUFFER + dimension));
		sp.setTranslateX(WIDTH_BUFFER);


		side_panel.getChildren().addAll(input_panel, options); //divide, button_panel, //TODO add buttons back
		/* build */
        root.getChildren().addAll(side_panel, instructions, mapPane);
        scene = new Scene(root, width, height);
        return scene;
	}


	private StackPane createMapPane(){
		StackPane mapPane = new StackPane();
		mapPane.setPrefHeight((height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER));
		mapPane.setMaxHeight((height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER));
		mapPane.setMinHeight(450);
		mapPane.setPrefWidth(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2);
		mapPane.setMaxWidth(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2);
		mapPane.setMinWidth(600);
		mapPane.setStyle("-fx-background-color: #eeeeee");
		this.mapDisplay = new MapDisplay( this.controller); //(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER),
		mapPane.getChildren().add(mapDisplay);
		mapPane.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
		mapPane.setTranslateY(HEIGHT_BUFFER);
		return mapPane;
	}

	/**
	 * Creates/returns/sets input panel
	 * @return Input Panel
	 */
	private StackPane createInputsPane() {

		StackPane pane = new StackPane();

		/* background */
		Rectangle background = new Rectangle(INPUT_WIDTH, (2) * (30 + GAP), Color.web("#638CA6", .5));
		background.setArcWidth(5);
		background.setArcHeight(5);
		background.setStroke(Color.web("#BFD4D9", .5));
		background.setStrokeWidth(2);

		/* vbox of buttons */
		inputs = new VBox();
		inputs.setSpacing(GAP);

		/* select map input */
		Label mapDescriptor = new Label("Select a map!");
		mapDescriptor.setMinWidth(INPUT_WIDTH);
		mapDescriptor.setMaxWidth(INPUT_WIDTH);

		/* start */
		this.start = new Inputs("Search WPI Maps", INPUT_WIDTH);
		start.setOnAction(e -> {
			if (start.getValue() != null && !start.getValue().toString().isEmpty()) {
				logic.Node node = (logic.Node) start.getValue();
				/*
				if (controller.endNode != null){
					mapDisplay.clearSelection(node.getID());
				}
				*/
				controller.startNode = node;

				mapDisplay.setStartNode(node.getID());

				if (controller.FLAG) {
					if (controller.endNode != null) {
						mapDisplay.clearNodesEdges(node.getID(), controller.endNode.getID());
					} else {
						mapDisplay.clearNodesEdges(node.getID(), -1);
					}
				}

				if (controller.startNode != null && controller.endNode != null) {findPaths();}
			}
		});

		/* end */
		this.end = new Inputs("For Destination", INPUT_WIDTH);
		end.setOnAction(e -> {



			if (end.getValue() != null && !end.getValue().toString().isEmpty() && controller.startNode != null) {
				logic.Node node = (logic.Node) end.getValue();

				/*
				if (controller.endNode != null){
					mapDisplay.clearSelection(node.getID());
				}
				*/
				mapDisplay.setStartNode(node.getID());

				if (controller.FLAG) {
					if (controller.startNode != null) {
						mapDisplay.clearNodesEdges(node.getID(), controller.startNode.getID());
					} else {
						mapDisplay.clearNodesEdges(node.getID(), -1);
					}
				}

				controller.endNode = node;
				if (controller.startNode != null && controller.endNode != null){findPaths();}

			}
		});

		//ComboBox choose Map
		this.chooseMap = new Inputs("maps", INPUT_WIDTH);
		chooseMap.setItems(chooseMap.getMaps(controller.getMaps().getMaps()));

		chooseMap.setOnAction(e -> {
			if (chooseMap.getValue() != null && !chooseMap.getValue().toString().isEmpty()) {
				logic.Map newMap = (logic.Map) chooseMap.getValue();
				controller.setCurrentMap(newMap.getID());
				mapDisplay.setMap(newMap);

				start.setItems(start.convertNodes(controller.getNamedNodesOfMap()));
				end.setItems(start.convertNodes(controller.getNamedNodesOfMap()));

				controller.endNode = null;
				controller.startNode = null;
			}
		});

		/* select start input */
		Label startDescriptor = new Label("Select a Starting Location!");
		mapDescriptor.setMinWidth(INPUT_WIDTH);
		mapDescriptor.setMaxWidth(INPUT_WIDTH);


		/* select end input */
		Label endDescriptor = new Label("Select an Ending Location!");
		mapDescriptor.setMinWidth(INPUT_WIDTH);
		mapDescriptor.setMaxWidth(INPUT_WIDTH);

		this.inputs.getChildren().addAll(mapDescriptor, chooseMap, startDescriptor,start,endDescriptor, end);

		pane.getChildren().addAll( inputs); /* background taken out for now */

		DropShadow ds = new DropShadow();
		ds.setOffsetX(.3);
		ds.setOffsetY(.3);
		ds.setColor(Color.LIGHTGRAY);
		pane.setEffect(ds);

		return pane;

	}


	/**
	 * Creates a the button panel
	 * 	-> background
	 * 	-> panel of buttons
	 *
	 * @return button panel
	 */
	private StackPane createButtonPane(){
		StackPane pane = new StackPane();

		Rectangle stack_pane_background = new Rectangle(INPUT_WIDTH, (30 + GAP)); /* background */
		stack_pane_background.setArcHeight(5);
		stack_pane_background.setArcWidth(5);
		stack_pane_background.setFill(Color.LIGHTGREY);
		stack_pane_background.setOpacity(.7);

		HBox buttonPanel = new HBox();
		buttonPanel.setSpacing(GAP);
		buttonPanel.setMaxWidth(INPUT_WIDTH);

		/* add button */
		Button addButton = new Button("+", 30);
		addButton.setOnMouseClicked(e -> addAnotherSlot());

		/* check button */
		Button checkButton = new Button("✓", 30);
		checkButton.setOnMouseClicked(e -> findPaths());

		/* refresh button */
		Image refresh;
		try {
			refresh =  new Image(getClass().getResourceAsStream("../images/refresh.png"), 35, 35, true, true);
		}
		catch (NullPointerException e) {
			refresh =  new Image(getClass().getResourceAsStream("/images/refresh.png"), 35, 35, true, true);
		}

		ImageView refreshView = new ImageView(refresh);
		Button questionButton = new Button(refreshView, "refresh", 30);
		questionButton.setOnMouseClicked(e -> refreshInformation());

		/* menu button */
		VBox bars = new VBox();
		bars.setSpacing(3);
		for (int i = 0; i < 3; i++){
			Rectangle bar = new Rectangle(BUTTON_SIZE - 5, 4);
			bar.setArcHeight(4);
			bar.setArcWidth(4);
			bar.setFill(Color.web("#404040"));
			bars.getChildren().add(bar);
		}
		Button menuButton = new Button(bars, "menu", 30);
		menuButton.setOnMouseClicked(e -> showMenu());

		/* email button
		Button emailButton = new Button("@", 30);
		emailButton.setOnMouseClicked(e -> sendEmail());
		*/

		//addButton

		buttonPanel.getChildren().addAll(checkButton); //, menuButton, questionButtonTODO add this back
		pane.getChildren().addAll(stack_pane_background, buttonPanel);
		buttonPanel.setAlignment(Pos.CENTER);
		pane.setAlignment(Pos.CENTER_LEFT);
		return pane;

	}

	/**
	 * find paths calls the controller
	 */
	private void findPaths(){
		this.controller.findPaths();
	}


	/**
	 * refresh all data
	 */
	private void refreshInformation(){
		this.root.getChildren().remove(this.mapPane);
		this.mapPane.getChildren().remove(this.mapDisplay);
		this.mapPane = createMapPane();
		this.root.getChildren().add(this.mapPane);

		this.instructions.setItems(null);
		this.chooseMap.getSelectionModel().clearSelection();
		this.start.getSelectionModel().clearSelection();
		this.end.getSelectionModel().clearSelection();
		this.controller.reset();
		//this.start
		//this.end
		//this.mapvisual.removePath
		//this.instructions.remove
	}

	/**
	 *
	 */
	private void showMenu(){
		if (!MENU_VISIBLE) {
			this.options.setVisible(true);
			this.divide.setVisible(true);
			MENU_VISIBLE = true;
		} else {
			MENU_VISIBLE = false;
			this.options.setVisible(false);
			this.divide.setVisible(false);
		}
	}

	/**
	 *
	 */


	private void sendEmail(){
		TextInputDialog dialog = new TextInputDialog("Type Here");
		dialog.setTitle("WPI Navigational App");
		dialog.setHeaderText("Directions will be sent to your email");
		dialog.setContentText("Please enter your email:");

	// Traditional way
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		}

	// The Java 8 way
		result.ifPresent(name -> System.out.println("Email: " + name));

	}
	/**
	 * Adds another input slot to inputs
	 * If there are already  MAX_INPUTS - do nothing
	 * Event is logged
	 */
	private void addAnotherSlot(){}
		/*
		if (controller.current_mid_way_points < controller.max_mid_way_points){

			*//* first input slot*//*
			HBox inputSlot = new HBox();
			inputSlot.setSpacing(GAP);

			Button addButton = new Button("-", 25);
			addButton.setOnMouseClicked(e -> removeThisSlot(inputSlot));
			addButton.setVisible(false);

			*//* start *//*
			Inputs next = new Inputs("Mid-Way Point", INPUT_WIDTH);
			next.setOnMouseEntered(e -> {
					addButton.setVisible(true);
			});

			inputSlot.getChildren().addAll(next, addButton);
			inputs.getChildren().add(controller.current_mid_way_points + 1, inputSlot);

			controller.current_mid_way_points++;
			logger.info("Mid-Way Point Added");

		} else {
			logger.info("Max inputs added");
		}

	}
*/
	/**
	 * Removes this slot -> should prompt re-validation and re-display
	 */
/*	private void removeThisSlot(Node node){
		inputs.getChildren().remove(node);
		controller.current_mid_way_points--;
	}*/
	
	/**
	 * Creates the InstructionsTable
	 * Somewhere in here must add an event that maps each instruction row to a node to a bool to an image
	 */
	private TableView<Instructions> createInstructionsTable() {
		this.instructions = new TableView<>();
		instructions.setMinWidth(TABLE_WIDTH);
		instructions.setMaxWidth(TABLE_WIDTH);
		instructions.setMinHeight(TABLE_HEIGHT);
		instructions.setMaxHeight(TABLE_HEIGHT);
		instructions.getColumns().addAll(Instructions.getColumn(instructions));
		instructions.setColumnResizePolicy(
	            TableView.CONSTRAINED_RESIZE_POLICY
		        );
		return instructions;
	}

	/**
	 * Call to set the instructions
	 */
	public void setInstructions(ArrayList<logic.Node> nodes, ArrayList<String> instructions){
		ObservableList<Instructions> data = FXCollections.observableArrayList();

		for (int i = 0; i < nodes.size(); i++){
			data.add(new Instructions(instructions.get(i), nodes.get(i)));
		}

		this.instructions.setItems(data);
	}

}

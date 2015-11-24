package visuals;

import controller.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

	final BooleanProperty firstTime = new SimpleBooleanProperty(true);

    /* visuals */
	private Scene scene;
	//private StackPane root;
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



	/*****************************************************************/
	/** New Values / Variables for UI Rework **/

	//Constants & Variables
	private static final double CONTROL_WIDTH = 40;
	private static final double expandedWidth = 150;
	private static final double MAP_WIDTH = 700;
	private static final double MAP_BORDER = 15;
	private static final double TITLE_HEIGHT = 35;

	private BooleanProperty MISC_VISIBLE;
	//Visual Elements
	VBox dashBoard;
	SlidingVBox slidingDashboard;
	VBox directions;
	StackPane root;
	HBox dashBoardTitleBox;




	/**
	 * Basic constructor.
	 * @param width
	 * @param height
	 */
	public Display(Double width, Double height, Controller controller){
        //root = new AnchorPane();
        root = new StackPane();
		root.setId("root");
		//root.getStylesheets().add("style.css");
		//root.applyCss();

		this.width = width;
        this.height = height;
		this.controller = controller;
		this.dashBoard = new VBox();
		this.directions = new VBox();
		this.MISC_VISIBLE.setValue(true);
	}

	/**
	 * This sets up the GUI interface - both visuals and interactions.
	 * @return
	 */
	public Scene Init() {

		/*****************************************************************/

		//Stuff that is NOT visible when slid in!
		HBox divider_0 = createDivider();
		HBox divider_1 = createDivider();
		HBox divider_2 = createDivider();
		HBox divider_3 = createDivider();

		Image pin =	new Image(getClass().getResourceAsStream("../images/pin.png"), 30, 30, true, true);
		ImageView pinView = new ImageView(pin);

		Image info =	new Image(getClass().getResourceAsStream("../images/info.png"), 30, 30, true, true);
		ImageView infoView = new ImageView(info);

		Image gears =	new Image(getClass().getResourceAsStream("../images/gears.png"), 30, 30, true, true);
		ImageView gearsView = new ImageView(gears);

		divider_0.visibleProperty().bind(MISC_VISIBLE);
		divider_1.visibleProperty().bind(MISC_VISIBLE);
		divider_2.visibleProperty().bind(MISC_VISIBLE);
		divider_3.visibleProperty().bind(MISC_VISIBLE);

		pinView.visibleProperty().bind(MISC_VISIBLE);
		infoView.visibleProperty().bind(MISC_VISIBLE);
		gearsView.visibleProperty().bind(MISC_VISIBLE);







		/*****************************************************************/
		/** Dashboard **/
		dashBoardTitleBox = new HBox();
		dashBoardTitleBox.setStyle("-fx-background-color: #333333");
		dashBoardTitleBox.setMinHeight(TITLE_HEIGHT);
		dashBoardTitleBox.setMaxHeight(TITLE_HEIGHT);
		dashBoardTitleBox.setPrefHeight(TITLE_HEIGHT);
		dashBoardTitleBox.setAlignment(Pos.CENTER_LEFT);

		Label dashBoardTitleLabel = new Label("Dashboard");
		dashBoardTitleLabel.setTextFill(Color.web("#eeeeee"));
		dashBoardTitleBox.getChildren().addAll(dashBoardTitleLabel);

		HBox divider_1 = createDivider();

		dashBoard.getChildren().addAll(dashBoardTitleBox, divider_1);


		/*****************************************************************/
		/** DashboardControlBox **/
		VBox dashBoardControlBox = new VBox();


		VBox bars = new VBox();
		bars.setSpacing(3);
		for (int i = 0; i < 4; i++) {
			Rectangle bar = new Rectangle(CONTROL_WIDTH - 17, 3);
			bar.setArcHeight(3);
			bar.setArcWidth(3);
			bar.setFill(Color.web("#eeeeee"));
			bars.getChildren().add(bar);
		}
		//bars.setTranslateX(8);
		//bars.setTranslateY(7);


		dashBoardControlBox.getChildren().addAll(bars);

		dashBoardControlBox.setStyle("-fx-background-color: #333333");
		dashBoardControlBox.setMaxWidth(CONTROL_WIDTH);
		dashBoardControlBox.setPrefWidth(CONTROL_WIDTH);
		dashBoardControlBox.setMinWidth(CONTROL_WIDTH);


		SlidingVBox slidingDashboard = new SlidingVBox(expandedWidth, MISC_VISIBLE, bars, dashBoardTitleBox, divider_1);
		slidingDashboard.setStyle("-fx-background-color: #333333");
		slidingDashboard.setMaxWidth(expandedWidth);
		slidingDashboard.setPrefWidth(expandedWidth);
		slidingDashboard.setMinWidth(0);


		/** STYLE BUTTON HERE **/ //TODO review here
		javafx.scene.control.Button button = slidingDashboard.getButton();
		button.setId("dashboardButton");
		button.setTranslateX(0);
		button.setTranslateY(0);

		/*
		button.setStyle("-fx-padding: 0px;" +
						"-fx-alignment: CENTER;" +
				        "-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");
		*/
		dashBoardControlBox.getChildren().add(button);
		/*****************************************************************/
		/** Directions **/
		HBox directionsTitleBox = new HBox();
		directionsTitleBox.setStyle("-fx-background-color: #ac2738");
		directionsTitleBox.setMinHeight(TITLE_HEIGHT);
		directionsTitleBox.setMaxHeight(TITLE_HEIGHT);
		directionsTitleBox.setPrefHeight(TITLE_HEIGHT);
		directionsTitleBox.setAlignment(Pos.CENTER_LEFT);


		Label directionsTitleLabel = new Label("Directions");
		//dashBoardTitleLabel.setStyle("-fx-font-family: 'HelveticaNeue'; -fx-font-size: 20");
		//directionsTitleLabel.setFont(Font.loadFont("file:resources/fonts/HelveticaNeue-Light.otf", 20));
		//directionsTitleLabel.setFont(Font.loadFont("file:resources/fonts/HelveticaNeue-Light.otf", 20));
		directionsTitleLabel.setTextFill(Color.web("#eeeeee"));

		directionsTitleBox.getChildren().addAll(directionsTitleLabel);

		directions.getChildren().addAll(directionsTitleBox);
		directions.setStyle("-fx-background-color: #ffffff");
		directions.setMaxWidth(expandedWidth);
		directions.setPrefWidth(expandedWidth);
		directions.setMinWidth(0);



		/*****************************************************************/
		/** DirectionsControlBox **/
		VBox directionsControlBox = new VBox();
		HBox directionsControlBoxBox = new HBox();

		Image directionsArrow =	new Image(getClass().getResourceAsStream("../images/forward.png"), 30, 30, true, true);
		ImageView directionsArrowView = new ImageView(directionsArrow);

		//directionsControlBoxBox.getChildren().addAll(directionsArrowView);
		directionsControlBoxBox.setStyle("-fx-background-color: #ac2738");
		directionsControlBoxBox.setMinHeight(TITLE_HEIGHT);

		/*
		SlidingVBox slidingDirections = new SlidingVBox(expandedWidth, directionsArrowView, directions);
		slidingDirections.setStyle("-fx-background-color: #ffffff");
		slidingDirections.setMaxWidth(expandedWidth);
		slidingDirections.setPrefWidth(expandedWidth);
		slidingDirections.setMinWidth(0);
*/

		directionsControlBoxBox.getChildren().add(directionsArrowView);

		directionsArrowView.setTranslateX(5);
		directionsArrowView.setTranslateY(2);

		directionsControlBox.getChildren().addAll(directionsControlBoxBox);
		directionsControlBox.setMinWidth(CONTROL_WIDTH);
		directionsControlBox.setMaxWidth(CONTROL_WIDTH);
		directionsControlBox.setPrefWidth(CONTROL_WIDTH);
		directionsControlBox.setStyle("-fx-background-color: #ffffff");

		/*****************************************************************/
		/** Map **/

		VBox map = new VBox();

		/*
		HBox mapTitle = new HBox();
		mapTitle.setMaxHeight(TITLE_HEIGHT);
		mapTitle.setPrefHeight(TITLE_HEIGHT);
		mapTitle.setMinHeight(0);
		mapTitle.setAlignment(Pos.CENTER);
		mapTitle.setStyle("-fx-background-color: #555555");
		*/
		HBox mapTitle = new HBox();


		/** Label **/
		Label mapTitleLabel = new Label("CapraNav");
		mapTitleLabel.setTextFill(Color.web("#eeeeee"));
		//ATTENTION: below is some nice trixksz! it binds the location of the title to the center
		mapTitleLabel.translateXProperty().bind((mapTitle.widthProperty().subtract(mapTitleLabel.widthProperty()).divide(2)));
		mapTitleLabel.translateYProperty().bind((mapTitle.heightProperty().subtract(mapTitleLabel.heightProperty()).divide(2)));

		mapTitle.setMaxHeight(TITLE_HEIGHT);
		mapTitle.setPrefHeight(TITLE_HEIGHT);
		mapTitle.setMinHeight(0);
		mapTitle.setStyle("-fx-background-color: #444444");
		mapTitle.getChildren().add(mapTitleLabel);


		map.setMinWidth(MAP_WIDTH);
		map.setPrefWidth(MAP_WIDTH+MAP_BORDER*2);

		map.setMinHeight(MAP_WIDTH+TITLE_HEIGHT);
		map.setPrefHeight(MAP_WIDTH+MAP_BORDER*2+TITLE_HEIGHT);

		map.getChildren().addAll(mapTitle);
		map.setStyle("-fx-background-color:#eeeeee ;");
		/*****************************************************************/
		/** Add to the Section **/
		HBox sections = new HBox();
		HBox.setHgrow(map, Priority.ALWAYS);
		HBox.setHgrow(dashBoard, Priority.SOMETIMES);
		HBox.setHgrow(directions, Priority.SOMETIMES);

		sections.setStyle("-fx-background-color: #333333");
		sections.getChildren().addAll(dashBoardControlBox, slidingDashboard, directionsControlBox, directions, map);
		/*****************************************************************/
		/** Add sections to Root */
		StackPane root = new StackPane();
		root.getChildren().add(sections);

		/*****************************************************************/
		/** create scene **/
		root.setAlignment(Pos.TOP_LEFT);

		Scene scene = new Scene(root, MAP_WIDTH+MAP_BORDER*2+CONTROL_WIDTH*2+expandedWidth*2, MAP_WIDTH);//+MAP_BORDER*2+TITLE_HEIGHT

	/*	scene.widthProperty().addListener(
				new ChangeListener() {
					public void changed(ObservableValue observable,
										Object oldValue, Object newValue) {
						Double width = (Double)newValue;
						root.setPrefWidth(width);
					}
				});

		scene.heightProperty().addListener(
				new ChangeListener() {
					public void changed(ObservableValue observable,
										Object oldValue, Object newValue) {
						Double height = (Double)newValue;
						root.setPrefHeight(height);
					}
				});*/




		return scene;

	}


	private HBox createDivider(){
		HBox divide = new HBox();
		divide.setStyle("-fx-background-color: #888888");


		/* setting sizes */
		divide.setMinWidth(0);
		divide.setMaxWidth(expandedWidth - CONTROL_WIDTH);
		divide.setPrefWidth(expandedWidth - CONTROL_WIDTH);

		divide.setMinHeight(1);
		divide.setMaxHeight(1);
		divide.setPrefHeight(1);
		/* binding size */

//		divide.translateXProperty().bind((slidingDashboard.widthProperty().subtract(divide.widthProperty()).divide(2)));

		return divide;
	}







		public Scene oldInit() {
		/*****************************************************************/
		/** side - panel: inputs + divisor + options + divisor + buttons */
		VBox side_panel = new VBox();
		side_panel.setTranslateX(WIDTH_BUFFER);
		side_panel.setTranslateY(HEIGHT_BUFFER);
		side_panel.setSpacing(2 * GAP);

		/** button panel **/
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
		start.setOnAction(e -> handleInput(start, true));

		/* end */
		this.end = new Inputs("For Destination", INPUT_WIDTH);
		end.setOnAction(e ->handleInput(end, false));


/*		private Popover<PopOver, Label> controller = new Po();

		PopOver popOver = new PopOver();
		Circle c = new Circle(5);
		popOver.show(c);*/

		//popOver.show(circle);
		//p//opOver.setContentNode(new Text(v.toString()));

		//TODO: the map combo box will soon be gone
		this.chooseMap = new Inputs("maps", INPUT_WIDTH);
		chooseMap.setItems(chooseMap.convertMaps(controller.getMaps().getMaps()));

		chooseMap.setOnAction(e -> {
			try {
				if (chooseMap.getValue() != null && !chooseMap.getValue().toString().isEmpty()) {
					logic.Map newMap = (logic.Map) chooseMap.getValue();
					controller.setCurrentMap(newMap.getID());
					mapDisplay.setMap(newMap);
					clearInstructions();
					start.setItems(start.convertNodes(controller.getNamedNodesOfMap()));
					end.setItems(end.convertNodes(controller.getNamedNodesOfMap()));

					controller.endNode = null;
					controller.startNode = null;
				}
			} catch (ClassCastException  cce) {
				/***   only a partial string currently -> no mapping to a node  ***/
				System.out.println("NOT A NODE: " + start.getValue());
			}
		});

		/***************************** Auto Complete Search *******************************/
		AutoCompleteComboBoxListener searchMap = new AutoCompleteComboBoxListener(chooseMap);
		AutoCompleteComboBoxListener searchStart = new AutoCompleteComboBoxListener(start);
		AutoCompleteComboBoxListener searchEnd = new AutoCompleteComboBoxListener(end);

		this.chooseMap.setPlaceholder(new Label("Search or Select Map"));
		this.start.setPlaceholder(new Label("Search or Select Starting Location"));
		this.end.setPlaceholder(new Label("Search or Select End Location"));

		this.chooseMap.setPromptText("Search or Select Map");
		this.start.setPromptText("Search or Select Map");
		this.end.setPromptText("Search or Select Map");

		this.chooseMap.focusedProperty().addListener(((observable, oldValue, newValue) -> {
			if(newValue && firstTime.get()){
				inputs.requestFocus();
				firstTime.setValue(false);
			}
		}));



		/* select start input */
		Label startDescriptor = new Label("Select a Starting Location!");
		mapDescriptor.setMinWidth(INPUT_WIDTH);
		mapDescriptor.setMaxWidth(INPUT_WIDTH);


		/* select end input */
		Label endDescriptor = new Label("Select an Ending Location!");
		mapDescriptor.setMinWidth(INPUT_WIDTH);
		mapDescriptor.setMaxWidth(INPUT_WIDTH);

		//mapDescriptor
		this.inputs.getChildren().addAll(mapDescriptor, chooseMap, startDescriptor, start, endDescriptor, end);

		pane.getChildren().addAll( inputs); /* background taken out for now */

		DropShadow ds = new DropShadow();
		ds.setOffsetX(.3);
		ds.setOffsetY(.3);
		ds.setColor(Color.LIGHTGRAY);
		pane.setEffect(ds);

		return pane;
	}

	private void handleInput(Inputs v, boolean START){
		if (v.getValue() != null && !v.getValue().toString().isEmpty()) {
			try {
				logic.Node node = (logic.Node) v.getValue();
				if (START) controller.startNode = node;
				else controller.endNode = node;

				mapDisplay.setStartNode(node.getID(), true);

				if (controller.FLAG) {
					if (controller.endNode != null) {
						mapDisplay.clearNodesEdges(node.getID(), controller.endNode.getID());
					} else {
						mapDisplay.clearNodesEdges(node.getID(), -1);
					}
				}

				if (controller.startNode != null && controller.endNode != null) {
					findPaths();
				}


			} catch (ClassCastException cce) {
				/***   only a partial string currently -> no mapping to a node  ***/
				System.out.println("NOT A NODE: " + v.getValue());
			}
				/*
				if (controller.endNode != null){
					mapDisplay.clearSelection(node.getID());
				}
				*/
		}
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
		instructions.setPlaceholder(new Label(" "));
		instructions.getColumns().addAll(Instructions.getColumn(instructions));
		instructions.setColumnResizePolicy(
	            TableView.CONSTRAINED_RESIZE_POLICY
		        );
		return instructions;
	}

	public void clearInstructions(){
		this.instructions.setItems(null);
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

package visuals;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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


public class Display {
	/* logger */
	private final Logger logger = LoggerFactory.getLogger(Display.class);

	/* constants */
	private static final double WIDTH_BUFFER = 15;
	private static final double HEIGHT_BUFFER = 15;
	private static final double GAP = 5.5;
	private static final double BUTTON_SIZE = 26;
	private static final Color BACKGROUND_COLOR = Color.web("a5adb0");
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
	private Map map;


	/**
	 * Currently bogus testing. Later will call instance of Controller to get info from logic.
	 * @return Instructions
	 */
	private static ObservableList<Instructions> getInstructionsTest(){
		ObservableList<Instructions> data = FXCollections.observableArrayList();


		/* Here we need an array/struct with strings of instructions in it */
		for (int i = 0; i < 10; i++){
			data.add(new Instructions("Go North!!", 50));
		}

		return data;

	}

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
		this.map = new Map( (width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER), this.controller);
		map.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
		map.setTranslateY(HEIGHT_BUFFER);

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


		side_panel.getChildren().addAll(button_panel, input_panel, divide, options);
		/* build */
        root.getChildren().addAll(side_panel, instructions, map);
        scene = new Scene(root, width, height, BACKGROUND_COLOR);
        return scene;
	}


	/**
	 * Creates/returns/sets input panel
	 * @return Input Panel
	 */
	private StackPane createInputsPane() {

		StackPane pane = new StackPane();

		/* background */
		Rectangle background = new Rectangle(INPUT_WIDTH, (controller.current_mid_way_points + 2) * (30 + GAP), Color.web("#638CA6", .5));
		background.setArcWidth(5);
		background.setArcHeight(5);
		background.setStroke(Color.web("#BFD4D9", .5));
		background.setStrokeWidth(2);

		/* vbox of buttons */
		inputs = new VBox();
		inputs.setSpacing(GAP);


		/* start */
		Inputs start = new Inputs("Search WPI Maps", INPUT_WIDTH);

		/* end */
		Inputs end = new Inputs("For Destination", INPUT_WIDTH);

		this.inputs.getChildren().addAll(start, end);

		pane.getChildren().addAll(inputs); /* background taken out for now */

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
		Image refresh =  new Image(getClass().getResourceAsStream("images/refresh.png"), 35, 35, true, true);
		ImageView refreshView = new ImageView(refresh);
		Button questionButton = new Button(refreshView, "refresh", 30);

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


		buttonPanel.getChildren().addAll(checkButton, addButton, menuButton, questionButton);
		pane.getChildren().addAll(stack_pane_background, buttonPanel);
		buttonPanel.setAlignment(Pos.CENTER);
		pane.setAlignment(Pos.CENTER_LEFT);
		return pane;
	}

	/**
	 * This will kick everything off!
	 * We can later change it so other things trigger this.
	 * We also have to think about clearing things
	 */
	private void findPaths(){
		//String name = controller.getMapName();
		//map.setMap(name);
		map.drawPath();
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
	 * Adds another input slot to inputs
	 * If there are already  MAX_INPUTS - do nothing
	 * Event is logged
	 */
	private void addAnotherSlot(){
		if (controller.current_mid_way_points < controller.max_mid_way_points){

			/* first input slot*/
			HBox inputSlot = new HBox();
			inputSlot.setSpacing(GAP);

			Button addButton = new Button("-", 25);
			addButton.setOnMouseClicked(e -> removeThisSlot(inputSlot));
			addButton.setVisible(false);

			/* start */
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

	/**
	 * Removes this slot -> should prompt re-validation and re-display
	 */
	private void removeThisSlot(Node node){
		inputs.getChildren().remove(node);
		controller.current_mid_way_points--;
	}
	
	/**
	 * Creates the InstructionsTable
	 * Somewhere in here must add an event that maps each instruction row to a node to a bool to an image
	 */
	private TableView<Instructions> createInstructionsTable() {
		TableView<Instructions> instructions = new TableView<>();
		instructions.setMinWidth(TABLE_WIDTH);
		instructions.setMaxWidth(TABLE_WIDTH);
		instructions.setMinHeight(TABLE_HEIGHT);
		instructions.setMaxHeight(TABLE_HEIGHT);
		instructions.getColumns().addAll(Instructions.getColumn(instructions));
		instructions.setItems(getInstructionsTest());
		instructions.setColumnResizePolicy(
	            TableView.CONSTRAINED_RESIZE_POLICY
		        );
		return instructions;
	}

}

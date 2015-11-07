package visuals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
	private static final double TABLE_WIDTH = 600;
	private static final double TABLE_HEIGHT = 200;
	private static final double INPUT_WIDTH = 160;

	private static final int MAX_INPUTS = 3;
		
	private Double width;
	private Double height; 
	
	/* variables */
    private int NUMBER_INPUTS = 0;

    /* visuals */
	private Scene scene;
	private AnchorPane root;
	private VBox inputs;
	

	
	/* simply for testing!!!! */
	private static ObservableList<Instructions> getInstructionsTest(){
		ObservableList<Instructions> data = FXCollections.observableArrayList();
		
		for (int i = 0; i < 10; i++){
			data.add(new Instructions("Go North!!", 50));
		}
		
		return data;
		
	}
	
	public Display(Double width, Double height){
        root = new AnchorPane();
        this.width = width;
        this.height = height;
	}
	
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
		Rectangle divide = new Rectangle(160 + GAP + BUTTON_SIZE, 2);
		divide.setArcHeight(2);
		divide.setArcWidth(2);
		divide.setFill(Color.GRAY);

		/*
		inputs = new VBox();
		inputs.getChildren().addAll(inputSlot, inputSlotTwo);
		inputs.setTranslateX(WIDTH_BUFFER);
		inputs.setTranslateY(HEIGHT_BUFFER);
		inputs.getChildren().add(divide);
		*/


		/* options */
		OptionsMenu options = new OptionsMenu(INPUT_WIDTH, 300);
		options.setTranslateX(WIDTH_BUFFER);
		options.setTranslateY(HEIGHT_BUFFER + (MAX_INPUTS) * (BUTTON_SIZE + GAP) + 75); //this is some bad bad stuff

		/* map */
		Map map = new Map((height - TABLE_HEIGHT - GAP - 2 * HEIGHT_BUFFER), (width - GAP * 2 - BUTTON_SIZE - 160 - WIDTH_BUFFER * 2));
		map.setTranslateX(WIDTH_BUFFER + GAP * 2 + 160 + BUTTON_SIZE);	
		map.setTranslateY(HEIGHT_BUFFER);
		
		/* instructions */
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


		side_panel.getChildren().addAll(button_panel, input_panel);
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
		Rectangle background = new Rectangle(INPUT_WIDTH, (NUMBER_INPUTS + 2) * (30 + GAP), Color.web("#638CA6", .5));
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
	 * Creates a StackPane
	 * 	-> background
	 * 	-> panel of buttons
	 *
	 * @return StackPane
	 */
	private StackPane createButtonPane(){
		StackPane pane = new StackPane();

		Rectangle stack_pane_background = new Rectangle(INPUT_WIDTH, (30 + GAP)); /* background */
		stack_pane_background.setArcHeight(5);
		stack_pane_background.setArcWidth(5);
		stack_pane_background.setFill(Color.web("#17A697", .7));

		HBox buttonPanel = new HBox();
		buttonPanel.setSpacing(GAP);
		buttonPanel.setMaxWidth(INPUT_WIDTH);

		/* add button */
		Button addButton = new Button("+");
		addButton.setOnMouseClicked(e -> addAnotherSlot());

		/* check/ button */
		Button checkButton = new Button("✓");

		/* /refresh button */
		Image refresh =  new Image(getClass().getResourceAsStream("images/refresh.png"), 35, 35, true, true);
		ImageView refreshView = new ImageView(refresh);
		Button questionButton = new Button(refreshView, "refresh");

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
		Button menuButton = new Button(bars, "menu");

		buttonPanel.getChildren().addAll(checkButton, addButton, menuButton, questionButton);
		pane.getChildren().addAll(stack_pane_background, buttonPanel);
		buttonPanel.setAlignment(Pos.CENTER);
		return pane;
	}


	/**
	 * Adds another input slot to inputs
	 * If there are already  MAX_INPUTS - do nothing
	 * Event is logged
	 */
	private void addAnotherSlot(){
		if (NUMBER_INPUTS < MAX_INPUTS){
			
			/* first input slot*/
			HBox inputSlot = new HBox();
			inputSlot.setSpacing(GAP);
			
			/* start */
			Inputs next = new Inputs("Mid-Way Point", INPUT_WIDTH);
			Button addButton = new Button("-");
			addButton.setOnMouseClicked(e -> removeThisSlot(inputSlot));
			//inputSlot.getChildren().addAll(next, addButton);
			inputs.getChildren().add(NUMBER_INPUTS + 1, next);
			
			NUMBER_INPUTS++;
		}
		logger.info("Max inputs added");
	}
	
	/**
	 * Removes this slot -> should prompt re-validation and re-display
	 */
	private void removeThisSlot(javafx.scene.Node node){
		inputs.getChildren().remove(node);
		NUMBER_INPUTS--;
	}
	
	/**
	 * Creates the InstructionsTable
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

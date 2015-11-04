package visuals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
	private static final int MAX_INPUTS = 5;
		
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
		
		/* first input slot*/
		HBox inputSlot = new HBox();
		inputSlot.setSpacing(GAP);
		
		/* start */
		Inputs start = new Inputs("Starting Location?");
		Label addButton = button("+", start.getMaxWidth(), 0);
		addButton.setOnMouseClicked(e -> addAnotherSlot());
		inputSlot.getChildren().addAll(start, addButton);
		
		/* second input slot */
		HBox inputSlotTwo = new HBox();
		inputSlotTwo.setSpacing(GAP);

		/* end */
		Inputs end = new Inputs("Destination!");
		Label minusButton = button("✓", start.getMaxWidth(), 0);
		inputSlotTwo.getChildren().addAll(end, minusButton);
		
		/* visual divide */
		Rectangle divide = new Rectangle(160 + GAP + BUTTON_SIZE, 2);
		divide.setArcHeight(2);
		divide.setArcWidth(2);
		divide.setFill(Color.GRAY);		
		
		/* start/end location section */
		inputs = new VBox();
		inputs.getChildren().addAll(inputSlot, inputSlotTwo);
		inputs.setSpacing(GAP);
		inputs.setTranslateX(WIDTH_BUFFER);
		inputs.setTranslateY(HEIGHT_BUFFER);
		inputs.getChildren().add(divide);
	
		
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

		
		/* build */
        root.getChildren().addAll(inputs, instructions, map);
        scene = new Scene(root, width, height, BACKGROUND_COLOR);
        return scene;
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
			Inputs next = new Inputs("Mid-Way Point");
			Label addButton = button("-", next.getMaxWidth(), 0);
			addButton.setOnMouseClicked(e -> removeThisSlot(inputSlot));
			inputSlot.getChildren().addAll(next, addButton);
			inputs.getChildren().add(NUMBER_INPUTS + 1, inputSlot);
			
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
		TableView<Instructions> instructions = new TableView<Instructions>();
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

	
	private Label button(String content, double width, double height){
		Label button = new Label(content);
		button.getStyleClass().add("my_button");		
		button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
		button.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);
		
		DropShadow ds = new DropShadow();
		ds.setOffsetX(.2);
		ds.setOffsetY(.2);
		ds.setColor(Color.GRAY);
		button.setEffect(ds);
		
		return button;
	}
}

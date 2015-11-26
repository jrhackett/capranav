package visuals;

import controller.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class Display {
	/* logger */
	private final Logger logger = LoggerFactory.getLogger(Display.class);

	/* constants */
	private static final double WIDTH_BUFFER = 15;
	private static final double HEIGHT_BUFFER = 15;
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
	//private TableView<Instructions> instructions;
	public Inputs start;
	public Inputs end;



	/*****************************************************************/
	/** New Values / Variables for UI Rework **/

	//Constants & Variables
	private static final double CONTROL_WIDTH = 30;
	private static final double GAP = 5;
	private final double EDGE;
	private static final double expandedWidth = 170;//150
	private static final double MAP_WIDTH = 660;
    private static final double MAP_HEIGHT = 495;
    private static final double MAP_BORDER = 15;

	private BooleanProperty VISIBLE;
	private boolean EMAIL = false;
	//Visual Elements
	VBox dashBoard;
	SlidingAnchorPane slidingDashboard;
	AnchorPane directions;
	VBox map;
	StackPane root;
	HBox dashBoardTitleBox;
	private ListView<Instructions> instructions;




	/**
	 * Basic constructor.
	 */
	public Display(Controller controller){
        root = new StackPane();
		root.setId("root");

		this.controller = controller;
		this.directions = new AnchorPane();
        this.map        = new VBox();
        this.slidingDashboard = new SlidingAnchorPane();

		this.VISIBLE = new SimpleBooleanProperty(true);
		this.EDGE = GAP * 2 + CONTROL_WIDTH;
	}

	/**
	 * This sets up the GUI interface - both visuals and interactions.
	 * @return
	 */
	public Scene Init() {
		/*****************************************************************/
		/** Sliding Dashboard **/
		initSlidingDashBoard();

		/*****************************************************************/
		/** Directions VBox **/
		initDirections();

		/*****************************************************************/
		/** Map **/
		initMap();

		/*****************************************************************/
		/** Add to the Section **/
		HBox sections = new HBox();
		HBox.setHgrow(map, Priority.ALWAYS);
		HBox.setHgrow(slidingDashboard, Priority.SOMETIMES);
		HBox.setHgrow(directions, Priority.SOMETIMES);


		sections.setStyle("-fx-background-color: #333333");
		sections.getChildren().addAll(slidingDashboard, directions, map); //dashBoardControlBox
		/*****************************************************************/
		/** Add sections to Root */
		StackPane root = new StackPane();
		root.getChildren().add(sections);

		/*****************************************************************/
		/** create scene **/
		root.setAlignment(Pos.TOP_LEFT);

		Scene scene = new Scene(root, MAP_WIDTH + MAP_BORDER * 2 + EDGE * 2 + expandedWidth * 2, MAP_HEIGHT + 2 * MAP_BORDER + EDGE);//+MAP_BORDER*2+TITLE_HEIGHT
        //Scene scene = new Scene(root, MAP_WIDTH+MAP_BORDER*2+EDGE*2+expandedWidth*2, MAP_WIDTH + 2 * EDGE);//+MAP_BORDER*2+TITLE_HEIGHT

		return scene;

	}
	/****************************************************************************************************************
	          FUNCTIONS SETTING UP BASE DESIGN THAT ALSO CALL THE SPECIFIC CREATION OF FURTHER VISUAL ELEMENTS
	 ****************************************************************************************************************/

	private void initSlidingDashBoard(){
		/*****************************************************************/

		/** dividers **/
		HBox divider_0 = createDivider();
		HBox divider_1 = createDivider();
		HBox divider_2 = createDivider();
		HBox divider_3 = createDivider();

		divider_0.visibleProperty().bind(VISIBLE);
		divider_1.visibleProperty().bind(VISIBLE);
		divider_2.visibleProperty().bind(VISIBLE);
		divider_3.visibleProperty().bind(VISIBLE);

		AnchorPane.setTopAnchor(divider_0, EDGE + 1);
		AnchorPane.setLeftAnchor(divider_0, GAP);
		AnchorPane.setRightAnchor(divider_0, GAP);

		AnchorPane.setTopAnchor(divider_1, 3 * EDGE + 1);
		AnchorPane.setLeftAnchor(divider_1, GAP);
		AnchorPane.setRightAnchor(divider_1, GAP);

		AnchorPane.setTopAnchor(divider_2, 6 * EDGE + 1);
		AnchorPane.setLeftAnchor(divider_2, GAP);
		AnchorPane.setRightAnchor(divider_2, GAP);

		AnchorPane.setBottomAnchor(divider_3, EDGE - 1);
		AnchorPane.setLeftAnchor(divider_3, GAP);
		AnchorPane.setRightAnchor(divider_3, GAP);

		/** images **/
		//Image pin =				new Image(getClass().getResourceAsStream("../images/pin.png"), 20, 20, true, true);
		//ImageView pinView = 	new ImageView(pin);
		SVGPath pinView = new SVGPath();
		pinView.setContent("M233.292,0c-85.1,0-154.334,69.234-154.334,154.333c0,34.275,21.887,90.155,66.908,170.834" +
				"c31.846,57.063,63.168,104.643,64.484,106.64l22.942,34.775l22.941-34.774c1.317-1.998,32.641-49.577,64.483-106.64" +
				"c45.023-80.68,66.908-136.559,66.908-170.834C387.625,69.234,318.391,0,233.292,0z M233.292,233.291c-44.182,0-80-35.817-80-80" +
				"s35.818-80,80-80c44.182,0,80,35.817,80,80S277.473,233.291,233.292,233.291z");
		pinView.setScaleX(.05);
		pinView.setScaleY(.05);
		pinView.setTranslateX(-50 * 2.5 - 15);
		pinView.setTranslateY(-50 * 4.5);
		pinView.setId("pinView");

		//Image info =			new Image(getClass().getResourceAsStream("../images/info.png"), 20, 20, true, true);
		//ImageView infoView = 	new ImageView(info);
		SVGPath infoView = new SVGPath();
		infoView.setContent("M254.26,0C113.845,0,0,113.845,0,254.26s113.845,254.26,254.26,254.26"+
		"s254.26-113.845,254.26-254.26S394.675,0,254.26,0z M286.042,381.39c0,17.544-14.239,31.782-31.782,31.782"+
		"s-31.782-14.239-31.782-31.782V222.477c0-17.544,14.239-31.782,31.782-31.782s31.782,14.239,31.782,31.782V381.39z"+
		"M254.26,159.421c-17.544,0-31.782-14.239-31.782-31.782s14.239-31.782,31.782-31.782s31.782,14.239,31.782,31.782"+
		"S271.804,159.421,254.26,159.421z");
		infoView.setScaleX(.05);
		infoView.setScaleY(.05);
		infoView.setTranslateX(-50 * 4.5 - 9);
		infoView.setTranslateY(-50 * 2 - 12);
		infoView.setId("pinView");


		Image gears =			new Image(getClass().getResourceAsStream("../images/gears.png"), 20, 20, true, true);
		ImageView gearsView = 	new ImageView(gears);
		//SVGPath gearsView = new SVGPath();
		/*
		gearsView.setContent("M61.2,341.538c4.9,16.8,11.7,33,20.3,48.2l-24.5,30.9c-8,10.1-7.1,24.5,1.9,33.6l42.2,42.2c9.1,9.1,23.5,9.899,33.6,1.899" +
				"l30.7-24.3c15.8,9.101,32.6,16.2,50.1,21.2l4.6,39.5c1.5,12.8,12.3,22.4,25.1,22.4h59.7c12.8,0,23.6-9.601,25.1-22.4l4.4-38.1" +
				"c18.8-4.9,36.8-12.2,53.7-21.7l29.7,23.5c10.1,8,24.5,7.1,33.6-1.9l42.2-42.2c9.1-9.1,9.9-23.5,1.9-33.6l-23.1-29.3" +
				"c9.6-16.601,17.1-34.3,22.1-52.8l35.6-4.1c12.801-1.5,22.4-12.3,22.4-25.1v-59.7c0-12.8-9.6-23.6-22.4-25.1l-35.1-4.1" +
				"c-4.801-18.3-12-35.8-21.199-52.2l21.6-27.3c8-10.1,7.1-24.5-1.9-33.6l-42.1-42.1c-9.1-9.1-23.5-9.9-33.6-1.9l-26.5,21" +
				"c-17.2-10.1-35.601-17.8-54.9-23l-4-34.3c-1.5-12.8-12.3-22.4-25.1-22.4h-59.7c-12.8,0-23.6,9.6-25.1,22.4l-4,34.3" +
				"c-19.8,5.3-38.7,13.3-56.3,23.8l-27.5-21.8c-10.1-8-24.5-7.1-33.6,1.9l-42.2,42.2c-9.1,9.1-9.9,23.5-1.9,33.6l23,29.1" +
				"c-9.2,16.6-16.2,34.3-20.8,52.7l-36.8,4.2c-12.8,1.5-22.4,12.3-22.4,25.1v59.7c0,12.8,9.6,23.6,22.4,25.1L61.2,341.538z" +
				" M277.5,180.038c54.4,0,98.7,44.3,98.7,98.7s-44.3,98.7-98.7,98.7c-54.399,0-98.7-44.3-98.7-98.7S223.1,180.038,277.5,180.038z" +
				"M867.699,356.238l-31.5-26.6c-9.699-8.2-24-7.8-33.199,0.9l-17.4,16.3c-14.699-7.1-30.299-12.1-46.4-15l-4.898-24" +
				"c-2.5-12.4-14-21-26.602-20l-41.1,3.5c-12.6,1.1-22.5,11.4-22.9,24.1l-0.799,24.4c-15.801,5.7-30.701,13.5-44.301,23.3" +
				"l-20.799-13.8c-10.602-7-24.701-5-32.9,4.7l-26.6,31.7c-8.201,9.7-7.801,24,0.898,33.2l18.201,19.399" +
				"c-6.301,14.2-10.801,29.101-13.4,44.4l-26,5.3c-12.4,2.5-21,14-20,26.601l3.5,41.1c1.1,12.6,11.4,22.5,24.1,22.9l28.1,0.899" +
				"c5.102,13.4,11.801,26.101,19.9,38l-15.699,23.7c-7,10.6-5,24.7,4.699,32.9l31.5,26.6c9.701,8.2,24,7.8,33.201-0.9l20.6-19.3" +
				"c13.5,6.3,27.699,11,42.299,13.8l5.701,28.2c2.5,12.4,14,21,26.6,20l41.1-3.5c12.6-1.1,22.5-11.399,22.9-24.1l0.9-27.601" +
				"c15-5.3,29.199-12.5,42.299-21.399l22.701,15c10.6,7,24.699,5,32.9-4.7l26.6-31.5c8.199-9.7,7.799-24-0.9-33.2l-18.301-19.399" +
				"c6.701-14.2,11.602-29.2,14.4-44.601l25-5.1c12.4-2.5,21-14,20-26.601l-3.5-41.1c-1.1-12.6-11.4-22.5-24.1-22.9l-25.1-0.8" +
				"c-5.201-14.6-12.201-28.399-20.9-41.2l13.699-20.6C879.4,378.638,877.4,364.438,867.699,356.238z M712.801,593.837" +
				"c-44.4,3.801-83.602-29.3-87.301-73.699c-3.801-44.4,29.301-83.601,73.699-87.301c44.4-3.8,83.602,29.301,87.301,73.7" +
				"C790.301,550.938,757.199,590.138,712.801,593.837z" +
				"M205,704.438c-12.6,1.3-22.3,11.899-22.4,24.6l-0.3,25.3c-0.2,12.7,9.2,23.5,21.8,25.101l18.6,2.399" +
				"c3.1,11.301,7.5,22.101,13.2,32.301l-12,14.8c-8,9.899-7.4,24.1,1.5,33.2l17.7,18.1c8.9,9.1,23.1,10.1,33.2,2.3l14.899-11.5" +
				"c10.5,6.2,21.601,11.101,33.2,14.5l2,19.2c1.3,12.6,11.9,22.3,24.6,22.4l25.301,0.3c12.699,0.2,23.5-9.2,25.1-21.8l2.3-18.2" +
				"c12.601-3.101,24.601-7.8,36-14l14,11.3c9.9,8,24.101,7.4,33.201-1.5l18.1-17.7c9.1-8.899,10.1-23.1,2.301-33.2L496.6,818.438" +
				"c6.6-11,11.701-22.7,15.201-35l16.6-1.7c12.6-1.3,22.299-11.9,22.4-24.6l0.299-25.301c0.201-12.699-9.199-23.5-21.799-25.1" +
				"l-16.201-2.1c-3.1-12.2-7.699-24-13.699-35l10.1-12.4c8-9.9,7.4-24.1-1.5-33.2l-17.699-18.1c-8.9-9.101-23.102-10.101-33.201-2.3" +
				"l-12.101,9.3c-11.399-6.9-23.6-12.2-36.399-15.8l-1.601-15.7c-1.3-12.601-11.899-22.3-24.6-22.4l-25.3-0.3" +
				"c-12.7-0.2-23.5,9.2-25.101,21.8l-2,15.601c-13.199,3.399-25.899,8.6-37.699,15.399l-12.5-10.2c-9.9-8-24.101-7.399-33.201,1.5" +
				"l-18.2,17.801c-9.1,8.899-10.1,23.1-2.3,33.199l10.7,13.801c-6.2,11-11.1,22.699-14.3,35L205,704.438z M368.3,675.837" +
				"c36.3,0.4,65.399,30.301,65,66.601c-0.4,36.3-30.301,65.399-66.601,65c-36.3-0.4-65.399-30.3-65-66.601" +
				"C302.1,704.538,332,675.438,368.3,675.837z");
				*/

		//gearsView.setScaleX(.5);
		//gearsView.setScaleY(.5);
		//gearsView.setTranslateX(50 * 9 + 9);
		//gearsView.setTranslateY(50 * 1 + 5);
		//gearsView.setId("pinView");


		pinView.visibleProperty().bind(VISIBLE);
		infoView.visibleProperty().bind(VISIBLE);
		gearsView.visibleProperty().bind(VISIBLE);

		AnchorPane.setTopAnchor(pinView, 1 * EDGE + GAP + 5);
		AnchorPane.setLeftAnchor(pinView, GAP);

		//AnchorPane.setTopAnchor(infoView, 3 * EDGE + GAP + 5); //<--- TODO notice this, these lines break a lot of stuff, no idea why
		//AnchorPane.setLeftAnchor(infoView, GAP);

		AnchorPane.setBottomAnchor(gearsView, 0.0 + GAP * 2);//TODO these will change with svg
		AnchorPane.setLeftAnchor(gearsView, GAP * 2);

		/*****************************************************************/
		/** Dashboard **/
		dashBoardTitleBox = new HBox();
		dashBoardTitleBox.setStyle("-fx-background-color: #333333");
		dashBoardTitleBox.setMinHeight(EDGE);
		dashBoardTitleBox.setMaxHeight(EDGE);
		dashBoardTitleBox.setPrefHeight(EDGE);
		dashBoardTitleBox.setAlignment(Pos.CENTER_LEFT);

		Label dashBoardTitleLabel = new Label("Dashboard");
		dashBoardTitleLabel.setTextFill(Color.web("#eeeeee"));

		dashBoardTitleBox.getChildren().addAll(dashBoardTitleLabel);
		dashBoardTitleBox.setMinWidth(0);
		dashBoardTitleBox.setPrefWidth(expandedWidth);
		dashBoardTitleBox.setMaxWidth(expandedWidth);

		/*****************************************************************/
		/** DashboardControlBox **/
		VBox bars = new VBox();
		bars.setSpacing(3);
		for (int i = 0; i < 4; i++) {
			Rectangle bar = new Rectangle(CONTROL_WIDTH - 7, 3);
			bar.setArcHeight(3);
			bar.setArcWidth(3);
			bar.setFill(Color.web("#eeeeee"));
			bars.getChildren().add(bar);
		}

		AnchorPane.setTopAnchor(dashBoardTitleBox, 0.0);
		AnchorPane.setLeftAnchor(dashBoardTitleBox, EDGE);

		/*****************************************************************/
		/** Section Labels **/
		/** Location **/
		HBox locationLabelBox = new HBox();
		locationLabelBox.setMinHeight(EDGE);
		locationLabelBox.setMaxHeight(EDGE);
		locationLabelBox.setPrefHeight(EDGE);
		locationLabelBox.setAlignment(Pos.CENTER_LEFT);

		Label locationLabel = new Label("Locations");
		locationLabel.setTextFill(Color.web("#eeeeee"));

		locationLabelBox.getChildren().addAll(locationLabel);
		locationLabelBox.setMinWidth(0);
		locationLabelBox.setPrefWidth(expandedWidth);
		locationLabelBox.setMaxWidth(expandedWidth);

		AnchorPane.setTopAnchor(locationLabelBox, 1 * EDGE + GAP * .5);
		AnchorPane.setLeftAnchor(locationLabelBox, EDGE);

        /** Inputs **/
        VBox inputs = createInput();
        AnchorPane.setTopAnchor(inputs, 1 * EDGE + GAP * .5 + GAP * 1.5);
        AnchorPane.setLeftAnchor(inputs, EDGE);



		/** Resources **/
		HBox resourcesLabelBox = new HBox();
		resourcesLabelBox.setMinHeight(EDGE);
		resourcesLabelBox.setMaxHeight(EDGE);
		resourcesLabelBox.setPrefHeight(EDGE);
		resourcesLabelBox.setAlignment(Pos.CENTER_LEFT);

		Label resourcesLabel = new Label("Resources");
		resourcesLabel.setTextFill(Color.web("#eeeeee"));

		resourcesLabelBox.getChildren().addAll(resourcesLabel);
		resourcesLabelBox.setMinWidth(0);
		resourcesLabelBox.setPrefWidth(expandedWidth);
		resourcesLabelBox.setMaxWidth(expandedWidth);

		AnchorPane.setTopAnchor(resourcesLabelBox, 3 * EDGE + GAP * .5);
		AnchorPane.setLeftAnchor(resourcesLabelBox, EDGE);

		/*****************************************************************/
		/** Change Settings Zone **/
		HBox settingsLabelBox = new HBox();
		settingsLabelBox.setMinHeight(EDGE);
		settingsLabelBox.setMaxHeight(EDGE);
		settingsLabelBox.setPrefHeight(EDGE);
		settingsLabelBox.setAlignment(Pos.CENTER_LEFT);

		Label settingsLabel = new Label("Settings");
		settingsLabel.setTextFill(Color.web("#eeeeee"));

		settingsLabelBox.getChildren().addAll(settingsLabel);
		settingsLabelBox.setMinWidth(0);
		settingsLabelBox.setPrefWidth(expandedWidth);
		settingsLabelBox.setMaxWidth(expandedWidth);

		AnchorPane.setBottomAnchor(settingsLabelBox, 0.0);// 2 * EDGE - 2 * GAP - 20);
		AnchorPane.setLeftAnchor(settingsLabelBox, EDGE);

		settingsLabel.setOnMouseClicked(e -> handleSettings());
		gearsView.setOnMouseClicked(e -> handleSettings());

		/*****************************************************************/
		/** Building of Sliding Dashboard Anchorpane  **/
		this.slidingDashboard = new SlidingAnchorPane(expandedWidth, EDGE, VISIBLE, bars, divider_0, divider_1, divider_2, divider_3, gearsView, dashBoardTitleBox,locationLabelBox, resourcesLabelBox, infoView, settingsLabelBox, pinView, inputs); //, , ,
		slidingDashboard.setStyle("-fx-background-color: #333333");

		/** STYLE BUTTON HERE **/
		javafx.scene.control.Button button = slidingDashboard.getButton();
		button.setId("dashboardButton");
		button.setMaxWidth(EDGE);
		button.setMinWidth(EDGE);
		button.setPrefWidth(EDGE);
		AnchorPane.setTopAnchor(button, 0.0);
		AnchorPane.setLeftAnchor(button, 0.0);
        //slidingDashboard.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + 2 * EDGE);
		slidingDashboard.getChildren().addAll(button);
	}

	private void initDirections(){
		/**
		 * There should be no need to modify this into an AnchorPane, hopefully
		 * all we need to do is add the Email Directions button and Label after the TableView
		 * and set the tableview min height to be ~200
		 *
		 *
		 * UPDATE: it would be a lot better to make this an AnchorPane ];
		 **/

		/** Title Box **/
		HBox directionsTitleBox = new HBox();
		directionsTitleBox.setStyle("-fx-background-color: #ac2738");
		directionsTitleBox.setMinHeight(EDGE);
		directionsTitleBox.setMaxHeight(EDGE);
		directionsTitleBox.setPrefHeight(EDGE);
		directionsTitleBox.setAlignment(Pos.CENTER_LEFT);
		directionsTitleBox.setSpacing(GAP*3);

		VBox directionsControlBox = new VBox();
		Image directionsArrow =	new Image(getClass().getResourceAsStream("../images/forward.png"), 30, 30, true, true);
		ImageView directionsArrowView = new ImageView(directionsArrow);
		directionsArrowView.setTranslateX(5);
		directionsArrowView.setTranslateY(2);
		directionsControlBox.getChildren().addAll(directionsArrowView);
		directionsControlBox.setStyle("-fx-background-color: #ac2738");
		directionsControlBox.setMinHeight(EDGE);


		/** Label **/
		Label directionsTitleLabel = new Label("Directions");
		directionsTitleLabel.setTextFill(Color.web("#eeeeee"));
		directionsTitleBox.getChildren().addAll(directionsControlBox, directionsTitleLabel);


		AnchorPane.setTopAnchor(directionsTitleBox, 0.0);
		AnchorPane.setLeftAnchor(directionsTitleBox, 0.0);
		AnchorPane.setRightAnchor(directionsTitleBox, 0.0);


		/** TableView **/
		createInstructionListView();

		AnchorPane.setTopAnchor(instructions, EDGE);
		AnchorPane.setLeftAnchor(instructions, 0.0);
		AnchorPane.setRightAnchor(instructions, 0.0);
		AnchorPane.setBottomAnchor(instructions, EDGE);

		/** Email Box **/
		HBox emailBox = new HBox();
		emailBox.setStyle("-fx-background-color: #ffffff");
		emailBox.setMinHeight(EDGE);
		emailBox.setMaxHeight(EDGE);
		emailBox.setPrefHeight(EDGE);
		emailBox.setAlignment(Pos.CENTER_LEFT);
		emailBox.setSpacing(GAP*3);

		VBox emailIconBox = new VBox();
		Image email =	new Image(getClass().getResourceAsStream("../images/email109.png"), 30, 30, true, true);
		ImageView emailView = new ImageView(email);
		emailView.setTranslateX(5);
		emailView.setTranslateY(2);
		emailIconBox.getChildren().addAll(emailView);
		emailIconBox.setStyle("-fx-background-color: #ffffff");
		emailIconBox.setMinHeight(EDGE);


		/** Label **/
		Label emailLabel = new Label("Email Me");
		emailLabel.setTextFill(Color.web("#333333"));
		emailBox.getChildren().addAll(emailIconBox, emailLabel);

		emailLabel.setOnMouseClicked(e -> handleEmail(emailBox));
		emailView.setOnMouseClicked(e -> handleEmail(emailBox));


		AnchorPane.setBottomAnchor(emailBox, 0.0);
		AnchorPane.setLeftAnchor(emailBox, 0.0);
		AnchorPane.setRightAnchor(emailBox, 0.0);

		directions.getChildren().addAll(directionsTitleBox, instructions, emailBox);
		directions.setStyle("-fx-background-color: #ffffff");
		directions.setPrefWidth(expandedWidth + EDGE);
		directions.setMinWidth(0);
		directions.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + EDGE);
	}

	private void initMap(){
		this.map = new VBox();
		HBox mapTitle = new HBox();


		/** Label **/
		Label mapTitleLabel = new Label("CapraNav");
		mapTitleLabel.setTextFill(Color.web("#eeeeee"));
		//ATTENTION: below is some nice trixksz! it binds the location of the title to the center
		mapTitleLabel.translateXProperty().bind((mapTitle.widthProperty().subtract(mapTitleLabel.widthProperty()).divide(2)));
		mapTitleLabel.translateYProperty().bind((mapTitle.heightProperty().subtract(mapTitleLabel.heightProperty()).divide(2)));

		mapTitle.setMaxHeight(EDGE);
		mapTitle.setPrefHeight(EDGE);
		mapTitle.setMinHeight(EDGE);
		mapTitle.setStyle("-fx-background-color: #444444");
		mapTitle.getChildren().add(mapTitleLabel);


        this.mapPane = createMapPane();

        mapPane.setAlignment(Pos.CENTER);


		map.setMinWidth(MAP_WIDTH);
		map.setPrefWidth(MAP_WIDTH+MAP_BORDER*2);

		map.setMinHeight(MAP_HEIGHT+EDGE);
		map.setPrefHeight(MAP_HEIGHT+ MAP_BORDER * 2 + EDGE);

		map.getChildren().addAll(mapTitle, mapPane);
		map.setStyle("-fx-background-color:#eeeeee ;");
	}

	/****************************************************************************************************************
     				FUNCTIONS SETTING UP SPECIFIC VISUAL ELEMENTS AND FURTHER SUBSIDIARIES
	****************************************************************************************************************/

    private StackPane createMapPane(){
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(MAP_WIDTH + MAP_BORDER * 2);
        mapPane.setMinHeight(MAP_HEIGHT);

        mapPane.setPrefWidth(MAP_WIDTH + MAP_BORDER * 2);
        mapPane.setMinWidth(MAP_WIDTH);

        mapPane.setStyle("-fx-background-color: #eeeeee");
        this.mapDisplay = new MapDisplay( this.controller); //(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER),
        mapPane.getChildren().add(mapDisplay);
        //mapPane.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
        //mapPane.setTranslateY(HEIGHT_BUFFER);
        return mapPane;
    }

    private VBox createInput(){

		/* start */
        this.start = new Inputs("Search WPI Maps", INPUT_WIDTH);
        start.setOnAction(e -> handleInput(start, true));

		/* end */
        this.end = new Inputs("For Destination", INPUT_WIDTH);
        end.setOnAction(e ->handleInput(end, false));

        start.setId("input");
        end.setId("input");

        VBox inputs = new VBox();
        inputs.setSpacing(GAP);
        inputs.getChildren().addAll(start, end);

        return inputs;
    }

	private HBox createDivider(){
		HBox divide = new HBox();
		divide.setStyle("-fx-background-color: #888888");

		/* setting sizes */
		divide.setMinWidth(EDGE);
		divide.setMaxWidth(expandedWidth + CONTROL_WIDTH);
		divide.setPrefWidth(expandedWidth + CONTROL_WIDTH);

		divide.setMinHeight(1);
		divide.setMaxHeight(1);
		divide.setPrefHeight(1);
		/* binding size */

		//divide.translateXProperty().bind((slidingDashboard.widthProperty().subtract(divide.widthProperty()).divide(2)));

		return divide;
	}


	private void createInstructionListView() {
		this.instructions = new ListView<Instructions>();
		instructions.setCellFactory((ListView<Instructions> lv) ->
				new ListCell<Instructions>() {
					@Override
					public void updateItem(Instructions in, boolean empty) {
						super.updateItem(in, empty);
						if (empty) {
							setText(null);
						} else {
							// use whatever data you need from the album
							// object to get the correct displayed value:
							setText(in.toString());
						}
					}
				}
		);

		instructions.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Instructions> obs, Instructions oldAlbum, Instructions selectedAlbum) -> {
							if (selectedAlbum != null) {
								// do something with selectedAlbum
							}
						});

		instructions.setPlaceholder(new Label(" "));
		instructions.setMinWidth(0);
		instructions.setMaxWidth(expandedWidth + EDGE * 2);
		instructions.setMinHeight(0);
		//instructions.setPrefHeight(MAP_WIDTH - EDGE * 4);
		this.instructions.setItems(FXCollections.observableArrayList());
		//instructions.setPrefHeight(TABLE_HEIGHT);
		//instructions.getColumns().addAll(Instructions.getColumn(instructions));


	}



	/****************************************************************************************************************
	 				 						FUNCTIONS THAT HANDLE EVENTS
	 ****************************************************************************************************************/

	private void handleSettings(){
		//TODO what do we want to happen here? A POPUP / OR WHAT??
		//TODO add visual affects to both change settings ICON and WORDS
		// TODO Have both flash Green on white?

	}

	private void handleEmail(Node n){

		if (!EMAIL) {
			EMAIL = true;
			PopOver popOver = new PopOver();
			VBox emailBox = new VBox();
			TextField yourEmail = new TextField("Enter Email Here");
			javafx.scene.control.Button go = new javafx.scene.control.Button("Send Directions");
			emailBox.getChildren().addAll(yourEmail, go);
			go.setOnAction(e -> {
				if (yourEmail.getText() != null) {
					if (sendEmail(yourEmail.getText())){
						yourEmail.setText("Email Sent");
						popOver.hide();
						EMAIL = false;
					} else {
						yourEmail.setText("Invalid Email");
					}
				}
			});
			emailBox.setSpacing(GAP);
			emailBox.setAlignment(Pos.CENTER);
			emailBox.setMaxWidth(EDGE + expandedWidth);
			popOver.setContentNode(emailBox);
			popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
			//popOver.setAnchorY(EDGE*3);
			popOver.show(n);
			popOver.setOnAutoHide(e -> {
				EMAIL = false;
			});
			popOver.setOnHidden(e -> {
				EMAIL = false;
			});
			popOver.setOnCloseRequest(e -> {
				EMAIL = false;
			});
		}
	}









	/****************************************************************************************************************
	 						    FUNCTIONS THAT CONTACT THE CONTROLLER FOR INFORMATION
	 ****************************************************************************************************************/
private boolean sendEmail(String email){
	if (!email.equals("") && !email.equals("Enter Email Here") && !email.equals("Email Sent") && !email.equals("Invalid Email") ) {
		controller.sendEmail(email);
		return true;
	} else {
		return false;
	}
}








	/****************************************************************************************************************
	 													RELICS
	 ****************************************************************************************************************/




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

		//TableView<Instructions> instructions = createInstructionsTable(); NOTE DOESNT WORK WITH NEW LISTVIEW
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
/*
		*//* start *//*
		this.start = new Inputs("Search WPI Maps", INPUT_WIDTH);
		start.setOnAction(e -> handleInput(start, true));

		*//* end *//*
		this.end = new Inputs("For Destination", INPUT_WIDTH);
		end.setOnAction(e ->handleInput(end, false));*/


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
				logic.INode node = (logic.INode) v.getValue();
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

    /*
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
        //mapPane.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
        //mapPane.setTranslateY(HEIGHT_BUFFER);
        return mapPane;
    }
     */

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

/*
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
	*/
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
	/*private TableView<Instructions> createInstructionsTable() {
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
	}*/

	public void clearInstructions(){
		this.instructions.setItems(null);
	}

	/**
	 * Call to set the instructions
	 */
	public void setInstructions(ArrayList<logic.INode> nodes, ArrayList<String> instructions){
		ObservableList<Instructions> data = FXCollections.observableArrayList();

		for (int i = 0; i < nodes.size(); i++){
			data.add(new Instructions(instructions.get(i), nodes.get(i)));
		}

		this.instructions.setItems(data);
	}

}

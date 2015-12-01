package visuals;

import controller.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;


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
    private OptionsMenu options;
    private Rectangle divide;
    //private Map map; //TODO CHANGE THIS

    private StackPane mapPane;
    public MapDisplay mapDisplay;
    private Inputs chooseMap;
    //private TableView<Instructions> instructions;
    public Inputs start;
    public Inputs end;


    /*****************************************************************/
    /**
     * New Values / Variables for UI Rework
     **/

    //Constants & Variables
    private static final double CONTROL_WIDTH = 30;
    private static final double GAP = 5;
    private final double EDGE;
    private static final double expandedWidth = 170;//150
    private static final double MAP_WIDTH = 660;
    private static final double MAP_HEIGHT = 495;
    private static final double MAP_BORDER = 15;

    private BooleanProperty DASHBOARD_VISIBLE;
    private BooleanProperty SETTINGS_VISIBLE;
    private BooleanProperty EMAIL_VISIBLE;
    public BooleanProperty BUILDING_VISIBLE;
    public BooleanProperty PHOTO_ICON_VISIBLE;

    private boolean EMAIL = false;
    //Visual Elements
    private VBox dashBoard;
    private SlidingAnchorPane slidingDashboard;
    public SlidingAnchorPane slidingBuilding;
    private AnchorPane directions;
    private AnchorPane map; //VBox
    public StackPane root;
    private HBox dashBoardTitleBox;
    private ListView<Instructions> instructions; //ListView<Instruction>
    private javafx.scene.control.Button hiddenHandler;


    private Label buildingName;
    private Label buildingNumber;

    private Label nodeTitle;
    private StackPane nodeViewHolder;
    private ImageView nodeView;
    //private ImageView nodeIconView;
    private javafx.scene.control.Button nodeIconViewButton;

    private javafx.scene.control.Button left;
    private javafx.scene.control.Button right;

    javafx.scene.control.Button leftArrowButton;
    javafx.scene.control.Button rightArrowButton;

    /**
     * Basic constructor.
     */
    public Display(Controller controller) {
        root = new StackPane();
        root.setId("root");

        this.controller = controller;
        this.directions = new AnchorPane();
        this.map = new AnchorPane();
        this.slidingDashboard = new SlidingAnchorPane();

        this.DASHBOARD_VISIBLE = new SimpleBooleanProperty(true);
        this.SETTINGS_VISIBLE = new SimpleBooleanProperty(true);
        this.BUILDING_VISIBLE = new SimpleBooleanProperty(false);
        this.EMAIL_VISIBLE = new SimpleBooleanProperty(true);

        this.PHOTO_ICON_VISIBLE = new SimpleBooleanProperty(false);

        this.EDGE = GAP * 2 + CONTROL_WIDTH;
    }

    /**
     * This sets up the GUI interface - both visuals and interactions.
     *
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
        sections.getChildren().addAll(slidingDashboard, directions, map); //dashBoardControlBox //, directions, map
        /*****************************************************************/
        /** Add sections to Root */
        root = new StackPane();
        root.getChildren().add(sections);

        /*****************************************************************/
        /** create scene **/
        root.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(root, MAP_WIDTH + MAP_BORDER * 2 + EDGE * 5 + expandedWidth * 2, MAP_HEIGHT + 2 * MAP_BORDER + EDGE * 4);//+MAP_BORDER*2+TITLE_HEIGHT
        //Scene scene = new Scene(root, MAP_WIDTH+MAP_BORDER*2+EDGE*2+expandedWidth*2, MAP_WIDTH + 2 * EDGE);//+MAP_BORDER*2+TITLE_HEIGHT

        return scene;

    }

    /****************************************************************************************************************
     * FUNCTIONS SETTING UP BASE DESIGN THAT ALSO CALL THE SPECIFIC CREATION OF FURTHER VISUAL ELEMENTS
     ****************************************************************************************************************/

    private void initSlidingDashBoard() {
        /*****************************************************************/

        /** dividers **/
        HBox divider_0 = createDivider();
        HBox divider_1 = createDivider();
        HBox divider_2 = createDivider();
        HBox divider_3 = createDivider();


        divider_0.visibleProperty().bind(DASHBOARD_VISIBLE);
        divider_1.visibleProperty().bind(DASHBOARD_VISIBLE);
        divider_2.visibleProperty().bind(DASHBOARD_VISIBLE);
        divider_3.visibleProperty().bind(DASHBOARD_VISIBLE);


        AnchorPane.setTopAnchor(divider_0, EDGE + 2);
        AnchorPane.setLeftAnchor(divider_0, GAP);
        AnchorPane.setRightAnchor(divider_0, GAP);

        AnchorPane.setTopAnchor(divider_1, 3 * EDGE + 26);
        AnchorPane.setLeftAnchor(divider_1, GAP);
        AnchorPane.setRightAnchor(divider_1, GAP);

        AnchorPane.setTopAnchor(divider_2, 6 * EDGE + 26);
        AnchorPane.setLeftAnchor(divider_2, GAP);
        AnchorPane.setRightAnchor(divider_2, GAP);

       // divider_3.setTranslateX(GAP);
		//AnchorPane.setBottomAnchor(divider_3, EDGE - 1);
        divider_3.setTranslateX(GAP);
        AnchorPane.setTopAnchor(divider_3, 0.0);
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
        pinView.setScaleX(.04);
        pinView.setScaleY(.04);
        pinView.setTranslateX(-50 * 2.5 - 15);
        pinView.setTranslateY(-50 * 4.5 + 3);
        pinView.setId("pinView");

        AnchorPane.setTopAnchor(pinView, 1 * EDGE + GAP + 5);
        AnchorPane.setLeftAnchor(pinView, GAP);


        //Image info =			new Image(getClass().getResourceAsStream("../images/info.png"), 20, 20, true, true);
        //ImageView infoView = 	new ImageView(info);
        //the real infoview
        SVGPath infoView = new SVGPath();
        infoView.setContent("M254.26,0C113.845,0,0,113.845,0,254.26s113.845,254.26,254.26,254.26" +
                "s254.26-113.845,254.26-254.26S394.675,0,254.26,0z M286.042,381.39c0,17.544-14.239,31.782-31.782,31.782" +
                "s-31.782-14.239-31.782-31.782V222.477c0-17.544,14.239-31.782,31.782-31.782s31.782,14.239,31.782,31.782V381.39z" +
                "M254.26,159.421c-17.544,0-31.782-14.239-31.782-31.782s14.239-31.782,31.782-31.782s31.782,14.239,31.782,31.782" +
                "S271.804,159.421,254.26,159.421z");
        infoView.setScaleX(.035);
        infoView.setScaleY(.035);
        infoView.setTranslateX(-50 * 4.5 - 9);
        infoView.setTranslateY(-50 * 2 + 14);
        infoView.setId("pinView");


        Image gears = new Image(getClass().getResourceAsStream("../images/gears.png"), 20, 20, true, true);
        ImageView gearsView = new ImageView(gears);
        //gearsView.setStyle("-fx-fill: #eeeeee;");
        //SVGPath gearsView = new SVGPath();


        //gearsView.setScaleX(.5);
        //gearsView.setScaleY(.5);
        //gearsView.setTranslateX(50 * 9 + 9);
        //gearsView.setTranslateY(50 * 1 + 5);
        //gearsView.setId("pinView");


        pinView.visibleProperty().bind(DASHBOARD_VISIBLE);
        infoView.visibleProperty().bind(DASHBOARD_VISIBLE);
        gearsView.visibleProperty().bind(DASHBOARD_VISIBLE);

        //AnchorPane.setTopAnchor(infoView, 3 * EDGE + GAP + 5); //<--- TODO notice this, these lines break a lot of stuff, no idea why
        //AnchorPane.setLeftAnchor(infoView, GAP);

        //AnchorPane.setBottomAnchor(gearsView, GAP * 2);//TODO these will change with svg
        //AnchorPane.setLeftAnchor(gearsView, GAP * 2);

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
            Rectangle bar = new Rectangle(CONTROL_WIDTH - 8, 3);
            bar.setArcHeight(3);
            bar.setArcWidth(3);
            bar.setFill(Color.web("#eeeeee"));
            bars.getChildren().add(bar);
        }
        bars.setTranslateX(1);
        bars.setTranslateY(1);

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

        AnchorPane.setTopAnchor(locationLabelBox, 1 * EDGE + GAP - 5);
        AnchorPane.setLeftAnchor(locationLabelBox, EDGE);

        /** Inputs **/
        VBox inputs = createInput();
        AnchorPane.setTopAnchor(inputs, 1 * EDGE + GAP * .5 + GAP * 1.5 + 30);
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

        AnchorPane.setTopAnchor(resourcesLabelBox, 3 * EDGE + GAP * .5 + 25);
        AnchorPane.setLeftAnchor(resourcesLabelBox, EDGE);

        /*****************************************************************/
        /** Change Settings Zone **/
        HBox settingsLabelBox = new HBox();
        settingsLabelBox.setMinHeight(EDGE);
        settingsLabelBox.setMaxHeight(expandedWidth); //change this to whatever height
        settingsLabelBox.setPrefHeight(EDGE);
        settingsLabelBox.setAlignment(Pos.CENTER_LEFT);

        Label settingsLabel = new Label("Settings");
        settingsLabel.setTextFill(Color.web("#eeeeee"));

        settingsLabel.setTranslateX(5);
        //settingsLabelBox.getChildren().addAll(settingsLabel);
        settingsLabelBox.setMinWidth(0);
        settingsLabelBox.setPrefWidth(expandedWidth);
        settingsLabelBox.setMaxWidth(expandedWidth);


        //settings a sliding pane!
        SlidingAnchorPane slidingSettings = new SlidingAnchorPane(expandedWidth, EDGE, Direction.UP, SETTINGS_VISIBLE, gearsView);
        slidingSettings.setStyle("-fx-background-color: #333333");

        javafx.scene.control.Button slidingButton = slidingSettings.getButton();
        slidingButton.setId("dashboardButton");
        slidingButton.setMaxWidth(EDGE - 5);
        slidingButton.setMinWidth(EDGE - 5);
        slidingButton.setPrefWidth(EDGE - 5);
        //AnchorPane.setBottomAnchor(slidingButton, 0.0);
        //AnchorPane.setLeftAnchor(slidingButton, 0.0);
        settingsLabelBox.getChildren().addAll(slidingButton, settingsLabel);

        HBox settingsWalkingBox = new HBox();
        Label settingsWalkingLabel = new Label("Set walking speed:");
        settingsWalkingLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        settingsWalkingLabel.setTextFill(Color.web("#eeeeee"));

        settingsWalkingBox.getChildren().addAll(settingsWalkingLabel);

        ArrayList<Walking> walkingArrayList = new ArrayList<>();
        walkingArrayList.add(new Walking("Walking with your grandmother (slow)", 0.5));
        walkingArrayList.add(new Walking("Normal walking pace (medium)", 1.0));
        walkingArrayList.add(new Walking("Walking to class when late (fast)", 1.5));
        Inputs walkingSpeedBox = new Inputs("Select walking speed", INPUT_WIDTH, controller);
        walkingSpeedBox.setTranslateX(8);  //TODO fix width of this?
        walkingSpeedBox.setItems(walkingSpeedBox.createWalkingItems(walkingArrayList));
        walkingSpeedBox.setPromptText("Walking speed");

        walkingSpeedBox.setOnAction(e -> handleWalkingInput(walkingSpeedBox, true));    //TODO finish handleWalkingInput

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Enter your email");
        emailTextField.setMaxWidth(INPUT_WIDTH);
        emailTextField.setMaxHeight(walkingSpeedBox.getMaxHeight());
        emailTextField.setTranslateX(8);
        emailTextField.setTranslateY(8);
        emailTextField.setId("text-field");

        Label setEmailLabel = new Label("Set your email:");
        setEmailLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        setEmailLabel.setTextFill(Color.web("#eeeeee"));

        emailTextField.setOnAction(e -> handleEmailInput(emailTextField, true));    //TODO finish handleWalkingInput

        /*AnchorPane.setLeftAnchor(settingsWalkingBox, EDGE);
        AnchorPane.setLeftAnchor(settingsWalkingLabel, EDGE);
        AnchorPane.setLeftAnchor(setEmailLabel, EDGE);
        AnchorPane.setLeftAnchor(emailTextField, EDGE);*/

        settingsWalkingBox.setTranslateX(EDGE - 7);
        walkingSpeedBox.setTranslateX(EDGE);
        setEmailLabel.setTranslateX(EDGE - 7);
        emailTextField.setTranslateX(EDGE);


        VBox settingsVbox = new VBox();
        settingsVbox.visibleProperty().bind(DASHBOARD_VISIBLE);
        settingsVbox.getChildren().addAll(divider_3, settingsLabelBox, settingsWalkingBox, walkingSpeedBox, setEmailLabel, emailTextField);


        AnchorPane.setBottomAnchor(slidingSettings, 0.0);// 2 * EDGE - 2 * GAP - 20);
        AnchorPane.setLeftAnchor(slidingSettings, 0.0);
        slidingSettings.getChildren().addAll(settingsVbox);

        //VBOX: Divider - seetingsLabelBox - actual settings


        /*****************************************************************/
        /** Building of Sliding Dashboard Anchorpane  **/
        this.slidingDashboard = new SlidingAnchorPane(expandedWidth, EDGE, Direction.LEFT, DASHBOARD_VISIBLE, bars, divider_0, divider_1, divider_2, dashBoardTitleBox, locationLabelBox, resourcesLabelBox, infoView, pinView, inputs, slidingSettings); //gearsView, settingsLabelBox, divider_3,
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

    private void initDirections() {
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
        directionsTitleBox.setSpacing(GAP * 3);

        VBox directionsControlBox = new VBox();
        Image directionsArrow = new Image(getClass().getResourceAsStream("../images/forward.png"), 27, 27, true, true);
        ImageView directionsArrowView = new ImageView(directionsArrow);
        directionsArrowView.setTranslateX(8);
        directionsArrowView.setTranslateY(5);
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


        /** ListView **/
        createInstructionListView();

        AnchorPane instructionArrows = new AnchorPane();

        instructionArrows.setPrefHeight(36);
        instructionArrows.setMinHeight(36);
        instructionArrows.setMaxHeight(36);
        instructionArrows.setMinWidth(0);

        AnchorPane.setTopAnchor(instructionArrows, EDGE);
        AnchorPane.setLeftAnchor(instructionArrows, 0.0);
        AnchorPane.setRightAnchor(instructionArrows, 0.0);

        Image leftArrow = new Image(getClass().getResourceAsStream("../images/leftArrow.png"), 24, 24, true, true);
        ImageView leftArrowView = new ImageView(leftArrow);

        leftArrowButton = new javafx.scene.control.Button();
        leftArrowButton.setGraphic(leftArrowView);
        leftArrowButton.setId("arrow-buttons-grayed");
        leftArrowButton.setOnAction(e -> handleLeftArrowButton());

        Image rightArrow = new Image(getClass().getResourceAsStream("../images/rightArrow.png"), 24, 24, true, true);
        ImageView rightArrowView = new ImageView(rightArrow);

        rightArrowButton = new javafx.scene.control.Button();
        rightArrowButton.setGraphic(rightArrowView);
        rightArrowButton.setId("arrow-buttons-grayed");
        rightArrowButton.setOnAction(e -> handleRightArrowButton());

        AnchorPane.setRightAnchor(leftArrowButton, expandedWidth - 5.5 - leftArrowButton.getPrefWidth());
        AnchorPane.setTopAnchor(leftArrowButton, 5.5);
        AnchorPane.setLeftAnchor(leftArrowButton, 8.0);

        AnchorPane.setLeftAnchor(rightArrowButton, expandedWidth - 5.5 - rightArrowButton.getPrefWidth());
        AnchorPane.setTopAnchor(rightArrowButton, 5.5);
        AnchorPane.setRightAnchor(rightArrowButton, 8.0);

        instructionArrows.getChildren().addAll(leftArrowButton, rightArrowButton);

        AnchorPane.setTopAnchor(instructions, EDGE + 36);
        AnchorPane.setLeftAnchor(instructions, 0.0);
        AnchorPane.setRightAnchor(instructions, 0.0);
        AnchorPane.setBottomAnchor(instructions, EDGE);
        //instructions.setPrefHeight(MAP_HEIGHT + 2 * EDGE - 36);

        /** Email Box **/

        HBox emailBox = new HBox();
        emailBox.setStyle("-fx-background-color: #ffffff");
        emailBox.setMinHeight(EDGE);
        emailBox.setMaxHeight(EDGE);
        emailBox.setPrefHeight(EDGE);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        emailBox.setSpacing(GAP * 3);



        Image emailImage = new Image(getClass().getResourceAsStream("../images/email109.png"), 25, 25, true, true);
        ImageView emailView = new ImageView(emailImage);

        //VBox emailIconBox = new VBox();
        emailView.setTranslateX(7);
        emailView.setTranslateY(0);
        /*emailIconBox.getChildren().addAll(emailView);
        emailIconBox.setMaxWidth(20);
        emailIconBox.setMaxHeight(20);
        emailIconBox.setStyle("-fx-background-color: #ffffff");
        emailIconBox.setMinHeight(EDGE);*/





        /** Label **/
        Label emailLabel = new Label("Email Me");
        emailLabel.setTextFill(Color.web("#333333"));
        emailLabel.setStyle("-fx-background-color:white;");
        emailBox.setStyle("-fx-background-color:white;");

        //emailBox.getChildren().addAll(emailIconBox, emailLabel);

       /* emailLabel.setOnMouseClicked(e -> handleEmail(emailBox));
        emailView.setOnMouseClicked(e -> handleEmail(emailBox));*/

        /** Sliding Anchor Pane **/
        SlidingAnchorPane slidingEmail = new SlidingAnchorPane(EDGE * 2, EDGE, Direction.UP, EMAIL_VISIBLE, emailView);
        slidingEmail.setStyle("-fx-background-color:white;");

        javafx.scene.control.Button slidingEmailButton = slidingEmail.getButton();
        slidingEmailButton.setId("dashboardButton");
        slidingEmailButton.setStyle("-fx-background-color:white;");
        slidingEmailButton.setMaxWidth(EDGE - 5);
        slidingEmailButton.setMinWidth(EDGE - 5);
        slidingEmailButton.setPrefWidth(EDGE - 5);

        HBox divider_4 = createDivider();
        divider_4.visibleProperty().bind(new SimpleBooleanProperty(true));
        divider_4.setTranslateY(-21);
        divider_4.setTranslateX(-152);   //TODO fix this janky translate garbage

        emailBox.getChildren().addAll(slidingEmailButton, emailLabel, divider_4);

        /////////// INFO IN EMAIL SLIDE

        VBox emailBoxContent = new VBox();
        TextField yourEmail = new TextField("Enter Email Here");
        yourEmail.setStyle("-fx-font-size:12;-fx-padding:4 4;");
        yourEmail.setTranslateX(0);
        yourEmail.setPrefWidth(190);
        yourEmail.setMinWidth(190);
        yourEmail.setMaxWidth(190);
        javafx.scene.control.Button go = new javafx.scene.control.Button("Send Directions");
        go.setId("email-button");
        go.setTranslateX(41);
        emailBoxContent.getChildren().addAll(yourEmail, go);
        go.setOnAction(e -> {
            if (yourEmail.getText() != null) {
                if (sendEmail(yourEmail.getText())) {
                    yourEmail.setText("Email Sent"); //TODO ADD GREEN COLOR
                    EMAIL = false;
                } else {
                    yourEmail.setText("Invalid Email");//TODO ADD RED COLOR
                }
            }
        });
        emailBoxContent.setSpacing(GAP);
        emailBoxContent.setAlignment(Pos.CENTER);
        emailBoxContent.setMaxWidth(EDGE + expandedWidth);

        //emailBoxContent.visibleProperty().bind(EMAIL_VISIBLE);

        VBox finalSlidingBox = new VBox();
        finalSlidingBox.getChildren().addAll(emailBox, emailBoxContent);

        slidingEmail.getChildren().addAll(finalSlidingBox);


        /////////////


        AnchorPane.setBottomAnchor(slidingEmail, 0.0);
        AnchorPane.setLeftAnchor(slidingEmail, 0.0);
        AnchorPane.setRightAnchor(slidingEmail, 0.0);

        instructions.setOnMouseEntered(e->{
            instructions.requestFocus();
        });



        directions.getChildren().addAll(directionsTitleBox, instructionArrows, instructions, slidingEmail);
        directions.setStyle("-fx-background-color: #ffffff");
        directions.setPrefWidth(expandedWidth + EDGE);
        directions.setMinWidth(0);
        directions.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + EDGE);

    }

    private void initMap() {
        this.map = new AnchorPane();

        /** Title **/
        HBox mapTitle = new HBox();
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

        AnchorPane.setTopAnchor(mapTitle, 0.0);
        AnchorPane.setLeftAnchor(mapTitle, 0.0);
        AnchorPane.setRightAnchor(mapTitle, 0.0);

        /** Hidden Sliding Panel **/
        //slidingBuilding  = new SlidingAnchorPane(EDGE * 2, EDGE, Direction.UP, BUILDING_VISIBLE, new Text("hidden"));
        HBox nodeBox     = createNodeBox();
        HBox buildingBox = createBuildingBox();
        buildingBox.visibleProperty().bind(BUILDING_VISIBLE);

        //slidingBuilding.getChildren().addAll(nodeBox, buildingBox);//buildingBox
        //slidingBuilding.setMaxHeight(EDGE);
        //slidingBuilding.setMinHeight(0);

        VBox information = new VBox();
        information.getChildren().addAll(nodeBox, buildingBox);
        information.setAlignment(Pos.CENTER);
        information.setSpacing(GAP * 3); //play with this

        AnchorPane.setBottomAnchor(information, GAP * 5);
        AnchorPane.setLeftAnchor(information, 0.0);
        AnchorPane.setRightAnchor(information, 0.0);

        this.mapPane = createMapPane();
        mapPane.setAlignment(Pos.CENTER);

        Group group = new Group(mapPane);
        GraphicsScaling graphicsScaling = new GraphicsScaling();
        Parent zoomPane = graphicsScaling.createZoomPane(group);
        zoomPane.setOnMouseEntered(e->{
            zoomPane.requestFocus();
        });

        AnchorPane.setTopAnchor(zoomPane, EDGE);//
        AnchorPane.setLeftAnchor(zoomPane, 0.0);
        AnchorPane.setRightAnchor(zoomPane, 0.0);
        AnchorPane.setBottomAnchor(zoomPane, EDGE * 2); //+ GAP + 2 * EDGE

        map.setMinWidth(MAP_WIDTH);
        map.setPrefWidth(MAP_WIDTH + MAP_BORDER * 2);

        map.setMinHeight(MAP_HEIGHT + EDGE);
        map.setPrefHeight(MAP_HEIGHT + MAP_BORDER * 2 + EDGE + EDGE); // + EDGE for NODE INFO

        map.getChildren().addAll(mapTitle, zoomPane, information);
        map.setStyle("-fx-background-color:#eeeeee ;");

    }

    /****************************************************************************************************************
     * FUNCTIONS SETTING UP SPECIFIC VISUAL ELEMENTS AND FURTHER SUBSIDIARIES
     ****************************************************************************************************************/

    private StackPane createMapPane() {
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(MAP_HEIGHT + MAP_BORDER * 2);
        mapPane.setMinHeight(MAP_HEIGHT);

        mapPane.setPrefWidth(MAP_WIDTH + MAP_BORDER * 2);
        mapPane.setMinWidth(MAP_WIDTH);

        mapPane.setStyle("-fx-background-color: #eeeeee");
        this.mapDisplay = new MapDisplay(this.controller); //(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER),
        mapPane.getChildren().add(mapDisplay);
        //mapPane.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
        //mapPane.setTranslateY(HEIGHT_BUFFER);
        return mapPane;
    }


    private HBox createNodeBox(){
        HBox hbox = new HBox();

        nodeViewHolder = new StackPane();
        nodeTitle = new Label();

        Image nodeIconImage = new Image(getClass().getResourceAsStream("../images/photography111.svg"), 27,27,true,true);
        ImageView nodeIconView = new ImageView(nodeIconImage);
       // nodeIconView.setScaleX(.05);
       // nodeIconView.setScaleY(.05);
        nodeIconView.setFitHeight(22);
        nodeIconView.setFitWidth(22);

        nodeIconViewButton = new javafx.scene.control.Button();
        nodeIconViewButton.visibleProperty().bind(PHOTO_ICON_VISIBLE);
        nodeIconViewButton.setGraphic(nodeIconView);
        nodeIconViewButton.setOnAction(event -> handleFullScreenPicture());
        nodeIconViewButton.setId("arrow-buttons");
        nodeIconViewButton.setStyle("-fx-background-color: #eeeeee");

        hbox.getChildren().addAll(nodeViewHolder, nodeTitle, nodeIconViewButton);
        hbox.setMaxHeight(EDGE);
        hbox.setMinHeight(EDGE);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(GAP);//TODO MAKE SURE THIS LOOKS GOOD

        return hbox;
    }


    public void updatePictureIcon(boolean val){
        //change visibility
        PHOTO_ICON_VISIBLE.setValue(val);
        //change image it is connected with
    }

    public void updateNodeTitle(String s){
        this.nodeTitle.setText(s);
    }

    public void updateNodeIcon(ImageView i){
        System.out.println("node view icon");
        this.nodeViewHolder.getChildren().remove(nodeView);
        this.nodeViewHolder.getChildren().add(i); //= i;
        this.nodeView = i;
    }

    //TODO THIS IS START OF BUILDING BOX PANE!
    private HBox createBuildingBox() { //its going to be an HBox with stuff inside of the sliding anchorpane

        HBox box = new HBox();
        this.left = new javafx.scene.control.Button();
        this.right = new javafx.scene.control.Button();

        Image minus = new Image(getClass().getResourceAsStream("../images/minus104.png"), 20, 20, true, true);
        Image plus = new Image(getClass().getResourceAsStream("../images/plus79.png"), 20, 20, true, true);

        ImageView minusView = new ImageView(minus);
        ImageView plusView = new ImageView(plus);

        this.left.setGraphic(minusView);
        this.right.setGraphic(plusView);

        this.left.setStyle("-fx-background-color:#eee;");
        this.right.setStyle("-fx-background-color:#eee;");

        buildingName = new Label();
        buildingNumber = new Label();

        left.setOnMouseClicked(e -> controller.handleDecreaseFloorButton());
        right.setOnMouseClicked(e -> controller.handleIncreaseFloorButton());

        box.setMaxHeight(EDGE);
        box.setMinHeight(0);
        box.setPrefHeight(0);

        //TODO set min widths
        box.setAlignment(Pos.CENTER);
        box.setSpacing(GAP);
        box.getChildren().addAll(left, buildingName, buildingNumber, right);
        return box;
    }

    public void setRightButtonID(String id){
        right.setId(id);
    }

    public void setLeftButtonID(String id){
        left.setId(id);
    }

    public void setBuildingName(String s) {
        this.buildingName.setText(s);
    }

    public void setBuildingNumber(int i) {
        //TODO ADD FLICKERING ANIMATION
        System.out.println("building number set called");
        this.buildingNumber.setText(Integer.toString(i));
    }

    private VBox createInput() {

		/* start */
        this.start = new Inputs("Search WPI Maps", INPUT_WIDTH, controller);
        start.setOnAction(e -> handleSearchInput(start, true));

		/* end */
        this.end = new Inputs("For Destination", INPUT_WIDTH, controller);
        end.setOnAction(e -> handleSearchInput(end, false));

//        start.getStyleClass().add("combo-box");

        //      end.getStyleClass().add("combo-box");

        //start.applyCss();
        //end.applyCss();

        /** Add All Interesting Nodes to the List **/


        start.createInputItems(this.getNodes(), this.getMaps());
        start.setItems(start.data);
        //start.getStyleClass().add("combobox");
        end.createInputItems(this.getNodes(), this.getMaps());
        end.setItems(end.data);
        //end.getStyleClass().add("combobox");


        VBox inputs = new VBox();
        inputs.setSpacing(GAP);
        inputs.getChildren().addAll(start, end);

        //this.start.setPlaceholder(new Label("Search or Select Starting Location"));
        //this.end.setPlaceholder(new Label("Search or Select End Location"));

        this.start.setPromptText("Starting point");
        this.end.setPromptText("Destination");

        this.start.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                inputs.requestFocus();//<-- this could cause null
                firstTime.setValue(false);
            }
        }));

        AutoCompleteComboBoxListener searchStart = new AutoCompleteComboBoxListener(start);
        AutoCompleteComboBoxListener searchEnd = new AutoCompleteComboBoxListener(end);

        return inputs;
    }

    private HBox createDivider() {
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
                .addListener((ObservableValue<? extends Instructions> obs, Instructions oldinstruction, Instructions selectedInstruction) -> {
                    if (selectedInstruction != null) {
                        //TODO Set the string of the label to this
                        this.controller.updateNodeInformation(selectedInstruction.getNode());
                    }
                });


        instructions.setPlaceholder(new Label(" "));
        instructions.setMinWidth(0);
        instructions.setMaxWidth(expandedWidth + EDGE * 2);
        instructions.setMinHeight(expandedWidth);
        instructions.setPrefHeight(MAP_HEIGHT-40-EDGE);
        this.instructions.setItems(FXCollections.observableArrayList());
        //instructions.setPrefHeight(TABLE_HEIGHT);
        //instructions.getColumns().addAll(Instructions.getColumn(instructions));


    }
    public void clearInstructions() {
        this.instructions.setItems(null);
    }

    /**
     * Call to set the instructions
     */
    public void setInstructions(ArrayList<Instructions> instructions) {
        ObservableList<Instructions> data = FXCollections.observableArrayList();
        data.addAll(instructions);
        this.instructions.setItems(data);
    }



    /****************************************************************************************************************
     * FUNCTIONS THAT HANDLE EVENTS
     ****************************************************************************************************************/

    private void handleFullScreenPicture(){
        this.controller.showNodeImage();
    }

    private void handleSearchInput(Inputs v, boolean START) {
        if (v.getValue() != null && !v.getValue().toString().isEmpty())
            try {
                controller.handleSearchInput(((InputItem) v.getValue()).getId(), START);
            } catch (ClassCastException cce) {
                logger.error("INPUT VALUE IS NOT YET A FULL INPUT, IT IS JUST A STRING: {}", v.getValue());
            }

    }

    //TODO finish this
    private void handleWalkingInput(Inputs v, boolean START) {
        String value = (String)v.getValue();
        //TODO Store value in User

    }

    //TODO finish this
    private void handleEmailInput(TextField v, boolean START) {
        //TODO How do you get info from a TextField?? Maybe getCharacters()?
        //TODO Store value in User
        v.getCharacters();
    }

    private void handleRightArrowButton(){
        this.controller.handleIncrementPathMap();
    }

    private void handleLeftArrowButton(){
        this.controller.handleDecrementPathMap();
    }

    public void setIDRightArrowButton(String s){
        this.rightArrowButton.setId(s);
    }
    public void setIDLeftArrowButton(String s){
        this.leftArrowButton.setId(s);
    }

    /****************************************************************************************************************
     * FUNCTIONS THAT CONTACT THE CONTROLLER FOR INFORMATION
     ****************************************************************************************************************/
    private boolean sendEmail(String email) {
        if (!email.equals("") && !email.equals("Enter Email Here") && !email.equals("Email Sent") && !email.equals("Invalid Email")) {
            return controller.sendEmail(email); //Should return true if the email goes through
        } else {
            return false;
        }
    }

    private HashMap<Integer, logic.INode> getInterestingNodes() {
        return controller.getInterestingNodes();
    }

    private HashMap<Integer, logic.INode> getNodes() {
        return controller.getNodes();
    }

    private HashMap<Integer, logic.IMap> getMaps() {
        return controller.getMaps();
    }


    /****************************************************************************************************************
     * RELICS
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
     *
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
//*
		/**///**//**//* start *//**//**//**//*
//		this.start = new Inputs("Search WPI Maps", INPUT_WIDTH);
        //start.setOnAction(e -> handleInput(start, true));

        //**//**//**//* end *//**//**//**//*
        //this.end = new Inp*/uts("For Destination", INPUT_WIDTH);
//		end.setOnAction(e ->handleInput(end, false));*//*
//*/

/*		private Popover<PopOver, Label> controller = new Po();

		PopOver popOver = new PopOver();
		Circle c = new Circle(5);
		popOver.show(c);*/

        //popOver.show(circle);
        //p//opOver.setContentNode(new Text(v.toString()));

        //TODO: the map combo box will soon be gone
		/*this.chooseMap = new Inputs("maps", INPUT_WIDTH);
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
				*//***   only a partial string currently -> no mapping to a node  ***//*
				System.out.println("NOT A NODE: " + start.getValue());
			}
		});*/

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
            if (newValue && firstTime.get()) {
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

        pane.getChildren().addAll(inputs); /* background taken out for now */

        DropShadow ds = new DropShadow();
        ds.setOffsetX(.3);
        ds.setOffsetY(.3);
        ds.setColor(Color.LIGHTGRAY);
        pane.setEffect(ds);

        return pane;
    }
/*
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
    //	System.out.println("NOT A NODE: " + v.getValue());
    //	}
				/*
				if (controller.endNode != null){
					mapDisplay.clearSelection(node.getID());
				}
				*/
    //	}
    //}

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
     * -> background
     * -> panel of buttons
     *
     * @return button panel
     */
    private StackPane createButtonPane() {
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
            refresh = new Image(getClass().getResourceAsStream("../images/refresh.png"), 35, 35, true, true);
        } catch (NullPointerException e) {
            refresh = new Image(getClass().getResourceAsStream("/images/refresh.png"), 35, 35, true, true);
        }

        ImageView refreshView = new ImageView(refresh);
        Button questionButton = new Button(refreshView, "refresh", 30);
        questionButton.setOnMouseClicked(e -> refreshInformation());

		/* menu button */
        VBox bars = new VBox();
        bars.setSpacing(3);
        for (int i = 0; i < 3; i++) {
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
    private void findPaths() {//THIS IS A RELIC NOT CURRENTLY USED
        this.controller.findPaths();
    }


    /**
     * refresh all data
     */
    private void refreshInformation() {
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
    private void showMenu() {
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
    private void addAnotherSlot() {
    }
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


}

package visuals;

import controller.Controller;
import feed.Event;
import feed.Feed;
import javafx.animation.Animation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import logic.*;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashMap;


public class Display {
    /****************************************************************************************************************
                                              Constants, Variables, Elements
     ****************************************************************************************************************/

    // logger
    private final Logger logger = LoggerFactory.getLogger(Display.class);


    //Constants
    private static final double INPUT_WIDTH = 160;
    private static final double CONTROL_WIDTH = 30;
    public static final double GAP = 5;
    private final double EDGE;
    public static final double expandedWidth = 170;//150
    private static final double MAP_WIDTH = 585; //originally 580+80
    private static final double MAP_HEIGHT = 460;//originally 455+40
    private static final double MAP_BORDER = 15;

    //Variables
    public Controller controller;


    public BooleanProperty DASHBOARD_VISIBLE;
    private BooleanProperty SETTINGS_VISIBLE;
    private BooleanProperty EMAIL_VISIBLE;
    public BooleanProperty BUILDING_VISIBLE;
    public BooleanProperty PHOTO_ICON_VISIBLE;
    public BooleanProperty ICON_VISIBLE;
    public BooleanProperty TIME_VISIBLE;

    public BooleanProperty DIRECTIONS_VISIBLE;

    final BooleanProperty firstTime = new SimpleBooleanProperty(true);


    private boolean EMAIL = false;
    //Visual Elements
    private VBox dashBoard;
    private SlidingAnchorPane slidingDashboard;
    public SlidingAnchorPane slidingBuilding;
    private AnchorPane directions;
    private AnchorPane map; //VBox
    public StackPane root;
    private HBox dashBoardTitleBox;
    public ListView<Instructions> instructions; //ListView<Instruction>
    private javafx.scene.control.Button hiddenHandler;

    private Label totalTimeLabel;

    private ArrayList<Walking> walkingArrayList;

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

    private javafx.scene.control.Button nodeTransitionButton;

    private StackPane mapPane;
    public MapDisplay mapDisplay;
    public Inputs<String> start;
    public Inputs<String> end;

    public TextField yourEmail;

    private PopOver eventPopOver;


    boolean FLIP = true;
    ZoomAndPan zoomAndPan;

    String directionStyle = "-fx-background-color: #ac2738";
    String whiteBGStyle = "-fx-background-color: #FFFFFF";
    SlidingAnchorPane    slidingDirections;

    SlidingAnchorPane slidingEmail;
    SlidingAnchorPane slidingSettings;


    Button slidingDirectionsButton;


    RadioButton handicapRadioButton;
    RadioButton weatherRadioButton;

    HBox directionsTitleBox;
    VBox directionsControlBox;
    HBox emailBox;

    javafx.scene.control.Button menuButton;
    Button infoButton;
    Button helpButton;
    Button locationClearButton;
    Button slidingButton;
    Button slidingEmailButton;
    Label newsLabel;
    ArrayList<InfoTip> infoTips = new ArrayList<>();
    int currentToolTip;

    /****************************************************************************************************************
                                                      Functions
     ****************************************************************************************************************/

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
        this.TIME_VISIBLE = new SimpleBooleanProperty(false);
        this.DIRECTIONS_VISIBLE = new SimpleBooleanProperty(false); // <<<<<

        this.PHOTO_ICON_VISIBLE = new SimpleBooleanProperty(false);
        this.ICON_VISIBLE = new SimpleBooleanProperty(false);

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
        HBox.setHgrow(slidingDirections, Priority.SOMETIMES); //directions


        sections.setStyle("-fx-background-color: #333333");
        sections.getChildren().addAll(slidingDashboard, slidingDirections, map); // directions
        /*****************************************************************/
        /** Add sections to Root */
        root = new StackPane();
        root.getChildren().add(sections);

        /*****************************************************************/
        /** create scene **/
        root.setAlignment(Pos.TOP_LEFT);
        createInfoTips();

        Scene scene = new Scene(root, MAP_WIDTH + MAP_BORDER * 2 + EDGE * 5 + expandedWidth * 2, MAP_HEIGHT + 2 * MAP_BORDER + EDGE * 4);//+MAP_BORDER*2+TITLE_HEIGHT
        //Scene scene = new Scene(root, MAP_WIDTH+MAP_BORDER*2+EDGE*2+expandedWidth*2, MAP_WIDTH + 2 * EDGE);//+MAP_BORDER*2+TITLE_HEIGHT

        return scene;

    }

    /****************************************************************************************************************
                                Functions that populate base visual features
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

        divider_3.setTranslateX(GAP);
        AnchorPane.setTopAnchor(divider_3, 0.0);
        AnchorPane.setLeftAnchor(divider_3, GAP);
        AnchorPane.setRightAnchor(divider_3, GAP);

        divider_3.setTranslateY(-1);

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

        Image gears = FileFetch.getImageFromFile("gears.png", 20, 20, true, true);

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

        //AnchorPane.setBottomAnchor(gearsView, GAP * 2);//TODO these will change wit.setStyle("-fx-background-color: #333333");h svg
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
            //bar.setId("normallyEEEEEEBG");
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

        locationClearButton = new Button();
        Label clearLabel = new Label("Clear");
        clearLabel.setTextFill(Color.web("#eeeeee"));
        clearLabel.setStyle("-fx-font-size:11");
        locationClearButton.setGraphic(clearLabel);
        locationClearButton.setTranslateX(26);
        locationClearButton.setTranslateY(1);
        locationClearButton.setId("clear-button");

        locationClearButton.setOnMouseClicked(e -> handleClear());

        locationLabelBox.getChildren().addAll(locationLabel, locationClearButton);
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

        //TODO REMOVE THIS TEMP BUTTOn
        javafx.scene.control.Button goButton = new Button("GO");
        //goButton.setOnAction();


        /*****************************************************************/
        /** Get Connected NewsFeed **/

        HBox newsLabelBox = new HBox();
        newsLabelBox.setMinHeight(EDGE);
        newsLabelBox.setMaxHeight(expandedWidth);
        newsLabelBox.setPrefHeight(EDGE);
        newsLabelBox.setAlignment(Pos.CENTER);

        newsLabel = new Label("Get Involved");
        newsLabel.setTextFill(Color.web("#eeeeee"));

        newsLabelBox.getChildren().addAll(newsLabel);
        AnchorPane.setTopAnchor(newsLabelBox, 3 * EDGE + GAP * .5 + 20);
        AnchorPane.setLeftAnchor(newsLabelBox, EDGE);

        Image newsIcon = FileFetch.getImageFromFile("news37.png", 20, 20, true, true);
        ImageView newsIconView = new ImageView(newsIcon);

        newsIconView.visibleProperty().bind(DASHBOARD_VISIBLE);
        AnchorPane.setTopAnchor(newsIconView, 3 * EDGE + GAP * .5 + 30);
        AnchorPane.setLeftAnchor(newsIconView, 2 * GAP);

        ListView<Event> eventListView = createEventListView();
        eventListView.setId("newsfeed-list-view");

        AnchorPane.setTopAnchor(eventListView, 3 * EDGE + GAP * .5 + 60);
        AnchorPane.setBottomAnchor(eventListView, EDGE-3);
        eventListView.visibleProperty().bind(DASHBOARD_VISIBLE);


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


        //settings a sliding pane!div

        slidingSettings = new SlidingAnchorPane(expandedWidth, EDGE, Direction.UP, SETTINGS_VISIBLE, gearsView); //remove EDGE * 2 + EDGE * 2 + 7 * 6
        slidingSettings.setStyle("-fx-background-color: #333333");

        slidingButton = slidingSettings.getButton();
        slidingButton.setId("dashboardButton");
        slidingButton.setMaxWidth(EDGE - 5);
        slidingButton.setMinWidth(EDGE - 5);
        slidingButton.setPrefWidth(EDGE - 5);
        //AnchorPane.setBottomAnchor(slidingButton, 0.0);
        //AnchorPane.setLeftAnchor(slidingButton, 0.0);
        settingsLabelBox.getChildren().addAll(slidingButton, settingsLabel);


        /**------------------------------------------------------------------------------------------------*/
        /**COLOR CHANGE SETTINGS---------------------------------------------------------------------------*/
        /**------------------------------------------------------------------------------------------------*/

        HBox settingsColorBox = new HBox();
        HBox settingsColorBoxLine2 = new HBox();
        Label settingsColorLabel = new Label("Color Style:");
        settingsColorLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        settingsColorLabel.setTextFill(Color.web("#eeeeee"));

        HBox settingsWeightBox = new HBox();
        Label settingsWeightLabel = new Label("Path Preferences:");
        settingsWeightLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        settingsWeightLabel.setTextFill(Color.web("#eeeeee"));

        //TODO: Force all nodes/etc. to update on interaction with checkbox
        final ToggleGroup colorgroup = new ToggleGroup();
        final ToggleGroup weightgroup = new ToggleGroup();
        RadioButton w1 = new RadioButton("Handicap");
        RadioButton w2 = new RadioButton("Inside Preferred");
        w1.setToggleGroup(weightgroup);
        w1.setUserData("Handicap");
        w1.setText("Handicap");
        w1.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        w1.setTextFill(Color.web("#eeeeee"));

        w2.setToggleGroup(weightgroup);
        w2.setUserData("Inside Preferred");
        w2.setText("Inside Preferred");
        w2.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        w2.setTextFill(Color.web("#eeeeee"));


        RadioButton rb1 = new RadioButton("Colorblind");
        rb1.setToggleGroup(colorgroup);
        rb1.setUserData("Colorblind");
        rb1.setText("Colorblind");
        rb1.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        rb1.setTextFill(Color.web("#eeeeee"));

        RadioButton rb2 = new RadioButton("Default");
        rb2.setToggleGroup(colorgroup);
        rb2.setUserData("Default");
        rb2.setText("Default");
        rb2.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        rb2.setTextFill(Color.web("#eeeeee"));
        rb2.setSelected(true);

        colorgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (colorgroup.getSelectedToggle() != null) {
                    if(colorgroup.getSelectedToggle().getUserData().toString().equals("Default")){
                        mapDisplay.setNodePathDefault();
                        controller.setStyleSheet("../visuals/style.css");
                        directionStyle = "-fx-background-color: #ac2738";
                        directionsTitleBox.setStyle("-fx-background-color: #ac2738");
                        directionsControlBox.setStyle("-fx-background-color: #ac2738");

                        //mapDisplay.changeBackOldPathNodes();
                        //controller.updateNodeInformation(controller.getNode(mapDisplay.getStartID()));
                    }

                    if(colorgroup.getSelectedToggle().getUserData().toString().equals("Colorblind")){
                        mapDisplay.setNodePathColorBlind();
                        controller.setStyleSheet("../visuals/styleColorBlind.css");
                        directionStyle = "-fx-background-color: #ffa500";
                        directionsTitleBox.setStyle("-fx-background-color: #ffa500");
                        directionsControlBox.setStyle("-fx-background-color: #ffa500");

                        //   mapDisplay.changeBackOldPathNodes();
                        //controller.updateNodeInformation(controller.getNode(mapDisplay.getStartID()));
                    }

                }
            }
        });


        weightgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (weightgroup.getSelectedToggle() != null) {
                    if(weightgroup.getSelectedToggle().getUserData().toString().equals("Handicap")){
                    }

                    if(weightgroup.getSelectedToggle().getUserData().toString().equals("Inside Preferred")){
                    }

                }
            }
        });

        settingsColorBox.getChildren().add(settingsColorLabel);
        settingsColorBox.getChildren().add(rb2);
        settingsColorBox.getChildren().add(rb1);
        //settingsColorBoxLine2.getChildren().add(rb3);

        settingsWeightBox.getChildren().add(settingsWeightLabel);
        settingsWeightBox.getChildren().add(w1);
        settingsWeightBox.getChildren().add(w2);
        /**-------------------------------------------------------------------------------------------*/

        HBox settingsWalkingBox = new HBox();
        Label settingsWalkingLabel = new Label("Set walking speed:");
        settingsWalkingLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        settingsWalkingLabel.setTextFill(Color.web("#eeeeee"));

        settingsWalkingBox.getChildren().addAll(settingsWalkingLabel);

        walkingArrayList = new ArrayList<>();
        walkingArrayList.add(new Walking("Casual (Walking with your Grandmother)", 2.0));
        walkingArrayList.add(new Walking("Quick  (Walking to Class)", 3.0));
        walkingArrayList.add(new Walking("Fast   (Late to Class)", 4.0));
        walkingArrayList.add(new Walking("Ultra  (Late to Interview)", 6.0));
        Inputs walkingSpeedBox = new Inputs("Select walking speed", INPUT_WIDTH, controller);
        walkingSpeedBox.setTranslateX(3);  //TODO fix width of this?
        walkingSpeedBox.setItems(walkingSpeedBox.createWalkingItems(walkingArrayList));
        if (User.isUserNew()) {
            walkingSpeedBox.setValue(walkingArrayList.get(1));
            User.setSpeed(3.0);
        }
        else {
            int speed = (int)User.getSpeed();
            int index = 1;
            switch (speed) {
                case 2 : index = 0; break;
                case 3 : index = 1; break;
                case 4 : index = 2; break;
                case 6 : index = 3; break;
            }
            walkingSpeedBox.setValue(walkingArrayList.get(index));
        }

        walkingSpeedBox.setOnAction(e -> handleWalkingInput(walkingSpeedBox, true));    //TODO finish handleWalkingInput

        TextField emailTextField = new TextField();
        if (User.getEmail() == null) emailTextField.setPromptText("Enter your email");
        else                         emailTextField.setText(User.getEmail());
        emailTextField.setMaxWidth(INPUT_WIDTH);
        emailTextField.setMaxHeight(walkingSpeedBox.getMaxHeight());
        emailTextField.setTranslateX(3);
        emailTextField.setTranslateY(5);
        emailTextField.setId("text-field");

        Label setEmailLabel = new Label("Set your email:");
        setEmailLabel.setStyle("-fx-padding: 8 8; -fx-font-size:12;");
        setEmailLabel.setTextFill(Color.web("#eeeeee"));
        setEmailLabel.setTranslateY(3);

        emailTextField.setOnAction(e -> handleEmailInput(emailTextField, true));

        settingsWalkingBox.setTranslateX(EDGE - 7);
        walkingSpeedBox.setTranslateX(EDGE/* - 42*/ );
        setEmailLabel.setTranslateX(EDGE - 7);
        emailTextField.setTranslateX(EDGE/* - 42*/ );
        /**---------------------------------------*/
        settingsColorLabel.setTranslateX(EDGE - 7);
        settingsColorBox.setTranslateX(EDGE/* - 42*/);
        settingsColorBoxLine2.setTranslateX(EDGE/* - 42*/);
        /**---------------------------------------*/
        //buttons for handicap / weather

        /*

        handicapRadioButton = new RadioButton();
        handicapRadioButton.setText("Handicap");
        handicapRadioButton.setTextFill(Color.web("eee"));
        weatherRadioButton  = new RadioButton("Weather");
        weatherRadioButton.setText("Weather");
        weatherRadioButton.setTextFill(Color.web("eee"));
        handicapRadioButton.setTranslateX(EDGE);
        handicapRadioButton.setTranslateY(8);
        weatherRadioButton.setTranslateX(EDGE);
        weatherRadioButton.setTranslateY(11); //TODO play with this

        handicapRadioButton.setOnAction(e -> handleRadioButtons());
        weatherRadioButton.setOnAction(e -> handleRadioButtons());

         */
        //handicapRadioButton, weatherRadioButton


        VBox settingsVbox = new VBox();
        settingsVbox.visibleProperty().bind(DASHBOARD_VISIBLE);
        settingsVbox.setPrefWidth(300); //TODO make this less dirty
        //settingsVbox.getChildren().addAll(divider_3, settingsLabelBox,  settingsWalkingBox, walkingSpeedBox, setEmailLabel, emailTextField);

        settingsWeightLabel.setTranslateX(EDGE - 7);
        settingsWeightBox.setTranslateX(EDGE/* - 42*/);



        /** has weight settings */
        //settingsVbox.getChildren().addAll(divider_3, settingsLabelBox, settingsWalkingBox, walkingSpeedBox, settingsWeightLabel, settingsWeightBox, settingsColorLabel, settingsColorBox, settingsColorBoxLine2, setEmailLabel, emailTextField);
        /** doesn't have weight settings */
        settingsVbox.getChildren().addAll(divider_3, settingsLabelBox, settingsWalkingBox, walkingSpeedBox, /*settingsColorLabel, settingsColorBox, settingsColorBoxLine2,*/ setEmailLabel, emailTextField);


        AnchorPane.setBottomAnchor(slidingSettings, 0.0);// 2 * EDGE - 2 * GAP - 20);
        AnchorPane.setLeftAnchor(slidingSettings, 0.0);
        slidingSettings.getChildren().addAll(settingsVbox);

        //VBOX: Divider - seetingsLabelBox - actual settings


        /*****************************************************************/
        /** Building of Sliding Dashboard Anchorpane  **/
        this.slidingDashboard = new SlidingAnchorPane(expandedWidth, EDGE, Direction.LEFT, DASHBOARD_VISIBLE, bars, divider_0, divider_1, dashBoardTitleBox, locationLabelBox, pinView, inputs, newsIconView, newsLabelBox, eventListView, slidingSettings); //gearsView, settingsLabelBox, divider_3, // resourcesLabelBox, infoView,
        slidingDashboard.setStyle("-fx-background-color: #333333");

        /** STYLE BUTTON HERE **/
        menuButton = slidingDashboard.getButton();
        menuButton.setId("dashboardButton");
        menuButton.setMaxWidth(EDGE);
        menuButton.setMinWidth(EDGE);
        menuButton.setPrefWidth(EDGE);
        AnchorPane.setTopAnchor(menuButton, 0.0);
        AnchorPane.setLeftAnchor(menuButton, 0.0);
        //slidingDashboard.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + 2 * EDGE);
        slidingDashboard.getChildren().addAll(menuButton);
    }

    private ListView<Event> createEventListView() {
        ListView<Event> listView = new ListView<>();
        listView.setId("newsfeed-list-view");
        listView.setMaxWidth(expandedWidth *2);
        Feed f = new Feed(50);

        ObservableList<Event> items = FXCollections.observableArrayList ();

        //TODO check for internet access here
        for(Event e : f) {
            //String x = "\n" + e.getTitle() + "\n" + e.getDateInfo() + "\n";
            items.add(e);
        }

        listView.setCellFactory((ListView<Event> lv) ->
                new ListCell<Event>() {
                    @Override
                    public void updateItem(Event in, boolean empty) {
                        super.updateItem(in, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            // use whatever data you need from the album
                            // object to get the correct displayed value:
                            Text text = new Text(in.toString());
                            text.setFill(Color.web("#eee"));
                            text.setWrappingWidth(expandedWidth-10);
                            //setText(in.toString());
                            setGraphic(text);
                            setId("event-list-cell");
                            //setStyle("-fx-font-fill:#eee; -fx-background-color:#333;" +
                                   // "-fx-border-color:#888;-fx-border-width:1 0 0 0;");
                            setTranslateX(GAP);

                            setOnMouseClicked(e -> {
                                //Popover?
                                eventPopOver = createPopOverForEvent(in);
                                eventPopOver.setId("event-popover");
                                eventPopOver.show(this, 10);
                            });
                        }
                    }
                }
        );

        listView.setItems(items);

        return listView;
    }

    public PopOver createPopOverForEvent(Event in) {
        PopOver popOver = new PopOver();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding:8 8 8 8;");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(2);
        flowPane.setVgap(2);
        flowPane.setPrefWrapLength(275);

        Text title = new Text(in.getTitle());
        title.setWrappingWidth(275);
        title.setStyle("-fx-font-weight:bold;");

        Text location = new Text(in.getLocation());
        location.setWrappingWidth(275);

        Text date = new Text(in.getDateInfo());
        date.setWrappingWidth(275);

        Text description = new Text(in.getDescription());
        description.setWrappingWidth(275);

        flowPane.getChildren().add(description);

        HBox bottomHBox = new HBox();
        Button goToButton = new Button();
        goToButton.setGraphic(new Text("Go to location"));
        goToButton.setId("popover-buttons");
        goToButton.setStyle("-fx-max-width:200 !important");

        Button moreInfoButton = new Button();
        moreInfoButton.setGraphic(new Text("More info"));
        moreInfoButton.setId("popover-buttons");
        moreInfoButton.setStyle("-fx-max-width:200 !important");

        moreInfoButton.setOnMouseClicked(e -> {
            showMoreEventInfo(in);
            eventPopOver.hide();
        });

        bottomHBox.getChildren().addAll(goToButton, moreInfoButton);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setSpacing(10);

        vbox.getChildren().addAll(title, location, date, flowPane, bottomHBox);
        vbox.setAlignment(Pos.CENTER);
        popOver.setContentNode(vbox);

        return popOver;
    }

    public void showMoreEventInfo(Event in) {
        StackPane imageStack = new StackPane();
        StackPane shadowStack = new StackPane();
        shadowStack.setStyle("-fx-background-color: #333333; -fx-opacity: .75");

        imageStack.setOnMouseClicked(e -> {
            this.root.getChildren().removeAll(imageStack, shadowStack);
        });

        VBox vbox = new VBox();
        vbox.setId("about-panel");
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.TOP_CENTER);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setId("about-title");
        Label aboutLabel = new Label(in.getTitle());
        aboutLabel.setId("about-label");
        aboutLabel.setTextFill(Color.web("#eeeeee"));

        hbox.getChildren().add(aboutLabel);

        FlowPane flowPane = new FlowPane();
        Text text = new Text();
        text.setId("about-text");
        text.setWrappingWidth(500);
        //text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(in.getDescription());

        flowPane.setPrefWrapLength(500);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(text);

        vbox.getChildren().addAll(hbox, flowPane);
        imageStack.getChildren().add(vbox);

        this.root.getChildren().addAll(shadowStack, imageStack);
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
        directionsTitleBox = new HBox();
        //directionsTitleBox.getStyleClass().add("directionLabel");
        directionsTitleBox.setStyle(directionStyle);
        directionsTitleBox.setMinHeight(EDGE);
        directionsTitleBox.setMaxHeight(EDGE);
        directionsTitleBox.setPrefHeight(EDGE);
        directionsTitleBox.setAlignment(Pos.CENTER_LEFT);
        directionsTitleBox.setSpacing(GAP * 3);

        directionsControlBox = new VBox();
        directionsControlBox.setStyle(directionStyle);

        Image directionsArrow = FileFetch.getImageFromFile("forward.png", 27, 27, true, true);

        ImageView directionsArrowView = new ImageView(directionsArrow);
        //directionsArrowView.setTranslateX(8);
        directionsArrowView.setTranslateY(5);
        //directionsControlBox.getChildren().addAll(directionsArrowView); //TODO CHANGE BACK
        //directionsControlBox.getStyleClass().add("directionLabel");
        //directionsControlBox.setStyle("-fx-background-color: #ac2738");
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

        Image leftArrow = FileFetch.getImageFromFile("leftArrow.png", 24, 24, true, true);

        ImageView leftArrowView = new ImageView(leftArrow);

        leftArrowButton = new Button();
        leftArrowButton.setGraphic(leftArrowView);
        leftArrowButton.setId("arrow-buttons-grayed");
        leftArrowButton.setStyle("-fx-background-color:white;");

        leftArrowButton.setOnAction(e -> {
            handleLeftArrowButton();
            rightArrowButton.setId("arrow-buttons");
            rightArrowButton.setStyle("-fx-background-color:white;");

            //controller.handleIncrementPathMap();
        });

        Image rightArrow = FileFetch.getImageFromFile("rightArrow.png", 24, 24, true, true);

        ImageView rightArrowView = new ImageView(rightArrow);

        rightArrowButton = new Button();
        rightArrowButton.setGraphic(rightArrowView);
        rightArrowButton.setId("arrow-buttons-grayed");
        rightArrowButton.setStyle("-fx-background-color:white;");
        rightArrowButton.setOnAction(e -> {
            handleRightArrowButton();
            leftArrowButton.setId("arrow-buttons");
            leftArrowButton.setStyle("-fx-background-color:white;");

            //controller.handleIncrementPathMap();
        });

        AnchorPane.setRightAnchor(leftArrowButton, expandedWidth - 5.5 - leftArrowButton.getPrefWidth());
        AnchorPane.setTopAnchor(leftArrowButton, 5.5);
        AnchorPane.setLeftAnchor(leftArrowButton, 8.0);

        AnchorPane.setLeftAnchor(rightArrowButton, expandedWidth - 5.5 - rightArrowButton.getPrefWidth());
        AnchorPane.setTopAnchor(rightArrowButton, 5.5);
        AnchorPane.setRightAnchor(rightArrowButton, 8.0);

        VBox vbox = new VBox();

        Label topLabel = new Label("Time Estimation:");
        topLabel.setId("time-label");
        topLabel.setTextFill(Color.web("#333"));
        topLabel.visibleProperty().bind(TIME_VISIBLE);

        totalTimeLabel = new Label();
        totalTimeLabel.setText(Directions.getTime(User.getSpeed()));
        totalTimeLabel.setId("time-label");
        totalTimeLabel.setTextFill(Color.web("#333"));
        totalTimeLabel.visibleProperty().bind(TIME_VISIBLE);

        vbox.getChildren().addAll(topLabel, totalTimeLabel);
        vbox.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(vbox, 65.0);
        AnchorPane.setTopAnchor(vbox, 4.0);

        instructionArrows.getChildren().addAll(leftArrowButton, vbox, rightArrowButton);

        AnchorPane.setTopAnchor(instructions, EDGE + 36);
        AnchorPane.setLeftAnchor(instructions, 0.0);
        AnchorPane.setRightAnchor(instructions, 0.0);
        AnchorPane.setBottomAnchor(instructions, EDGE);
        //instructions.setPrefHeight(MAP_HEIGHT + 2 * EDGE - 36);

        /** Email Box **/

        emailBox = new HBox();
        emailBox.setId("normallyWhiteBG");
        emailBox.setMinHeight(EDGE);
        emailBox.setMaxHeight(EDGE);
        emailBox.setPrefHeight(EDGE);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        emailBox.setSpacing(GAP * 3);

        Image emailImage = FileFetch.getImageFromFile("email109.png", 25, 25, true, true);

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
        emailLabel.setId("normallyWhiteBG");

        //emailLabel.setStyle("-fx-background-color:white;");
        //emailBox.setStyle("-fx-background-color:white;");

        //emailBox.getChildren().addAll(emailIconBox, emailLabel);

       /* emailLabel.setOnMouseClicked(e -> handleEmail(emailBox));
        emailView.setOnMouseClicked(e -> handleEmail(emailBox));*/

        /** Sliding Anchor Pane **/
        slidingEmail = new SlidingAnchorPane(EDGE * 2, EDGE, Direction.UP, EMAIL_VISIBLE, emailView);
        slidingEmail.setId("normallyWhiteBG");

        slidingEmailButton = slidingEmail.getButton();
        slidingEmailButton.setId("dashboardButton");
        slidingEmailButton.setId("normallyWhiteBG");
        slidingEmailButton.setMaxWidth(EDGE - 5);
        slidingEmailButton.setMinWidth(EDGE - 5);
        slidingEmailButton.setPrefWidth(EDGE - 5);

        HBox divider_4 = createDivider();
        divider_4.visibleProperty().bind(new SimpleBooleanProperty(true));
        divider_4.setTranslateY(-21);
        divider_4.setTranslateX(-145);   //TODO fix this janky translate garbage

        emailBox.getChildren().addAll(slidingEmailButton, emailLabel, divider_4);

        /////////// INFO IN EMAIL SLIDE

        VBox emailBoxContent = new VBox();
        yourEmail = new TextField();
        yourEmail.setStyle("-fx-font-size:12;-fx-padding:4 4;");
        yourEmail.setPromptText("Enter your email");

        yourEmail.setTranslateX(0);
        yourEmail.setPrefWidth(190);
        yourEmail.setMinWidth(190);
        yourEmail.setMaxWidth(190);
        yourEmail.setId("email-text-field");

        yourEmail.setOnAction(e -> handleEmailInput2(yourEmail, true));
        Button go = new Button("Send Directions");
        go.setId("email-button");
        go.setTranslateX(45);
        emailBoxContent.getChildren().addAll(yourEmail, go);
        go.setOnAction(e -> handleEmailInput2(yourEmail, true));
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

        instructions.setOnMouseEntered(e -> {
            instructions.requestFocus();
        });

        slidingDirections = new SlidingAnchorPane(expandedWidth, EDGE, Direction.LEFT, DIRECTIONS_VISIBLE, directionsArrowView);
        slidingDirectionsButton = slidingDirections.getButton();
        slidingDirectionsButton.setId("directionsButton");//TODO jake fix ccs here
        slidingDirectionsButton.setMaxWidth(EDGE);
        slidingDirectionsButton.setMinWidth(EDGE);
        slidingDirectionsButton.setPrefWidth(EDGE);
        directionsControlBox.getChildren().addAll(slidingDirectionsButton);
//        directions.getChildren().addAll(directionsTitleBox, instructionArrows, instructions, slidingEmail);
//        directions.setStyle("-fx-background-color: #ffffff");
//        directions.setPrefWidth(expandedWidth + EDGE);
//        directions.setMinWidth(0);
//        directions.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + EDGE);
        //directionsTitleBox.visibleProperty().bind(DIRECTIONS_VISIBLE);
        directionsTitleLabel.visibleProperty().bind(DIRECTIONS_VISIBLE);
        instructionArrows.visibleProperty().bind(DIRECTIONS_VISIBLE);
        instructions.visibleProperty().bind(DIRECTIONS_VISIBLE);
        slidingEmail.visibleProperty().bind(DIRECTIONS_VISIBLE);

        slidingDirections.getChildren().addAll(directionsTitleBox, instructionArrows, instructions, slidingEmail);
        slidingDirections.setId("normallyWhiteBG");
        slidingDirections.setPrefWidth(expandedWidth + EDGE);
        slidingDirections.setMinWidth(0);
        slidingDirections.setPrefHeight(MAP_HEIGHT + 2 * MAP_BORDER + EDGE);
        slidingDirections.playHidePane(DIRECTIONS_VISIBLE);
        //testing sliding stuff:
        //slidingDirections.getChildren().addAll(directions);


    }

    private void initMap() {
        this.map = new AnchorPane();
        //this.map.setStyle("-fx-background-color: #f4f4f4");
        /** Title **/
        HBox mapTitle = new HBox();
        Label mapTitleLabel = new Label("CapraNav");
        mapTitleLabel.setTextFill(Color.web("#eeeeee"));
        //ATTENTION: below is some nice trixksz! it binds the location of the title to the center
        mapTitleLabel.translateXProperty().bind((mapTitle.widthProperty().subtract(mapTitleLabel.widthProperty()).divide(2)));
        mapTitleLabel.translateYProperty().bind((mapTitle.heightProperty().subtract(mapTitleLabel.heightProperty()).divide(2)));

        Image help = FileFetch.getImageFromFile("question58.png", 18, 18, true, true);
        ImageView helpButtonView = new ImageView(help);
        helpButton = new Button();
        helpButton.setGraphic(helpButtonView);
        helpButton.setId("question-button");
        helpButton.setTranslateX(-80);  //TODO fix this janky shit
        helpButton.setTranslateY(10);
        helpButtonView.setFitHeight(18);

        helpButton.setOnMouseClicked(e -> handleHelp());

        Image info = FileFetch.getImageFromFile("info.png", 18, 18, true, true);
        ImageView infoButtonView = new ImageView(info);
        infoButton = new Button();
        infoButton.setGraphic(infoButtonView);
        infoButton.setId("question-button");
        infoButton.setTranslateX(-65);  //TODO fix this janky shit
        infoButton.setTranslateY(10);
        infoButtonView.setFitHeight(18);

        infoButton.setOnMouseClicked(e -> {
            this.controller.showAboutPanel();
        });

        mapTitle.setMaxHeight(EDGE);
        mapTitle.setPrefHeight(EDGE);
        mapTitle.setMinHeight(EDGE);
        mapTitle.setStyle("-fx-background-color: #444444");
        mapTitle.getChildren().addAll(mapTitleLabel, helpButton, infoButton);

        AnchorPane.setTopAnchor(mapTitle, 0.0);
        AnchorPane.setLeftAnchor(mapTitle, 0.0);
        AnchorPane.setRightAnchor(mapTitle, 0.0);

        /** Hidden Sliding Panel **/
        //slidingBuilding  = new SlidingAnchorPane(EDGE * 2, EDGE, Direction.UP, BUILDING_VISIBLE, new Text("hidden"));
        HBox nodeBox = createNodeBox();
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
        mapPane.setStyle("-fx-background-color:#eee;");
        mapPane.setAlignment(Pos.CENTER);

        Group group = new Group(mapPane);
//        GraphicsScaling graphicsScaling = new GraphicsScaling();
//        Parent zoomPane = graphicsScaling.createZoomPane(group);
        zoomAndPan = new ZoomAndPan();
        Parent zoomPane = zoomAndPan.createZoomPane(group);
        zoomPane.setOnMouseEntered(e -> {
            zoomPane.requestFocus();
        });
        zoomPane.setStyle("-fx-background-color:#eee;");

        AnchorPane.setTopAnchor(zoomPane, EDGE);//
        AnchorPane.setLeftAnchor(zoomPane, MAP_BORDER);//00
        AnchorPane.setRightAnchor(zoomPane, MAP_BORDER);
        AnchorPane.setBottomAnchor(zoomPane, EDGE * 2); //+ GAP + 2 * EDGE
        //ImageView imageView = new ImageView();
       // mapDisplay.mapView.fitHeightProperty().bind(map.layoutYProperty());
       // mapDisplay.mapView.fitWidthProperty().bind(map.layoutXProperty());


        map.setMinWidth(MAP_WIDTH);
        map.setPrefWidth(MAP_WIDTH + MAP_BORDER * 2);

        map.setMinHeight(MAP_HEIGHT + EDGE);
        map.setPrefHeight(MAP_HEIGHT + MAP_BORDER * 2 + EDGE + EDGE); // + EDGE for NODE INFO

        map.setId("mapBorder");
        map.getChildren().addAll(mapTitle, zoomPane, information);
    }

    /****************************************************************************************************************
                         Functions that set up further subsidiaries of base visual features
     ****************************************************************************************************************/

    private StackPane createMapPane() {
        mapPane = new StackPane();
        mapPane.setPrefHeight(MAP_HEIGHT + MAP_BORDER * 2);
        mapPane.setMinHeight(MAP_HEIGHT);
        mapPane.setPrefWidth(MAP_WIDTH + MAP_BORDER * 2);
        mapPane.setMinWidth(MAP_WIDTH);

        //mapPane.setId("normallyEEEEEEBG");
        mapPane.setStyle("-fx-background-color:#333;");
        this.mapDisplay = new MapDisplay(this.controller); //(width - GAP * 2 - BUTTON_SIZE - INPUT_WIDTH - WIDTH_BUFFER * 2), (height - TABLE_HEIGHT - GAP * 2 - 2 * HEIGHT_BUFFER),
        mapPane.getChildren().add(mapDisplay);
        //mapPane.setTranslateX(WIDTH_BUFFER + GAP * 2 + INPUT_WIDTH + BUTTON_SIZE);
        //mapPane.setTranslateY(HEIGHT_BUFFER);
        return mapPane;
    }


    private HBox createNodeBox() {
        HBox hbox = new HBox();

        /*nodeViewHolder = new StackPane();
        nodeTitle = new Label();
        nodeTransitionButton = new javafx.scene.control.Button();

        Image nodeIconImage = FileFetch.getImageFromFile("picture.png", 27, 27, true, true);

        ImageView nodeIconView = new ImageView(nodeIconImage);
        nodeTransitionButton.setGraphic(nodeViewHolder);
        nodeIconView.setFitHeight(27);
        nodeIconView.setFitWidth(27);

        nodeTransitionButton.visibleProperty().bind(PHOTO_ICON_VISIBLE);
        this.nodeTransitionButton.setId("arrow-buttons");
        this.nodeTransitionButton.setStyle("-fx-background-color:#eeeeee");

        nodeIconViewButton = new javafx.scene.control.Button();
        nodeIconViewButton.visibleProperty().bind(PHOTO_ICON_VISIBLE);
        nodeIconViewButton.setGraphic(nodeIconView);
        nodeIconViewButton.setOnAction(event -> handleFullScreenPicture());
        nodeIconViewButton.setId("arrow-buttons");
        nodeIconViewButton.setStyle("-fx-background-color: #eeeeee");*/

        Button backToCampus = new Button();
        backToCampus.setText("Back to Campus");
        backToCampus.setId("campus-button");
        backToCampus.setOnMouseClicked(e -> {
            if(controller.getCurrentMap().getID() != 0) {
                controller.prevBuilding = 0;
                controller.setCurrentMap(0);
                controller.hideBuildingPane();
            }

        });


        HBox box = new HBox();
        this.left = new javafx.scene.control.Button();
        this.right = new javafx.scene.control.Button();

        Image minus = FileFetch.getImageFromFile("minus104.png", 20, 20, true, true);
        Image plus = FileFetch.getImageFromFile("plus79.png", 20, 20, true, true);

        ImageView minusView = new ImageView(minus);
        ImageView plusView = new ImageView(plus);

        this.left.setGraphic(minusView);
        this.right.setGraphic(plusView);

        left.setId("arrow-buttons");
        left.setStyle("-fx-background-color:#eee");
        right.setId("arrow-buttons");
        right.setStyle("-fx-background-color:#eee");
        buildingName = new Label();
        buildingNumber = new Label();

        left.setOnMouseClicked(e -> controller.handleDecreaseFloorButton());
        right.setOnMouseClicked(e -> controller.handleIncreaseFloorButton());

        left.setMaxWidth(24);
        left.setMinWidth(24);
        left.setMaxHeight(24);
        left.setMinHeight(24);

        right.setMaxWidth(24);
        right.setMinWidth(24);


        hbox.getChildren().addAll(left, backToCampus, right);
        hbox.visibleProperty().bind( BUILDING_VISIBLE);
        hbox.setMaxHeight(EDGE);
        hbox.setMinHeight(EDGE);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(GAP);//TODO MAKE SURE THIS LOOKS GOOD
        hbox.setStyle("-fx-background-color:#eee;");
        box.setStyle("-fx-background-color:#eee;");

        return hbox;
    }


    public void updatePictureIcon(boolean val) {
        //change visibility
        logger.info("Entered updatePictureIcon with {}", val);
        PHOTO_ICON_VISIBLE.setValue(val);
        //change image it is connected with
    }

    public void updateNodeTitle(String s) {
        this.nodeTitle.setText(s);
    }

    public void updateNodeIcon(ImageView i, INode iNode) {
        logger.info("Entered updateNodeIcon with iNode {}", iNode.toString());
        this.nodeViewHolder.getChildren().remove(nodeView);
        this.nodeViewHolder.getChildren().add(i); //= i;
        this.nodeView = i;
        if (iNode.isTransition()) {
            this.nodeTransitionButton.setId("arrow-buttons");
            this.nodeTransitionButton.setOnAction(e -> controller.handleEnterBuilding((Transition) iNode));
        } else {
            this.nodeTransitionButton.setId("arrow-buttons-grayed2");
            this.nodeTransitionButton.setOnAction(e -> {});//// TODO: 12/2/15 fix
        }
    }

    public void updateTimeEstimation() {
        String input;
        //input = "Time Estimation:\n";
        input = Directions.getTime(User.getSpeed());
        totalTimeLabel.setText(input);
    }

    //TODO THIS IS START OF BUILDING BOX PANE!
    private HBox createBuildingBox() { //its going to be an HBox with stuff inside of the sliding anchorpane

          HBox box = new HBox();
//        this.left = new javafx.scene.control.Button();
//        this.right = new javafx.scene.control.Button();
//
//        Image minus = FileFetch.getImageFromFile("minus104.png", 20, 20, true, true);
//        Image plus = FileFetch.getImageFromFile("plus79.png", 20, 20, true, true);
//
//        ImageView minusView = new ImageView(minus);
//        ImageView plusView = new ImageView(plus);
//
//        this.left.setGraphic(minusView);
//        this.right.setGraphic(plusView);
//
//        this.left.setStyle("-fx-background-color:#eee;");
//        this.right.setStyle("-fx-background-color:#eee;");
//
          buildingName = new Label();
          buildingNumber = new Label();
//
//        left.setOnMouseClicked(e -> controller.handleDecreaseFloorButton());
//        right.setOnMouseClicked(e -> controller.handleIncreaseFloorButton());

        box.setMaxHeight(EDGE);
        box.setMinHeight(0);
        box.setPrefHeight(0);

        //TODO LEFT AND RIGHT HERE
        box.setAlignment(Pos.CENTER);
        box.setSpacing(GAP);
        box.getChildren().addAll( buildingName, buildingNumber);
        return box;
    }

    public void setRightButtonID(String id) {
        right.setId(id);
    }
    public void setRightButtonStyle(String style) {
        right.setStyle(style);
    }

    public void setLeftButtonID(String id) {
        left.setId(id);
    }
    public void setLeftButtonStyle(String style) {
        left.setStyle(style);
    }

    public void setBuildingName(String s) {
        this.buildingName.setText(s);
    }

    public void setBuildingNumber(int i) {
        //TODO ADD FLICKERING ANIMATION
        //System.out.println("building number set called");
        this.buildingNumber.setText(Integer.toString(i));
    }

    private VBox createInput() {

		/* start */
        this.start = new Inputs<String>("Search WPI Maps", INPUT_WIDTH, controller);
        start.setOnAction(e -> handleSearchInput(start, true));
//        start.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                //.getSelectionModel().getSelectedItem();
//                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB){
//                    //handleSearchInput(start, true);
//                    handleSearchInput(start, true);
//                    end.requestFocus();
//                }
//            }
//        });



		/* end */
        this.end = new Inputs<String>("For Destination", INPUT_WIDTH, controller);
        end.setOnAction(e -> handleSearchInput(end, false));
//        end.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                //.getSelectionModel().getSelectedItem();
//                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB){
//                    handleSearchInput(end, true);
//                    root.requestFocus();
//                }
//            }
//        });



        /*
        start.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("Start ENTER");
                    handleSearchInput(start, true);
                    end.requestFocus();
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("Start ESCAPE");
                    start.setValue(null);
                    end.setValue(null);
                } else if (event.getCode() == KeyCode.TAB) {
                    System.out.println("Start TAB");
                    end.requestFocus();
                }
            }});
            */

        //start.setOnAction(e -> handleSearchInput(start, true));

		/* end */
       /*
        end.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("End ENTER");
                    handleSearchInput(end, true);
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("End ESCAPE");
                    start.setValue(null);
                    end.setValue(null);
                } else if (event.getCode() == KeyCode.TAB) {
                    System.out.println("End TAB");
                    handleSearchInput(end, true);
                }
            }});
*/

       // end.setOnAction(e -> handleSearchInput(end, false));

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
        end.setTranslateY(3);
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
        //this.instructions.setId("normallyEEEEEEBG");
        this.instructions.setStyle("-fx-background-color:white;");
        this.instructions.setPrefWidth(expandedWidth-10);

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
                            Text text = new Text(in.toString());
                            text.setWrappingWidth(expandedWidth-10);
                            //setText(in.toString());
                            setGraphic(text);
                        }
                    }
                }
        );


        instructions.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Instructions> obs, Instructions oldinstruction, Instructions selectedInstruction) -> {
                    if (selectedInstruction != null) {
                        FLIP = false;
                        this.mapDisplay.highlightPath(selectedInstruction.getNode().getID());
                        this.mapDisplay.softSelectAnimation(controller.lastSoft, selectedInstruction.getNode().getID());
                        controller.lastSoft = selectedInstruction.getNode().getID();
                        zoomAndPan.zoomToNode(controller.getNode(selectedInstruction.getNode().getID()));
                    }
                });

        //instructions.setFocusModel();
        instructions.setOnMouseClicked(event -> {
            if (instructions.getSelectionModel().getSelectedItem() != null && FLIP) {
                this.mapDisplay.highlightPath(instructions.getSelectionModel().getSelectedItem().getNode().getID());
                this.mapDisplay.softSelectAnimation(controller.lastSoft, instructions.getSelectionModel().getSelectedItem().getNode().getID());
                controller.lastSoft = instructions.getSelectionModel().getSelectedItem().getNode().getID();
                zoomAndPan.zoomToNode(controller.getNode(instructions.getSelectionModel().getSelectedItem().getNode().getID()));
            }
            FLIP = true;
        });


        instructions.setPlaceholder(new Label(" "));
        instructions.setMinWidth(0);
        instructions.setMaxWidth(expandedWidth + EDGE * 2);
        instructions.setMinHeight(expandedWidth);
        instructions.setPrefHeight(MAP_HEIGHT - 40 - EDGE);
        this.instructions.setItems(FXCollections.observableArrayList());
        //instructions.setPrefHeight(TABLE_HEIGHT);
        //instructions.getColumns().addAll(Instructions.getColumn(instructions));



    }

    public void clearInstructions() {
        instructions.setCellFactory((ListView<Instructions> lv) ->
                new ListCell<Instructions>() {
                    @Override
                    public void updateItem(Instructions in, boolean empty) {
                        super.updateItem(in, empty);
                        setGraphic(null);
                    }
                }
        );
        this.instructions.setItems(FXCollections.observableArrayList());
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
                            Text text = new Text(in.toString());
                            text.setWrappingWidth(expandedWidth-10);
                            //setText(in.toString());
                            setGraphic(text);
                        }
                    }
                }
        );
        //this.instructions.getSelectionModel().clearSelection();
        /*instructions.setCellFactory((ListView<Instructions> lv) ->
                new ListCell<Instructions>() {
                    @Override
                    public void updateItem(Instructions in, boolean empty) {
                        setText(null);
                        setGraphic(null);
                    }
                }
        );*/
    }

    /**
     * Call to set the instructions
     */
    public void setInstructions(ArrayList<Instructions> instructions) {

        //this.SETTINGS_VISIBLE.setValue(!SETTINGS_VISIBLE.getValue());

        this.clearInstructions();

        ObservableList<Instructions> data = FXCollections.observableArrayList();
        data.addAll(instructions);
        this.instructions.setItems(data);
        if (DIRECTIONS_VISIBLE.getValue() == false) this.slidingDirections.playShowPane(DIRECTIONS_VISIBLE);

    }


    /****************************************************************************************************************
                                                Llambda Event Handlers
     ****************************************************************************************************************/

    /** calls the controller to do the correct help **/
    private void handleHelp(){
       this.controller.help();
    }

    /** called from the controller and shows all the help tooltips **/
    public void showToolTips(){

        Animation animation1 = null;
        Animation animation2 = null;



        if (!DASHBOARD_VISIBLE.getValue())  animation1 = slidingDashboard.playShowPaneCustom(DASHBOARD_VISIBLE);
        if (!DIRECTIONS_VISIBLE.getValue()) animation2 = slidingDirections.playShowPaneCustom(DIRECTIONS_VISIBLE);


        if (animation1 != null) animation1.play();
        if (animation2 != null) animation2.play();

        if (animation1 != null) {
            animation1.setOnFinished(e -> {
                playToolTips();
                DASHBOARD_VISIBLE.setValue(true);
            });
        } else if(animation2 != null){
            animation2.setOnFinished(e -> {
                playToolTips();
                DIRECTIONS_VISIBLE.setValue(true);
            });
        } else {
            playToolTips();
        }
    }

    /** create tool tips **/
    public void createInfoTips() {
        this.infoTips = new ArrayList<InfoTip>();

        /** menu bar info tip **/
        InfoTip a = new InfoTip("This is our dashboard. Click to hide.", menuButton, PopOver.ArrowLocation.LEFT_CENTER);

        /** start search bar **/
        InfoTip b = new InfoTip("Search for a location, a room, the nearest bathroom or for food.", start, PopOver.ArrowLocation.LEFT_TOP);

        /** get involved **/
        InfoTip c = new InfoTip("Click to view our about screen!", infoButton, PopOver.ArrowLocation.LEFT_TOP);

        /** help button **/
        InfoTip d = new InfoTip("Click to view all tooltips!", helpButton, PopOver.ArrowLocation.TOP_LEFT);

        /** clear button **/
        InfoTip e = new InfoTip("Clear your path and locations!", locationClearButton , PopOver.ArrowLocation.TOP_RIGHT);

        /** git involved **/
        InfoTip f = new InfoTip("View events around campus!", newsLabel, PopOver.ArrowLocation.BOTTOM_LEFT );

        /** sliding settings **/
        InfoTip g = new InfoTip("Click to show settings!", slidingButton, PopOver.ArrowLocation.BOTTOM_LEFT);

        /** email button **/
        InfoTip h = new InfoTip("Click to send directions to your email!", slidingEmailButton, PopOver.ArrowLocation.BOTTOM_LEFT);

        /** map **/
        InfoTip i = new InfoTip("Click on the map to add starting/ending locations!", mapPane, PopOver.ArrowLocation.TOP_CENTER);

        /** map **/
        InfoTip j = new InfoTip("Step by step directions will be shown below!", directionsTitleBox, PopOver.ArrowLocation.TOP_LEFT);

        infoTips.add(a);
        infoTips.add(b);
        infoTips.add(c);
        infoTips.add(d);
        infoTips.add(e);
        infoTips.add(f);
        infoTips.add(g);
        infoTips.add(h);
        infoTips.add(i);
        infoTips.add(j);
    }

    /** called from the controller and plays the sequence **/
    public void playToolTips(){
        PopOver controlTutorial = new PopOver();
        controlTutorial.setTitle("Tutorial");
        Button back = new Button("Back");
        back.setId("tutorial-panel-button");
        Button next = new Button("Next");
        next.setId("tutorial-panel-button");
        Button showAll = new Button("Show All Tips");
        showAll.setId("tutorial-panel-button");
        Button endTutorial = new Button("End Tutorial");
        endTutorial.setId("tutorial-panel-button");
        HBox buttons = new HBox(back, next);
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);
        HBox buttonstwo = new HBox(showAll, endTutorial);
        buttonstwo.setSpacing(8);
        VBox tutorial = new VBox(buttons, buttonstwo);
        tutorial.setStyle("-fx-padding:8 8 8 8");
        tutorial.setAlignment(Pos.CENTER);
        tutorial.setSpacing(8);
        controlTutorial.setContentNode(tutorial);
        controlTutorial.setDetached(true);
        controlTutorial.show(mapPane);

        next.setOnAction(e -> playNext());
        back.setOnAction(e -> playBack());
        endTutorial.setOnAction(e -> {
            for(InfoTip infoTip : infoTips) infoTip.hide();
            controlTutorial.hide();
        });
        showAll.setOnAction(e -> {
            for(InfoTip infoTip : infoTips) infoTip.show();
        });

        currentToolTip = 0;
    }

    private void playNext(){
        if (currentToolTip + 1 < infoTips.size()){
            playToolTip(1);
        }
    }

    private void playBack(){
        if (currentToolTip - 1 < infoTips.size() && currentToolTip - 1 > -1){
            playToolTip(-1);
        }
    }

    private void playToolTip(int i){
        hideToolTip(currentToolTip);
        currentToolTip += i;
        if (this.infoTips.size() > currentToolTip && currentToolTip > -1) this.infoTips.get(currentToolTip).show();
    }

    private void hideToolTip(int i){
        if (this.infoTips.size() > i && i > -1) this.infoTips.get(i).hide();

    }

    private void handleClear(){
        /** clear controller data **/
        this.controller.clear();

        /** clear visuals **/
        this.start.getSelectionModel().clearSelection();
        this.end.getSelectionModel().clearSelection();
        this.mapDisplay.revertPathNodes();
        this.mapDisplay.clearPath();
        this.clearInstructions();
        this.updateTimeEstimation();
        this.setIDLeftArrowButton("arrow-buttons-grayed");
        this.setIDRightArrowButton("arrow-buttons-grayed");
        if (DIRECTIONS_VISIBLE.getValue() == true) this.slidingDirections.playHidePane(DIRECTIONS_VISIBLE);
        TIME_VISIBLE.setValue(false);
    }

    private void handleRadioButtons(){
        controller.handleWeightOptions(weatherRadioButton.selectedProperty().getValue(), handicapRadioButton.selectedProperty().getValue());
    }

    private void handleSearchInput(Inputs v, boolean START) {

        if (v.getValue() != null && !v.getValue().toString().isEmpty()) {
            if (v.containsNode(v.getValue().toString())) {
                controller.handleSearchInput(v.getNode(v.getValue().toString()), START);
            } else {
                controller.handleSpecificSearch(v.getValue().toString());
            }
        }
    }



        private void handleWalkingInput (Inputs v,boolean START){
            visuals.Walking value = (visuals.Walking) v.getValue();
            User.setSpeed(value.getWalkingSpeed());
            updateTimeEstimation();
            //System.out.println(value.getWalkingSpeed()); //TODO Remove
        }

        //Green if email addr valid, red if not
        private void handleEmailInput (TextField v,boolean START){
            User.setEmail(v.getText());

            //Validate Address
            try {
                new InternetAddress(v.getText()).validate();
            } catch (AddressException e) { //If invalid, set color to red
                v.setId("text-field-denied");
                return;
            }
            //If valid, set other textbox & color
            yourEmail.setText(User.getEmail());
            v.setId("text-field-confirmed");
        }

        //Green if email is sent, red if not
        private void handleEmailInput2 (TextField v,boolean START){
            if (yourEmail.getText() != null) {
                if (sendEmail(yourEmail.getText())) {
                    yourEmail.setId("email-text-field-confirmed");
                    EMAIL = false;
                } else {
                    yourEmail.setId("email-text-field-denied");
                }
            }
        }


        private void handleRightArrowButton () {
            this.controller.handleIncrementPathMap();
        }

        private void handleLeftArrowButton () {
            this.controller.handleDecrementPathMap();
        }

        public void setIDRightArrowButton (String s){
            this.rightArrowButton.setId(s);
        }

        public void setIDLeftArrowButton (String s){
            this.leftArrowButton.setId(s);
        }

        /****************************************************************************************************************
         Semi Facade Interface with Controller
         ****************************************************************************************************************/
        private boolean sendEmail (String email){
            if (!email.equals("") && !email.equals("Enter Email Here") && !email.equals("Email Sent") && !email.equals("Invalid Email")) {
                return controller.sendEmail(email); //Should return true if the email goes through
            } else {
                return false;
            }
        }

        private HashMap<Integer, logic.INode> getInterestingNodes () {
            return controller.getInterestingNodes();
        }

        private HashMap<Integer, logic.INode> getNodes () {
            return controller.getNodes();
        }

        private HashMap<Integer, logic.IMap> getMaps () {
            return controller.getMaps();
        }

        public void setDirectionStyle (String s){
            this.directionStyle = s;
        }
}


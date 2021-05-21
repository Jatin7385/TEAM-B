package sample;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.sothawo.mapjfx.offline.OfflineCache;
import eu.hansolo.airseries.Altimeter;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVReader;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.sothawo.mapjfx.*;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

//Note: The Button calls have been handled in Scene Builder.

public class Overall_controller implements Initializable {
    private int t=0,t1=0,t2=0;

    @FXML
    private LineChart<?, ?> altitude_time_sp;

    @FXML
    private LineChart<?, ?> temperature_time_sp;

    @FXML
    private LineChart<?, ?> airspeed_time_sp;

    @FXML
    private LineChart<?, ?> altitude_time;

    @FXML
    private LineChart<?, ?> latitude_time;

    @FXML
    private LineChart<?, ?> longitude_time;

    @FXML
    private LineChart<?, ?> pitch_time;

    @FXML
    private LineChart<?, ?> roll_time;

    @FXML
    private LineChart<?, ?> yaw_time;

    final int WINDOW_SIZE = 200;

    private ScheduledExecutorService scheduledExecutorService;

    @FXML
    private Text team_id;

    @FXML
    private Text mission_time;

    @FXML
    private Text packet_count;

    @FXML
    private Text gps_time;

    @FXML
    private AnchorPane model_anchor;

    @FXML
    private Text stage_text;

    @FXML
    private HBox topControls;

    @FXML
    private Button buttonZoom;

    @FXML
    private Slider sliderZoom;

    @FXML
    private MapView mapView;

    private CoordinateLine CarrierTrack;

    @FXML
    private SubScene model_subscene_sp;

    private static final Coordinate carrierCoordinates = new Coordinate(12.975555555555555, 79.16055555555556);
    private final List<Coordinate> coordinates = new ArrayList<>();

    private final Marker carrierMarker;
    private final MapLabel carrierLabel;

    private static final int ZOOM_DEFAULT = 14;

    @FXML
    private MapView map_View;

    final ObservableList<CarrierDataModel> Carrier_data = FXCollections.observableArrayList();
    final ObservableList<SPDATAMODEL> SP_data = FXCollections.observableArrayList();

    @FXML
    private TableView<CarrierDataModel> carrier_data_view;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_altitude_data;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_latitude_data;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_longitude_data;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_pitch_data;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_roll_data;

    @FXML
    private TableColumn<CarrierDataModel, String> carrier_yaw_data;

    @FXML
    private TableView<SPDATAMODEL> sp_data_view;

    @FXML
    private TableColumn<SPDATAMODEL, String> sp_altitude_data;

    @FXML
    private TableColumn<SPDATAMODEL, String > sp_temperature_data;

    @FXML
    private TableColumn<SPDATAMODEL, String > sp_airspeed_data;

    private static final String PORT = "COM5";
    private static final int BAUD_RATE = 9600;
    private static final String REMOTE_NODE_IDENTIFIER = "REMOTE";

    @FXML
    private AnchorPane anchor_subscene_sp;

    @FXML
    private AnchorPane anchor_subscene_carrier;

    @FXML
    private SubScene model_subscene_carrier;

    @FXML
    private VBox vbox_airseries;

    @FXML
    private MediaView gif_player;

    private eu.hansolo.airseries.Horizon horizon;
    private eu.hansolo.airseries.Altimeter altimeter;
    private eu.hansolo.airseries.Altimeter altimeter_sp;

    private long           lastTimerCall;
    private AnimationTimer timer;

    @FXML
    private LineChart<?, ?> all_carrier_altitude_time;

    @FXML
    private LineChart<?, ?> all_carrier_latitude_time;

    @FXML
    private LineChart<?, ?> all_carrier_longitude_time;

    @FXML
    private LineChart<?, ?> all_carrier_pitch_time;

    @FXML
    private LineChart<?, ?> all_carrier_roll_time;

    @FXML
    private LineChart<?, ?> all_carrier_yaw_time;

    @FXML
    private LineChart<?, ?> all_sp_altitude_time;

    @FXML
    private LineChart<?, ?> all_sp_airspeed_time;

    @FXML
    private LineChart<?, ?> all_sp_temperature_time;

    //Setting up the xbee device

    //XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);

    //XBeeNetwork xbeeNetwork = myDevice.getNetwork();
    /*RemoteXBeeDevice remoteDevice;

    {
        try {
            remoteDevice = xbeeNetwork.discoverDevice(REMOTE_NODE_IDENTIFIER);
        } catch (XBeeException e) {
            e.printStackTrace();
        }
    }*/

    private boolean xbee_is_open = false;

    private boolean isBeginTelemetry = false;

    private static final Random RND = new Random();

    private int prevStage = 0;

    //Constructor

    public Overall_controller() {
        carrierMarker = Marker.createProvided(Marker.Provided.RED).setPosition(carrierCoordinates).setVisible(
                true);
        carrierLabel = new MapLabel("CARRIER").setPosition(carrierCoordinates).setVisible(true);
        carrierMarker.attachLabel(carrierLabel);
        horizon   = new eu.hansolo.airseries.Horizon();
        altimeter = new eu.hansolo.airseries.Altimeter();
        altimeter_sp = new Altimeter();
        lastTimerCall = System.nanoTime();
    }

    /** params for the WMS server. */
    private WMSParam wmsParam = new WMSParam()
            .setUrl("http://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    private XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions(
                    "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");


    //Function for initializing Map and Controls

    public void initMapAndControls(Projection projection) {
        // init MapView-Cache
        final OfflineCache offlineCache = mapView.getOfflineCache();
        final String cacheDir = System.getProperty("java.io.tmpdir") + "/mapjfx-cache";

        // set the custom css file for the MapView
        mapView.setCustomMapviewCssURL(getClass().getResource("/custom_mapview.css"));

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        // load two coordinate lines
        CarrierTrack = new CoordinateLine().setColor(Color.MAGENTA).setVisible(true);
        MapType mapType=MapType.OSM;;
        mapView.setMapType(mapType);

        // finally initialize the map view
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
    }

    //Function for making the Coordinate Line

    private Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (
                Stream<String> lines = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines()
        ) {
            return Optional.of(new CoordinateLine(
                    lines.map(line -> line.split(";")).filter(array -> array.length == 2)
                            .map(values -> new Coordinate(Double.valueOf(values[0]), Double.valueOf(values[1])))
                            .collect(Collectors.toList())));
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    //Function for setting the Air Series devices

    public void setAirSeries()
    {
        Text text = new Text();
        text.setText("CARRIER");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setUnderline(true);
        text.setWrappingWidth(211.33333333333334);
        text.setFill(Color.BLACK);
        text.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 20));
        text.setStyle("-fx-font-size: 20px;");


        Text text_sp = new Text();
        text_sp.setText("SCIENCE PAYLOAD");
        text_sp.setTextAlignment(TextAlignment.CENTER);
        text_sp.setUnderline(true);
        text_sp.setWrappingWidth(211.33333333333334);
        text_sp.setFill(Color.BLACK);
        text_sp.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 20));
        text_sp.setStyle("-fx-font-size: 20px;");


        vbox_airseries.getChildren().addAll(text,altimeter,horizon,text_sp,altimeter_sp);
        vbox_airseries.setSpacing(10);
    }

    //Function for playing the gif
    public void playGif()
    {
        File f = new File("C:\\Users\\Jatin Dhall\\Desktop\\Desktop File\\VIT\\VIT\\CLUBS,CHAPTERS,TEAMS\\Team sammard\\CODES\\JAVA\\GUI_NEW\\src\\sample\\SAMMARD GIF B.mp4");
        Media media = null;
        try {
            media = new Media(f.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        javafx.scene.media.MediaPlayer player = new   javafx.scene.media.MediaPlayer(media);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gif_player.setMediaPlayer(player);
                player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
                player.play();
            }
        });

    }

    //Function for setting the zoom function,adding the Label,Marker and Coordinate Lines
    private void afterMapIsInitialized() {
        mapView.setZoom(ZOOM_DEFAULT);
        mapView.setCenter(carrierCoordinates);
        // add the markers to the map - they are still invisible
        mapView.addMarker(carrierMarker);

        // add the fix label, the other's are attached to markers.
        mapView.addLabel(carrierLabel);

        // add the tracks
        mapView.addCoordinateLine(CarrierTrack);
    }

    //Function for the Rotation Transition on the 3D Model
    private RotateTransition rotate3dGroup(Group group) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(30), group);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }


    //Function for Creating the Science Payload SubScene
    private void createSubScene_sp() {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-2);
        camera.setTranslateX(0.35);
        camera.setTranslateY(0.2);
        Group model = loadModel(getClass().getResource("Payload assembly 2.obj"));
        model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        anchor_subscene_sp.getChildren().remove(model_subscene_sp);

        Group root = new Group(model);

        RotateTransition rotate = rotate3dGroup(root);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rotate.play();
            }
        });

        model_subscene_sp = new SubScene(root, 1281, 442,true,SceneAntialiasing.BALANCED);
        model_subscene_sp.setCamera(camera);
        anchor_subscene_sp.getChildren().add(model_subscene_sp);
    }
    //Function for changing the model as per the stage
    private void createSubScene_sp_stage(int stage) {

        PerspectiveCamera camera = new PerspectiveCamera(true);

        Group model = null;
        Group root = null;
        anchor_subscene_sp.getChildren().remove(model_subscene_sp);

        if(stage == 3)
        {
            camera.setTranslateZ(-2);
            camera.setTranslateX(0.35);
            camera.setTranslateY(0.2);
            model = loadModel(getClass().getResource("Payload assembly deployted.obj"));
            model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));



            root = new Group(model);

            RotateTransition rotate = rotate3dGroup(root);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    rotate.play();
                }
            });
        }
        else if(stage == 4)
        {
             camera.setTranslateZ(-2);
             camera.setTranslateX(0.35);
             camera.setTranslateY(-0.1);
             model = loadModel(getClass().getResource("Payload assembly 4 (1).obj"));

            model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));



            root = new Group(model);
        }
        model_subscene_sp = new SubScene(root, 1281, 442,true,SceneAntialiasing.BALANCED);
        model_subscene_sp.setCamera(camera);
        anchor_subscene_sp.getChildren().add(model_subscene_sp);
    }

    //ORIENTATION FOR CARRIER
/*
    private void matrixRotateNode(Group n, double alf, double bet, double gam){
        double A11=Math.cos(alf)*Math.cos(gam);
        double A12=Math.cos(bet)*Math.sin(alf)+Math.cos(alf)*Math.sin(bet)*Math.sin(gam);
        double A13=Math.sin(alf)*Math.sin(bet)-Math.cos(alf)*Math.cos(bet)*Math.sin(gam);
        double A21=-Math.cos(gam)*Math.sin(alf);
        double A22=Math.cos(alf)*Math.cos(bet)-Math.sin(alf)*Math.sin(bet)*Math.sin(gam);
        double A23=Math.cos(alf)*Math.sin(bet)+Math.cos(bet)*Math.sin(alf)*Math.sin(gam);
        double A31=Math.sin(gam);
        double A32=-Math.cos(gam)*Math.sin(bet);
        double A33=Math.cos(bet)*Math.cos(gam);

        double d = Math.acos((A11+A22+A33-1d)/2d);
        if(d!=0d){
            double den=2d*Math.sin(d);
            Point3D p= new Point3D((A32-A23)/den,(A13-A31)/den,(A21-A12)/den);
            n.setRotationAxis(p);
            n.setRotate(Math.toDegrees(d));
        }
    }

    // ORIENTATION FOR CARRIER

    private void createSubScene_carrier_stage(int pitch, int roll, int yaw) {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-3.5);
        camera.setTranslateX(0.7);
        camera.setTranslateY(-0.2);
        Group model = loadModel(getClass().getResource("assem for jatin.obj"));

        matrixRotateNode(model,roll,pitch,yaw);

        //model.getTransforms().add(new Rotate(pitch, Rotate.X_AXIS));
        //model.getTransforms().add(new Rotate(roll, Rotate.Y_AXIS));
        //model.getTransforms().add(new Rotate(yaw, Rotate.Z_AXIS));

        anchor_subscene_carrier.getChildren().remove(model_subscene_carrier);

        Group root = new Group(model);

        /*RotateTransition rotate = rotate3dGroup(root);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rotate.play();
            }
        });

        model_subscene_carrier = new SubScene(root, 1281, 442,true,SceneAntialiasing.BALANCED);
        model_subscene_carrier.setCamera(camera);
        anchor_subscene_carrier.getChildren().add(model_subscene_carrier);
    }
    */

    //Creating the Carrier SubScene
    private void createSubScene_carrier() {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-3.5);
        camera.setTranslateX(0.9);
        camera.setTranslateY(0.5);
        Group model = loadModel(getClass().getResource("assem for jatin.obj"));
        model.getTransforms().add(new Rotate(90,Rotate.X_AXIS));
        model.getTransforms().add(new Rotate(8,Rotate.Z_AXIS));
        //matrixRotateNode(model,0,0,0);

        anchor_subscene_carrier.getChildren().remove(model_subscene_carrier);

        Group root = new Group(model);

        RotateTransition rotate = rotate3dGroup(root);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rotate.play();
            }
        });

        model_subscene_carrier = new SubScene(root, 1281, 442,true,SceneAntialiasing.BALANCED);
        model_subscene_carrier.setCamera(camera);
        anchor_subscene_carrier.getChildren().add(model_subscene_carrier);
    }

    //Function to Load the 3D .obj Model into the GUI
    private Group loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }


/*
    //Function for reading the Data Packet from XBEE
    public String xbee_read(int option)
    {
        //Option 0 means return Carrier data and don't recieve sp_data
        //Option 1 means return sp data
        //Option 2 means return Carrier data but recieve Sp data too

        String datapacket_carrier =null;
        String datapacket_sp = null;
        try {
            final String[] Carrier_dataPacket_temp = new String[1];
            final String[] SP_dataPacket_temp = new String[1];
            if(xbee_is_open == false) {
                myDevice.open();
                xbee_is_open = true;
            }
            myDevice.addDataListener(new IDataReceiveListener() {
                @Override
                public void dataReceived(XBeeMessage xBeeMessage) {
                    Carrier_dataPacket_temp[0] = new String(xBeeMessage.getData());
                }
            });
            datapacket_carrier = Carrier_dataPacket_temp[0];
            if(option != 0) {
                myDevice.addDataListener(new IDataReceiveListener() {
                    @Override
                    public void dataReceived(XBeeMessage xBeeMessage) {
                        SP_dataPacket_temp[0] = new String(xBeeMessage.getData());
                    }
                });
                datapacket_sp = SP_dataPacket_temp[0];
            }
        } catch (XBeeException e) {
            e.printStackTrace();
        }
        if(option == 0 || option == 2)
        {
            return datapacket_carrier;
        }
        else
        {
            return datapacket_sp;
        }
    }

    //Calibrate Command Function
    public void xbee_write_calibrate()
    {
        String Data_to_send = "C";
        try {
            byte[] dataToSend = Data_to_send.getBytes();
            if(xbee_is_open == false)
            {
                myDevice.open();
                xbee_is_open = true;
            }
            if (remoteDevice == null) {
                System.out.println("Couldn't find the remote XBee device with '" + REMOTE_NODE_IDENTIFIER + "' Node Identifier.");
            }
            System.out.format("Sending data to %s >> %s | %s... ", remoteDevice.get64BitAddress(),
                    HexUtils.prettyHexString(HexUtils.byteArrayToHexString(dataToSend)),
                    new String(dataToSend));

            myDevice.sendData(remoteDevice, dataToSend);

            System.out.println("Success");

        } catch (XBeeException e) {
            e.printStackTrace();
        }
    }

    //Set Time Command Function
    public void xbee_write_setTime()
    {
        String Data_to_send = "S";
        try {
            byte[] dataToSend = Data_to_send.getBytes();
            if(xbee_is_open == false)
            {
                myDevice.open();
                xbee_is_open = true;
            }
            if (remoteDevice == null) {
                System.out.println("Couldn't find the remote XBee device with '" + REMOTE_NODE_IDENTIFIER + "' Node Identifier.");
            }
            System.out.format("Sending data to %s >> %s | %s... ", remoteDevice.get64BitAddress(),
                    HexUtils.prettyHexString(HexUtils.byteArrayToHexString(dataToSend)),
                    new String(dataToSend));

            myDevice.sendData(remoteDevice, dataToSend);

            System.out.println("Success");

        } catch (XBeeException e) {
            e.printStackTrace();
        }
    }

    //Telemetry Command Function
    public void xbee_write_Telemetry()
    {
        String Data_to_send = "T";
        try {
            byte[] dataToSend = Data_to_send.getBytes();
            if(xbee_is_open == false)
            {
                myDevice.open();
                xbee_is_open = true;
            }
            if (remoteDevice == null) {
                System.out.println("Couldn't find the remote XBee device with '" + REMOTE_NODE_IDENTIFIER + "' Node Identifier.");
            }
            System.out.format("Sending data to %s >> %s | %s... ", remoteDevice.get64BitAddress(),
                    HexUtils.prettyHexString(HexUtils.byteArrayToHexString(dataToSend)),
                    new String(dataToSend));

            myDevice.sendData(remoteDevice, dataToSend);

            System.out.println("Success");

        } catch (XBeeException e) {
            e.printStackTrace();
        }
    }*/

    //Function for the Telemtry Button
    public void TelemetryButton()
    {
        if(isBeginTelemetry == false) {
            carrier_altitude_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("ALTITUDE"));
            carrier_latitude_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("LATITUDE"));
            carrier_longitude_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("LONGITUDE"));
            carrier_pitch_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("PITCH"));
            carrier_roll_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("ROLL"));
            carrier_yaw_data.setCellValueFactory(new PropertyValueFactory<CarrierDataModel, String>("YAW"));

            sp_altitude_data.setCellValueFactory(new PropertyValueFactory<SPDATAMODEL, String>("SP_ALTITUDE"));
            sp_temperature_data.setCellValueFactory(new PropertyValueFactory<SPDATAMODEL, String>("TEMPERATURE"));
            sp_airspeed_data.setCellValueFactory(new PropertyValueFactory<SPDATAMODEL, String>("AIR_SPEED"));
            setAltitude_time();
            setLatitude_time();
            setLongitude_time();
            setPitch_time();
            setRoll_time();
            setYaw_time();
            setAltitude_time_sp();
            setTemperature_time_sp();
            setAirspeed_time_sp();

            isBeginTelemetry = true;
        }
    }
    //Function for the SET TIME Button
    public void SET_TIME()
    {
        System.out.println("SET TIME BUTTON CLICKED");
    }

    //Function for the CALIBRATE Button
    public void CalibrateButton()
    {
        System.out.println("CALIBRATE BUTTON");
    }
    //Function for plotting Altitude vs Time(Carrier) in Real Time
    public void setAltitude_time() {
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series_all = new XYChart.Series();
        altitude_time.setCreateSymbols(false);
        altitude_time.getData().add(series);

        altitude_time.setLegendVisible(false);

        all_carrier_altitude_time.setCreateSymbols(false);
        all_carrier_altitude_time.getData().add(series_all);

        all_carrier_altitude_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                String stage = "";
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    //write_into_csv_container(columns);
                    write_into_csv(columns,1);
                    Platform.runLater(() -> {
                        series.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[4])));
                        series_all.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[4])));


                        if (series.getData().size() > WINDOW_SIZE)
                            series.getData().remove(0);

                        if (series_all.getData().size() > WINDOW_SIZE)
                            series_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.SECONDS);

    }

    //Function for plotting Latitude vs Time in Real Time(Carrier)
    public void setLatitude_time() {
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series1_all = new XYChart.Series();
        latitude_time.setCreateSymbols(false);
        latitude_time.getData().add(series1);

        latitude_time.setLegendVisible(false);

        all_carrier_latitude_time.setCreateSymbols(false);
        all_carrier_latitude_time.getData().add(series1_all);

        all_carrier_latitude_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    Platform.runLater(() -> {
                        series1.getData().add(new XYChart.Data<>(columns[1], Double.parseDouble(columns[5])));

                        series1_all.getData().add(new XYChart.Data<>(columns[1], Double.parseDouble(columns[5])));

                        Coordinate newcarrierCoordinates = new Coordinate(Double.parseDouble(columns[5]),Double.parseDouble(columns[6]));
                        carrierMarker.setPosition(newcarrierCoordinates).setVisible(true);
                        mapView.setCenter(newcarrierCoordinates);
                        coordinates.add(new Coordinate(Double.parseDouble(columns[5]),Double.parseDouble(columns[6])));
                        CarrierTrack = new CoordinateLine(coordinates)
                                .setColor(Color.RED)
                                .setFillColor(Color.web("lawngreen", 0.4))
                                .setClosed(true);
                        mapView.addCoordinateLine(CarrierTrack);
                        CarrierTrack.setVisible(true);

                        if (series1.getData().size() > WINDOW_SIZE)
                            series1.getData().remove(0);

                        if (series1_all.getData().size() > WINDOW_SIZE)
                            series1_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.SECONDS);

    }

    //Function for plotting Longitude vs Time in Real Time(Carrier)
    public void setLongitude_time() {
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series2_all = new XYChart.Series();
        longitude_time.setCreateSymbols(false);
        longitude_time.getData().add(series2);

        longitude_time.setLegendVisible(false);

        all_carrier_longitude_time.setCreateSymbols(false);
        all_carrier_longitude_time.getData().add(series2_all);

        all_carrier_longitude_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    LocalTime time = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    Platform.runLater(() -> {
                        team_id.setText("TEAM B");
                        mission_time.setText(columns[1]);
                        packet_count.setText(columns[2]);
                        gps_time.setText(time.format(formatter));
                        stage_text.setText(columns[0]);
                        Carrier_data.add(new CarrierDataModel(columns[4],columns[5],columns[6],columns[7],columns[8],columns[9]));
                        carrier_data_view.setItems(Carrier_data);
                        series2.getData().add(new XYChart.Data<>(columns[1], Double.parseDouble(columns[6])));
                        series2_all.getData().add(new XYChart.Data<>(columns[1], Double.parseDouble(columns[6])));
                        if (series2.getData().size() > WINDOW_SIZE)
                            series2.getData().remove(0);

                        if (series2_all.getData().size() > WINDOW_SIZE)
                            series2_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.SECONDS);

    }
    //Function for plotting Pitch vs Time in Real Time(Carrier)
    public void setPitch_time() {
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series3_all = new XYChart.Series();
        pitch_time.setCreateSymbols(false);
        pitch_time.getData().add(series3);

        pitch_time.setLegendVisible(false);

        all_carrier_pitch_time.setCreateSymbols(false);
        all_carrier_pitch_time.getData().add(series3_all);

        all_carrier_pitch_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    Platform.runLater(() -> {
                        //horizon.setPitch(RND.nextInt(90) - 45);
                        //horizon.setRoll(RND.nextInt(90) - 45);

                        horizon.setPitch( Integer.parseInt(columns[7]));
                        horizon.setRoll( Integer.parseInt(columns[8]));

                        altimeter.setValue(Integer.parseInt(columns[4]));
                        series3.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[7])));
                        series3_all.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[7])));
                        if(Integer.parseInt(columns[7])>180){
                            series3.getNode().setStyle("-fx-stroke: #FF0000; ");
                            series3_all.getNode().setStyle("-fx-stroke: #FF0000; ");
                        }
                        else{
                            series3.getNode().setStyle("-fx-stroke: #00FF00;");
                            series3_all.getNode().setStyle("-fx-stroke: #00FF00;");
                        }
                        if (series3.getData().size() > WINDOW_SIZE)
                            series3.getData().remove(0);

                        if (series3_all.getData().size() > WINDOW_SIZE)
                            series3_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

    }
    //Function for plotting Roll vs Time in Real Time(Carrier)
    public void setRoll_time() {
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series4_all = new XYChart.Series();
        roll_time.setCreateSymbols(false);
        roll_time.getData().add(series4);

        roll_time.setLegendVisible(false);

        all_carrier_roll_time.setCreateSymbols(false);
        all_carrier_roll_time.getData().add(series4_all);

        all_carrier_roll_time.setLegendVisible(false);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    Platform.runLater(() -> {
                        if(prevStage!=Integer.parseInt(columns[0]) && Integer.parseInt(columns[0]) == 3 || Integer.parseInt(columns[0]) == 4)
                        {
                            createSubScene_sp_stage(Integer.parseInt(columns[0]));
                            prevStage = Integer.parseInt(columns[0]);
                        }
                        else if(prevStage!=Integer.parseInt(columns[0]))
                        {
                            prevStage = Integer.parseInt(columns[0]);
                        }
                        series4.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[8])));
                        series4_all.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[8])));
                        if(Integer.parseInt(columns[8])>180){
                            series4.getNode().setStyle("-fx-stroke: #FF0000; ");
                            series4_all.getNode().setStyle("-fx-stroke: #FF0000; ");
                        }
                        else{
                            series4.getNode().setStyle("-fx-stroke: #00FF00;");
                            series4_all.getNode().setStyle("-fx-stroke: #00FF00;");
                        }
                        if (series4.getData().size() > WINDOW_SIZE)
                            series4.getData().remove(0);

                        if (series4_all.getData().size() > WINDOW_SIZE)
                            series4_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

    }
    //Function for plotting Yaw vs Time in Real Time(Carrier)
    public void setYaw_time() {
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series5_all = new XYChart.Series();
        yaw_time.setCreateSymbols(false);
        yaw_time.getData().add(series5_all);

        yaw_time.setLegendVisible(false);

        all_carrier_yaw_time.setCreateSymbols(false);
        all_carrier_yaw_time.getData().add(series5);

        all_carrier_yaw_time.setLegendVisible(false);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                while ((nextline = reader.readNext()) != null) {
                    String[] columns = nextline;
                    Platform.runLater(() -> {
                        //createSubScene_carrier_stage(Integer.parseInt(columns[7]),Integer.parseInt(columns[8]),Integer.parseInt(columns[9]));
                        series5.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[9])));
                        series5_all.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[9])));
                        if(Integer.parseInt(columns[9])>180){
                            series5.getNode().setStyle("-fx-stroke: #FF0000; ");
                            series5_all.getNode().setStyle("-fx-stroke: #FF0000; ");
                        }
                        else{
                            series5.getNode().setStyle("-fx-stroke: #00FF00;");
                            series5_all.getNode().setStyle("-fx-stroke: #00FF00;");
                        }
                        if (series5.getData().size() > WINDOW_SIZE)
                            series5.getData().remove(0);
                        if (series5_all.getData().size() > WINDOW_SIZE)
                            series5_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    //Function for plotting Altitude vs Time in Real Time(Science Payload)
    public void setAltitude_time_sp() {
        XYChart.Series series_sp = new XYChart.Series();
        XYChart.Series series_sp_all = new XYChart.Series();
        altitude_time_sp.setCreateSymbols(false);
        altitude_time_sp.getData().add(series_sp);

        altitude_time_sp.setLegendVisible(false);

        all_sp_altitude_time.setCreateSymbols(false);
        all_sp_altitude_time.getData().add(series_sp_all);

        all_sp_altitude_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Probe_CSV.csv"));
                CSVReader reader1 = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                String[] nextline1;
                while ((nextline = reader.readNext()) != null && (nextline1 = reader1.readNext()) != null){
                    String[] columns = nextline;
                    String[] columns1 = nextline1;
                    //write_into_csv_sp(columns);
                    write_into_csv(columns,2);
                    Platform.runLater(() -> {

                        SP_data.add(new SPDATAMODEL(columns[2],columns[3],columns[4]));
                        sp_data_view.setItems(SP_data);
                        altimeter_sp.setValue(Integer.parseInt(columns[2]));
                        series_sp.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[2])));
                        series_sp_all.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[2])));
                        if (series_sp.getData().size() > WINDOW_SIZE)
                            series_sp.getData().remove(0);
                        if (series_sp_all.getData().size() > WINDOW_SIZE)
                            series_sp_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t++;
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    //Function for plotting Temperature vs Time in Real Time(Science Payload)
    public void setTemperature_time_sp() {
        XYChart.Series series1_sp = new XYChart.Series();
        XYChart.Series series1_sp_all = new XYChart.Series();
        temperature_time_sp.setCreateSymbols(false);
        temperature_time_sp.getData().add(series1_sp);

        temperature_time_sp.setLegendVisible(false);

        all_sp_temperature_time.setCreateSymbols(false);
        all_sp_temperature_time.getData().add(series1_sp_all);

        all_sp_temperature_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
             try {
                CSVReader reader = new CSVReader(new FileReader("Probe_CSV.csv"));
                CSVReader reader1 = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                String[] nextline1;
                 while ((nextline = reader.readNext()) != null && (nextline1 = reader1.readNext()) != null){
                    String[] columns = nextline;
                    String[] columns1 = nextline1;
                    Platform.runLater(() -> {
                        series1_sp.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[3])));
                        series1_sp_all.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[3])));
                        if(Integer.parseInt(columns[3])<-20 && Integer.parseInt(columns[3])>45){
                            series1_sp.getNode().setStyle("-fx-stroke: #FF0000; ");
                            series1_sp_all.getNode().setStyle("-fx-stroke: #FF0000; ");
                        }
                        else{
                            series1_sp.getNode().setStyle("-fx-stroke: #00FF00;");
                            series1_sp_all.getNode().setStyle("-fx-stroke: #00FF00;");
                        }
                        if (series1_sp.getData().size() > WINDOW_SIZE)
                            series1_sp.getData().remove(0);
                        if (series1_sp_all.getData().size() > WINDOW_SIZE)
                            series1_sp_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t1++;
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    //Function for plotting Air Speed vs Time in Real Time(Science Payload)
    public void setAirspeed_time_sp() {
        XYChart.Series series2_sp = new XYChart.Series();
        XYChart.Series series2_sp_all = new XYChart.Series();
        airspeed_time_sp.setCreateSymbols(false);
        airspeed_time_sp.getData().add(series2_sp);

        airspeed_time_sp.setLegendVisible(false);

        all_sp_airspeed_time.setCreateSymbols(false);
        all_sp_airspeed_time.getData().add(series2_sp_all);

        all_sp_airspeed_time.setLegendVisible(false);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                CSVReader reader = new CSVReader(new FileReader("Probe_CSV.csv"));
                CSVReader reader1 = new CSVReader(new FileReader("Carrier_Data.csv"));
                String[] nextline;
                String[] nextline1;
                while ((nextline = reader.readNext()) != null && (nextline1 = reader1.readNext()) != null) {
                    String[] columns = nextline;
                    String[] columns1 = nextline1;
                    Platform.runLater(() -> {
                        series2_sp.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[4])));
                        series2_sp_all.getData().add(new XYChart.Data<>(Integer.parseInt(columns1[1]), Integer.parseInt(columns[4])));
                        if(Integer.parseInt(columns[4])>20){
                            series2_sp.getNode().setStyle("-fx-stroke: #FF0000; ");
                            series2_sp_all.getNode().setStyle("-fx-stroke: #FF0000; ");
                        }
                        else{
                            series2_sp.getNode().setStyle("-fx-stroke: #00FF00;");
                            series2_sp_all.getNode().setStyle("-fx-stroke: #00FF00;");
                        }
                        if (series2_sp.getData().size() > WINDOW_SIZE)
                            series2_sp.getData().remove(0);

                        if (series2_sp_all.getData().size() > WINDOW_SIZE)
                            series2_sp_all.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t2++;
                }
                Thread.currentThread().stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    //Function for Writing the data in csv file.
    // If option = 1, then adding the data in Container_Data.csv, whereas,
    // If option = 2, then the data will be adding in SciencePayloadData.csv
    public void write_into_csv(String[] data_array,int option)
    {
        try {
            String data="";
            for(int i=0;i< data_array.length;i++)
            {
                if(i!= data_array.length-1) {
                    data += data_array[i] + ",";
                }
                else{
                    data+=data_array[i];
                }
            }
            if(option == 1) {
                FileWriter fw = new FileWriter("Carrier.csv", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                pw.println(data);
                pw.flush();
                pw.close();
            }
            else{
                FileWriter fw = new FileWriter("Science_payload.csv", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                pw.println(data);
                pw.flush();
                pw.close();
            }

        } catch (Exception e) {

        }
    }
    //First function called when the Controller class takes over
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAirSeries();
        playGif();
        createSubScene_carrier();
        createSubScene_sp();
    }
}

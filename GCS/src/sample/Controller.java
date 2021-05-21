package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
//import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


//Issue 1: Unable to access any of the text fields from the thread run class hence am unable to display the logic on the text views
//Issue 2:Multitreading for Pressure_chart and Altitude_Chart classes is showing the same error as above
//Issue 3: The icon is not showing.(RESOLVED)
//https://github.com/dlsc-software-consulting-gmbh/GMapsFX FOR SHOWING THE LAT AND LONGITUDE ON GMAPS
//http://fxexperience.com/2011/05/maps-in-javafx-2-0/ for maps in javafx

/*class Altitude_chart implements Runnable{

    private String parameter;
    public Altitude_chart(String parameter) {
        this.parameter = parameter;
        run();
    }

    @Override
    public void run() {
        Controller c2 = new Controller();
        while(true) {
            //c2.setAltitude_time(parameter);
            System.out.println(parameter);
        }
    }
}

class Read_from_csv implements Runnable{

    @Override
    public void run() {
        Controller c3 = new Controller();
        c3.reading_from_csv();
    }
}

/*class Write_into_csv implements Runnable{

    @Override
    public void run() {
        Controller c2 = new Controller();
        while(true) {
            c2.write_into_csv();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}*/

public class Controller implements Initializable{
    @FXML
    private AnchorPane anchor_scene;

    @FXML
    private Text team_id;

    @FXML
    private Text mission_time;

    @FXML
    private Text packet_count;

    @FXML
    private Text gps_time;

    private ScheduledExecutorService scheduledExecutorService;



    public void set_Container_scene() throws IOException {
        AnchorPane container_pane = FXMLLoader.load(getClass().getResource("container_scene.fxml"));
        anchor_scene.getChildren().clear();
        anchor_scene.getChildren().add(container_pane);
        System.out.println(anchor_scene.getChildren().toString());
    }

    public void set_SP_scene() throws IOException {
        AnchorPane sp_pane = FXMLLoader.load(getClass().getResource("SciencePayload.fxml"));
        anchor_scene.getChildren().clear();
        anchor_scene.getChildren().add(sp_pane);
        System.out.println(anchor_scene.getChildren().toString());
    }

    public void set_text() {
        String fileName = "data.csv";
        File file = new File(fileName);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                BufferedReader br = Files.newBufferedReader(Paths.get("data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(DELIMITER);
                    LocalTime time = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    Platform.runLater(() -> {
                        team_id.setText(columns[0]);
                        mission_time.setText(columns[1]);
                        packet_count.setText(columns[2]);
                        gps_time.setText(time.format(formatter));
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println(values[0]);
                }
                scheduledExecutorService.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart

        }, 0, 1, TimeUnit.SECONDS);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ContainerScene containerScene = new ContainerScene();
        containerScene.setAltitude_time();
        containerScene.setLatitude_time();
        containerScene.setLongitude_time();
        containerScene.setPitch_time();
        containerScene.setRoll_time();
        containerScene.setYaw_time();

        SciencePayload sciencePayload = new SciencePayload();
        sciencePayload.setAltitude_time();
        sciencePayload.setTemperature_time();
        sciencePayload.setAirSpeed_time();*/

        try {
            set_Container_scene();
        } catch (IOException e) {
            e.printStackTrace();
        }
        set_text();
    }

    /*private int t = 0;
    private int t1 = 0;
    private int t2 = 0;

    final int WINDOW_SIZE = 40;

    private ScheduledExecutorService scheduledExecutorService;

    XYChart.Series series = new XYChart.Series();

    @FXML
    private ImageView icon_image;

    @FXML
    private Button Recieve_data;

    @FXML
    private Text time_text;

    @FXML
    private LineChart<?, ?> Altitude_time;

    @FXML
    private LineChart<?, ?> Pressure_time;

    @FXML
    private LineChart<?, ?> Airspeed_time;
    private static final String PORT = "COM1";

    private static final int BAUD_RATE = 9600;


    private ArrayList<Number> altitude_values = new ArrayList<Number>();
    private ArrayList<Number> pressure_values = new ArrayList<Number>();

    @FXML
    GoogleMapView mapView;
    GoogleMap map;


    public void SciencePayloadWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SciencePayload.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Science Payload");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTime(String time) {
        time_text.setText(time);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Thread t2 = new Thread(new Reading_from_csv());
        //t2.start();

        //Altitude_time.setTitle("Altitude Vs Time Graph");
        //reading_from_csv();
        //setTime();
        setAltitude_time();

        //SciencePayloadWindow();

    }

    public void setRecieve_data()
    {
        XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);

        try {
            myDevice.open();

            myDevice.addDataListener(new MyDataReceiveListener());
            //setAltitude_time();
            //setPressure_time();
            //setAirSpeed_time();

            //System.out.println("\n>> Waiting for data...");

        } catch (XBeeException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void reading_from_csv() {
        try {
                BufferedReader br = Files.newBufferedReader(Paths.get("data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void write_into_csv(String[] data_array) {
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
            FileWriter fw = new FileWriter("Container_data.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(data);
            pw.flush();
            pw.close();
        } catch (Exception e) {

        }
    }
// This is for case 1. The above one is the preferred method. Lets see if it works.
    public void setAltitude_time() {
        String fileName = "data.csv";
        File file = new File(fileName);

        XYChart.Series series = new XYChart.Series();
        Altitude_time.setTitle("Altitude Vs Time Graph");

        Altitude_time.getData().add(series);

        //System.out.println(altitude_values);
        //System.out.println(pressure_values);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                BufferedReader br = Files.newBufferedReader(Paths.get("data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(DELIMITER);
                    write_into_csv(columns);
                    System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        setTime(columns[1]);
                        // get current time
                        //Date now = new Date();
                        // put random number with current time
                        series.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[4])));

                        if (series.getData().size() > WINDOW_SIZE)
                            series.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t++;

                    //System.out.println(values[0]);
                }
                scheduledExecutorService.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart

        }, 0, 1, TimeUnit.SECONDS);

    }*/

}
package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SciencePayload implements Initializable {

    private int t = 0;
    private int t1 = 0;
    private int t2 = 0;

    final int WINDOW_SIZE = 40;

    private ScheduledExecutorService scheduledExecutorService;

    @FXML
    private LineChart<?, ?> altitude_time_sp;

    @FXML
    private LineChart<?, ?> temperature_time_sp;

    @FXML
    private LineChart<?, ?> airspeed_time_sp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAltitude_time();
        setTemperature_time();
        setAirSpeed_time();
    }

    public void setAltitude_time() {
        XYChart.Series series = new XYChart.Series();
        altitude_time_sp.setTitle("Altitude Vs Time Graph");

        altitude_time_sp.getData().add(series);


        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                BufferedReader br = Files.newBufferedReader(Paths.get("sciencepayload_data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(DELIMITER);
                    System.out.println(columns[0]);
                    write_into_csv(columns);

                    Platform.runLater(() -> {
                        // get current time
                        //Date now = new Date();
                        // put random number with current time
                        series.getData().add(new XYChart.Data<>(t, Integer.parseInt(columns[2])));

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

    }

    public void setTemperature_time() {
        XYChart.Series series1 = new XYChart.Series();
        temperature_time_sp.setTitle("Temperature Vs Time Graph");

        temperature_time_sp.getData().add(series1);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                BufferedReader br = Files.newBufferedReader(Paths.get("sciencepayload_data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(DELIMITER);

                    Platform.runLater(() -> {
                        // get current time
                        //Date now = new Date();
                        // put random number with current time
                        series1.getData().add(new XYChart.Data<>(t1, Integer.parseInt(columns[3])));

                        if (series1.getData().size() > WINDOW_SIZE)
                            series1.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t1++;

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

    public void setAirSpeed_time() {
        XYChart.Series series2 = new XYChart.Series();
        airspeed_time_sp.setTitle("Airspeed Vs Time Graph");

        airspeed_time_sp.getData().add(series2);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                BufferedReader br = Files.newBufferedReader(Paths.get("sciencepayload_data.csv"));
                String DELIMITER = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(DELIMITER);

                    Platform.runLater(() -> {
                        // get current time
                        //Date now = new Date();
                        // put random number with current time
                        series2.getData().add(new XYChart.Data<>(t2, Integer.parseInt(columns[4])));

                        if (series2.getData().size() > WINDOW_SIZE)
                            series2.getData().remove(0);
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
            t2++;
            //Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart

        }, 0, 1, TimeUnit.SECONDS);
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
            FileWriter fw = new FileWriter("SciencePayloadData.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(data);
            pw.flush();
            pw.close();
        } catch (Exception e) {

        }
    }
}

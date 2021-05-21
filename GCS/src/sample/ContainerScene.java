package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContainerScene implements Initializable {
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

    final int WINDOW_SIZE = 40;

    private ScheduledExecutorService scheduledExecutorService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAltitude_time();
        setLatitude_time();
        setLongitude_time();
        setPitch_time();
        setRoll_time();
        setYaw_time();
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
            FileWriter fw = new FileWriter("Container_Data.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(data);
            pw.flush();
            pw.close();
        } catch (Exception e) {

        }
    }

    public void setAltitude_time() {
        String fileName = "data.csv";
        File file = new File(fileName);
        XYChart.Series series = new XYChart.Series();
        altitude_time.setTitle("Altitude Vs Time Graph");

        altitude_time.getData().add(series);

        //System.out.println(altitude_values);
        //System.out.println(pressure_values);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            try {
                Scanner inputStream = new Scanner(file);
                while (inputStream.hasNext()) {
                    String data = inputStream.next();
                    String[] columns = data.split(",");
                    //System.out.println(columns[1]);
                    write_into_csv(columns);
                    Platform.runLater(() -> {
                        series.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[4])));

                        if (series.getData().size() > WINDOW_SIZE)
                            series.getData().remove(0);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(values[0]);
                }
                inputStream.close();
                scheduledExecutorService.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart

        }, 0, 1, TimeUnit.SECONDS);

    }
    public void setLatitude_time() {
        XYChart.Series series1 = new XYChart.Series();
        latitude_time.setTitle("Latitude Vs Time Graph");

        latitude_time.getData().add(series1);

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
                    //System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        series1.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[5])));

                        if (series1.getData().size() > WINDOW_SIZE)
                            series1.getData().remove(0);
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
    public void setLongitude_time() {
        XYChart.Series series2 = new XYChart.Series();
        longitude_time.setTitle("Longitude Vs Time Graph");

        longitude_time.getData().add(series2);

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
                    //System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        series2.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[5])));

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

            //Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart

        }, 0, 1, TimeUnit.SECONDS);

    }
    public void setPitch_time() {
        XYChart.Series series3 = new XYChart.Series();
        pitch_time.setTitle("Pitch Vs Time Graph");

        pitch_time.getData().add(series3);

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
                    //System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        series3.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[6])));

                        if (series3.getData().size() > WINDOW_SIZE)
                            series3.getData().remove(0);
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
    public void setRoll_time() {
        XYChart.Series series4 = new XYChart.Series();
        roll_time.setTitle("Roll Vs Time Graph");

        roll_time.getData().add(series4);

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
                    //System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        series4.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[7])));

                        if (series4.getData().size() > WINDOW_SIZE)
                            series4.getData().remove(0);
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
    public void setYaw_time() {
        XYChart.Series series5 = new XYChart.Series();
        yaw_time.setTitle("Yaw Vs Time Graph");

        yaw_time.getData().add(series5);

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
                    //System.out.println(columns[1]);
                    Platform.runLater(() -> {
                        series5.getData().add(new XYChart.Data<>(columns[1], Integer.parseInt(columns[8])));

                        if (series5.getData().size() > WINDOW_SIZE)
                            series5.getData().remove(0);
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

}

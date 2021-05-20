package sample;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

//import static jdk.xml.internal.SecuritySupport.getResourceAsStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        //Scene scene = new Scene(root, 600, 400, Color.WHITE);
        Scene scene = new Scene(root, 1280, 670, Color.WHITE);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream( "Team Sammard Icon1.png" )));
        primaryStage.setTitle("TEAM SAMMARD GROUND STATION");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        final Overall_controller controller = fxmlLoader.getController();
        final Projection projection = getParameters().getUnnamed().contains("wgs84")
                ? Projection.WGS_84 : Projection.WEB_MERCATOR;
        controller.initMapAndControls(projection);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

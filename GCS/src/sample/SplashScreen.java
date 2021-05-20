package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreen implements Initializable {
    @FXML
    private AnchorPane anchor_pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            new splash().start();
    }

    class splash extends Thread{
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Parent root1 = null;
                    try {
                        root1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
                        Scene scene = new Scene(root1, 1280, 670, Color.WHITE);
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image(Main.class.getResourceAsStream("Team Sammard Icon1.png")));
                        stage.setTitle("TEAM SAMMARD GROUND STATION");
                        stage.setScene(scene);
                        try {
                            Scene oldScene = anchor_pane.getScene();
                        }
                        catch (Exception e){
                            System.out.println("NAHI HO RAHA BC");
                        }
                        //anchor_pane.getScene().getWindow().hide();
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        }
    }

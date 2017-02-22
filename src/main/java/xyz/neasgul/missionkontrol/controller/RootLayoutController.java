package xyz.neasgul.missionkontrol.controller;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.javatuples.Triplet;
import xyz.neasgul.missionkontrol.APPSettings;
import xyz.neasgul.missionkontrol.ConnectDialog;
import xyz.neasgul.missionkontrol.ConnexionManager;
import xyz.neasgul.missionkontrol.Launcher;
import xyz.neasgul.missionkontrol.Utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by benoitchopinet on 11/08/2016.
 */
public class RootLayoutController {
    private Launcher mainApp;
    private ConnexionManager connexionManager;
    private APPSettings appSettings = APPSettings.getSettings();

    private Task<Void> testConnexionTask;
    /**
     * Is called by the launcher to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Launcher mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    Label statusLabel;
    @FXML
    ProgressIndicator loadingProgress;
    @FXML
    BorderPane rootPane;

    @FXML
    private void handleConnect(){
        ConnectDialog connectDialog = new ConnectDialog("Connexion to the KRPC server",
                Triplet.with(appSettings.getSetting(APPSettings.DEFAULT_CONNEXION_NAME),appSettings.getSetting(APPSettings.DEFAULT_CONNEXION_ADDRESS),appSettings.getSetting(APPSettings.SAVE_CONNEXION_INFO).equals("true")));
        Triplet<String, String, Boolean> result = connectDialog.showDialog();
        if (result != null){
            System.out.println(reachServer(result));

            saveConnexionInformation(result);

        }

    }
    @FXML
    public void handleQuickConnect() {
        // TODO: 22/02/2017 quick connect

    }
    @FXML
    private void handleExit(){
        System.exit(0);
    }
    @FXML
    private void handleSettings(){
        Parent settings;
        Stage settingstage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(Launcher.class.getResource("/view/settings.fxml"));
            settings = loader.load();
            Scene scene = new Scene(settings);
            settingstage.setScene(scene);
            settingstage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleHelp(){

    }
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Utils.MainTitle);
        alert.setHeaderText("About");

        alert.setContentText("Author: Benoit Chopinet\nAll Right Reserved");

        alert.showAndWait();
    }
    @FXML
    void initialize() {
        connexionManager = ConnexionManager.getInstance();

        Gauge ms2 = GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .value(0)
                .title("Acceleration")
                .animated(true)
                .build();
        Gauge LF = GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.LEVEL)
                .value(320)
                .maxValue(320)
                .title("Liquid Fuel")
                .build();
        Gauge LO = GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.LEVEL)
                .value(200)
                .maxValue(200)
                .title("Liquid Oxygen")
                .build();

        rootPane.setRight(LO);
        rootPane.setLeft(LF);
        LO.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

            if(newValue.doubleValue() <=0){
                fiveSecondsWonder.stop();
                System.out.println("this is stopped");
            }
        });
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("this is called every seconds on UI thread");
                LO.setValue(LO.getCurrentValue()-5);
            }
        }));
        fiveSecondsWonder.setCycleCount(Animation.INDEFINITE);
        fiveSecondsWonder.play();

    }
    Timeline fiveSecondsWonder;

    private void saveConnexionInformation(Triplet<String, String, Boolean> toSave){
        if (toSave.getValue2()){
            appSettings.addSetting(APPSettings.DEFAULT_CONNEXION_NAME,toSave.getValue0());
            appSettings.addSetting(APPSettings.DEFAULT_CONNEXION_ADDRESS,toSave.getValue1());
            appSettings.addSetting(APPSettings.SAVE_CONNEXION_INFO,toSave.getValue2().toString());
        }
        else {
            appSettings.addSetting(APPSettings.SAVE_CONNEXION_INFO,toSave.getValue2().toString());
        }

        appSettings.saveSettings();
    }

    /**
     *
     * @param result
     * @return true if the connexion is established, false if can't connect to the host
     */
    private boolean reachServer(Triplet<String,String,Boolean> result) {
        boolean[] isReached = {false};
        loadingProgress.setVisible(true);
        testConnexionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(LocalTime.now());
                System.out.println("Tread : start test");
                updateMessage("Trying to connect to "+ result.getValue1());
                try {
                    // test the connexion with the ip address exepts localhost
                    if(!Objects.equals(result.getValue1(), "127.0.0.1")){
                        System.out.println("Thread : test connexion to "+ result.getValue1());
                        try (Socket soc = new Socket()) {
                            soc.connect(new InetSocketAddress(result.getValue1(), 23), 2000);
                        }
                    }
                    isReached[0] = true;

                    // if host has respond
                    System.out.println("Thread : start connexion");
                    // start a open connexion procedure
                    if(connexionManager.OpenConnexion(result.getValue0(),result.getValue1())){
                        //ok
                        isReached[0] = true;
                        updateMessage("Connexion establish with a KRPC server on "+ result.getValue1());
                    }
                    else {
                        //not ok
                        isReached[0] = false;
                        updateMessage("Can't connect to a KRPC server on "+ result.getValue1());
                    }
                    return null;
                // when the socket can't be open
                } catch (IOException ex) {
                    ex.printStackTrace();
                    isReached[0] = false;
                    updateMessage("No Connection to "+ result.getValue1());
                }

                System.out.println("Thread : finish " + isReached[0]);
                System.out.println(LocalTime.now());
                loadingProgress.setVisible(false);
                statusLabel.textProperty().unbind();
                return null;
            }
        };

        Thread testco = new Thread(testConnexionTask);
        statusLabel.textProperty().bind(testConnexionTask.messageProperty());
        testco.start();

        return isReached[0];
    }
}

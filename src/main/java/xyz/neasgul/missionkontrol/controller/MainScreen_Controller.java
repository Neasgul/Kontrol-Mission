package xyz.neasgul.missionkontrol.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import krpc.client.RPCException;
import krpc.client.Stream;
import krpc.client.StreamException;
import krpc.client.services.KRPC;
import krpc.client.services.SpaceCenter;
import org.javatuples.Triplet;
import xyz.neasgul.missionkontrol.*;
import xyz.neasgul.missionkontrol.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by benoitchopinet on 04/05/2016.
 *//*
public class MainScreen_Controller {
    public static final String TAG = "MainScreen";
    ConnectionManager connexionManager;
    private StageManager stageManager;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Server;
    @FXML
    private TextField SOI;

    @FXML
    private TextField ship_name;

    @FXML
    private MenuItem Menu_About;

    @FXML
    private MenuItem Menu_Settings;

    @FXML
    private MenuItem Menu_Close;

    @FXML
    private MenuItem Menu_Help;


    //Stream
    private Stream<String> shipname;
    private Stream<SpaceCenter.CelestialBody> sphereofinfluence;
    private Stream<KRPC.GameScene> gamescene;

    @FXML
    void initialize() {
        assert SOI != null : "fx:id=\"SOI\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert ship_name != null : "fx:id=\"ship_name\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert Server != null : "fx:id=\"Server\" was not injected: check your FXML file 'RootLayout.fxml'.";
        APPSettings settings = APPSettings.getSettings();
        connexionManager = ConnectionManager.getInstance();
        stageManager = StageManager.getInstance();
        // Launch the connexion dialog
        Launcher.showConnexionDialog("Connexion to the KRPC server",
                Triplet.with(settings.getSetting(APPSettings.DEFAULT_CONNEXION_NAME),settings.getSetting(APPSettings.DEFAULT_CONNEXION_ADDRESS), settings.getSetting(APPSettings.SAVE_CONNEXION_INFO).equals("true")));
        // check if the user click on cancel or ok
        /*Triplet<String, String, Boolean> value = connexionValue.orElse(null);
        if(value!=null){
            // try to connect to krpc server

            //check if saveinfo is checked
            if(value.getValue2()) {
                settings.addSetting(APPSettings.DEFAULT_CONNEXION_NAME, value.getValue0());
                settings.addSetting(APPSettings.DEFAULT_CONNEXION_ADDRESS, value.getValue1());
                settings.addSetting(APPSettings.SAVE_CONNEXION_INFO, value.getValue2() ? "true" : "false");
                settings.saveSettings();
            }
        }else {
            //If it's cancel, close app (Not the best way)
            System.exit(0);
        }


        //stageManager.ChangeStageTitle(TAG, Utils.MainTitle);
        Server.setText(Server.getText() + connexionManager.getConnexionName());

        Menu_Settings.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/Settings.png"))));

        //try {
        //initializeStream();
        getStreamValue();
        /*} catch (StreamException e) {
            e.printStackTrace();
        } catch (RPCException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initializeStream() throws StreamException, RPCException, IOException {

        shipname = connexionManager.addStream(SpaceCenter.Vessel.class, "getName");
        sphereofinfluence = connexionManager.addStream(SpaceCenter.Orbit.class, "getBody");
        gamescene = connexionManager.addStream(KRPC.class, "getCurrentGameScene");

    }

    void getStreamValue() {
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            System.out.println("this is called every 5 seconds on UI thread");
            try {
                KRPC.GameScene currentscene;
                currentscene = KRPC.GameScene.FLIGHT;
                stageManager.ChangeStageTitle(TAG, Utils.MainTitle + " : " + currentscene.toString());
                System.out.println(currentscene.toString());
                switch (currentscene) {
                    case SPACE_CENTER:
                    case TRACKING_STATION:
                    case EDITOR_VAB:
                    case EDITOR_SPH:

                        break;
                    case FLIGHT:
                        ship_name.setText(shipname.get());
                        SOI.setText(sphereofinfluence.get().getName());
                        break;
                }
            } catch (StreamException | RPCException | IOException e) {
                e.printStackTrace();
            }

        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    void onMenuClose(ActionEvent actionEvent) {
        Stage current = stageManager.getStage(TAG);
        current.close();
    }

    @FXML
    void onMenuAbout(ActionEvent actionEvent){

    }

    @FXML
    void onMenuSettings(ActionEvent actionEvent) throws IOException {
        //Stage settings = stageManager.CreateStage(Settings_Controller.TAG,MainScreen_Controller.class.getResource("/view/settings.fxml"),Utils.SettingsTitle);
        //settings.showAndWait();
    }
}
*/
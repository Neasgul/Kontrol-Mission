package xyz.neasgul.missionkontrol;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Benoit on 29/04/2016.
 */
public class StageManager {
    private static StageManager instance;

    private static LinkedList<Stage> mStageList;

    private StageManager() {
        mStageList  =new LinkedList<Stage>();
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public LinkedList<Stage> getStageList() {
        return mStageList;
    }

    public Stage getStage(String title) {
        Stage tstage = null;
        for (int i = 0; i < mStageList.size(); i++) {
            tstage = mStageList.get(i);
            if(tstage.getTitle().equals(title)){
                break;
            }
        }
        return tstage;
    }

    public Stage CreateStage(URL location, String name) throws IOException {
        Parent root = FXMLLoader.load(location);
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle(name);
        newStage.setScene(scene);
        mStageList.add(newStage);
        return newStage;
    }

    public Stage RemoveStage( String title) {
        Stage tstage = null;
        for (int i = 0; i < mStageList.size(); i++) {
            tstage = mStageList.get(i);
            if(tstage.getTitle().equals(title)){
                mStageList.remove(i);
                break;
            }
        }
        return tstage;
    }
}

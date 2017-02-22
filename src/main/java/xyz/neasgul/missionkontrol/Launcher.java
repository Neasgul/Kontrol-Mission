package xyz.neasgul.missionkontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import xyz.neasgul.missionkontrol.Utils.Utils;
import xyz.neasgul.missionkontrol.controller.RootLayoutController;

import java.io.IOException;

/**
 * Created by benoitchopinet on 18/05/2016.
 */
public class Launcher extends Application{

        private Stage primaryStage;
        private BorderPane rootLayout;

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle(Utils.MainTitle);

            initRootLayout();
        }

        /**
         * Initializes the root layout.
         */
        protected void initRootLayout() {
            try {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Launcher.class.getResource("/view/RootLayout.fxml"));
                rootLayout = loader.load();
                RootLayoutController rootcontroller = loader.getController();
                rootcontroller.setMainApp(this);

                // Show the scene containing the root layout.
                Rectangle2D mainscreen = Screen.getPrimary().getVisualBounds();

                //Scene scene = new Scene(rootLayout);
                Scene scene = new Scene(rootLayout,mainscreen.getWidth() *0.75,mainscreen.getHeight() * 0.75);

                primaryStage.setScene(scene);
                //primaryStage.setMaximized(true);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns the main stage.
         * @return
         */
        public Stage getPrimaryStage() {
            return primaryStage;
        }


    public static void main(String[] args) {

        launch(args);
    }
}

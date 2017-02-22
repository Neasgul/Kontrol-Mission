package xyz.neasgul.missionkontrol;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.javatuples.Triplet;
import xyz.neasgul.missionkontrol.Utils.Utils;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by benoitchopinet on 11/08/2016.
 */
public class ConnectDialog {
    private Dialog<Triplet<String, String, Boolean>> dialog;
    private TextField connexionName;
    private TextField IPAddress;
    private CheckBox info;

    public ConnectDialog(String message, Triplet<String, String, Boolean> defaultValue) {
        // Create the custom dialog.

        dialog = new Dialog<>();
        dialog.setTitle("Kerbal Auto Pilot : Connexion Screen");
        dialog.setHeaderText(message);

        // Set the icon.
        dialog.setGraphic(new ImageView(Launcher.class.getResource("/img/Logo_small.png").toString()));

        // Set the button types.
        ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(connectButtonType, cancelButtonType);

        // Create the connexionName and IPAddress labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        if(defaultValue.getValue2()) {
            connexionName = new TextField(defaultValue.getValue0());

            IPAddress = new TextField(defaultValue.getValue1());

            info = new CheckBox("Save Information");
            info.setSelected(defaultValue.getValue2());
        }
        else
        {
            connexionName = new TextField();

            IPAddress = new TextField();

            info = new CheckBox("Save Information");
            info.setSelected(false);
        }
        connexionName.setPromptText("Connexion Name");
        IPAddress.setPromptText("IP Address");
        grid.add(new Label("Connexion Name :"), 0, 0);
        grid.add(connexionName, 1, 0);
        grid.add(new Label("IP address :"), 0, 1);
        grid.add(IPAddress, 1, 1);
        grid.add(info,1,2);

        // Enable/Disable login button depending on whether a valid IP Address was entered.
        Node connectButton = dialog.getDialogPane().lookupButton(connectButtonType);
        connectButton.setDisable(!Utils.isIPv4(IPAddress.getText()));

        // Do some validation (using the Java 8 lambda syntax).
        IPAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            connectButton.setDisable(!Utils.isIPv4(newValue));
        });

        dialog.getDialogPane().setContent(grid);


        // Convert the result to a Triplet when the login button is clicked.
        dialog.setResultConverter(new Callback<ButtonType, Triplet<String, String, Boolean>>() {
            @Override
            public Triplet<String, String, Boolean> call(ButtonType b) {
                if (b == connectButtonType){
                    return Triplet.with(connexionName.getText(),IPAddress.getText(),info.isSelected());
                }
                return null;
            }
        });
    }

    public Triplet<String,String, Boolean> showDialog(){

        Optional<Triplet<String,String, Boolean>> result = dialog.showAndWait();
        Triplet<String,String, Boolean> resultValue = result.orElse(null);
        return resultValue;
    }
}

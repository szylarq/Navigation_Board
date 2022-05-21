package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import navigation.AppRoot;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class MainController implements Initializable {
    
    private Stage primaryStage;
    @FXML
    private VBox mainMenuId;
    @FXML
    private Separator menuSeparotorId;
    @FXML
    private VBox centerContainerId;
    @FXML
    private HBox selectionMenuId;
    
    
    @FXML
    private void handleOnActionButtonLogin(ActionEvent event) throws IOException {
        //Load UI objects
        Parent root = FXMLLoader.load(Utils.getFXMLName(LoginController.class));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
    }
    
    @FXML
    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
    }

    @FXML
    private void onAboutClicked(ActionEvent event) {
        Utils.shoeAbout();
    }

    @FXML
    private void onContactClick(ActionEvent event) {
        Utils.showContact();
    }

    @FXML
    private void onHelpClick(ActionEvent event) {
        Utils.showHelp();
    }
    
    @FXML
    private void handleOnActionButtonSignUp(ActionEvent event) throws IOException {
        //Load UI objects
        Parent root = FXMLLoader.load(Utils.getFXMLName(SignUpController.class));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
    }
    
        @FXML
    private void handleOnActionButtonExit(ActionEvent event) throws IOException {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
    }    
}

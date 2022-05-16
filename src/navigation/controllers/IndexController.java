package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import navigation.AppRoot;
import navigation.utils.Utils;

public class IndexController implements Initializable {
    
    @FXML
    private Button signupButton;
    
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onLoginpButtonClicked() throws IOException {
        AppRoot.getMainStage().setScene(new Scene(FXMLLoader.load(Utils.getFXMLName(LoginController.class))));
    }
    
    @FXML
    private void onSignupButtonClicked() throws IOException {
        AppRoot.getMainStage().setScene(new Scene(FXMLLoader.load(Utils.getFXMLName(SignupController.class))));
    }
    
}
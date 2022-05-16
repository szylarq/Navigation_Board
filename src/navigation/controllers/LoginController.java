package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import navigation.AppRoot;
import navigation.utils.Utils;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;
    
    @FXML
    private TextField usernameTextField;
    
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onLoginButtonClicked() throws IOException {
        AppRoot.getMainStage().setScene(new Scene(FXMLLoader.load(Utils.getFXMLName(ProblemsController.class))));
    }       
}

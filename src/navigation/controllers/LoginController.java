package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import navigation.AppRoot;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Label passworderror_label, usernameerror_label;

    @FXML
    private Button accept_button, cancel_button;

    private StringProperty username, password;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }

    @FXML
    private void handleOnActionButtonAccept(ActionEvent event) throws IOException {
        User user;
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!AppRoot.getDbDriver().exitsNickName(username_field.getText())) {
            alert.setTitle("Error - Log in");
            alert.setHeaderText("ERROR 404");
            alert.setContentText("Username not found in database");

        } else if ((user = AppRoot.getDbDriver().loginUser(username.getValue(), password.getValue())) == null) {
            alert.setTitle("Error - Log in");
            alert.setHeaderText("ERROR");
            alert.setContentText("Username and password do not match");
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success - Log in");
            alert.setHeaderText("SUCCESS!");
            alert.setContentText("User " + username.getValue() + " is logged in.");
            
            AppRoot.setCurrentUser(user);
            //Load UI objects
            Parent root = FXMLLoader.load(Utils.getFXMLName(NavigatorController.class));
            
            //Show win2
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Navigator tool");
        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        username = new SimpleStringProperty();
        username.bind(username_field.textProperty());

        password = new SimpleStringProperty();
        password.bind(pass_field.textProperty());
    }
}

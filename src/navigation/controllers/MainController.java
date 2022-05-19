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
    private void handleOnActionButtonLogin(ActionEvent event) throws IOException {
        //Load UI objects
        Parent root = FXMLLoader.load(Utils.getFXMLName(LoginController.class));
//        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
//        Parent root = myLoader.load();
//        
//        //Access to wind2 controller and init win2
//        LoginController loginController = myLoader.<LoginController>getController();
//        loginController.initLoginWindow(primaryStage);
        
        //Show win2
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
       // primaryStage.show();

    }
    
    @FXML
    private void handleOnActionButtonSignUp(ActionEvent event) throws IOException {
        //Load UI objects
        Parent root = FXMLLoader.load(Utils.getFXMLName(SignUpController.class));
//        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
//        Parent root = myLoader.load();
//        
//        //Access to wind2 controller and init win2
//        SignUpController signUpController = myLoader.<SignUpController>getController();
//        signUpController.initSignUpWindow(primaryStage);
        
        //Show win2
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
       // primaryStage.show();

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

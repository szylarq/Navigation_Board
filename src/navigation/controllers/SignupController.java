package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Navegacion;
import navigation.AppRoot;
import navigation.dao.NavigationDAO;
import navigation.utils.Utils;

public class SignupController implements Initializable {
    
    private Navegacion dbDriver; 
    
    @FXML
    private Button signupButton;
    
    @FXML
    private TextField usernameTextField;
    
    @FXML
    private TextField emailTextField;
        
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private DatePicker birthDatePicker;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbDriver = NavigationDAO.initializeInstanceDAO(); 
    }

    @FXML
    private void onSignupButtonClicked() throws IOException {
       String username = usernameTextField.getText();
        try{
            if(dbDriver != null){
                if(!dbDriver.exitsNickName(username)) {
               dbDriver.registerUser(username, emailTextField.getText(), 
                    passwordField.getText(), Utils.getDefaultAvatarImage(), 
                    birthDatePicker.getValue());
                } else {
//                  TODO Komunikat o juĹĽ istniejÄ…cym uĹĽytkowniku
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
       }
        
        AppRoot.getMainStage().setScene(new Scene(FXMLLoader.load(Utils.getFXMLName(IndexController.class))));
    }       
}

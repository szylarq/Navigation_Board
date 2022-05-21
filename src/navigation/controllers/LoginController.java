package navigation.controllers;

import java.io.FileInputStream;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import navigation.AppRoot;
import navigation.model.CurrentSession;
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


    private StringProperty username, password;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    @FXML
    private VBox mainMenuId;
    @FXML
    private VBox centerContainerId;
    @FXML
    private Button button_accept;
    @FXML
    private Button button_cancel;
    @FXML
    private Button getBackBtnId;
    @FXML
    private VBox formContainerId;
    @FXML
    private HBox selectionMenuId;

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

        } else if ((user = AppRoot.getDbDriver().loginUser(username.getValue(), 
                password.getValue())) == null) {
            alert.setTitle("Error - Log in");
            alert.setHeaderText("ERROR");
            alert.setContentText("Username and password do not match");
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success - Log in");
            alert.setHeaderText("SUCCESS!");
            alert.setContentText("User " + username.getValue() + " is logged in.");
            
            AppRoot.setCurrentSession(new CurrentSession());
            CurrentSession currentSession = AppRoot.getCurrentSession();
            currentSession.setUser(user);
            
            //Load UI objects
            Parent root = 
                    FXMLLoader.load(Utils.getFXMLName(ProblemsController.class));

            //Show win2
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Navigator tool");
        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initFronendSetings();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        username = new SimpleStringProperty();
        username.setValue("dbliakharchuk");
        username.bind(username_field.textProperty());
        username_field.textProperty().setValue("dbliakharchuk");

        password = new SimpleStringProperty();
        password.setValue("1234Ui!@");
        password.bind(pass_field.textProperty());
        pass_field.textProperty().setValue("1234Ui!@");
    }
    
    void initFronendSetings() throws Exception {
        FileInputStream getBack = new FileInputStream(Utils.GET_BACK_ICON_PATH);
        Image getBackImage = new Image(getBack);
        ImageView imageViewGetBack = new ImageView(getBackImage);

        imageViewGetBack.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewGetBack.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);

        getBackBtnId.setText("");
        getBackBtnId.setGraphic(imageViewGetBack);
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
}

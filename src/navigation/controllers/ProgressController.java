package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Session;
import navigation.AppRoot;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProgressController implements Initializable {

    @FXML
    private DatePicker dateFromPicker;
    @FXML
    private DatePicker dateToPicker;
    @FXML
    private Label correctnessLabel;
    @FXML
    private Label hitsLabel;
    @FXML
    private Label faultsLabel;
    @FXML
    private ImageView profileImageView;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        setValues(Utils.getAllUserSessions());
    }    

    @FXML
    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
    }

    @FXML
    private void onAboutClicked(ActionEvent event) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2022");
        mensaje.showAndWait();
    }

    @FXML
    private void onLogOutClicked(ActionEvent event) throws IOException{
        Utils.saveCurrentSession();
        
        Parent root = FXMLLoader.load(Utils.getFXMLName(MainController.class));
        
        AppRoot.getCurrentSession().setUser(null);
        
        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
    }

    @FXML
    private void onProfileClicked() throws IOException{
        Parent root = FXMLLoader.load(Utils.getFXMLName(ProfileController.class)); 

        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.setScene(scene);
        profileStage.setTitle("User Profile");
            
        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.showAndWait();
    }

    @FXML
    private void onFilterButtonClicked(ActionEvent event) {
        setValues(Utils.getSessionsFromTheRange(dateFromPicker.getValue(), dateToPicker.getValue()));
    }
    
    private void setValues(List<Session> userSessions){
        int hits = 0;
        int faults = 0;
        double overallCorrectness = 0.0;
        
        if(!userSessions.isEmpty()){
            for(Session session : userSessions){
            hits += session.getHits();
            faults += session.getFaults();
        }
        
        overallCorrectness = (double)hits / (hits + faults) * 100;
        }

        hitsLabel.setText(String.valueOf(hits));
        faultsLabel.setText(String.valueOf(faults));
        correctnessLabel.setText(String.format("%.2f", overallCorrectness) + "%");
    }
}

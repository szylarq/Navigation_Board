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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SingleProblemController implements Initializable {

    @FXML
    private ImageView profileImageView;
    @FXML
    private Label problemLabel;
    @FXML
    private Label ans1Label;
    @FXML
    private Label ans2Label;
    @FXML
    private Label ans3Label;
    @FXML
    private Label ans4Label;
    @FXML
    private RadioButton ans1RadioButton;
    @FXML
    private RadioButton ans2RadioButton;
    @FXML
    private RadioButton ans3RadioButton;
    @FXML
    private RadioButton ans4RadioButton;
    @FXML
    private Button submitButton;
    
    private ObservableProblem problem;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        ToggleGroup radioButtonsGroup = new ToggleGroup();
        
        ans1RadioButton.setToggleGroup(radioButtonsGroup);
        ans2RadioButton.setToggleGroup(radioButtonsGroup);
        ans3RadioButton.setToggleGroup(radioButtonsGroup);
        ans4RadioButton.setToggleGroup(radioButtonsGroup);
    }

    public void init(ObservableProblem problem){
        this.problem = problem;
        problemLabel.setText(problem.getId() + ". " + problem.getProblem().getText());
    }

    @FXML
    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
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
    private void onAboutClicked(ActionEvent event) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2022");
        mensaje.showAndWait();
    }

    @FXML
    private void onLogOutClicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Utils.getFXMLName(MainController.class));
        
        AppRoot.setCurrentUser(null);
        
        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
    }

    @FXML
    private void onShowNavigationMapClicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Utils.getFXMLName(NavigatorController.class)); 

        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.setScene(scene);
        profileStage.setTitle("Navigator tool");
            
        profileStage.initModality(Modality.NONE);
        profileStage.showAndWait();
    }

    @FXML
    private void onSubmitClicked(ActionEvent event) {
    }

    @FXML
    private void onCancelClicked(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }
    
}

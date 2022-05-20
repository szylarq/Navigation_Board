package navigation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Answer;
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
    private Button submitButton, cancelFinishButton;
    
    private ObservableProblem problem;
    private Answer correctAnswer;
    
    private ToggleGroup radioButtonsGroup;
    
    private Map<RadioButton, Answer> radioButtonsAnswersMap;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        radioButtonsGroup = new ToggleGroup();
        
        ans1RadioButton.setToggleGroup(radioButtonsGroup);
        ans2RadioButton.setToggleGroup(radioButtonsGroup);
        ans3RadioButton.setToggleGroup(radioButtonsGroup);
        ans4RadioButton.setToggleGroup(radioButtonsGroup);
        
        submitButton.setDisable(true);
        
        radioButtonsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,                    
                Toggle old_toggle, Toggle new_toggle) {
                if (radioButtonsGroup.getSelectedToggle() != null) {
                    submitButton.setDisable(false);
                } else {
                    submitButton.setDisable(true);
                }               
        }});
    }

    public void init(ObservableProblem problem){
        this.problem = problem;
        problemLabel.setText(problem.getId() + ". " + problem.getProblem().getText());
                
        List<Answer> answers = new ArrayList<>(problem.getProblem().getAnswers());
        correctAnswer = answers.stream().filter(ans -> ans.getValidity() == true).findFirst().get();
        
        Collections.shuffle(answers);
        
        radioButtonsAnswersMap = Map.of(
                ans1RadioButton, answers.get(0),
                ans2RadioButton, answers.get(1),
                ans3RadioButton, answers.get(2),
                ans4RadioButton, answers.get(3)    
        );
        
        for(RadioButton radioButton : radioButtonsAnswersMap.keySet()){
            radioButton.setText(radioButton.getText() + " " + radioButtonsAnswersMap.get(radioButton).getText());
        }
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
        RadioButton selectedRadioButton = (RadioButton) radioButtonsGroup.getSelectedToggle();
        
        boolean correct = radioButtonsAnswersMap.get(selectedRadioButton).equals(correctAnswer);
        
        submitButton.setDisable(true);
        cancelFinishButton.setText("Finish");
    }

    @FXML
    private void onCancelClicked(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }
    
}

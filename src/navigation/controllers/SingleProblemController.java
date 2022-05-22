package navigation.controllers;

import java.io.FileInputStream;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Answer;
import model.User;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SingleProblemController implements Initializable {

    private Label problemLabel;
    private RadioButton ans1RadioButton;
    private RadioButton ans2RadioButton;
    private RadioButton ans3RadioButton;
    private RadioButton ans4RadioButton;
    private Button submitButton;
    @FXML
    private Button cancelFinishButton;
    
    private ObservableProblem problem;
    private Answer correctAnswer;
    
    private ToggleGroup radioButtonsGroup;
    
    private Map<RadioButton, Answer> radioButtonsAnswersMap;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    private User user;
    @FXML
    private VBox mainMenuId;
    private Button getBackBtnId;
    private Button chartBtnId;
    private MenuButton profileMenuBtnId;
    @FXML
    private Slider zoom_slider;
    @FXML
    private ListView<?> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private ImageView imgView;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            user = AppRoot.getCurrentSession().getUser();
            initFronendSetings();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        radioButtonsGroup = new ToggleGroup();
        
        ans1RadioButton.setToggleGroup(radioButtonsGroup);
        ans2RadioButton.setToggleGroup(radioButtonsGroup);
        ans3RadioButton.setToggleGroup(radioButtonsGroup);
        ans4RadioButton.setToggleGroup(radioButtonsGroup);
        
        submitButton.setDisable(true);
        
        radioButtonsGroup.selectedToggleProperty().addListener(
            new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov,                    
                    Toggle old_toggle, Toggle new_toggle) {
                    if (radioButtonsGroup.getSelectedToggle() != null) {
                        submitButton.setDisable(false);
                    } else {
                        submitButton.setDisable(true);
                    }               
                }
            }
        );
    }

    public void init(ObservableProblem problem){
        this.problem = problem;
        problemLabel.setText(problem.getId() + ". " 
                + problem.getProblem().getText());
                
        List<Answer> answers = new ArrayList<>(problem.getProblem().getAnswers());
        correctAnswer = answers.stream()
                .filter(ans -> ans.getValidity() == true)
                .findFirst()
                .get();
        
        Collections.shuffle(answers);
        
        radioButtonsAnswersMap = Map.of(
                ans1RadioButton, answers.get(0),
                ans2RadioButton, answers.get(1),
                ans3RadioButton, answers.get(2),
                ans4RadioButton, answers.get(3)    
        );
        
        for(RadioButton radioButton : radioButtonsAnswersMap.keySet()){
            radioButton.setText(radioButton.getText() + " " 
                    + radioButtonsAnswersMap.get(radioButton).getText());
        }
    }

    private void onShowNavigationMapClicked(ActionEvent event) throws IOException{
        Parent root = 
                FXMLLoader.load(Utils.getFXMLName(NavigatorController.class)); 

        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.setScene(scene);
        profileStage.setTitle("Navigator tool");
            
        profileStage.initModality(Modality.NONE);
        profileStage.showAndWait();
    }

    private void onSubmitClicked(ActionEvent event) { 
        RadioButton selectedRadioButton = 
                (RadioButton) radioButtonsGroup.getSelectedToggle();
        
        boolean correct = radioButtonsAnswersMap.get(selectedRadioButton)
                .equals(correctAnswer);
        
        submitButton.setDisable(true);
        cancelFinishButton.setText("Finish");
        
        if(correct){
            AppRoot.getCurrentSession().incrementHits();
        } else {
            AppRoot.getCurrentSession().incrementFaults();
        }
    }
    
    void initFronendSetings() throws Exception {
        FileInputStream inputProfile = new FileInputStream(Utils.PROFILE_ICON_PATH);
        Image imageProfile = new Image(inputProfile);
        ImageView imageViewProfile = new ImageView(imageProfile);
        ImageView view = new ImageView(user.getAvatar()); //It doesn't update

        if (view != null) {
            imageViewProfile = view;
        }

        FileInputStream inputChart = new FileInputStream(Utils.CHART_ICON_PATH);
        Image imageChart = new Image(inputChart);
        ImageView imageViewChart = new ImageView(imageChart);

        FileInputStream getBack = new FileInputStream(Utils.GET_BACK_ICON_PATH);
        Image getBackImage = new Image(getBack);
        ImageView imageViewGetBack = new ImageView(getBackImage);

        imageViewProfile.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewProfile.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewChart.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewChart.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewGetBack.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewGetBack.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);

        getBackBtnId.setText("");
//        chartBtnId.setText("");
        profileMenuBtnId.setText("");
        getBackBtnId.setGraphic(imageViewGetBack);
        chartBtnId.setGraphic(imageViewChart);
        profileMenuBtnId.setGraphic(imageViewProfile);
    }

    private void onCancelClicked(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }
    
    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
    }

    private void onProfileClicked() throws IOException{
        Utils.showUserProfile();
    }

    private void onAboutClicked(ActionEvent event) {
        Utils.shoeAbout();
    }

    private void onLogOutClicked(ActionEvent event) throws IOException{
        Utils.logOut();
    }
    
    @FXML
    private void onContactClick(ActionEvent event) {
        Utils.showContact();
    }

    @FXML
    private void onHelpClick(ActionEvent event) {
        Utils.showHelp();
    }


    private void showProgress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Utils.getFXMLName(ProgressController.class));

        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
    }

    @FXML
    private void zoomOut(ActionEvent event) {
    }

    @FXML
    private void zoomIn(ActionEvent event) {
    }

    @FXML
    private void listClicked(MouseEvent event) {
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
    }
}

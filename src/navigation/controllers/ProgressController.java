package navigation.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Problem;
import model.Session;
import model.User;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
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

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    @FXML
    private VBox mainMenuId;
    @FXML
    private Button getBackBtnId;
    @FXML
    private Button chartBtnId;
    @FXML
    private Separator menuSeparotorId;
    @FXML
    private MenuButton profileMenuBtnId;
    
    private User user;

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }
    
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
        
        setValues(Utils.getAllUserSessions());
    }    

    @FXML
    private void onFilterButtonClicked(ActionEvent event) {
        setValues(Utils.getSessionsFromTheRange(
                dateFromPicker.getValue(), dateToPicker.getValue()));
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
        correctnessLabel.setText(String.format("%.2f", overallCorrectness) 
                + "%");
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

    @FXML
    private void onLogOutClicked(ActionEvent event) throws IOException {
        Utils.logOut();
    }

    @FXML
    private void onProfileClicked() throws IOException {
        Utils.showUserProfile();
    }

    @FXML
    private void showProgress(ActionEvent event) {
    }

    @FXML
    private void onShowNavigationMapClicked(ActionEvent event) throws IOException {
        Parent root
                = FXMLLoader.load(Utils.getFXMLName(NavigatorController.class));

        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.setScene(scene);
        profileStage.setTitle("Navigator tool");

        profileStage.initModality(Modality.NONE);
        profileStage.showAndWait();

    }
}
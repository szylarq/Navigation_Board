package navigation.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Problem;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
import navigation.utils.Utils;
import java.io.FileInputStream;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import model.User;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProblemsController implements Initializable {

    @FXML
    private ListView<ObservableProblem> problemsListView;
    @FXML
    private Button openSelectedButton;
    @FXML
    private MenuButton profileMenuBtnId;
    @FXML
    private Button chartBtnId;
    @FXML
    private Button getBackBtnId;
    @FXML
    private VBox mainMenuId;
    @FXML
    private Separator menuSeparotorId;
    @FXML
    private VBox centerContainerId;
    @FXML
    private HBox selectionMenuId;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            user = AppRoot.getCurrentSession().getUser();
            initFronendSetings();
            List<ObservableProblem> observableProblems = new ArrayList<>();

            int i = 0;
            for (Problem problem : AppRoot.getDbDriver().getProblems()) {
                observableProblems.add(new ObservableProblem(++i, problem));
            }
            problemsListView.setItems(FXCollections.observableArrayList(observableProblems));
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }

    @FXML
    private void onOpenRandomClicked(ActionEvent event) throws IOException {
        List<ObservableProblem> problems = problemsListView.getItems();

        Random rand = new Random();
        ObservableProblem randomProblem = problems.get(rand.nextInt(problems.size()));

        Stage stage = AppRoot.getMainStage();

        FXMLLoader loader = new FXMLLoader(Utils.getFXMLName(SingleProblemController.class));
        Parent root = loader.load();

        SingleProblemController controller = loader.<SingleProblemController>getController();
        controller.init(randomProblem);

        Scene scene = new Scene(root);
        stage.setTitle("Random Problem");
        stage.setScene(scene);
    }

    @FXML
    private void onOpenSelectedClicked(ActionEvent event) throws IOException {
        Stage stage = AppRoot.getMainStage();

        FXMLLoader loader = new FXMLLoader(Utils.getFXMLName(SingleProblemController.class));
        Parent root = loader.load();

        SingleProblemController controller = loader.<SingleProblemController>getController();
        if (problemsListView.getSelectionModel().getSelectedItem() == null) {
            Utils.showAlert("Unselected task!", "Please, Select a task!");
            return;
        }

        controller.init(problemsListView.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        stage.setTitle("Selected Problem");
        stage.setScene(scene);
    }

    @FXML
    private void showProgress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Utils.getFXMLName(ProgressController.class));

        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
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
}

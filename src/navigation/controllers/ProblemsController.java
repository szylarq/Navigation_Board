package navigation.controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Problem;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
import navigation.utils.Utils;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProblemsController implements Initializable {

    @FXML
    private ImageView profileImageView;
    @FXML
    private ListView<ObservableProblem> problemsListView;
    @FXML
    private Button openSelectedButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<ObservableProblem> observableProblems = new ArrayList<>();
        
        int i = 0;
        for (Problem problem : AppRoot.getDbDriver().getProblems()){
            observableProblems.add(new ObservableProblem(++i, problem));
        }
        
        problemsListView.setItems(FXCollections.observableArrayList(observableProblems));
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
    private void onOpenRandomClicked(ActionEvent event) throws IOException{
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
    private void onOpenSelectedClicked(ActionEvent event) throws IOException{
        Stage stage = AppRoot.getMainStage();

        FXMLLoader loader = new FXMLLoader(Utils.getFXMLName(SingleProblemController.class));
        Parent root = loader.load();
        
        SingleProblemController controller = loader.<SingleProblemController>getController();
        controller.init(problemsListView.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        stage.setTitle("Selected Problem");
        stage.setScene(scene);
    }

    @FXML
    private void showProgress(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Utils.getFXMLName(ProgressController.class));
  
        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
    }
}

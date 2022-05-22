package navigation.utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Session;
import navigation.AppRoot;
import navigation.controllers.LoginController;
import navigation.controllers.MainController;
import navigation.controllers.NavigatorController;
import navigation.controllers.ProblemsController;
import navigation.controllers.ProfileController;
import navigation.controllers.ProgressController;
import navigation.controllers.SignUpController;
import navigation.controllers.SingleProblemController;
import navigation.model.CurrentSession;

public class Utils {

    public static final Integer DEFAULT_MENU_HEIGHT = 20;
    public static final String DEFAULT_AVATAR_NAME = "default.png";
    public static final String AVATARS_FOLDER_PATH = "src/navigation/resources/avatars";
    public static final String NAVIGATION_RESOURCES_PATH = "src/navigation/resources";

    public static final String DEFAULT_AVATAR_PATH = AVATARS_FOLDER_PATH + "/"
            + DEFAULT_AVATAR_NAME;
    public static final String PROFILE_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "profile-icon.png";
    public static final String CHART_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "location-icon.png";
    public static final String GET_BACK_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "previous-icon.png";
    
    public static final String ANGLE_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "angle.png";
    public static final String ARC_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "arc.png";
    public static final String CROSS_MARC_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "cross-mark.png";
    public static final String DELETE_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "delete.png";
    public static final String START_POINT_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "start-point.png";
    public static final String TEXT_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "type.png";
    public static final String LINE_ICON_PATH = NAVIGATION_RESOURCES_PATH + "/" + "diagonal-line.png";

    private static final String VIEWS_FOLDER_PATH = "views/";
    private static final String FXML_EXTENSION = ".fxml";
    private static final Map<Class<?>, String> FXML_CONTROLLERS_MAP
            = Map.of(MainController.class, "main",
                    LoginController.class, "login",
                    NavigatorController.class, "navigator",
                    ProfileController.class, "profile",
                    SignUpController.class, "signUp",
                    ProblemsController.class, "problems",
                    SingleProblemController.class, "singleProblem",
                    ProgressController.class, "progress"
            );

    public static URL getFXMLName(Class<?> controllerClass) {
        return getResourceURL(VIEWS_FOLDER_PATH
                + FXML_CONTROLLERS_MAP.get(controllerClass) + FXML_EXTENSION);

    }

    private static URL getResourceURL(String path) {
        return AppRoot.class.getResource(path);
    }

    public static Boolean checkBirthDate(LocalDate date) {

        // If the password is empty , return false 
        if (date == null) {
            return false;
        }

        return date.isBefore(LocalDate.now());
    }

    public static void manageError(Label errorLabel, Node node,
            BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, node);
    }

    public static void manageCorrect(Label errorLabel, Node node,
            BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, node);
    }

    public static void showErrorMessage(Label errorLabel, Node node) {
        errorLabel.visibleProperty().set(true);
        node.styleProperty().setValue("-fx-background-colo: #FCE5E0");
    }

    public static void hideErrorMessage(Label errorLabel, Node node) {
        errorLabel.visibleProperty().set(false);
        node.styleProperty().setValue("");
    }

    public static void closeTheApp() {
        saveCurrentSession();
        System.exit(0);
    }

    public static void saveCurrentSession() {
        try {
            CurrentSession currentSession = AppRoot.getCurrentSession();
            AppRoot.getDbDriver().getUser(currentSession.getUser().getNickName())
                    .addSession(new Session(LocalDateTime.now(),
                            currentSession.getHits(),
                            currentSession.getFaults()));
        } catch (Exception e) {
            System.out.println("Couldn't save session info");
        }
    }

    private static boolean isDateBetweenTwoDates(LocalDate firstDate,
            LocalDate secondDate, LocalDate dateToCheck) {
        if (firstDate == null && secondDate == null) {
            return true;
        }

        if (firstDate == null && secondDate != null) {

            return dateToCheck.compareTo(secondDate) <= 0;
        } else if (firstDate != null && secondDate == null) {

            return dateToCheck.compareTo(firstDate) >= 0;
        } else {

            return (dateToCheck.compareTo(firstDate) >= 0
                    && dateToCheck.compareTo(secondDate) <= 0);
        }
    }

    public static List<Session> getSessionsFromTheRange(LocalDate firstDate,
            LocalDate secondDate) {
        return getAllUserSessions().stream()
                .filter(session -> isDateBetweenTwoDates(
                firstDate, secondDate, session.getLocalDate()))
                .collect(Collectors.toList());
    }

    public static List<Session> getAllUserSessions() {
        return AppRoot.getDbDriver().getUser(AppRoot.getCurrentSession()
                .getUser().getNickName()).getSessions();
    }

    public static void logOut() throws IOException {
        Utils.saveCurrentSession();

        Parent root = FXMLLoader.load(Utils.getFXMLName(MainController.class));

        AppRoot.getCurrentSession().setUser(null);

        Scene scene = new Scene(root);
        Stage stage = AppRoot.getMainStage();
        stage.setTitle(AppRoot.APP_NAME);
        stage.setScene(scene);
    }

    public static void shoeAbout() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("About");
        message.setHeaderText("IPC - 2022");
        message.showAndWait();
    }
    
    public static void showContact() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Contact");
        message.setHeaderText("Piotr Szylar: pszylar@etsinf.upv.es \nDmytro Bliakharchuk: dbliakh@etsinf.upv.es\nAndreas Demosthenous: ademost@etsinf.upv.es");
        message.showAndWait();
    }
    
    public static void showHelp() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Help");
        message.setHeaderText("In case of any problems contact with us by mail:\nPiotr Szylar: pszylar@etsinf.upv.es \nDmytro Bliakharchuk: dbliakh@etsinf.upv.es\nAndreas Demosthenous: ademost@etsinf.upv.es");
        message.showAndWait();
    }
    
    public static void showAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public static void showUserProfile() throws IOException {
        Parent root
                = FXMLLoader.load(Utils.getFXMLName(ProfileController.class));

        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.setScene(scene);
        profileStage.setTitle("User Profile");

        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.showAndWait();
    }
}

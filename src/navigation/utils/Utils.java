package navigation.utils;
 
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import navigation.AppRoot;
import navigation.controllers.LoginController;
import navigation.controllers.MainController;
import navigation.controllers.NavigatorController;
import navigation.controllers.ProfileController;
import navigation.controllers.SignUpController;
 
public class Utils {
        
    public static final String DEFAULT_AVATAR_NAME = "default.png"; 
    public static final String AVATARS_FOLDER_PATH = "src/navigation/resources/avatars";
    public static final String DEFAULT_AVATAR_PATH = AVATARS_FOLDER_PATH + "/" + DEFAULT_AVATAR_NAME;
        
    private static final String VIEWS_FOLDER_PATH = "views/";
    private static final String FXML_EXTENSION = ".fxml";
    private static final Map<Class<?>, String> FXML_CONTROLLERS_MAP = Map.of(MainController.class, "main",
            LoginController.class, "login",
            NavigatorController.class, "navigator",
            ProfileController.class, "profile",
            SignUpController.class, "signUp"
     );
    
    public static URL getFXMLName (Class<?> controllerClass) {
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

    public static void manageError(Label errorLabel, Node node, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, node);
    }

    public static void manageCorrect(Label errorLabel, Node node, BooleanProperty boolProp) {
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
 }


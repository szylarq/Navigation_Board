package navigation.utils;
 
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
        
    public static final String DEFAULT_AVATAR_NAME = "default.png"; 
    public static final String AVATARS_FOLDER_PATH = "src/navigation/resources/avatars";
    public static final String DEFAULT_AVATAR_PATH = AVATARS_FOLDER_PATH + "/" + DEFAULT_AVATAR_NAME;
        
    private static final String VIEWS_FOLDER_PATH = "views/";
    private static final String FXML_EXTENSION = ".fxml";
    private static final Map<Class<?>, String> FXML_CONTROLLERS_MAP = Map.of(MainController.class, "main",
            LoginController.class, "login",
            NavigatorController.class, "navigator",
            ProfileController.class, "profile",
            SignUpController.class, "signUp",
            ProblemsController.class, "problems",
            SingleProblemController.class, "singleProblem",
            ProgressController.class, "progress"
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
    
    public static void closeTheApp(){
        saveCurrentSession();
        System.exit(0);
    }
    
    public static void saveCurrentSession(){
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
    
    private static boolean isDateBetweenTwoDates(LocalDate firstDate, LocalDate secondDate, LocalDate dateToCheck){
        if(firstDate == null && secondDate == null){
            return true;
        }
        
        if(firstDate == null && secondDate != null){

            return dateToCheck.compareTo(secondDate) <= 0;
        }
        else if(firstDate != null && secondDate == null){

            return dateToCheck.compareTo(firstDate) >= 0;
        }
        else {

            return (dateToCheck.compareTo(firstDate) >= 0 && dateToCheck.compareTo(secondDate) <= 0);
        }
    }
    
    public static List<Session> getSessionsFromTheRange(LocalDate firstDate, LocalDate secondDate){
        return getAllUserSessions().stream()
                .filter(session -> isDateBetweenTwoDates(firstDate, secondDate, session.getLocalDate())).collect(Collectors.toList());
    }
    
    public static List<Session> getAllUserSessions() {
        return AppRoot.getDbDriver().getUser(AppRoot.getCurrentSession().getUser().getNickName()).getSessions();
    }
 }


package navigation.utils;

import java.net.URL;
import java.util.Map;
import javafx.scene.image.Image;
import navigation.AppRoot;
import navigation.controllers.IndexController;
import navigation.controllers.SignupController;
import navigation.controllers.LoginController;
import navigation.controllers.ProblemsController;
import navigation.controllers.ChartController;

public class Utils {
        
    public static final String DEFAULT_AVATAR_NAME = "avatar1.png"; 
    
    private static final String VIEWS_FOLDER_PATH = "views/";
    private static final String AVATARS_FOLDER_PATH = "resources/avatars/";
    private static final String FXML_EXTENSION = ".fxml";
    
    private static final Map<Class<?>, String> FXML_CONTROLLERS_MAP = Map.of(IndexController.class, "index",
            SignupController.class, "signup",
            LoginController.class, "login",
            ProblemsController.class, "problems",
            ChartController.class, "chart" 
    );
    
    public static URL getFXMLName (Class<?> controllerClass) {
        return getResourceURL(VIEWS_FOLDER_PATH 
                + FXML_CONTROLLERS_MAP.get(controllerClass) + FXML_EXTENSION);
                
    }
    
    private static URL getResourceURL(String path) {
        return AppRoot.class.getResource(path);
    }
    
    public static Image getAvatarImage(String avatarName){
        try{
            return new Image(getResourceURL(AVATARS_FOLDER_PATH + avatarName)
                    .toURI().toString());
        } catch (Exception e){
            e.printStackTrace();
            
           return null; 
        }
    }
    
    public static Image getDefaultAvatarImage(){
        return getAvatarImage(DEFAULT_AVATAR_NAME);
    }
}

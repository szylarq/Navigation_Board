package navigation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import model.Navegacion;
import model.User;
import navigation.controllers.MainController;
import navigation.utils.Utils;

public class AppRoot extends Application {
    
    private static final String APP_NAME = "Navigation Board";
    
    private static User USER;
    private static Stage STAGE;
    
    private static Navegacion dbDriver;
    
    @Override
    public void start(Stage stage) throws Exception {
        dbDriver = Navegacion.getSingletonNavegacion();
        STAGE = stage;

        Parent root = FXMLLoader.load(Utils.getFXMLName(MainController.class));
        Scene scene = new Scene(root);
        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
   
    public static Stage getMainStage(){
        return STAGE;
    }
    
    public static Navegacion getDbDriver() {
        return dbDriver;
    }
    
    public static User getCurrentUser() {
        return USER;
    }
    
    public static void setCurrentUser(User user) {
        USER = user;
    }
}
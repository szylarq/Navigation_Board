package navigation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import model.Navegacion;
import navigation.controllers.MainController;
import navigation.model.CurrentSession;
import navigation.utils.Utils;

public class AppRoot extends Application {
    
    public static final String APP_NAME = "Navigation Board";
    
    private static CurrentSession currentSession;
    private static Stage stage;
    
    private static Navegacion dbDriver;
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        dbDriver = Navegacion.getSingletonNavegacion();
        AppRoot.stage = stage;

        Parent root = FXMLLoader.load(Utils.getFXMLName(MainController.class));
        Scene scene = new Scene(root);
        stage.setTitle(APP_NAME);
        stage.setMinHeight(300);
        stage.setMinWidth(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
   
    public static Stage getMainStage(){
        return stage;
    }
    
    public static Navegacion getDbDriver() {
        return dbDriver;
    }

    public static CurrentSession getCurrentSession() {
        return currentSession;
    }

    public static void setCurrentSession(CurrentSession currentSession) {
        AppRoot.currentSession = currentSession;
    }
}
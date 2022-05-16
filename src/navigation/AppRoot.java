package navigation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import navigation.controllers.IndexController;
import navigation.utils.Utils;

/**
 *
 * @author jose
 */
public class AppRoot extends Application {
    
    private static final String APP_NAME = "Navigation Board";
    private static Stage STAGE;
    
    @Override
    public void start(Stage stage) throws Exception {
        STAGE = stage;

        Parent root = FXMLLoader.load(Utils.getFXMLName(IndexController.class));

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
    
}

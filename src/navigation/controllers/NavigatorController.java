package navigation.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Problem;
import model.User;
import navigation.AppRoot;
import navigation.model.ObservableProblem;
import navigation.model.Poi;
import navigation.utils.Utils;
import static navigation.utils.Utils.NAVIGATION_RESOURCES_PATH;

/**
 *
 * @author jsoler
 */
public class NavigatorController implements Initializable {

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;

    @FXML
    private ListView<Poi> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;

    @FXML
    private ImageView imgView;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    private User user;
    @FXML
    private VBox mainMenuId;
    @FXML
    private VBox centerContainerId;
    @FXML
    private Button lineBtnId;
    @FXML
    private Button arcBtnId;
    @FXML
    private Button textBtnId;
    @FXML
    private Button endPointBtnId;
    @FXML
    private Button measureAngleBtnId;
    @FXML
    private Button deleteMarkBtnId;
    @FXML
    private Button onCleanChartBtnId;
    @FXML
    private ChoiceBox<?> strokeChooserId;
    @FXML
    private ColorPicker colorChooserId;

    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }

    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    private void onCancelClicked(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }

    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
    }

    private void onProfileClicked() throws IOException {
        Utils.showUserProfile();
    }

    private void onAboutClicked(ActionEvent event) {
        Utils.shoeAbout();
    }

    private void onLogOutClicked(ActionEvent event) throws IOException {
        Utils.logOut();
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
    void listClicked(MouseEvent event) {
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    private void initData() {
        hm.put("2F", new Poi("2F", "Edificion del DSIC", 325, 225));
        hm.put("Agora", new Poi("Agora", "Agora", 600, 360));
        map_listview.getItems().add(hm.get("2F"));
        map_listview.getItems().add(hm.get("Agora"));
    }

    void initFronendSetings() throws Exception {
        FileInputStream inputAngle = new FileInputStream(Utils.ANGLE_ICON_PATH);
        Image imageAngle = new Image(inputAngle);
        ImageView imageViewAngle = new ImageView(imageAngle);
        
        FileInputStream inputArc = new FileInputStream(Utils.ARC_ICON_PATH);
        Image imageArc = new Image(inputArc);
        ImageView imageViewArc = new ImageView(imageArc);
        
        FileInputStream inputCrossMarc = new FileInputStream(Utils.CROSS_MARC_ICON_PATH);
        Image imageCrossMarc = new Image(inputCrossMarc);
        ImageView imageViewCrossMarc = new ImageView(imageCrossMarc);
        
        FileInputStream inputDelete = new FileInputStream(Utils.DELETE_ICON_PATH);
        Image imageDelete = new Image(inputDelete);
        ImageView imageViewDelete = new ImageView(imageDelete);

        FileInputStream startPoint = new FileInputStream(Utils.START_POINT_ICON_PATH);
        Image imageStartPoint = new Image(startPoint);
        ImageView imageViewStartPoint = new ImageView(imageStartPoint);
        
        FileInputStream inputTextIcon = new FileInputStream(Utils.TEXT_ICON_PATH);
        Image imageText = new Image(inputTextIcon);
        ImageView imageViewText = new ImageView(imageText);
        
        FileInputStream inputLine = new FileInputStream(Utils.LINE_ICON_PATH);
        Image imageLine = new Image(inputLine);
        ImageView imageViewLine = new ImageView(imageLine);
        

        imageViewAngle.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewAngle.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewArc.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewArc.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewCrossMarc.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewCrossMarc.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewDelete.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewDelete.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewStartPoint.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewStartPoint.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewText.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewText.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);
        imageViewLine.setFitHeight(Utils.DEFAULT_MENU_HEIGHT);
        imageViewLine.setFitWidth(Utils.DEFAULT_MENU_HEIGHT);

        lineBtnId.setText("");
        lineBtnId.setGraphic(imageViewLine);

        arcBtnId.setText("");
        arcBtnId.setGraphic(imageViewArc);
        
        textBtnId.setText("");
        textBtnId.setGraphic(imageViewText);
        
        endPointBtnId.setText("");
        endPointBtnId.setGraphic(imageViewStartPoint);
        
        measureAngleBtnId.setText("");
        measureAngleBtnId.setGraphic(imageViewAngle);
        
//        deleteMarkBtnId.setText("");
//        deleteMarkBtnId.setGraphic(imageViewDelete);
        deleteMarkBtnId.setText("Delete Mark");
//        onCleanChartBtnId.setText("");
//        onCleanChartBtnId.setGraphic(imageViewCrossMarc);
        onCleanChartBtnId.setText("Clean Chart");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initFronendSetings();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        // TODO
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();

        user = AppRoot.getCurrentSession().getUser();

        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal)
                -> zoom((Double) newVal));

        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);

    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: "
                + (int) event.getSceneY() + "\n" + "         X: "
                + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    @FXML
    private void onLineBtnClick(ActionEvent event) {
    }

    @FXML
    private void onArcBtnClick(ActionEvent event) {
    }

    @FXML
    private void onTextBtnClick(ActionEvent event) {
    }

    @FXML
    private void onEndPointBtnClick(ActionEvent event) {
    }

    @FXML
    private void onMeasureAngleClick(ActionEvent event) {
    }

    @FXML
    private void onDeleteMarkBtnClick(ActionEvent event) {
    }

    @FXML
    private void onCleanChartClick(ActionEvent event) {
    }
}

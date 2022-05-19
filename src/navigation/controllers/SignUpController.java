package navigation.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import navigation.AppRoot;
import navigation.utils.Utils;

public class SignUpController implements Initializable {

    @FXML
    private TextField username_field, email_field;

    @FXML
    private PasswordField pass_field, repaeatPass_field;

    @FXML
    private DatePicker date_field;

    @FXML
    private Label emailerror_label, passworderror_label, repeatedpassworderror_label, birthdateerror_label, usernameerror_label, avatarerror_label, avatar_label;

    @FXML
    private Button accept_button, cancel_button;

    private StringProperty username, email, password, birthdate, avatarPath;
    private BooleanProperty validEmail, validPassword, equalPasswords, validBirthDate, validUsername, validAvatar;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

//    public void initSignUpWindow(Stage stage) {
//        primaryStage = stage;
//        primaryScene = primaryStage.getScene();
//        primaryTitle = primaryStage.getTitle();
//    }

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }

    @FXML
    private void handleOnActionButtonAccept(ActionEvent event) {
        try {
            AppRoot.getDbDriver().registerUser(username.getValue(), email.getValue(), password.getValue(),
                    new Image(new FileInputStream(new File(avatarPath.getValue()))), date_field.getValue());

        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText(ex.getMessage());
            error.showAndWait();
        }
        if (AppRoot.getDbDriver().exitsNickName(username.getValue())) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("User Registered");
            confirmation.setContentText(" User " + username.getValue() + " registered succesfully!");

            primaryStage.setScene(primaryScene);
            primaryStage.setTitle(primaryTitle);
            confirmation.showAndWait();
        }
    }

    @FXML
    private void handleOnActionButtonSelectAvatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Avatar File");
        fileChooser.setInitialDirectory(new File(Utils.AVATARS_FOLDER_PATH));
        File avatarFile = null;
        avatarFile = fileChooser.showOpenDialog(primaryStage);
        
        if(avatarFile != null) {
            setAvatar(avatarFile);
        }
    }

    private void setAvatar(File avatarFile) {
        
        if(!(avatarFile.getAbsolutePath().endsWith(".png") || avatarFile.getAbsolutePath().endsWith(".jpg"))){
            Utils.manageError(avatarerror_label, avatar_label, validAvatar);
            return;
        }
        try {
            Image avatar = new Image(new FileInputStream(avatarFile));
            ImageView view = new ImageView(avatar);
            view.setFitHeight(80);
            view.setFitWidth(75);
            view.setPreserveRatio(true);
            avatar_label.setGraphic(view);
        } catch (Exception ex) {
            Utils.manageError(avatarerror_label, avatar_label, validAvatar);
        }
        avatarPath.setValue(avatarFile.getPath());
        Utils.manageCorrect(avatarerror_label, avatar_label, validAvatar);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        username = new SimpleStringProperty();
        username.bind(username_field.textProperty());

        email = new SimpleStringProperty();
        email.bind(email_field.textProperty());

        password = new SimpleStringProperty();
        password.bind(pass_field.textProperty());

        birthdate = new SimpleStringProperty();
        birthdate.bind(date_field.valueProperty().asString());

        avatarPath = new SimpleStringProperty();
        avatarPath.setValue(Utils.DEFAULT_AVATAR_PATH);

        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE);

        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.FALSE);

        equalPasswords = new SimpleBooleanProperty();
        equalPasswords.setValue(Boolean.FALSE);

        validBirthDate = new SimpleBooleanProperty();
        validBirthDate.setValue(Boolean.FALSE);

        validUsername = new SimpleBooleanProperty();
        validUsername.setValue(Boolean.FALSE);

        validAvatar = new SimpleBooleanProperty();
        validAvatar.setValue(Boolean.FALSE);

        accept_button.disableProperty().bind(validPassword.not().or(validEmail.not()).or(equalPasswords.not()).or(validBirthDate.not()).or(validUsername.not()));

        //Check values when user leaves email field
        email_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkEmail();
            }
        });

        //Check values when user leaves pass field
        pass_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkPassword();
            }
        });

        repaeatPass_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkEquals();
            }
        });

        date_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkBirthDate();
            }
        });

        username_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkUsername();
            }
        });

        setAvatar(new File(Utils.DEFAULT_AVATAR_PATH));

    }

    private void checkEquals() {
        if (pass_field.textProperty().getValueSafe().compareTo(
                repaeatPass_field.textProperty().getValueSafe()) != 0) {
            Utils.showErrorMessage(repeatedpassworderror_label, repaeatPass_field);
            equalPasswords.setValue(Boolean.FALSE);
            pass_field.textProperty().setValue("");
            repaeatPass_field.textProperty().setValue("");
            pass_field.requestFocus();
        } else {
            Utils.manageCorrect(repeatedpassworderror_label, repaeatPass_field, equalPasswords);
        }
    }

    private void checkPassword() {
        if (!User.checkPassword(password.getValueSafe())) //Incorrect email
        {
            Utils.manageError(passworderror_label, pass_field, validPassword);
        } else {
            Utils.manageCorrect(passworderror_label, pass_field, validPassword);
        }
    }

    private void checkEmail() {
        if (!User.checkEmail(email.getValueSafe())) //Incorrect email
        //Incorrect email
        {
            Utils.manageError(emailerror_label, email_field, validEmail);
        } else {
            Utils.manageCorrect(emailerror_label, email_field, validEmail);
        }
    }

    private void checkBirthDate() {
        if (!Utils.checkBirthDate(date_field.getValue())) //Incorrect email
        {
            Utils.manageError(birthdateerror_label, date_field, validBirthDate);
        } else {
            Utils.manageCorrect(birthdateerror_label, date_field, validBirthDate);
        }
    }

    private void checkUsername() {

        if (!User.checkNickName(username.getValueSafe())) //Incorrect email
        {
            Utils.manageError(usernameerror_label, username_field, validUsername);
        } else {
            Utils.manageCorrect(usernameerror_label, username_field, validUsername);
        }
    }
}

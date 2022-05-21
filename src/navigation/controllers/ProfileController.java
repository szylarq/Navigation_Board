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

/**
 * FXML Controller class
 *
 * @author andre
 */
public class ProfileController implements Initializable {
    
    @FXML
    private TextField username_field, email_field;
    
    @FXML
    private PasswordField pass_field, repaeatPass_field;
    
    @FXML
    private DatePicker date_field;
    
    @FXML
    Button apply_button, cancel_button;
    
    private User user;
    
    private StringProperty username, email, password1, password2, birthdate, 
            avatarPath;
    private BooleanProperty validEmail, validPassword, equalPasswords, 
            validBirthDate, validUsername, validAvatar;
    
    @FXML
    private Label emailerror_label, passworderror_label, 
            repeatedpassworderror_label, birthdateerror_label, 
            usernameerror_label, avatarerror_label, avatar_label;
    
    private Stage primaryStage, currentStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    @FXML
    private void handleOnActionButtonSelectAvatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Avatar File");
        fileChooser.setInitialDirectory(new File(Utils.AVATARS_FOLDER_PATH));
        File avatarFile = null;
        avatarFile = fileChooser.showOpenDialog(currentStage);
        
        if(avatarFile != null) {
            setAvatar(avatarFile);
        }
    }
    
    private void setAvatar(File avatarFile) {
        
        if(!(avatarFile.getAbsolutePath().endsWith(".png") 
                || avatarFile.getAbsolutePath().endsWith(".jpg"))){
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
    
    @FXML
    private void handleOnActionButtonApply(ActionEvent event) {
        try {
            user.setEmail(email.getValue());
            user.setPassword(password1.getValue());
            user.setBirthdate(date_field.getValue());
            
            if(avatarPath.getValue()!=null) {
                user.setAvatar(new Image(new FileInputStream(
                        new File(avatarPath.getValue()))));  
            }
        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("ERROR");
            error.setContentText(ex.getMessage());
            System.out.println(ex.getStackTrace());
            error.showAndWait();
            
            currentStage.close();
            return;
        }
        
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(" Changes applied");
        info.setHeaderText("SUCCESS");
        info.setContentText(" User profile saved ");
        
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        Stage stage = (Stage) apply_button.getScene().getWindow();
        stage.close();
        
        info.showAndWait(); 
    }
    
    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        primaryStage = AppRoot.getMainStage();
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        user = AppRoot.getCurrentSession().getUser();
        
        username = new SimpleStringProperty();
        username.bind(username_field.textProperty());
        
        email = new SimpleStringProperty();
        email.bind(email_field.textProperty());
        
        password1 = new SimpleStringProperty();
        password1.bind(pass_field.textProperty());
        
        password2 = new SimpleStringProperty();
        password2.bind(repaeatPass_field.textProperty());
        
        birthdate = new SimpleStringProperty();
        birthdate.bind(date_field.valueProperty().asString());
        
        avatarPath = new SimpleStringProperty();
        avatarPath.setValue(user.getAvatar().getUrl());
        
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.TRUE);
        
        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.TRUE);
        
        equalPasswords = new SimpleBooleanProperty();
        equalPasswords.setValue(Boolean.TRUE);
        
        validBirthDate = new SimpleBooleanProperty();
        validBirthDate.setValue(Boolean.TRUE);
        
        validUsername = new SimpleBooleanProperty();
        validUsername.setValue(Boolean.TRUE);
        
        validAvatar = new SimpleBooleanProperty();
        validAvatar.setValue(Boolean.TRUE);
        
        apply_button.disableProperty().bind(validPassword.not()
                .or(validEmail.not()).or(equalPasswords.not())
                .or(validBirthDate.not())
                .or(validUsername.not()));

        //Check values when user leaves email field
        email_field.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkEmail();
            }
        });

        //Check values when user leaves pass field
        pass_field.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkPassword();
            }
        });
        
        repaeatPass_field.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkEquals();
            }
        });
        
        date_field.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkBirthDate();
            }
        });
        
        username_field.setText(user.getNickName());
        email_field.setText(user.getEmail());
        pass_field.setText(user.getPassword());
        repaeatPass_field.setText(user.getPassword());
        date_field.setValue(user.getBirthdate());

        ImageView view = new ImageView(user.getAvatar());
        view.setFitHeight(80);
        view.setFitWidth(75);
        view.setPreserveRatio(true);
        avatar_label.setGraphic(view);
    }
    
    @FXML
    private void onExitClicked(ActionEvent event) {
        Utils.closeTheApp();
    }

    @FXML
    private void onAboutClicked(ActionEvent event) {
        Utils.shoeAbout();
    }

    @FXML
    private void onContactClick(ActionEvent event) {
        Utils.showContact();
    }

    @FXML
    private void onHelpClick(ActionEvent event) {
        Utils.showHelp();
    }
    
    private void checkEquals() {
        if (pass_field.textProperty().getValueSafe().compareTo(
                repaeatPass_field.textProperty().getValueSafe()) != 0) {
            Utils.showErrorMessage(repeatedpassworderror_label, 
                    repaeatPass_field);
            equalPasswords.setValue(Boolean.FALSE);
            pass_field.textProperty().setValue("");
            repaeatPass_field.textProperty().setValue("");
            pass_field.requestFocus();
        } else {
            Utils.manageCorrect(repeatedpassworderror_label, repaeatPass_field, 
                    equalPasswords);
        }
    }
    
    private void checkPassword() {
        if (!User.checkPassword(password1.getValueSafe())) //Incorrect email
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
            Utils.manageCorrect(birthdateerror_label, date_field, 
                    validBirthDate);
        }
    } 
}

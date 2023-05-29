package view;

import controller.JsonController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import controller.LoginController;
import view.Commands.LoginAndRegisterCommands;
import view.Messages.RegisterMessages;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    public TextField email;
    public TextField nickname;
    public TextField slogan;
    public CheckBox check;
    public TextField passwordShow;
    public PasswordField passwordHide;
    public Button randomSloganButton;

    //Labels error handeleng
    public Label usernameError;
    public Label passwordError;
    public Label emailError;
    public Label nicknameError;
    public Label sloganError;
    public VBox vBoxErrorHandling;
    //    public CheckBox check;
    @FXML
    private TextField username;
    private Pane pane;

    @FXML
    private Label label;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL url = LoginMenu.class.getResource("/fxml/loginMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        this.pane = pane;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void showAndHidePassword() {
        String text;
        if (passwordHide.isVisible()) {
            passwordShow.setVisible(true);
            text = passwordHide.getText();
            passwordShow.setText(text);
            passwordHide.setVisible(false);
        } else {
            text = passwordShow.getText();
            passwordShow.setVisible(false);
            passwordHide.setVisible(true);
            passwordHide.setText(text);
        }
    }

    @FXML
    public void initialize() {
        ListenerToUsernameField();
        ListenerToPassword();
    }

    private void checkPasswordError(Label label, String password) {
        RegisterMessages messages = LoginController.checkPassword(password);
        String text = null;
        switch (messages) {
            case EMPTY_FIELD:
                text = " You have empty Field";
                break;
            case WEAK_PASSWORD_FOR_LOWERCASE:
                text = "You should use lowercase characters in your password!";
                break;
            case WEAK_PASSWORD_FOR_UPPERCASE:
                text = "You should use uppercase characters in your password!";
                break;
            case WEAK_PASSWORD_FOR_LENGTH:
                text = "Length of your password must be more than five!";
                break;
            case WEAK_PASSWORD_FOR_NUMBER:
                text = "You should use number characters in your password!";
                break;
            case WEAK_PASSWORD_FOR_NOTHING_CHARS_EXCEPT_ALPHABETICAL:
                text = "You should use characters except alphabetical!";
                break;
            case SUCCESS:
                text = "Fill register form";
                break;
        }
        label.setText(text);
    }

    private void ListenerToPassword() {
        passwordShow.textProperty().addListener((observable, oldText, newText) -> {
            checkPasswordError(label, newText);
        });
        passwordHide.textProperty().addListener((observable, oldText, newText) -> {
            checkPasswordError(label, newText);
        });

    }

    private void ListenerToUsernameField() {
        username.textProperty().addListener((observable, oldText, newText) -> {
            usernameError(label, "Fill register form");
        });
    }

    public void checkingSlogan(MouseEvent mouseEvent) {
        if (slogan.isVisible()) slogan.setText("empty");
        else slogan.setText("");
        slogan.setVisible(!slogan.isVisible());
        randomSloganButton.setVisible(!randomSloganButton.isVisible());
    }

    public void randomPassword(MouseEvent mouseEvent) {
        if (passwordHide.isVisible()) {
            passwordHide.setText(LoginController.generateRandomPassword());
        } else {
            passwordShow.setText(LoginController.generateRandomPassword());
        }
    }

    public void randomSlogan(MouseEvent mouseEvent) {
        slogan.setText(LoginController.getRandomSlogan());
    }

    public void sloganCheck() {
        if (slogan.getText().equals("")) sloganError.setText("Empty field");
        else sloganError.setText("");
    }
    public void submit(MouseEvent mouseEvent) {
        usernameError(usernameError,"");
        checkPasswordError(passwordError,"");
        checkEmail();
        nicknameError();
        sloganCheck();
        vBoxErrorHandling.setVisible(true);

    }
    public void usernameError(Label label,String textSuccess){
        String newText =  username.getText();
        RegisterMessages messages = LoginController.checkUsername(newText);
        switch (messages){
            case EMPTY_FIELD:
                label.setText("You have empty field");
                break;
            case USERNAME_REPEATED :
                label.setText("Your username is repeated but username " +
                        LoginController.makeUserNameForUser(newText) +
                        " is exist now!");
                break;
            case INCORRECT_FORM_OF_USERNAME:
                label.setText("Invalid form of username!");
                break;
            case SUCCESS:
                label.setText(textSuccess);
        }
    }
    public void nicknameError(){
        String newText = nickname.getText();
        if(newText.equals("")) nicknameError.setText("Empty field");
        else nicknameError.setText("");
    }
    public void checkEmail(){
        String newText =  email.getText();
        RegisterMessages messages = LoginController.checkEmail(newText);
        switch (messages){
            case EMPTY_FIELD :
                emailError.setText("You have empty field");
                break;
            case REPEATED_EMAIL:
                emailError.setText("Your email is repeated");
                break;
            case INVALID_FORM_EMAIL:
                emailError.setText("Your form if email is invalid!");
                break;
            case SUCCESS:
                emailError.setText("");
                break;
        }
    }
}
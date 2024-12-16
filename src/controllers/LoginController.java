package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DBConnect;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private ChoiceBox<String> roleChoiceBox;

    private DBConnect dbConnect;

    public LoginController() {
        dbConnect = new DBConnect();
    }

    @FXML
    public void initialize() {
        roleChoiceBox.getItems().addAll("Customer", "Mechanic");
        roleChoiceBox.setValue("Select Role");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String selectedRole = roleChoiceBox.getValue();

        if (dbConnect.validateUser(username, password, selectedRole)) {
            try {
                Parent root;
                if ("Mechanic".equals(selectedRole)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mechanichome.fxml"));
                    root = loader.load();
                    MechanicHomeController controller = loader.getController();
                    controller.setMechanicName(username);
                } else {
                    root = FXMLLoader.load(getClass().getResource("/views/customerhome.fxml"));
                }
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username, password, or role.");
            alert.showAndWait();
        }
    }
}

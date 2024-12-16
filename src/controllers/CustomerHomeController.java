package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerHomeController {
    @FXML
    private Button bookServiceButton;

    @FXML
    private Button serviceHistoryButton;

    @FXML
    private Button logoutButton;

    /**
     * Handles the "Book Service" button click event.
     */
    @FXML
    private void handleBookService() {
        try {
            // Load the booking page (customerview.fxml should exist)
            Parent root = FXMLLoader.load(getClass().getResource("/views/customerview.fxml"));
            Stage stage = (Stage) bookServiceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Service History" button click event.
     */
    @FXML
    private void handleServiceHistory() {
        try {
            // Load the service history page (servicehistory.fxml should exist)
            Parent root = FXMLLoader.load(getClass().getResource("/views/servicehist.fxml"));
            Stage stage = (Stage) serviceHistoryButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the login page (login.fxml should exist)
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

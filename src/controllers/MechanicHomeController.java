package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.DBConnect;
import models.Service;
import views.SessionManager;

public class MechanicHomeController {
    @FXML
    private Button editServicesButton;

    @FXML
    private Button serviceScheduleButton;

    @FXML
    private Button addServicesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button viewAllServicesButton;

    public void setMechanicName(String mechanicName) {
        SessionManager.setMechanicName(mechanicName);
    }

    @FXML
    private void handleEditServices() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mechanichistory.fxml"));
            Parent root = loader.load();
            MechanicHistoryController controller = loader.getController();
            controller.setMechanicName(SessionManager.getMechanicName());

            Stage stage = (Stage) editServicesButton.getScene().getWindow();
            stage.setScene(new Scene(root));

            controller.setOnServiceSelected(this::editService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleServiceSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mechanichistory.fxml"));
            Parent root = loader.load();
            MechanicHistoryController controller = loader.getController();
            controller.setMechanicName(SessionManager.getMechanicName());

            Stage stage = (Stage) serviceScheduleButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddServices() {
        String mechanicName = SessionManager.getMechanicName();
        if (mechanicName == null || mechanicName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mechanic name is not set!");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Service");
        dialog.setHeaderText("Enter the service details:");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField serviceField = new TextField();
        TextField serviceDoneField = new TextField();
        TextField timeTakenField = new TextField();
        TextField priceField = new TextField();
        grid.add(new Label("Service:"), 0, 0);
        grid.add(serviceField, 1, 0);
        grid.add(new Label("Service Done:"), 0, 1);
        grid.add(serviceDoneField, 1, 1);
        grid.add(new Label("Time Taken:"), 0, 2);
        grid.add(timeTakenField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButtonType) {
            String service = serviceField.getText();
            String serviceDone = serviceDoneField.getText();
            String timeTaken = timeTakenField.getText();
            double price = Double.parseDouble(priceField.getText());

            try (Connection connection = new DBConnect().connect()) {
                String sql = "INSERT INTO Service (service, servicedone, timetaken, price, mechanicname) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, service);
                pstmt.setString(2, serviceDone);
                pstmt.setString(3, timeTaken);
                pstmt.setDouble(4, price);
                pstmt.setString(5, mechanicName);
                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Service added successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleViewAllServices() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/mechanicallserv.fxml"));
            Stage stage = (Stage) viewAllServicesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editService(Service service) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Service");
        dialog.setHeaderText("Edit the service details:");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField serviceField = new TextField(service.getService());
        TextField serviceDoneField = new TextField(service.getServiceDone());
        TextField timeTakenField = new TextField(service.getTimeTaken());
        TextField priceField = new TextField(String.valueOf(service.getPrice()));
        grid.add(new Label("Service:"), 0, 0);
        grid.add(serviceField, 1, 0);
        grid.add(new Label("Service Done:"), 0, 1);
        grid.add(serviceDoneField, 1, 1);
        grid.add(new Label("Time Taken:"), 0, 2);
        grid.add(timeTakenField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButtonType) {
            service.setService(serviceField.getText());
            service.setServiceDone(serviceDoneField.getText());
            service.setTimeTaken(timeTakenField.getText());
            service.setPrice(Double.parseDouble(priceField.getText()));

            try (Connection connection = new DBConnect().connect()) {
                String sql = "UPDATE Service SET service = ?, servicedone = ?, timetaken = ?, price = ? WHERE mechanicname = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, service.getService());
                pstmt.setString(2, service.getServiceDone());
                pstmt.setString(3, service.getTimeTaken());
                pstmt.setDouble(4, service.getPrice());
                pstmt.setString(5, SessionManager.getMechanicName());
                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Service updated successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

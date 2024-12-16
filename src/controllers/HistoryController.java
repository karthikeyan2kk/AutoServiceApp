package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.DBConnect;
import models.Service;

public class HistoryController {
    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Service, Integer> serviceidColumn;
    @FXML
    private TableColumn<Service, String> carModelColumn;
    @FXML
    private TableColumn<Service, String> serviceColumn;
    @FXML
    private TableColumn<Service, Double> priceColumn;
    @FXML
    private TableColumn<Service, String> dateColumn;
    @FXML
    private TableColumn<Service, String> mechanicNameColumn;

    private DBConnect conn;

    public HistoryController() {
        conn = new DBConnect();
    }

    @FXML
    public void initialize() {
        serviceidColumn.setCellValueFactory(new PropertyValueFactory<>("serviceid"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("carmodel"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        mechanicNameColumn.setCellValueFactory(new PropertyValueFactory<>("mechnicname"));

        tableView.setItems(getServiceHist());
    }

    public ObservableList<Service> getServiceHist() {
        ObservableList<Service> services = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT * FROM Servicehist");
            while (rs.next()) {
                services.add(new Service(
                        rs.getInt("serviceid"),
                        rs.getString("service"),
                        rs.getDouble("price"),
                        rs.getString("date"),
                        rs.getString("mechnicname"),
                        rs.getString("carmodel")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @FXML
    private void handleDeleteService() {
        Service selectedService = tableView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected service?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try (Connection connection = conn.connect()) {
                    String sql = "DELETE FROM Servicehist WHERE serviceid = ?";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setInt(1, selectedService.getServiceid());
                    pstmt.executeUpdate();

                    tableView.getItems().remove(selectedService);
                    Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    deleteAlert.setTitle("Delete Successful");
                    deleteAlert.setHeaderText(null);
                    deleteAlert.setContentText("Service deleted successfully!");
                    deleteAlert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a service to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/customerhome.fxml"));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

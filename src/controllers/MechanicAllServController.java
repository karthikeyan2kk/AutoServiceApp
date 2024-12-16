package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.DBConnect;
import models.Service;

public class MechanicAllServController {
    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Service, String> serviceColumn;
    @FXML
    private TableColumn<Service, String> serviceDoneColumn;
    @FXML
    private TableColumn<Service, String> mechanicNameColumn;
    @FXML
    private TableColumn<Service, String> timeTakenColumn;
    @FXML
    private TableColumn<Service, Double> priceColumn;

    private DBConnect conn;

    public MechanicAllServController() {
        conn = new DBConnect();
    }

    @FXML
    public void initialize() {
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        serviceDoneColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDone"));
        mechanicNameColumn.setCellValueFactory(new PropertyValueFactory<>("mechanicName"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.setItems(getServices());
    }

    public ObservableList<Service> getServices() {
        ObservableList<Service> services = FXCollections.observableArrayList();
        try (Connection connection = conn.connect()) {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Service");
            while (rs.next()) {
                services.add(new Service(
                        rs.getString("service"),
                        rs.getString("servicedone"),
                        rs.getString("mechanicname"),
                        rs.getString("timetaken"),
                        rs.getDouble("price")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @FXML
    private void handleBackToHomepage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/mechanichome.fxml"));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

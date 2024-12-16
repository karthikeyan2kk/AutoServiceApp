package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.DBConnect;
import models.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

public class RetrieveController {
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

    public RetrieveController() {
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
        try {
            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT * FROM Service");
            while (rs.next()) {
                services.add(new Service(
                        rs.getString("service"),
                        rs.getString("serviceDone"),
                        rs.getString("mechanicName"),
                        rs.getString("timeTaken"),
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
    private void handleBook() {
        Service selectedService = tableView.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog(); 
        dialog.setTitle("Car Model"); 
        dialog.setHeaderText("Enter Car Make and Model"); 
        dialog.setContentText("Car Make and Model:"); 
        Optional<String> result = dialog.showAndWait(); 
        if (result.isPresent()) { 
            String carModel = result.get();
        if (selectedService != null) {
            try (Connection connection = conn.connect()) {
                String sql = "INSERT INTO Servicehist (service, price, date, mechnicname, carmodel) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, selectedService.getService());
                pstmt.setString(4, selectedService.getMechanicName());
                pstmt.setDouble(2, selectedService.getPrice());
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                pstmt.setString(3, timeStamp);
                pstmt.setString(5, carModel);
                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Service booked successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a service to book.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleViewServiceHist() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/servicehist.fxml"));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
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

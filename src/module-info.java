module test{
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	
	exports models; 
	exports controllers;
	exports views;
	
	opens models to javafx.graphics, javafx.fxml, javafx.base;
	opens controllers to javafx.graphics, javafx.fxml, javafx.base;
	opens views to javafx.graphics, javafx.fxml, javafx.base;
}
AutoServiceApp
Overview
AutoServiceApp is a Java-based desktop application designed to streamline automotive service operations.
Built using JavaFX for the user interface and MySQL for the database, this application provides distinct functionalities for customers and mechanics, ensuring efficient service management.

Features
•	User Authentication: Secure login system for customers and mechanics.

•	Customer Dashboard:
o	View service history.
o	Schedule new services.

•	Mechanic Dashboard:
o	View all assigned services.
o	Access service history.

•	Database Integration: Seamless interaction with a MySQL database for real-time data management.

Project Structure
1. Source Code (src/)
•	Controllers:
o	CustomerHomeController.java: Manages customer dashboard operations.
o	HistoryController.java: Handles service history.
o	LoginController.java: Facilitates user login.
o	MechanicAllServController.java: Displays all services for mechanics.
o	MechanicHistoryController.java: Manages mechanic service history.
o	MechanicHomeController.java: Main interface for mechanics.
o	RetrieveController.java: Handles data retrieval from the database.

•	Models:
o	DBConnect.java: Establishes and manages database connections.
o	Main.java: Entry point of the application.
o	Service.java: Represents service-related data.

•	Views:
o	FXML Files: Define UI layouts (e.g., Login.fxml, customerhome.fxml).
o	CSS: Defines application styles (styles.css).
3. Resources (images/)
Contains graphical assets for the user interface (e.g., Autoservbackground.png).
4. Libraries (libs/)
Includes external dependencies, such as:
•	mysql-connector-j-9.1.0.jar: JDBC driver for MySQL database connectivity.
5. Build and Configuration
•	.classpath, .project: Eclipse IDE configuration files.
•	build.fxbuild: JavaFX build configuration.

Prerequisites
1.	Java Development Kit (JDK): Version 11 or higher.
2.	Eclipse IDE: With JavaFX plugin installed.
3.	MySQL Database:
o	Ensure the database is running and configured as per the DBConnect.java settings.

Usage
•	Login: Use the provided credentials to log in as a customer or mechanic.
username:user
password:password
user type:Customer

username:Karthik
password:abcd
user typeMechanic

Dependencies
•	JavaFX
•	MySQL Connector/J






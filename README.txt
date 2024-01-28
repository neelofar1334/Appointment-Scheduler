Title:
Scheduling Application

Purpose:
This Scheduling Application is designed to manage and schedule appointments.
It allows users to add, update, and delete customer records and appointments. 
It features timezone-aware scheduling and reporting functionalities.

Author: Neelofar Karimi

Contact Information: nkarim1@wgu.edu

Application Version: 1.0.0

Date: 1/27/2024

Development Environment:

IDE: IntelliJ IDEA Community 2023.2.2
JDK Version: Java SE 17.0.1
JavaFX Version: JavaFX SDK 17.0.0
MySQL Connector Version: mysql-connector-java 8.0.27

Directions for Running the Program:
Import the project into IntelliJ IDEA.
Ensure JavaFX SDK is properly configured in the project settings.
Add the MySQL Connector JAR file to the project's library.
Configure the database settings.
Run the Main class to start the application.
Log in using valid credentials (username: test, password: test).

Program "Quirks":
-When updating an appointment, you will need to remove the T between date and time 
and add the seconds in HH:mm:ss
-In the tableview for appointments, you can filter appointments via radio buttons. 
To reset the filter and see all appointments, you will need to go to another screen 
and come back to the tableview.
-You can update current customers and appointments in the "update" tab from the "add" button, 
but it will not populate the existing details unless you click on a customer/appointment 
and click the "update" button.

Additional Report:
The additional report allows users to view the total number of appointments 
for each customer. It provides insights into customer engagement 
by displaying how many appointments each customer has had. 
This report is useful for analyzing customer activity and identifying client usage of services.

MySQL Connector Version:
MySQL Connector/J 8.0\mysql-connector-java-8.0.25.jar
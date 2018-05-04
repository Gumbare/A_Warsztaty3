package pl.coderslab.warsztaty3.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

	// creating tables in previously prepared 'school' data base
	private static final String query1 = "CREATE TABLE UserGroup(id int(11) AUTO_INCREMENT,\n"
			+ "                   name varchar(255),\n" + "                   PRIMARY KEY(id));";
	private static final String query2 = "CREATE TABLE User(id bigint(20) AUTO_INCREMENT,\n"
			+ "                   username varchar(255),\n" + "                   email varchar(255) UNIQUE,\n"
			+ "                   password varchar(245),\n" + "                   UserGroup_id int(11),\n"
			+ "                   PRIMARY KEY(id),\n"
			+ "                   FOREIGN KEY(UserGroup_id) REFERENCES UserGroup(id) ON DELETE CASCADE);";
	private static final String query3 = "CREATE TABLE Exercise(id int(11) AUTO_INCREMENT,\n"
			+ "                      title varchar(255),\n" + "                      description TEXT,\n"
			+ "                      PRIMARY KEY(id));";
	private static final String query4 = "CREATE TABLE Solution(id int(11) AUTO_INCREMENT,\n"
			+ "                      created DATETIME,\n" + "                      updated DATETIME,\n"
			+ "                      description TEXT,\n" + "                      Exercise_id int(11),\n"
			+ "                      User_id bigint(20),\n" + "                      PRIMARY KEY(id),\n"
			+ "                      FOREIGN KEY(Exercise_id) REFERENCES Exercise(id) ON DELETE CASCADE,\n"
			+ "                      FOREIGN KEY(User_id) REFERENCES User(id) ON DELETE CASCADE);";

	public static void main(String[] args) {
		// creating Connection
		try (Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/school?useSSL=false", "root", "coderslab")) {

			Statement stm = conn.createStatement();
			stm.executeUpdate(query1);
			stm.executeUpdate(query2);
			stm.executeUpdate(query3);
			stm.executeUpdate(query4);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package me.huaiyu.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLWrapper {
	Connection dbConnection;

	/*
	 * final String host; final String dbName; final String username; final
	 * String password;
	 */

	public MySQLWrapper(String host, String dbName, String username, String password) {
		/*
		 * this.host = host; this.dbName = dbName; this.username = username;
		 * this.password = password;
		 */

		dbConnection = getNewConnection(host, dbName, username, password);
	}

	public MySQLWrapper() {
		this("huaiyu.me", "schedulevu", "guest", "devel");
	}

	public void addClass(String id, String name, String description, String grading, String hours, String prereq,
			String coreq) {
		try {
			Statement statement = dbConnection.createStatement();
			if (prereq.equals("")) {
				prereq = "NULL";
			} else {
				prereq = "\"prereq\"";
			}
			if (coreq.equals("")) {
				coreq = "NULL";
			} else {
				coreq = "\"coreq\"";
			}
			String query = String.format(
					"INSERT INTO classes (id, name, description, grading, creditHours, prereq, coreq) VALUES (%s, \"%s\", \"%s\", \"%s\", %s, %s, %s);",
					id, name, description, grading, hours, prereq, coreq);
			System.out.println("adding class: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addSection(String id, String classID, String section, String professor, String days, String startTime,
			String endTime, String locations) {
		try {
			Statement statement = dbConnection.createStatement();
			String query = String.format(
					"INSERT INTO sections (id, class_id, section, professor, days, start_time, end_time, locations) VALUES (%s, %s, %s, \"%s\", \"%s\", %s, %s, \"%s\");",
					id, classID, section, professor, days, startTime, endTime, locations);
			System.out.println("adding section: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/** Gets a connection to a database and exits on failure */
	private Connection getNewConnection(String host, String databaseName, String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionPath = String.format("jdbc:mysql://%s/%s", host, databaseName);
			return DriverManager.getConnection(connectionPath, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			System.exit(1);
		} catch (SQLException e) {
			System.out.println(e.toString());
			System.exit(1);
		}
		return null;
	}
}

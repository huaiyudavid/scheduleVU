package me.huaiyu.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import me.huaiyu.scheduleVU.Class;
import me.huaiyu.scheduleVU.Section;

public class MySQLWrapper {
	Connection dbConnection;
	
	public MySQLWrapper(String host, String dbName, String username, String password) {
		dbConnection = getNewConnection(host, dbName, username, password);
	}

	/** Default Constructor: makes connection to schedulevu database at huaiyu.me */
	public MySQLWrapper() {
		this("huaiyu.me", "schedulevu", "guest", "devel");
	}
	
	/** Returns an ArrayList of all Classes from the database */
	public ArrayList<Class> getClasses() {
		String query = "SELECT * FROM classes ORDER BY id;";
		ArrayList<Class> classes = new ArrayList<Class>(1000);
		String id, name, description, grading, hours, prereq, coreq;
		try {
			PreparedStatement statement = dbConnection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				id = results.getString(1);
				name = results.getString(2);
				description = results.getString(3);
				grading = results.getString(4);
				hours = results.getString(5);
				prereq = results.getString(6);
				coreq = results.getString(7);
				classes.add(new Class(id, name, description, grading, hours, prereq, coreq));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	public ArrayList<Section> getSections() {
		String query = "SELECT * FROM sections ORDER BY id;";
		ArrayList<Section> sections = new ArrayList<Section>(1700);
		String id, classID, section, professor, days, startTime, endTime, locations;
		try {
			PreparedStatement statement = dbConnection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				id = results.getString(1);
				classID = results.getString(2);
				section = results.getString(3);
				professor = results.getString(4);
				days = results.getString(5);
				startTime = results.getString(6);
				endTime = results.getString(7);
				locations = results.getString(8);
				sections.add(new Section(id, classID, section, professor, days, startTime, endTime, locations));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}

	/** Adds a class into the database with a unique ID */
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

	/** Adds a section into the database with a unique ID */
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
			java.lang.Class.forName("com.mysql.jdbc.Driver");
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

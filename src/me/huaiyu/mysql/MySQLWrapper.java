package me.huaiyu.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLWrapper 
{
	Connection dbConnection;
	
	final String host;
    final String dbName;
    final String username;
    final String password;
    
    public MySQLWrapper(String host, String dbName, String username, String password) {
    	this.host = host;
    	this.dbName = dbName;
    	this.username = username;
    	this.password = password;
    }
    
    public MySQLWrapper() {
    	this("huaiyu.me", "schedulevu", "guest", "devel");
    }
    
    /** Gets a connection to a database and exits on failure */
    private Connection getNewConnection(String host, String databaseName, String username, String password) {
       try {
          Class.forName("com.mysql.jdbc.Driver");
          String connectionPath = String.format("jdbc:mysql://%s/%s", host, databaseName);
          return DriverManager.getConnection(connectionPath, username, password);
       }
       catch(ClassNotFoundException e) {
          System.out.println(e.toString());
          System.exit(1);
       }
       catch(SQLException e) {
          System.out.println(e.toString());
          System.exit(1);
       }
       return null;
    }
}

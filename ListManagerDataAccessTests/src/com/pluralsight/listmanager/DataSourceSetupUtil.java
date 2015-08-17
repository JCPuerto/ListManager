package com.pluralsight.listmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.h2.jdbcx.JdbcDataSource;

import com.pluralsight.listmanager.data.util.DbConnector;

public class DataSourceSetupUtil {
	private static boolean hasSetupInitialContext;

	public static void setup() throws NamingException, SQLException {
		if (!hasSetupInitialContext) {
			setupInitialContext();
			hasSetupInitialContext = true;
		}

		setupDdl();
	}

	private static void setupInitialContext() throws NamingException {

		// Since we're not using WebSphere but Tomcat we need to redefine some
		// properties
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

		// Create the context
		InitialContext ctx = new InitialContext();
		ctx.createSubcontext("java:");
		ctx.createSubcontext("java:comp");
		ctx.createSubcontext("java:comp/env");
		ctx.createSubcontext("java:comp/env/jdbc");

		// Using H2

		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

		// Using SQL Server

		// This 2 simple line works
		// However the sql statements in setupDdl() and tearDown() needs to be
		// fixed.
		// I didn't do it!

		// SQLServerDataSource ds = new SQLServerDataSource();
		// ds.setURL("jdbc:sqlserver://localhost:1433; databaseName=AppDb;
		// user=AppDbUser; password=Password1");

		ctx.bind("java:comp/env/jdbc/AppDb", ds);
	}

	private static void setupDdl() throws SQLException {

		Connection connection = DbConnector.getConnection();

		Statement stmt = connection.createStatement();

		stmt.executeUpdate(
				"CREATE TABLE APP_USER (ID INT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(30) NOT NULL, DISPLAY_NAME VARCHAR(30) NOT NULL);");
		stmt.executeUpdate("INSERT INTO APP_USER (USERNAME, DISPLAY_NAME) VALUES ('test', 'Test User');");
		stmt.executeUpdate(
				"CREATE TABLE LIST_ITEM (ID INT PRIMARY KEY AUTO_INCREMENT, VALUE VARCHAR(4000) NOT NULL, USER_ID INTEGER NOT NULL, CONSTRAINT FK_LIST_ITEM_USER FOREIGN KEY (USER_ID) REFERENCES APP_USER (ID) ON DELETE CASCADE);");

		stmt.close();
		connection.close();
	}

	public static void tearDown() throws SQLException {
		Connection connection = DbConnector.getConnection();

		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE IF EXISTS APP_USER;");
		stmt.executeUpdate("DROP TABLE IF EXISTS LIST_ITEM;");

		stmt.close();
		connection.close();
	}

	public static void insertListItemForDefaultUser(String value) throws SQLException {
		Connection connection = DbConnector.getConnection();

		PreparedStatement stmt = connection.prepareStatement("INSERT INTO LIST_ITEM (USER_ID, VALUE) VALUES (1, ?);");
		stmt.setString(1, value);
		stmt.executeUpdate();
		stmt.close();

		connection.close();
	}

	public static long getListItemsCount() throws SQLException {
		Connection connection = DbConnector.getConnection();

		ResultSet rs = null;

		PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM LIST_ITEM WHERE USER_ID = 1");
		rs = stmt.executeQuery();

		long numberOfRows = 0;
		if (rs.next())
			numberOfRows = rs.getLong(1);

		stmt.close();

		connection.close();

		return numberOfRows;
	}
}

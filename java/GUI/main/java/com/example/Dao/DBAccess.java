package com.example.Dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import com.example.util.Common;

public class DBAccess {

	// create connection variable
	private static Connection connection;

	//csv driver
    private static String CSV_JDBC_DRIVER = "org.relique.jdbc.csv.CsvDriver"; 
	
	// db class init
	public DBAccess() {

		// read config file
		Properties prop = Common.getConfig();

		// read database address
		String connectionURI = prop.getProperty("spring.datasource.url");

		//read db username and password
		String name = prop.getProperty("spring.datasource.username");
		String pwd = prop.getProperty("spring.datasource.password");

		//init db connection
		this.initConnection(connectionURI, name, pwd);
		//CSVConnection();
	}

	// constructor method
	public DBAccess(String connectionURI, String name, String pwd) {
		this.initConnection(connectionURI, name, pwd);
	}

	/**
	 * db init
	 *
	 * @param mysql's
	 *            address
	 * @param name
	 *            user's name
	 * @param pwd
	 *            user's password
	 * @throws exception
	 */
	public void initConnection(String connectionURI, String name, String pwd) {
		try {
			// import mysql db driver
			Class.forName("com.mysql.jdbc.Driver");

			// create connection
			connection = DriverManager.getConnection(connectionURI, name, pwd);

			//
			//connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * execute sql
	 *
	 * @param sql
	 *            sql
	 * @param params
	 *            parameters
	 * @return result from db by sql+paramenters
	 * @throws exception
	 */
	public static List<LinkedHashMap<String, Object>> executeQuery(String sql, String[] params) throws SQLException {
		List<LinkedHashMap<String, Object>> result = new ArrayList<>();

		try {
			PreparedStatement st = connection.prepareStatement(sql);

			for (int i = 0; (params != null) && (i < params.length); i++) {
				st.setString(i + 1, params[i]);
			}

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				LinkedHashMap<String, Object> row = new LinkedHashMap<>();

				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
				}

				result.add(row);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return result;
	}

	/**
	 * execute sql by int parameters
	 *
	 * @param sql
	 *            sql
	 * @param params
	 *            parameters int
	 * @return result from db by sql+paramenters
	 * @throws exception
	 */
	public static List<LinkedHashMap<String, Object>> executeQuery(String sql, int[] params) throws SQLException {
		List<LinkedHashMap<String, Object>> result = new ArrayList<>();

		try {
			PreparedStatement st = connection.prepareStatement(sql);

			for (int i = 0; (params != null) && (i < params.length); i++) {
				st.setLong(i + 1, params[i]);
			}

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				LinkedHashMap<String, Object> row = new LinkedHashMap<>();

				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
				}

				result.add(row);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {

		}
		return result;
	}

	/**
	 * execute sql by int parameters
	 *
	 * @param sql
	 *            sql
	 * @param params
	 *            parameters int
	 * @return result from db by sql+paramenters
	 * @throws exception
	 */
	public static void CSVConnection() {
		try {
			// import csvjdbc driver
			Class.forName(CSV_JDBC_DRIVER);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// create config file
		final Properties props = new java.util.Properties();

		// sparate by comma
		props.put("separator", ",");

		// don't read headers
		props.put("suppressHeaders", "false");

		// read csv files
		props.put("fileExtension", ".csv");

		// UTF-8
		props.put("charset", "UTF-8");

		// file address import
		File directory = new File("./src/main/resources/data"); 
		try {
			
			// create connection
			connection = DriverManager.getConnection("jdbc:relique:csv:" + directory.getCanonicalPath(), props);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static int executeUpdate(String sql, String[] params) throws SQLException {
		int rev = -1;
		try {
			PreparedStatement st = connection.prepareStatement(sql);

			for (int i = 0; (params != null) && (i < params.length); i++) {
				st.setString(i + 1, params[i]);
			}

			rev = st.executeUpdate();
			
			st.close();
		} catch (SQLException e) {
			return rev;
		} finally {
			connection.close();
		}

		return rev;
	}

}

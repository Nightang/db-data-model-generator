package org.nightang.db.sqlite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseGenerator {
	
	public void createDatabaseAndSchema(String outputPath, String inputDDLPath) throws SQLException, IOException {
		System.out.println("Start Create Database and Schema ......");
		removeDatabaseFile(outputPath);
		createDatabaseFile(outputPath);
		createSchema(outputPath, inputDDLPath);
		System.out.println("End");
	}
	
	private void removeDatabaseFile(String outputPath) {
		File file = new File(outputPath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	private void createDatabaseFile(String outputPath) throws SQLException {
		String url = "jdbc:sqlite:" + outputPath;
		Connection conn = DriverManager.getConnection(url);
		if (conn != null) {
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("The driver name is " + meta.getDriverName());
			System.out.println("A new database has been created.");
		}
	}
	
	private void createSchema(String outputPath, String inputDDLPath) throws SQLException, IOException {
		String url = "jdbc:sqlite:" + outputPath;
		
		// SQL statement for creating a new table
		String[] sqls = readScripts(inputDDLPath);
		
		Connection conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();

		// create a new table
		if(sqls != null) {
			for(String sql : sqls) {
				stmt.addBatch(sql);
			}
		}
		stmt.executeBatch();
	}
	
	private String[] readScripts(String filepath) throws IOException {
		String data = null;
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    data = sb.toString();
		} finally {
		    br.close();
		}
		return data != null ? data.trim().split(";") : null;
	}

}

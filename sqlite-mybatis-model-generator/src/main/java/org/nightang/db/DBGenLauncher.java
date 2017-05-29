package org.nightang.db;

import java.io.File;

import org.nightang.db.generator.DBModelGenerator;
import org.nightang.db.generator.SQLiteDatabaseGenerator;

public class DBGenLauncher {
	
	public static final String OUTPUT_DIR = "output";
	
	public static final String INPUT_DIR = "input_sample";

	public static void main(String[] args) throws Exception {
		
		// Handle Input Parameters
		String inputDirectory = INPUT_DIR;
		String myBatisConfigFileName = "mybatis-generator-config.xml";
		String schemaDDLFileName = "schema-ddl.sql";
		String dbfileName = "database.db";
		if(args.length == 0) {
			System.out.println("No Parameter. Use Sample Setting.");
		} else if(args.length == 3) {
			inputDirectory = args[0];
			myBatisConfigFileName = args[1];
			schemaDDLFileName = args[2];
		} else if(args.length == 4) {
			inputDirectory = args[0];
			myBatisConfigFileName = args[1];
			schemaDDLFileName = args[2];
			dbfileName = args[3];			
		} else {
			throw new IllegalArgumentException("Invalid Input Parameters.");
		}
		printOut(inputDirectory, myBatisConfigFileName, schemaDDLFileName);
		
		// Clear Previous Files & Folder
		File dir = new File(OUTPUT_DIR);
		if(dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			for(File file : files) {
				deleteFiles(file);
			}
		} else {
			dir.mkdir();
		}
		
		// Create Database
		String dbFilePath = OUTPUT_DIR + File.separator + dbfileName;
		SQLiteDatabaseGenerator dbGen = new SQLiteDatabaseGenerator();
		dbGen.createDatabaseAndSchema(dbFilePath, inputDirectory + File.separator + schemaDDLFileName);
			
		// Create DB Model Code
		DBModelGenerator modelGen = new DBModelGenerator();
		modelGen.generateDBModel(inputDirectory + File.separator + myBatisConfigFileName, dbFilePath);

	}
	
	private static void printOut(String s1, String s2, String s3) {
		System.out.println("===============================================");
		System.out.println("> Input Folder       : " + s1);
		System.out.println("> Mybatis Config File: " + s2);
		System.out.println("> Schema DDL File    : " + s3);
		System.out.println("===============================================");
	}
	
	private static void deleteFiles(File file) {
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for(File subFile : subFiles) {
				deleteFiles(subFile);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

}

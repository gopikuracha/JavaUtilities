package com.kriss.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LocalFileReader {
	
	public List<String> results = new ArrayList<String>();
	
	public boolean print = false;
	
    public static void main(String[] args) {
        String filePath = "C:/Development/Data/spr_services.json"; // Replace with your file path
        
        // Method 1: Using BufferedReader (traditional approach)
        //readWithBufferedReader(filePath);
        
        // Method 2: Using Scanner
        readWithScanner(filePath);
        
        // Method 3: Using Files class (Java 7+)
        readWithFiles(filePath);
        
        // Method 4: Using Files with streams (Java 8+)
        readWithFilesStream(filePath);
    }
    
    // Method 1: Using BufferedReader
    public List<String> readWithBufferedReader(String filePath) {
    	BufferedReader reader = null;
        System.out.println("Reading with BufferedReader:");
        try {
        	reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (print) System.out.println(line);
                results.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } finally {
        	try { if(reader!=null) reader.close(); } catch(IOException ioe) {ioe.printStackTrace();}
        }
        System.out.println();
        return results;
    }
    
    // Method 2: Using Scanner
    public static void readWithScanner(String filePath) {
        System.out.println("Reading with Scanner:");
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
    
    // Method 3: Using Files class (Java 7+)
    public static void readWithFiles(String filePath) {
        System.out.println("Reading with Files class:");
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
    
    // Method 4: Using Files with streams (Java 8+)
    public static void readWithFilesStream(String filePath) {
        System.out.println("Reading with Files and Streams:");
        try {
            String content = Files.lines(Paths.get(filePath))
                    .collect(Collectors.joining("\n"));
            System.out.println(content);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

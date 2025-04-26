package com.example;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.sql.*;

public class TestResultUploader {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/test_results_db";
        String dbUser = "root"; // your DB user
        String dbPass = "Sagar@95"; // your DB password

        try {
            // Connect to database
            Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
            System.out.println("Connected to database!");

            // Parse XML file
            File dir = new File("target/surefire-reports");
            File[] files = dir.listFiles((d, name) -> name.endsWith(".xml"));

            if (files != null) {
                for (File file : files) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);

                    doc.getDocumentElement().normalize();

                    NodeList testcases = doc.getElementsByTagName("testcase");

                    for (int i = 0; i < testcases.getLength(); i++) {
                        Node testcase = testcases.item(i);

                        if (testcase.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) testcase;

                            String className = eElement.getAttribute("classname");
                            String testName = eElement.getAttribute("name");
                            String time = eElement.getAttribute("time");

                            String status = "PASS";
                            NodeList failure = eElement.getElementsByTagName("failure");
                            if (failure.getLength() > 0) {
                                status = "FAIL";
                            }

                            // Insert into DB
                            String sql = "INSERT INTO unit_test_results (test_class, test_name, status, execution_time_ms) VALUES (?, ?, ?, ?)";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, className);
                            pstmt.setString(2, testName);
                            pstmt.setString(3, status);
                            pstmt.setDouble(4, Double.parseDouble(time));
                            pstmt.executeUpdate();
                        }
                    }
                }
            }

            conn.close();
            System.out.println("All results inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

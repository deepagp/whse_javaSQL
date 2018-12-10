package com.query.application;

//STEP 1. Import required packages

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryApplication {

	public static void main(String[] args) {

		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			System.out.println("Getting database connection...");
			conn = getConnection();
			
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			
			String sql = getStudentsQuery();

		    rs = stmt.executeQuery(sql);

			List<Student> studentList = getStudentRecords(rs);

			printStudentRecords(studentList);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
				if(rs != null)
					rs.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	/**
	 * Print student records
	 * 
	 * @param studentList
	 */
	private static void printStudentRecords(List<Student> studentList) {

		for (Student student : studentList) {
			System.out.print("ID: " + student.getId());
			System.out.print(", Age: " + student.getAge());
			System.out.print(", First Name: " + student.getFristName());
			System.out.println(", Last Name: " + student.getLastName());
		}

	}

	/**
	 * Get Db Connection
	 * 
	 * @return
	 */
	private static Connection getConnection() {
		return DBConnection.getDBConnection();
	}

	/**
	 * Build select query
	 * 
	 * @return
	 */
	private static String getStudentsQuery() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT id, firstName, lastName, age FROM Employees");
		sql.append(getCriteria());

		return sql.toString();

	}

	/**
	 * Build condition
	 * 
	 * @return
	 */
	private static String getCriteria() {
		return " WHERE age < 30";
	}

	/**
	 * Fetch student records
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static List<Student> getStudentRecords(ResultSet rs) throws SQLException {

		List<Student> students = new ArrayList<Student>();
		Student student;
		while (rs.next()) {
			student = new Student();
			student.setId(rs.getInt("id"));
			student.setAge(rs.getInt("age"));
			student.setFristName(rs.getString("firstName"));
			student.setLastName(rs.getString("lastName"));

			students.add(student);
		}

		return students;

	}

}

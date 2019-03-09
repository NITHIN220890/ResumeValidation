package com.test.ResumeValidation;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.spi.XmlWriter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;


public class ResumeValidation {

	public static void main(String[] args) throws Exception {


		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/nithin","root","root");  

		String s1 = "java";
		String s2 = "apache";
		String s3 = "jsp";
		String s4 = "Jboss";
		String s5 = "restful";
		String s6 = "mvc"; 
		String s7 = "sql";
		String s8 = "Basic of R";
		StringBuffer sb = new StringBuffer();

		ArrayList<String> al = new ArrayList<String>();
		File file = new File("C://Users//nitin//Downloads//Nithin_Testing_resume.docx");
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		System.out.println(fis);

		/*File file = new File("D://Resume");   
		File[] listOfFiles = file.listFiles();

		for (File file1 : listOfFiles) {
		    if (file1.isFile()) {
		    	FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		 */

		XWPFDocument document = new XWPFDocument(fis);

		List<XWPFParagraph> paragraphs = document.getParagraphs();

		System.out.println("Total no of paragraph "+paragraphs.size());
		for (XWPFParagraph para : paragraphs) {
			sb.append(para.getText());

		}
		String document1 = sb.toString();
		System.out.println(document1);

		/*		String str;
		while ((str = br.readLine())!= null) {
			sb.append(str);

		}*/
		String data = (sb.toString());
		String data_lower = data.toLowerCase();

		if (data_lower.contains(s1)) {
			al.add(s1);
		}
		if (data_lower.contains(s2)) {
			al.add(s2);
		}
		if (data_lower.contains(s3)) {
			al.add(s3);
		}
		if (data_lower.contains(s4)) {
			al.add(s4);
		}
		if (data_lower.contains(s5)) {
			al.add(s5);
		}
		if (data_lower.contains(s6)) {
			al.add(s6);
		}  
		if (data_lower.contains(s7)) {
			al.add(s7);
		}
		if (data_lower.contains(s8)) {
			al.add(s8);
		}

		/* System.out.println(al); */

		String pt = ".*JAVA.*";
		Pattern pattern = Pattern.compile(pt);
		Matcher matcher = pattern.matcher(data);
		/* System.out.println(matcher.matches()); */

		String phone = validate(sb.toString());

		String email = getPersonalEmail(sb.toString());
		String dob = getDob(sb.toString());
		String Employer = null;

		System.out.println("KeySkills :" + al);
		/* System.out.println(al); */
		System.out.println(email);
		System.out.println(phone);
		System.out.println(dob);
		/* Class.forName("com.mysql.jdbc.Driver"); */
		Employer = getEmployers(data_lower);
		System.out.println(Employer);

		PreparedStatement stmt = con.prepareStatement("insert into testdb1 values(?,?,?,?,?)");
		// 1 specifies the first parameter in the query
		stmt.setString(2, email);
		stmt.setString(3, phone);
		stmt.setString(1, al.toString());
		stmt.setString(4, dob);
		stmt.setString(5, Employer);
		int i = stmt.executeUpdate();
		System.out.println(i + " records inserted");

		con.close();
		fis.close();

	}

	public static String validate(String s) {
		/* ArrayList<String> al = new ArrayList<String>(2); */
		String phone = null;
		// validate phone numbers of format "1234567890"
		/* if (s.matches("\\d{10}")) */ {
			Matcher m = Pattern.compile("\\d{10}").matcher(s);
			while (m.find()) {
				phone = m.group();
				/*
				 * al.add(m.group()); }System.out.println(al);
				 */
			}

		
			return phone;
		}

	}

	
	public static String getEmployers(String sb) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con1=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/nithin","root","root");  
		ArrayList<String> al = new ArrayList<String>();
		StringBuffer sb1 = new StringBuffer();


		PreparedStatement stmt = con1.prepareStatement("select * from Employers");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			
			al.add(rs.getString(1));

		}
		for (String string : al) {
			/* System.out.println("ukvu"+string.matches(sb)); */
			if (sb.contains(string)) {
				/* System.out.println(string); */
				sb1.append(string);
				sb1.append(",");
				/* System.out.println(sb1); */

			}

			/* System.out.println(al); */
		}

		String Emp = sb1.toString();
		int w = Emp.length();
		Emp = Emp.substring(0, w - 1);
		return Emp;

	}

	public static String getPersonalEmail(String s) {
		String email = null;
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
		while (m.find()) {
			email = m.group();
		}
		return email;
	}

	public static String getDob(String s) {
		String dob = null;
		Matcher m = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}").matcher(s);
		while (m.find()) {
			dob = m.group();
		}
		return dob;
	}

}

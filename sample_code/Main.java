// Copyright (c) 2013.  Nikolay Voronchikhin. All rights reserved. 
// Not for re-use or re-publication as academic work or otherwise.

import java.net.*;
import java.io.*;
//Sql.java -- sample program to read a database 
//Configure the database for ODBC access using Start->Settings->Control Panel->ODBC32
import java.sql.*;

public class Main {
	
	
	public static void readHTML(String Conference, String years) throws Exception {
		
		
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
        Statement stmt = null;
        Connection conn = null;
        
        	String dbURL = "jdbc:sqlserver://localhost"; 
            String user = "";
            String pass = ""; 
            conn = java.sql.DriverManager.getConnection(dbURL, user, pass);

            stmt = conn.createStatement();
            
       
        URL oracle2 = new URL(Conference);
        BufferedReader in2 = new BufferedReader(
        new InputStreamReader(oracle2.openStream()));
        
        String inputLine = in2.readLine();

        PrintStream out2 = new PrintStream(new FileOutputStream("output_asiacrypt2013.txt"));
        System.setOut(out2);

        
        String r1="";
        
        while ((inputLine = in2.readLine()) != null){
                r1 = inputLine.substring(inputLine.indexOf("Acknowledgements"),inputLine.indexOf("References"));
                out2.println(r1);
                out2.println("---Skip---");
                out2.println("---Skip---");
                
                String nsf="no", dod="no", foundations="no",  companies="no";
                
                if(r1.contains("NSF")) nsf = "yes"; 
                if(r1.contains("DARPA") || r1.contains("IARPA")) dod = "yes";
                
            	java.sql.CallableStatement cs = conn.prepareCall("{call cryptography.dbo.insertintoasiacrypt('"+years+"','"+nsf+"',"+dod+","+foundations+","+companies+")}"); 
       			
                System.out.println(cs);
                cs.execute();
        }
        
        
        	
       
        out2.close();
        in2.close();
    
    }
	


	// calls other procedures
    public static void main(String[] args) throws Exception {
    	String AsiaCrypt2013 = "file:///C:/asiacrypt2013.html";
    	readHTML(AsiaCrypt2013,"2013"); // AsiaCrypt 2013
    	
    }

}
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.Reader; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer; 
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author maukhan.bscs13seecs
 */
public class Lab5 {
static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
    /**
     * @param args the command line arguments
     */
   
   public static Connection CreateConnection(Connection conn,Statement stmt,String sql) throws ClassNotFoundException{
   
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 4: Execute a query
      System.out.println("Creating database...");
      stmt = conn.createStatement();
       }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }
   return conn;
   }
   
   public static Connection CreateDatabase(Connection conn) throws ClassNotFoundException
   {
      //  String sql=null;
   try{
       String sql=null;
      Statement stmt=null;
      stmt = conn.createStatement();
      sql = "DROP DATABASE GeoLiteCity;";
      stmt.executeUpdate(sql);
      
      sql = "CREATE DATABASE GeoLiteCity;";
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");
 
      sql="USE GeoLiteCity";
      stmt.executeQuery(sql);
      System.out.println("use executed");      
      
       sql = "CREATE TABLE Location " +
                   "(locId INTEGER not NULL, " +
                   " country VARCHAR(15), " + 
                   " region VARCHAR(15), " + 
                   " city VARCHAR(255), " +
                    " postalCode VARCHAR(15), " +
                    " longitude FLOAT, " +
                    " latitude FLOAT, " +
                    " metroCode INTEGER, " +
                    " areaCode INTEGER, " +
                   " PRIMARY KEY ( locId ));"; 

      stmt.executeUpdate(sql);
      System.out.println("create table  executed");  
     
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }
       return conn;
   }
   
public static void Populate(Connection conn) throws SQLException, IOException{
       String sql=null;
      Statement stmt=null;
      stmt = conn.createStatement();
               
String fileName="C:\\Users\\maukhan.bscs13seecs\\Documents\\NetBeansProjects\\lab5\\GeoLiteCity-Location.csv"; 
try { 
BufferedReader br = new BufferedReader( new FileReader(fileName)); 
String strLine = null; 
StringTokenizer st = null; 
int lineNumber = 1, tokenNumber = 0; 
br.readLine();
br.readLine();

String line=br.readLine();

while( lineNumber <501) 
{
    sql = "INSERT INTO Location " +
                   "VALUES ("+line+");";
    
    sql=sql.replace(",,", ",NULL,");
    sql=sql.replace(",)", ",NULL)");
    stmt.executeUpdate(sql);
 //   System.out.println("hello");
    
//    System.out.println(line);//"Line # " + lineNumber + 
    //", Token # " + tokenNumber 
//    "Token : "+ st.nextToken()); 
line=br.readLine();
lineNumber++;
} 
} 

catch (FileNotFoundException e) { 
// TODO Auto-generated catch block 
e.printStackTrace(); 
}
   }

public static void Search(Connection conn,String city) throws SQLException
{
     try{
         String sql=null;
         Statement stmt=null;
      stmt = conn.createStatement();
      System.out.print("in search");
      sql = "SELECT longitude,latitude FROM Location" +
                   " WHERE city = \""+city+"\";";
      ResultSet rs = stmt.executeQuery(sql);
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         float longitude  = rs.getFloat("longitude");
         float latitude = rs.getFloat("latitude");
         //Display values
         System.out.print("longitude: " + longitude);
         System.out.print(", latitude: " + latitude);
      }
      rs.close();
     }
      catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }
      
      
    
}
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
// TODO Auto-generated method stub 
         Connection conn = null;
   Statement stmt = null;
   String sql=null;
   String city=null;
   Scanner in = new Scanner(System.in);
  conn=CreateConnection(conn,stmt,sql);
  conn=CreateDatabase(conn);
  Populate(conn);
  System.out.println("Enter the city to search: ");
  city=in.nextLine();
  Search(conn,city);
 /* 
Connection conn = null;
   Statement stmt = null;
   String sql=null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 4: Execute a query
      System.out.println("Creating database...");
      stmt = conn.createStatement();
      sql = "DROP DATABASE GeoLiteCity;";
      stmt.executeUpdate(sql);
      
      sql = "CREATE DATABASE GeoLiteCity;";
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");
 
      sql="USE GeoLiteCity";
      stmt.executeQuery(sql);
      System.out.println("use executed");      
      
       sql = "CREATE TABLE Location " +
                   "(locId INTEGER not NULL, " +
                   " country VARCHAR(15), " + 
                   " region VARCHAR(15), " + 
                   " city VARCHAR(15), " +
                    " postalCode VARCHAR(15), " +
                    " longitude FLOAT, " +
                    " latitude FLOAT, " +
                    " metroCode INTEGER, " +
                    " areaCode INTEGER, " +
                   " PRIMARY KEY ( locId ));"; 

      stmt.executeUpdate(sql);
      System.out.println("create table  executed");  
     
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }
 catch (IOException e) { 
// TODO Auto-generated catch block 
e.printStackTrace(); 
}
   
  */
        
    }
    
}

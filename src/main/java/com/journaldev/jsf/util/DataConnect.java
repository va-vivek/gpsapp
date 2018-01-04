package com.journaldev.jsf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import static javax.servlet.SessionTrackingMode.URL;

public class DataConnect {
    
   public static void main(String[] args) {
        try {
            DataConnect obj = new DataConnect();
           obj.getConnection();
        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
        }
    }

      public static Connection getConnection() {
        try {
            Connection conn = null;
            File directory = new File(".");
            //String dirpath = directory.getAbsolutePath();;
            String dirpath = directory.getAbsolutePath();
            File file = new File(dirpath);
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            //ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops", Locale.ENGLISH, loader);
            /*ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops");
            String url = "";
            String user = "";
            String pwd = "";
            //ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops");
            url = rb.getString("url");
            user = rb.getString("username");
            pwd = rb.getString("password");
            System.out.println("user = " + user);
            System.out.println("pass = " + pwd);
            */
            
            Class.forName(System.getenv("MYSQL_DRIVER"));
	String url = System.getenv("MYSQL_URL");;
        String user= System.getenv("MYSQL_USER");
        String pwd = System.getenv("MYSQL_ROOT_PASSWORD");
            
      /* Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/gps";
            String user= "root";
            String pwd = "admin"; */
            
            
            
            conn = DriverManager.getConnection(url, user, pwd);
            return conn;
        } catch (Exception e) {
            System.out.println("Exception in getting connection:" + e);
    //        lw.writetoFile("Exception in getting connection-------->" + e + "----" + dt.toString());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}

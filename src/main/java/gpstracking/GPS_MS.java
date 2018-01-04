package gpstracking;

import gpstracking.GPS_SOA;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hs0013281
 */
public class GPS_MS {

    public static void main(String[] args) {
        try {
            GPS_MS obj = new GPS_MS();

        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
        }
    }

    public Connection getConnection() {
        try {
            //Mfile obj = new Mfile();
            File directory = new File(".");
            String dirpath = directory.getAbsolutePath();;
            //dirpath = obj.getdirectory();
            File file = new File(dirpath);
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops", Locale.ENGLISH, loader);
            Connection conn = null;
            String url = "";
            String user = "";
            String pwd = "";
            //ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops");
            url = rb.getString("url");
            user = rb.getString("username");
            pwd = rb.getString("password");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("user = " + user);
            System.out.println("pass = " + pwd);
            conn = DriverManager.getConnection(url, user, pwd);
            return conn;
        } catch (Exception e) {
            System.out.println("Exception in getting connection:" + e);
            //lw.writetoFile("Exception in getting connection-------->" + e + "----" + dt.toString());
            return null;
        }
    }

    public ArrayList executequery(String query) {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        String str = "";
        int numberOfColumns = 0;
        ArrayList list = new ArrayList();
        ResultSetMetaData rsmd;
        try {
            //dbconnection db = new dbconnection();
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                rsmd = rs.getMetaData();
                numberOfColumns = rsmd.getColumnCount();
                for (int i = 0; i < numberOfColumns; i++) {
                    str = str + "," + rs.getString(i + 1);
                }
                str = str.substring(1, str.length());
                list.add(str);
                str = "";
            }
            return list;
        } catch (Exception e) {
            System.out.println("Exception in executequery:" + e);
            // lw.writetoFile("Exception in executequery method of class Dataaccess ------------>" + e + "----" + dt.toString());
            return list;
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
                str = null;
                numberOfColumns = 0;
                list = null;
                rsmd = null;
                query = null;
            } catch (Exception ee) {
            }
        }

    }

    public boolean insertquery(String query, String params) {
        Connection conn = null;
        PreparedStatement ps = null;
        int numberOfColumns = 0;
        boolean flag = false;
        int j = 0;
        //dbconnection db = new dbconnection();
        String param[] = null;
        int count = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            if (params.length() > 0) {
                param = null;
                param = params.split(",");
                count = 0;
                count = param.length;
                for (int i = 0; i < count; i++) {
                    ps.setString(i + 1, param[i]);
                }
                j = ps.executeUpdate();
            }
            if (j == 0) {
                flag = false;
            } else {
                flag = true;
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Exception in executequery:" + e);
            //lw.writetoFile("Exception in insertquery in class DataAccess ------------> " + e + " ----   " + dt.toString());
            return false;
        } finally {
            try {
                ps.close();
                conn.close();
                numberOfColumns = 0;
                j = 0;
                param = null;
                count = 0;
                query = null;
            } catch (Exception ee) {
            }
        }
    }
}

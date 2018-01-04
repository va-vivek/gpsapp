package com.dev.user.model;

import com.journaldev.jsf.util.DataConnect;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean(name = "user")
public class User {
   private String uname;
   private String name;
   private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   private String email;
   private int phone;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

        public String add() {
        Connection con = null;
        PreparedStatement ps = null;
        int count=0;
        try {
            con = DataConnect.getConnection();
            System.out.println("IN USER ADD PAGE"+uname+name+password+email+phone);
            String sql = "INSERT INTO user_reg(username, name, password, email, phone) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, email);
            ps.setInt(5, phone);
            count = ps.executeUpdate();
            System.out.println("Data Added Successfully");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (count > 0) {
            return "output";
        } else {
            return "invalid";
        }
    }
}

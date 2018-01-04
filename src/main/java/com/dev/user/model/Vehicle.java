/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.user.model;

import com.journaldev.jsf.util.DataConnect;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

@ManagedBean(name = "vehicle")
public class Vehicle {

    public Vehicle() {

    }

    public Vehicle(String vehicleno) {
        this.vehicleno = vehicleno;
    }
    private String vehicle_no;
    private String vehicleno;
    private int selectedCategory;

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }
    private String vehicle_type;
    private String vehicle_brand;
    private String vehicle_colour;

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_brand() {
        return vehicle_brand;
    }

    public void setVehicle_brand(String vehicle_brand) {
        this.vehicle_brand = vehicle_brand;
    }

    public String getVehicle_colour() {
        return vehicle_colour;
    }

    public void setVehicle_colour(String vehicle_colour) {
        this.vehicle_colour = vehicle_colour;
    }
    Connection con;
    PreparedStatement ps;

    public List<Vehicle> getAllVehicle() {
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select vehicle_no from vehicle_reg;");
            ResultSet rs = ps.executeQuery();
            List<Vehicle> vehiclenos = new ArrayList<Vehicle>();
            while (rs.next()) {
                vehiclenos.add(new Vehicle(rs.getString(1)));
            }
            return vehiclenos;
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
        return null;
    }

    public List<SelectItem> getAllVehicles() {

        List<SelectItem> vehicles = new ArrayList<SelectItem>();
        List<Vehicle> vehicleList = getAllVehicle();
        for (Vehicle vehicle : vehicleList) {
            vehicles.add(new SelectItem(vehicle.vehicleno));
        }
        return vehicles;
    }

    public String add() {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            System.out.println("now here");
            con = DataConnect.getConnection();
            System.out.println(vehicle_no + vehicle_type + vehicle_brand + vehicle_colour);
            String sql = "INSERT INTO vehicle_reg(vehicle_no,vehicle_type,brand,colour) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, vehicle_no);
            ps.setString(2, vehicle_type);
            ps.setString(3, vehicle_brand);
            ps.setString(4, vehicle_colour);
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
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Vehicle Registered successfully",
                            "Success"));
            return "output";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Vehicle data Alreay Exist or Some Error Occurred",
                            "invalid"));
            return "invalid";
        }
    }
}

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

@ManagedBean(name = "route")
public class Route {

    public Route() {

    }

    public Route(int routeid) {
        this.routeid = routeid;
    }

    private int routeid;
    private String route1;
    private String route2;
    private String route3;
    private String route4;
    private int total_distance;
    private String flag;
    private int selectedCategory;

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRoute1() {
        return route1;
    }

    public void setRoute1(String route1) {
        this.route1 = route1;
    }

    public String getRoute2() {
        return route2;
    }

    public void setRoute2(String route2) {
        this.route2 = route2;
    }

    public String getRoute3() {
        return route3;
    }

    public void setRoute3(String route3) {
        this.route3 = route3;
    }

    public String getRoute4() {
        return route4;
    }

    public void setRoute4(String route4) {
        this.route4 = route4;
    }

    public int getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(int total_distance) {
        this.total_distance = total_distance;
    }
    Connection con;
    PreparedStatement ps;

    public List<Route> getAllRoute() {
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select route_id from route_master");
            ResultSet rs = ps.executeQuery();
            List<Route> routeids = new ArrayList<Route>();
            while (rs.next()) {
                routeids.add(new Route(rs.getInt(1)));
            }
            return routeids;
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

    public List<SelectItem> getAllRoutes() {

        List<SelectItem> routes = new ArrayList<SelectItem>();
        List<Route> routeList = getAllRoute();
        for (Route route : routeList) {
            routes.add(new SelectItem(route.routeid));
        }
        return routes;
    }

    public String add() {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            System.out.println(route1 + " " + route2 + " " + route3 + " " + route4 + " " + total_distance + " " + flag);
            con = DataConnect.getConnection();
            PreparedStatement ps1;
            ResultSet rs1;

            // System.out.println(vehicle_no+vehicle_type+vehicle_brand+vehicle_colour);
            String sql = "INSERT INTO route_master(route_1,route_2,route_3,route_4,total_distance,flag) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps1 = con.prepareStatement("Select city_name from city_master where city_id=" + route1);
            rs1 = ps1.executeQuery();
            rs1.next();
            ps.setString(1, rs1.getString(1));
            ps1 = con.prepareStatement("Select city_name from city_master where city_id=" + route2);
            rs1 = ps1.executeQuery();
            rs1.next();
            ps.setString(2, rs1.getString(1));
            ps1 = con.prepareStatement("Select city_name from city_master where city_id=" + route3);
            rs1 = ps1.executeQuery();
            rs1.next();
            ps.setString(3, rs1.getString(1));
            ps1 = con.prepareStatement("Select city_name from city_master where city_id=" + route4);
            rs1 = ps1.executeQuery();
            rs1.next();
            ps.setString(4, rs1.getString(1));
            ps.setInt(5, total_distance);
            ps.setString(6, flag);
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
                            "Route data added successfully",
                            "Success"));
            return "output";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Route not added, Some Error Occurred",
                            "invalid"));
            return "invalid";
        }
    }
}

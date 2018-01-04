/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.user.model;

import com.journaldev.jsf.util.DataConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "journey")
public class Journey {

    private String vehicletype;
    private String vehicleno;
    private int routeid;
    private int totaldistancecovered;
    private String flag;
    private String journey_status;
    private String startdate;
    private String enddate;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    
    public String getJourney_status() {
        return journey_status;
    }

    public void setJourney_status(String journey_status) {
        this.journey_status = journey_status;
    }

    public String getJourneystatus() {
        return journeystatus;
    }

    public void setJourneystatus(String journeystatus) {
        this.journeystatus = journeystatus;
    }
    private String journeystatus;

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public int getRouteid() {
        return routeid;
    }

    public void setRouteid(int routeid) {
        this.routeid = routeid;
    }

    public int getTotaldistancecovered() {
        return totaldistancecovered;
    }

    public void setTotaldistancecovered(int totaldistancecovered) {
        this.totaldistancecovered = totaldistancecovered;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    Connection con;
    PreparedStatement ps;

    public String add() {
        int count = 0;
        try {
            con = DataConnect.getConnection();
            SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");
            Date date1=formatter1.parse(startdate);
            Date date2=formatter1.parse(enddate);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reportDate = df.format(date1);
            String sql = "INSERT INTO journey_master(vehicle_type,vehicle_no,route_id,total_distance_covered,flag,journey_status,start_date,end_date) VALUES(?,?,?,?,?,?,?,?)";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, vehicletype);
            ps.setString(2, vehicleno);
            ps.setInt(3, routeid);
            ps.setInt(4, totaldistancecovered);
            ps.setString(5, flag);
            ps.setString(6, journey_status);
            ps.setString(7, df.format(date1));
            ps.setString(8, df.format(date2));
            
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
                            "Journey Details Added Successfully",
                            "Success"));
            return "output";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Journey Alreay Exist or Some Error Occurred",
                            "invalid"));
            return "invalid";
        }
    }
}

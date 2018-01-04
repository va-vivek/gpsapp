/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.user.model;

import com.journaldev.jsf.util.DataConnect;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author RN00468025
 */
@ManagedBean(name = "track_journey")
public class TrackJourney implements Serializable {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    static List<JourneyTrack> list;
    private String vehicle_no, start_date, end_date;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public List<JourneyTrack> getList() {
        add();
        return list;
    }

    public String add() {
        int count = 0;
        Connection con;
        ResultSet rs;
        try {
            con = DataConnect.getConnection();
            PreparedStatement ps;
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String startdate = "", enddate = "";
            if (!start_date.equals("")) {
                startdate = df.format(formatter1.parse(start_date));
            }
            if (!end_date.equals("")) {
                enddate = df.format(formatter1.parse(end_date));
            }

            System.out.println("ehh=" + vehicle_no + " start:" + startdate + " end:" + enddate);

            if (vehicle_no.equals("") && startdate.equals("") && enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id");
            } else if (vehicle_no.equals("") && !startdate.equals("") && !enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.start_date='" + startdate + "' and j.end_date='" + enddate + "'");
            } else if (!vehicle_no.equals("") && startdate.equals("") && !enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.vehicle_no='" + vehicle_no + "' and j.end_date='" + enddate + "'");
            } else if (!vehicle_no.equals("") && !startdate.equals("") && enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.vehicle_no='" + vehicle_no + "' and j.start_date='" + startdate + "'");
            } else if (vehicle_no.equals("") && startdate.equals("") && !enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.end_date='" + enddate + "'");
            } else if (!vehicle_no.equals("") && startdate.equals("") && enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.vehicle_no='" + vehicle_no+"'");
            } else if (vehicle_no.equals("") && !startdate.equals("") && enddate.equals("")) {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.start_date='" + startdate + "'");
            } else {
                ps = con.prepareStatement("SELECT j.vehicle_type,j.vehicle_no,r.route_1,r.route_2,r.route_3,r.route_4, j.total_distance_covered as tdc, r.total_distance as td, j.start_date as start_date, j.end_date as end_date FROM route_master r INNER JOIN journey_master j ON j.route_id = r.route_id where j.vehicle_no='" + vehicle_no + "' and j.start_date='" + startdate + "'and j.end_date='" + enddate + "'");
            }
            rs = ps.executeQuery();
            /*while (rs.next())
   {System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9));
   }*/
            list = new ArrayList<JourneyTrack>();
            while (rs.next()) {
                String city1 = rs.getString(3);
                String city2 = rs.getString(4);
                String city3 = rs.getString(5);
                String city4 = rs.getString(6);
                
                
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                String sdate= df1.format(formatter2.parse(rs.getString(9)));
                String edate= df1.format(formatter2.parse(rs.getString(10)));
                System.out.println("Start:" +sdate + "\nEnd:" + edate);
                
                int tdc = rs.getInt(7);
                int td = rs.getInt(8);
                int avg = td / 3;

                if (tdc == td) {
                    list.add(new JourneyTrack(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), city4, sdate, edate));
                } else if (tdc < td && (tdc >= (avg * 2) + 1)) {
                    list.add(new JourneyTrack(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), city3, sdate, edate));
                } else if (tdc <= (avg * 2) && tdc >= (avg + 1)) {
                    list.add(new JourneyTrack(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), city2, sdate, edate));
                } else {
                    list.add(new JourneyTrack(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), city1, sdate, edate));
                }
            }
            System.out.print("------------------");
            Iterator<JourneyTrack> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next().getStart_date());
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (count == 0) {
            return "track";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Some error occured please try again",
                            "invalid"));
            return "invalid";
        }

    }

}

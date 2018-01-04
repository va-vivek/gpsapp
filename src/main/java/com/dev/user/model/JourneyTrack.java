/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.user.model;

/**
 *
 * @author RN00468025
 */
public class JourneyTrack {
    
    public JourneyTrack(String vehicle_type, String vehicle_no, String route1, String route2, String route3, String route4, int tdc, int td, String current_city, String start_date, String end_date)
    {
        this.vehicle_type=vehicle_type;
        this.vehicle_no=vehicle_no;
        this.route1=route1;
        this.route2=route2;
        this.route3=route3;
        this.route4=route4;
        this.tdc=tdc;
        this.td=td;
        this.current_city=current_city;
        this.start_date=start_date;
        this.end_date=end_date;
        
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
    
public String vehicle_type, vehicle_no, start_date, end_date;
public String route1,route2,route3,route4,current_city;
public int tdc,td;


    public String getVehicle_type() {
        return vehicle_type;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getRoute1() {
        return route1;
    }

    public String getRoute2() {
        return route2;
    }

    public String getRoute3() {
        return route3;
    }

    public String getRoute4() {
        return route4;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public int getTdc() {
        return tdc;
    }

    public int getTd() {
        return td;
    }
    




}

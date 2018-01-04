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

@ManagedBean(name = "city")
public class City {

    public City(){
        this.cityname = cityname;
    }
    public City(int cityid, String cityname){
        this.cityid = cityid;
        this.cityname = cityname;
    }
    private String cityname;
    private List<City> listcityname;
    private int cityid;

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    private int selectedCategory;
    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }
    
  
    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname.toUpperCase();
    }

    Connection con = null;
    PreparedStatement ps = null;
    
    
    
    
    public List<City> getAllCity(){
         try{
         con = DataConnect.getConnection();
         ps = con.prepareStatement("select city_id, city_name from city_master");
         ResultSet rs = ps.executeQuery();
         List<City> citynames = new ArrayList<City>();
         while(rs.next())
         {
            citynames.add(new City(rs.getInt(1),rs.getString(2)));
         }
        return citynames;
    }
         catch (Exception e) {
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
    
    
    public List<SelectItem> getAllCities(){

   List<SelectItem> cities = new ArrayList<SelectItem>();
   List<City> cityList = getAllCity();
    for(City city: cityList){
       cities.add(new SelectItem(city.cityid, city.cityname));
   }
   return cities;
}
    
    
    
    public String add() {
       int count=0;
        try {
            System.out.println("now here");
            con = DataConnect.getConnection();
            String sql = "INSERT INTO city_master(city_name) VALUES(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,cityname);
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
							"City Added Successfully",
							"Success"));
			return "output";
        } else {
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"City Alreay Exist or Some Error Occurred",
							"invalid"));
            return "invalid";
        }
    }
}


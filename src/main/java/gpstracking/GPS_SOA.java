/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpstracking;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hs0013281
 */
public class GPS_SOA {
     Date dt = new Date();
    logwriter lw = new logwriter();
    public static void main(String[] args) {
        try {
           // Date dt = new Date();
            String query = "";
            ArrayList list = new ArrayList();
            GPS_SOA obj = new GPS_SOA();
            System.out.println("call  -- " + 0);
            query = "select journey_id, vehicle_type, route_id,  total_distance_covered, flag, journey_status "
                    + " from journey_master where flag != '10'";
            list = obj.getjourney(query);
            System.out.println("call  -- " + 1);
            obj.setjourney(list);
            query = "select a.route_id , a.total_distance , b.total_distance_covered , b.journey_status, b.flag "
                    + "from route_master a ,  journey_master b "
                    + "where a.route_id = b.route_id "
                    + "and b.flag != 'closed' "
                    + "and b.flag != 10";
            list = obj.getjourney(query);
            obj.revisejourney(list);
            System.out.println("call  -- " + 2);
             
        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
           
        }
    }
    
    public ArrayList getjourney(String query) {
        ArrayList list = new ArrayList();
        try {
            lw.writetoFile("Method getjourney of class GPS_SOA called ------------>" + dt.toString());
            GPS_MS obj1 = new GPS_MS();
            list = obj1.executequery(query);
            //System.out.println("list size  -- " + list.size());
            //System.out.println("list val  -- " + list.get(0).toString());
        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
             lw.writetoFile("Exception in getjourney method of class GPS_SOA ------------>" + e + "----" + dt.toString());
        }
        return list;
    }
    
    public void setjourney(ArrayList list) {
        try {
            String journeyid = "";
            String vehicle = "";
            String route_id = "";
            String distance = "";
            String flag = "";
            String status = "";
            String str = "";
            String query = "";
            GPS_MS obj1 = new GPS_MS();
            String vals = "";
            boolean bool = false;
            lw.writetoFile("Method setjourney of class GPS_SOA called ------------>" + dt.toString());
            if (list.size() > 0) {
                String param[] = null;
                for (int i = 0; i < list.size(); i++) {
                    str = list.get(i).toString();
                    param = str.split(",");
                    
                    if (param.length > 0) {
                        journeyid = param[0];
                        vehicle = param[1];
                        route_id = param[2];
                        distance = param[3];
                        flag = param[4];
                        status = param[5];
                        if (!(flag.equals("10") && status.equals("closed"))) {
                            int in = Integer.parseInt(distance);
                            in = in + 200;
                            distance = in + "";
                            /*query = "insert into journey_master(vehicle_type,route_id,total_distance_covered,"
                             + "flag,journey_status) values(?,?,?,?,?)";
                             * */
                            query = "update journey_master "
                                    + "set vehicle_type = ? , "
                                    + "route_id = ? , "
                                    + "total_distance_covered = ? , "
                                    + "flag = ?, "
                                    + "journey_status = ?  "
                                    + "where journey_id = ? ";
                            vals = vehicle + "," + route_id + "," + distance + "," + 2 + "," + "transit" + "," + journeyid;
                            bool = obj1.insertquery(query, vals);
                            System.out.println("bool value  -- " + bool);
                        }
                        
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
            lw.writetoFile("Exception in setjourney method of class GPS_SOA ------------>" + e + "----" + dt.toString());
        }
        
    }
    
    public void revisejourney(ArrayList list) {
        try {
            String journeyid = "";
            String vehicle = "";
            String route_id = "";
            String distancecovered = "";
            String totaldistance = "";
            String flag = "";
            String status = "";
            String str = "";
            String query = "";
            GPS_MS obj1 = new GPS_MS();
            String vals = "";
            boolean bool = false;
            lw.writetoFile("Method setjourney of class GPS_SOA called ------------>" + dt.toString());
            if (list.size() > 0) {
                String param[] = null;
                for (int i = 0; i < list.size(); i++) {
                    str = list.get(i).toString();
                    param = str.split(",");
                    
                    if (param.length > 0) {
                        route_id = param[0];
                        totaldistance = param[1];
                        distancecovered = param[2];
                        status = param[3];
                        flag = param[4];
                        
                        if (!(flag.equals("10") && status.equals("closed"))) {
                            
                            if (Integer.parseInt(distancecovered) >= Integer.parseInt(totaldistance)) {
                                query = "update journey_master set journey_status = 'closed' , flag = 10 where route_id = ?;";
                                vals = route_id;
                                bool = obj1.insertquery(query, vals);
                                System.out.println("revisejourney  -- " + bool);
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Exception  -- " + e);
            lw.writetoFile("Exception in setjourney method of class GPS_SOA ------------>" + e + "----" + dt.toString());
        }
        
    }
}

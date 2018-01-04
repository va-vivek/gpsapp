package gpstracking;


import java.io.FileWriter;
import java.util.Map;
import java.util.ResourceBundle;

public class logwriter {

    static String filename = "";
    
     public static void main(String[] args) {
         logwriter obj = new  logwriter ();
         obj.writetoFile("test this");
         
     }
    
    public void writetoFile(String content) {
            String key ;
            String keyval[];
            Map<String, String> eval = null;
            FileWriter fout ;
        try {
            ResourceBundle rb = ResourceBundle.getBundle("props.sqlprops");
            filename = rb.getString("path");
            System.out.println("log path + : " + filename);
            fout = new FileWriter(filename, true);
            fout.write(content);
            fout.write("\n");
            fout.flush();
            fout.close();
        } catch (Exception e) {
            System.out.println(" Exception in writetoFile:" + e);
            //lw.writetoFile("Exception in writetoFile method of class logwriter ------------>" + e + "----" + dt.toString());
        }finally{
            try{
                key =null ;
                keyval = null;
                eval = null;
                fout = null ;
            }catch(Exception e1){
                
            }
        }
    }
}

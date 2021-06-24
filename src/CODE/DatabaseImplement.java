/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CODE;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DatabaseImplement {
    PreparedStatement pst;
    ResultSet rs;
    java.sql.Connection con;

    public DatabaseImplement() {
        this.pst = null;
        this.rs = null;
        this.con=null;
    }
    
    public boolean CheckDB(){
        boolean res=true;
        try {
            this.con=DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/7qUeBuEP2c","7qUeBuEP2c", "ecP70dodyv");
        } catch (Exception e) {
            return false;
        }
        return res;
    }
    
    public ResultSet newQuery(String query,Object[] values,String type){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            if(CheckDB()){
                pst = (PreparedStatement) con.prepareStatement(query);
                if(!(values.length==0)){
                    for(int i=0 ; i<values.length ; i++){
                        if(values[i].getClass().getSimpleName().equals("Integer")){
                            pst.setInt(i+1, (int)values[i]);
                        }else if(values[i].getClass().getSimpleName().equals("String")){
                            if(values[i].equals("null")){
                                pst.setString(i+1, null);
                            }else{
                                pst.setString(i+1, (String)values[i]);
                            }

                        }else if(values[i].getClass().getSimpleName().equals("Double")){
                            pst.setDouble(i+1, (Double)values[i]);
                        }
                    }
                }
                if(type.equals("DMS")){
                    pst.executeUpdate();
                }else{
                    rs = pst.executeQuery();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Unable to connect with the database.."+"\n"+"Check Internet connection","ERROR",JOptionPane.ERROR_MESSAGE);
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
        
    }
    
    public void terminateConnection() {
        try {
            con.close();
        } catch (Exception e) {
        }
    }
    
}

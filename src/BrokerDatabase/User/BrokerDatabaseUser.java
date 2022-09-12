/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrokerDatabase.User;

import BrokerDatabase.*;
import DomainObject.GeneralDObject;
import DomainObject.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class BrokerDatabaseUser extends BrokerDatabase {
    
     Connection conn = null;

    @Override
    public boolean makeConnection() {
        
        String Url;
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Url = "jdbc:mysql://127.0.0.1:3306/SlotMachine"; 
            conn = DriverManager.getConnection(Url, "root", "java");
            conn.setAutoCommit(false);
        }
        catch (SQLException | ClassNotFoundException ex) {
            
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        
        return true;
    }

    @Override
    public boolean insertRecord(GeneralDObject odo) {
        
        String query = "INSERT INTO " + odo.getClassName() + " VALUES (" + odo.getAtrValue() + ")";
        
        return executeUpdate(query);
    }
    
    @Override
    public boolean getCounter(GeneralDObject odo, AtomicInteger counter) {
        
        String sql = "SELECT Counter FROM Counter WHERE TableName = '" + odo.getClassName() + "'";
        
        ResultSet rs = null;
        Statement st = null;
        
        boolean signal = false;
        
        try{
            
            st = conn.prepareStatement(sql);
            rs = st.executeQuery(sql);
            signal = rs.next();
            counter.set(rs.getInt("Counter") + 1);
        }
        catch (SQLException  ex)  { 
              
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
                
            signal = false;
         } 
        finally {
            
            close(null, st, rs);
        }
         
        return signal;
    }

    @Override
    public boolean increaseCounter(GeneralDObject odo, AtomicInteger counter) {

        String  query = "UPDATE counter SET counter =" + counter.get() + " WHERE TableName = '" + odo.getClassName() + "'";
        
        return executeUpdate(query);
    }
    
    @Override
    public boolean deleteRecord(GeneralDObject odo) {
        
        String query = "DELETE FROM " + odo.getClassName() + " WHERE " + odo.getWhereCondition();
        
        return executeUpdate(query);
    }

    @Override
    public boolean deleteRecords(GeneralDObject odo, String where) {
        
        String query = "DELETE FROM " + odo.getClassName() + " " + where;
        
        return executeUpdate(query);
    }

    @Override
    public boolean updateRecord(GeneralDObject odo, GeneralDObject odoold) {
    
        String query = "UPDATE " + odo.getClassName() +  " SET " + odo.setAtrValue() +   " WHERE " + odoold.getWhereCondition();
        
        return executeUpdate(query);
    }

    @Override
    public boolean updateRecord(GeneralDObject odo) {
        
        String query = "UPDATE " + odo.getClassName() +  " SET " + odo.setAtrValue() +   " WHERE " + odo.getWhereCondition();
        
        return executeUpdate(query);
    }

    public boolean executeUpdate(String query) 
    {   
        Statement st=null;
    
        boolean signal = false;
        
  	try {   
            st  = conn.prepareStatement(query);
            
            int rowcount = st.executeUpdate(query);
            
            if (rowcount > 0) {
                
                 signal = true;   
            }
	} 
        catch (SQLException ex)  {  
                
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
                    
            signal = false;
                
        } 
        finally {
            
            close(null,st,null);
        }
        
	return signal;
    }

    @Override
    public GeneralDObject findRecord(GeneralDObject odo) {
        
        ResultSet rs = null;
        Statement st = null;
        
        String  query = "SELECT * FROM " + odo.getClassName() +  " WHERE " + odo.getWhereCondition();
       
        boolean signal; 
  	try {   
            st  = conn.prepareStatement(query);
                
            rs = st.executeQuery(query);
                
            signal = rs.next(); 
               
            if (signal == true) {
            
                odo = odo.getNewRecord(rs);
            }      
            else {
            
                odo = null;
            }
                    
	} 
        catch (SQLException  ex)  {
            
              Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            
            close(null,st,rs);
        }
	return odo;
    }

    @Override
    public List<GeneralDObject> findRecord(GeneralDObject odo, String where) {
    
        ResultSet rs = null;
        Statement st = null;
        String  query = "SELECT * FROM " + odo.getClassName() +  " " + where;
        List<GeneralDObject> ls = new ArrayList<>();
        
        boolean signal; 
        
  	try {   
            st  = conn.prepareStatement(query);
            rs = st.executeQuery(query);
                
            while(rs.next()) { 
                
                ls.add(odo.getNewRecord(rs));
            }
	} 
        catch (SQLException  ex)  {   
              
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            
            close(null,st,rs);
        }
	return ls;
    }

    @Override
    public boolean commitTransation() {
        
        try { 
            conn.commit();
	} 
        catch(SQLException esql){ 
            
            return false; 
        }
        
	return true;
    }
    

    @Override
    public boolean rollbackTransation() {
        
        try { 
            
            conn.rollback();
	} 
        catch(SQLException esql){ 
            
            return false;  
        }
		   
	return true;
    }

   

    @Override
    public void closeConnection() {
        
        close(conn,null,null);
    }
    
    public void close(Connection conn, Statement st, ResultSet rs) { 
        
        if (rs != null) {  
            
            try { rs.close(); 
            } 
            catch (SQLException ex) { 
                
                Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (st != null) { 
            
            try { 
                
                st.close();
            } catch (SQLException ex) { 
                
                    Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (conn != null) { 
          
          try { 
              
            conn.close();
          } catch (SQLException ex) { 
              
                 Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }

    @Override
    public int getRecordsNumber(GeneralDObject odo) {
        
        ResultSet rs = null;
        Statement st = null;
        int recordsNumber = 0;
        
        String  query = "SELECT * FROM " + odo.getClassName();
        
        boolean signal; 
        
  	try {   
            st  = conn.createStatement(
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
           
            rs = st.executeQuery(query);
            
            if (rs.last()) { 
                
                recordsNumber = rs.getRow();
            }
	} 
        catch (SQLException  ex)  {   
                   
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            close(null,st,rs);
        }
	return recordsNumber;
    }
    
    @Override
    public GeneralDObject getRecord(GeneralDObject odo, int index) {
        
        ResultSet rs = null;
        Statement st = null;
        
        String  query = "SELECT * FROM " + odo.getClassName();
        query = query + " order by " + odo.getNameByColumn(0) + " ASC LIMIT " + index + ",1";	
        
        boolean signal; 
  	
        try {   
            
            st  = conn.prepareStatement(query);
            rs = st.executeQuery(query);
                
            signal = rs.next(); 
                
            if (signal == true) {
            
                 odo = odo.getNewRecord(rs);
            }
            else {
            
                odo = null;
            }
                    
	} 
        catch (SQLException  ex)  {  
                   
                odo = null; Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            
            close(null,st,rs);
        }
        
	return odo;
    }

    @Override
    public int findRecordPosition(GeneralDObject odo) {
        
        ResultSet rs = null;
        Statement st = null;
        
        String  query = "SELECT (COUNT(*)) as position FROM " + odo.getClassName() +  " WHERE " + odo.getNameByColumn(0) + " < " + odo.getPrimaryKey();
        System.out.println("Query:" + query);
        
        boolean signal; 
        
  	try {   
            
            st  = conn.prepareStatement(query);
            rs = st.executeQuery(query);
            
            signal = rs.next(); 
            
            if (signal == true) {
                
                return Integer.parseInt(rs.getString("position"));
            }
	} 
        catch (SQLException  ex)  {   
                   
            Logger.getLogger(BrokerDatabaseUser.class.getName()).log(Level.SEVERE, null, ex);
               
        } 
        finally {
            
            close(null,st,rs);     
        }
        
	return -1;
    }
    
    public User findRecordByUsernameAndPassword(String username, String password) {
    
        ResultSet rs = null;
        Statement st = null;
        
        String  query = "SELECT * FROM user  WHERE username = '" + username + "' AND password = '" + password + "'";
        
        User user = new User();

        return user;
    }
    
}

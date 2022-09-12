/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrokerDatabase.User;

import BrokerDatabase.*;
import DomainObject.GeneralDObject;
import java.io.Serializable;

/**
 *
 * @author Ivan
 */
public class DbIterator implements Serializable {
    
    private static final long serialVersionUID = 5765842764802546974L;
    
    BrokerDatabase bdb;
    int recordsNumber;
    int currentRecord = -1; 
    GeneralDObject gdo;
    
    public DbIterator() {
        
    }
    
    public DbIterator(GeneralDObject gdo1) {
        
        this.gdo = gdo1;
        currentRecord = -1;
    }
    
    public void createDB() { 
        
        bdb = new BrokerDatabaseUser();
    }
    
    public Object first() {
        
        Object ob = getRecordsNumber();
        
        if(ob == null) {
            
            return  ob;
        }
        
        currentRecord = 0;
        ob = bdb.getRecord(gdo, currentRecord);
        
        bdb.closeConnection();
        
        return  ob;
    }
    
    public Object next() {
        
       Object ob = getRecordsNumber();
        
        if(ob == null) {
            
            return  ob;
        }
        
        if(recordsNumber <= (currentRecord + 1)) {
            
        }
        else {
            
            currentRecord++;
        }
        
        ob = bdb.getRecord(gdo, currentRecord);
        bdb.closeConnection();
        
        return  ob;
    }
    
    public Object previous() {
    
        Object ob = getRecordsNumber();
        
        if(ob == null) {
            
            return  ob;
        }
        
        if(currentRecord - 1 < 0) {
            
        }
        else {
            
            currentRecord--;
        }
        
        ob = bdb.getRecord(gdo, currentRecord);
        bdb.closeConnection();
        
        return  ob;
    }
    
    public Object last() {
        
        Object ob = getRecordsNumber();
        
        if(ob == null) {
            
            return  ob;
        }
        
        currentRecord = recordsNumber - 1;
        
        ob = bdb.getRecord(gdo, currentRecord);
        
        bdb.closeConnection();
        
        return ob;
    }
    
    public Object getRecordsNumber() {
        
        createDB();
        
        Object ob = new Object();
        
        bdb.makeConnection();
        
        recordsNumber = bdb.getRecordsNumber(gdo);
        
        if(recordsNumber == 0) {
            
            currentRecord = -1;
            
            bdb.closeConnection();
            
            return null;
       }
        
        return  ob;
    }
    
    public int getCurrentRecord() {
        
        return currentRecord;
    }
    
    public void setCurrentRecord(int currentRecord) {
        
        this.currentRecord = currentRecord;
    }
    
}

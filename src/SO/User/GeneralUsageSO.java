/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO.User;

import BrokerDatabase.BrokerDatabase;
import BrokerDatabase.User.BrokerDatabaseUser;

import DomainObject.GeneralDObject;

/**
 *
 * @author Ivan
 */
public abstract class GeneralUsageSO {
    
    static public BrokerDatabase bdb = new BrokerDatabaseUser();
    int recordsNumber;
    int currentRecord = -1;
    GeneralDObject gdo;
    
    synchronized public boolean GeneralUsageSO() {
        
        bdb.makeConnection();
        boolean signal = executeSO();
        
        if(signal == true) {
            
            bdb.commitTransation();
        }
        else {
            
            bdb.rollbackTransation();
        }
        
        bdb.closeConnection();
        
        return  signal;
    }
    
    abstract public boolean  executeSO();
    
}

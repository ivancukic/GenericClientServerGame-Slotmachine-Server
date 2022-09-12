/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO.User;

import DomainObject.User;
import TransferObject.GenericTransferObject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Ivan
 */
public class CCreateDC extends GeneralUsageSO {
    
    GenericTransferObject gto;
    AtomicInteger counter = new AtomicInteger();
    
    public void createDC(GenericTransferObject gto) {
        
        this.gto = gto;
        GeneralUsageSO();
    }

    @Override
    public boolean executeSO() {
        
        gto.setSignal(false);
        
        User user1 = (User) bdb.findRecord(gto.getDC());
        
        if(user1 == null) {
            
            if(!bdb.getCounter(gto.getDC(), counter)) {
                
                gto.message = gto.getDC().message1();
            }
            
            if(!bdb.increaseCounter(gto.getDC(), counter)) {
                
                gto.message = gto.getDC().message2();
            }
            
            gto.getDC().setID(counter.get());
            
            if(bdb.insertRecord(gto.getDC())) {
                
                gto.message = gto.getDC().message3();
                gto.setSignal(true);
            }
            else {
            
                gto.message = gto.getDC().message4();
            }
        }
        else {
            
            gto.message = "User exit";
        }
        
        return  gto.getSignal();
    }
    
    
    
}

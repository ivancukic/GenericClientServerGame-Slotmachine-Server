/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO.User;

import DomainObject.GeneralDObject;
import TransferObject.GenericTransferObject;

/**
 *
 * @author Ivan
 */
public class CDeleteDC extends GeneralUsageSO {
    
    GenericTransferObject gto;
    
    public void deleteDC(GenericTransferObject gto) {
        
        this.gto = gto;
        GeneralUsageSO();
    }

    @Override
    public boolean executeSO() {
        
        gto.signal = false;
        GeneralDObject gdo1 = bdb.findRecord(gto.getDC());
        
        if(gdo1 != null) {
            
            if(bdb.deleteRecord(gto.getDC())) {
                
                gto.message = gto.getDC().message5();
                gto.signal = true;
            }
            else {
                
                gto.message = gto.getDC().message6();
            }
        }
        else {
            
            gto.message = gto.getDC().message7();
        }
        
        return  gto.signal;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO.User;

import DomainObject.GeneralDObject;
import static SO.User.GeneralUsageSO.bdb;
import TransferObject.GenericTransferObject;

/**
 *
 * @author Ivan
 */
public class CChangeDC extends GeneralUsageSO {
    
    GenericTransferObject gto;
    
    public void changeDC(GenericTransferObject gto) {
        
        this.gto = gto;
        GeneralUsageSO();
    }

    @Override
    public boolean executeSO() {
        
        gto.signal = false;
        GeneralDObject gdo1 = bdb.findRecord(gto.getDC());
        
        if(gdo1 != null) {
            
            if(bdb.updateRecord(gto.getDC())) {
                
                gto.message = gto.getDC().message8();
                gto.signal = true;
            }
            else {
            
                gto.message = gto.getDC().message9();
            }
        }
        else {
            
            gto.message = gto.getDC().message10();
        }
        
        return gto.signal;
    }
    
}

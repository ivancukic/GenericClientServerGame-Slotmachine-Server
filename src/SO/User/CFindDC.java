/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO.User;

import TransferObject.GenericTransferObject;

/**
 *
 * @author Ivan
 */
public class CFindDC extends GeneralUsageSO {
    
    GenericTransferObject gto;
    
    public void findDC(GenericTransferObject gto) {
        
        this.gto = gto;
        GeneralUsageSO();
    }

    @Override
    public boolean executeSO() {
        
        gto.signal = false;
        gto.setDC(bdb.findRecord(gto.getDC()));
        
        if(gto.getDC() != null) {
        
            gto.signal = true;
            gto.currentRecord = bdb.findRecordPosition(gto.getDC());
            System.out.println("Position of record: " + gto.currentRecord);
        }
        else {
        
            gto.currentRecord = -1;
        }
        
        return gto.signal;
    }
    
    
    
}

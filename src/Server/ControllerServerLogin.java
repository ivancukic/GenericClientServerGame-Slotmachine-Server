/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DomainObject.User;

/**
 *
 * @author Ivan
 */
public class ControllerServerLogin {
    
    public static void main(String[] args) throws Exception {
        
        GenericControllerServer gcs = new GenericControllerServer(new User());
        gcs.executeGenericController();
        
    }
    
}

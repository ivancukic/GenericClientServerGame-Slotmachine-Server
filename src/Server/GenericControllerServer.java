/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import BrokerDatabase.User.DbIterator;
import DomainObject.GeneralDObject;
import SO.User.*;
import TransferObject.GenericTransferObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class GenericControllerServer {
    
    static ServerSocket ss;
    static List<Client> listOfUsers = new ArrayList<>();
    static int numOfClient;
    GeneralDObject gdo;
    
    public GenericControllerServer(GeneralDObject gdo) {
    
        this.gdo = gdo;
    }
    
    void executeGenericController() throws Exception {
        
        ss = new ServerSocket(8189);
        System.out.println("Server runing...");
        
        while(true) {
            
            Socket socketS = ss.accept();
            Client cl = new Client(socketS, ++numOfClient);
            listOfUsers.add(cl);
        }
    }
}    
    class Client extends Thread {
    
    private Socket socketS;
    ObjectOutputStream out;
    ObjectInputStream in;
    
    int numOfClient;
    
    public Client(Socket socketS1, int numOfClient) {
        
        socketS = socketS1;
        this.numOfClient = numOfClient;
        
        System.out.println("Client: " + numOfClient + " has made connection!!!");
    
        start();
    }
   
    @Override
    public void run() {
        
        try {
            
            String signal = "";
            
            GenericTransferObject gto;
            
            while(true) {
            
                out = new ObjectOutputStream(socketS.getOutputStream());
		in = new ObjectInputStream(socketS.getInputStream());
                       
                       
                gto = (GenericTransferObject) in.readObject();
                
            
                if (gto.nameOfOperation.equals("createDC"))
                    { createDC(gto);}
                if (gto.nameOfOperation.equals("changeDC"))
                    { changeDC(gto);}
                if (gto.nameOfOperation.equals("deleteDC"))
                    { deleteDC(gto);}
                if (gto.nameOfOperation.equals("findDC"))
                    { findDC(gto);}
                if (gto.nameOfOperation.equals("onFirst"))
                    { onFirst(gto);}
                if (gto.nameOfOperation.equals("onNext"))
                    { onNext(gto);}
                if (gto.nameOfOperation.equals("onPrevious"))
                    { onPrevious(gto);}
                if (gto.nameOfOperation.equals("onLast"))
                    { onLast(gto); }
                if (gto.nameOfOperation.equals("findByUsernameAndPassword")) 
                    { onLast(gto); }
                out.writeObject(gto); 
            }
        }
        catch (IOException | ClassNotFoundException e)  { System.out.println(e);  }
    }
    
    public GenericTransferObject createDC(GenericTransferObject gto) {
        
        new CCreateDC().createDC(gto);
        
        return gto;
    }
    
    public GenericTransferObject changeDC(GenericTransferObject gto) {
        
        new CChangeDC().changeDC(gto);
        
        return gto;
    }
    
    public GenericTransferObject deleteDC(GenericTransferObject gto) {
        
        new CDeleteDC().deleteDC(gto);
        
        return gto;
    }
    
    
    public GenericTransferObject findDC(GenericTransferObject gto) {
        
        new CFindDC().findDC(gto);
        
        return gto;
    }
    
    public GenericTransferObject onFirst(GenericTransferObject gto) {
        
        DbIterator dbi = new DbIterator(gto.getDC());
        
        gto.setDC((GeneralDObject) dbi.first());
        
        gto.signal = (gto.getDC() == null) ? false : true;
        
        gto.currentRecord = dbi.getCurrentRecord();
        
        return gto;
    }
    
    public GenericTransferObject onNext(GenericTransferObject gto) {
        
        DbIterator dbi = new DbIterator(gto.getDC());
        
        dbi.setCurrentRecord(gto.currentRecord);
        
        gto.setDC((GeneralDObject) dbi.next());
        
        gto.signal = (gto.getDC() == null) ? false : true;
        
        gto.currentRecord = dbi.getCurrentRecord();
        
        return gto;
    }
    
    public GenericTransferObject onPrevious(GenericTransferObject gto) {
        
        DbIterator dbi = new DbIterator(gto.getDC());
        
        dbi.setCurrentRecord(gto.currentRecord);
        
        gto.setDC((GeneralDObject) dbi.previous());
        
        gto.signal = (gto.getDC() == null) ? false : true;
        
        gto.currentRecord = dbi.getCurrentRecord();
        
        return gto;
    }
    
    public GenericTransferObject onLast(GenericTransferObject gto) {
        
        
        DbIterator dbi = new DbIterator(gto.getDC());
        
        gto.setDC((GeneralDObject) dbi.last());
        
        gto.signal = (gto.getDC() == null) ? false : true;
        
        gto.currentRecord = dbi.getCurrentRecord();
        
        return gto;
    }
    
    public GenericTransferObject findByUsernameAndPassword(GenericTransferObject gto, String username, String password) {
        
        
        DbIterator dbi = new DbIterator(gto.getDC());
        
        gto.setDC((GeneralDObject) dbi.last());
        
        gto.signal = (gto.getDC() == null) ? false : true;
        
        gto.currentRecord = dbi.getCurrentRecord();
        
        return gto;
    }
    
}

 /* GenericTransferObject.java 
 * @autor Ivan Cukic,  
 * Univerzitet u Beogradu'
 * Fakultet organizacionih nauka 
 * Katedra za softversko inzenjerstvo 
 * Laboratorija za softversko inzenjerstvo 
 * Datum:2022-09-03 
 */ 
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransferObject;
 
 
 
import DomainObject.*;
import java.io.Serializable;
 
public class GenericTransferObject implements Serializable{
    
    private static final long serialVersionUID = 5765842764802546974L;
    
   public GeneralDObject gdo; 
   public String message;
   public boolean signal; // signal o uspesnosti izvrsenja operacije.
   public int currentRecord = -1;
  public String nameOfOperation;
      
   public void setDC(GeneralDObject gdo)  {this.gdo = gdo;}
   public String getMessage(){return message;}
   public boolean getSignal(){return signal;} 
   public GeneralDObject getDC(){return (GeneralDObject) gdo;}
   public void setSignal(boolean signal) {this.signal = signal;}
 public void setNameOfOperation(String nameOfOperation) {this.nameOfOperation = nameOfOperation;}
}
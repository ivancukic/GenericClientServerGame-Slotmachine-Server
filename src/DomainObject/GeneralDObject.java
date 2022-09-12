/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainObject;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 */
public abstract class GeneralDObject implements Serializable {
    
    private static final long serialVersionUID = 5765842764802546974L;
    
  abstract public String getAtrValue();
  abstract public String setAtrValue();
  abstract public String getClassName();
  abstract public String getWhereCondition();
  abstract public String getNameByColumn(int column);
  abstract public GeneralDObject getNewRecord(ResultSet rs) throws SQLException; 
  abstract public int getPrimaryKey();
  abstract public void setID(int id);
  abstract public String message1();
  abstract public String message2();
  abstract public String message3();
  abstract public String message4();
  abstract public String message5();
  abstract public String message6();
  abstract public String message7();
  abstract public String message8();
  abstract public String message9();
  abstract public String message10();
    
}

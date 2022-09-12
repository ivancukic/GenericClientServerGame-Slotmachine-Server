
/* @autor Ivan Cukic,  
 * Univerzitet u Beogradu'
 * Fakultet organizacionih nauka 
 * Katedra za softversko inzenjerstvo 
 * Laboratorija za softversko inzenjerstvo 
 /*  * Datum:2022-09-02 Problem with counter of user.
 */
package DomainObject;
 
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
 
 public class User  extends GeneralDObject implements Serializable{
     
    private static final long serialVersionUID = 5765842764802546974L; 
     
    public int idUser; 
    public String username; 
    public String password; 
 
    public User()
        { 
           idUser=0; 
           username=""; 
           password=""; 
         }
   
    // primarni kljuc
    public User( int idUser)
      { this.idUser = idUser;
          }
    
    @Override
    public void setID(int id)
      {  this.idUser =id; }
 
   public int getPrimaryKey()
      {return this.idUser;}  
   
 
 public  int getIdUser(){ return idUser;}  
 public  String getusername(){ return username;}  
 public  String getpassword(){ return password;}  
 
 public  void setidUser(int idUser){this.idUser = idUser;}  
 public  void setusername(String username){this.username = username;}  
 public  void setpassword(String password){this.password = password;}  
    
 @Override 
 public GeneralDObject getNewRecord(ResultSet rs) throws SQLException 
 {return new User.Builder(rs.getString("username"), rs.getString("password")).idUser(rs.getInt("idUser")).build();} 
 
 @Override 
 public String getAtrValue() {return  idUser + ", " +   (username == null ? null : "'" + username + "'") + ", " +   (password == null ? null : "'" + password + "'");} 
 @Override 
 public String setAtrValue(){return "idUser=" +  idUser + ", " +  "username=" +  (username == null ? null : "'" + username + "'") + ", " +  "password=" +  (password == null ? null : "'" + password + "'");} 
 @Override 
 public String getClassName(){return "user";} 
 @Override 
 public String getWhereCondition(){return "idUser = " + idUser;} 
 
@Override
public String getNameByColumn(int column) 
 { String names[] = {"idUser", "username", "password"}; 
 return names[column]; 
 } 
 
public String[] getNameAtributes() 
 { String names[] = {"idUser", "username", "password"}; 
 return names; 
 } 
    
    @Override
    public String message1() {return "Problem with counter of user.";}
    @Override
   public String message2() {return "The counter can't increase.";}
    @Override
  public String message3() {return "User is created.";}
    @Override
   public String message4() {return "User can't be created.";}
    @Override
    public String message5() {return "User is deleted.";}
    @Override
    public String message6() {return "User can not be deleted.";}
    @Override
    public String message7() {return "User can't be deleted because he doesen't exist.";}
    @Override
    public String message8() {return "User is changed.";}
    @Override
    public String message9() {return "User can't be changed.";}
    @Override
    public String message10() {return "User can't be changed because he doesen't exist.";}
 
    // PATTERN
    public static class Builder {
    
        public int idUser; 
        public String username; 
        public String password; 
        
        public Builder(String username, String password) {
        
            this.username = username;
            this.password = password;
        }
        
        public Builder idUser(int idUser) {
            
            this.idUser = idUser;
            
            return this;
        }
        
        public User build() {
            
            return new User(this);
        }
    }
    
    public User (Builder builder) {
    
        this.idUser = builder.idUser;
        this.username = builder.username;
        this.password = builder.password;
    }
   
}
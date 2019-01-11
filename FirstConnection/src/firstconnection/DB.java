
package firstconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    
    final String JDBC_DRIVER ="org.apache.derby.jdbc.EmbeddedDriver";
    final String URL ="jdbc:derby:sampleDB;create=true";
    final String USERNAME ="";
    final String PASSWORD="";
    
    //Létrehozzuk a kapcsolatot
    Connection conn=null;
    
    Statement createStatement = null;
    
    public DB(){
        
        
        

            //megpróbáljuk életre kelteni
            try {
                conn = DriverManager.getConnection(URL);
                System.out.println("a híd létrejött");
            } catch (SQLException ex) {
                System.out.println("valami baj van a connection létrehozásakor");
                System.out.println("" + ex);
            }
            //ha életre kelt, csinálunk egy megpakolható teherautót

            if(conn!=null){
                try {
                    createStatement = conn.createStatement();
                } catch (SQLException ex) {
                    System.out.println("valami baj van a createstatement létrehozásakor");
                    System.out.println("" + ex);
                }
            }
            //megnézzük, hogy üres e az adatbázis
        DatabaseMetaData dbmd = null;
            try {
                dbmd = conn.getMetaData();
            } catch (SQLException ex) {
                System.out.println("valami baj van a databasemetadata (adatbázis leírása) létrehozásakor");
                System.out.println("" + ex);
            }
            
        
        try {
            ResultSet rs1 = dbmd.getTables(null, "APP", "USERS", null);
            //ha rs1-nek nincs következő eleme akkor még nem futott le, ezért létrehozzuk
            if(!rs1.next()){
                createStatement.execute("create table users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("valami baj van az adattáblák létrehozásakor");
            System.out.println("" + ex);
        }
    }
    
    public void addUser(String name, String address){
        try {
            createStatement.execute("insert into users values ('Gyula','Budapest'), ('Mari','Budapest')");
        } catch (SQLException ex) {
            System.out.println("valami baj van a user hozzáadásakor");
            System.out.println("" + ex);
        }
    }
}

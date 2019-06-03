package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    public Connection connection;

    public Connection getConnectionAdmin(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="user01";
        String password="1";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(url,username,password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnectionEmployee(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="user02";
        String password="1";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(url,username,password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnectionManager(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="user03";
        String password="1";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(url,username,password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnectionShipper(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="user04";
        String password="1";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(url,username,password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }
}

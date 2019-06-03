package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chucvu {
    private String macv;
    private String tencv;

    public Chucvu(String macv, String tencv) {
        this.macv = macv;
        this.tencv = tencv;
    }

    public Chucvu() {
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getTencv() {
        return tencv;
    }

    public void setTencv(String tencv) {
        this.tencv = tencv;
    }

    public static ObservableList<String> loadMaCV(){
        String sql_query_CVList = "select * from chucvu";

        //Database Connection
        ConnectionClass DbConnection = new ConnectionClass();

        ObservableList<String> listCongviec=null;
        try{
            listCongviec = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_CVList);
            while(rs.next()){
                listCongviec.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCongviec;
    }
}

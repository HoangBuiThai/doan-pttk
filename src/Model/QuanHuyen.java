package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuanHuyen {
    private String maQuan;
    private String tenQuan;

    public QuanHuyen() {
    }

    public QuanHuyen(String maQuan, String tenQuan) {
        this.maQuan = maQuan;
        this.tenQuan = tenQuan;
    }

    public String getMaQuan() {
        return maQuan;
    }

    public void setMaQuan(String maQuan) {
        this.maQuan = maQuan;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public static ObservableList<String> loadMaQuan(){
        String sql_query_quanhuyenList = "select * from quanhuyen";

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        ObservableList<String> listQuanHuyen=null;
        try{
            listQuanHuyen = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_quanhuyenList);
            while(rs.next()){
                listQuanHuyen.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listQuanHuyen;
    }
}

package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;


public class Anpham {

    private String maap;
    private String manxb;
    private String tenap;
    private int giatien;

    public Anpham(String maap, String manxb, String tenap, int giatien) {
        this.maap = maap;
        this.manxb = manxb;
        this.tenap = tenap;
        this.giatien = giatien;
    }

    public Anpham() {
    }

    public String getMaap() {
        return maap;
    }

    public void setMaap(String maap) {
        this.maap = maap;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getTenap() {
        return tenap;
    }

    public void setTenap(String tenap) {
        this.tenap = tenap;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public static ObservableList<Anpham> HienThiAnPham(){
        ObservableList<Anpham> listAnpham = null;

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        String sql_query_anpham = "select * from anpham order by maap";
        try{
            listAnpham = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_anpham);
            while(rs.next()){
                listAnpham.add(new Anpham(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAnpham;
    }

    public static void themAnPham(String manxb,String tenap, String gia ){
        String sql_insert_anpham = "{call themanpham(?,?,?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_insert_anpham);
            callableStatement.setString(1,manxb);
            callableStatement.setString(2,tenap);
            callableStatement.setInt(3,Integer.parseInt(gia));

            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void suaAnPham(String maap, String manxb, String tenap, String gia){
        String sql_update_anpham = "{call suaanpham(?,?,?,?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_update_anpham);
            callableStatement.setString(1,maap);
            callableStatement.setString(2,manxb);
            callableStatement.setString(3,tenap);
            callableStatement.setInt(4,Integer.parseInt(gia));
            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xoaAnPham(String maap){
        String sql_del_anpham = "{call xoaanpham(?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_del_anpham);
            callableStatement.setString(1,maap);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void timAnpham(TextField TimAP, FilteredList filter, TableView tableAP){
        TimAP.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super Anpham>) ap->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }else if(ap.getTenap().toUpperCase().contains(newValue.toUpperCase())){
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableAP.comparatorProperty());
        tableAP.setItems(sort);
    }
}

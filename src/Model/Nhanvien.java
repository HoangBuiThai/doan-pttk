package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

public class Nhanvien {
    private String manv;
    private String maQuan;
    private String macv;
    private String Hoten;
    private Date Ngayvaolam;
    private String username;
    private String password;

    public Nhanvien(String manv, String maQuan, String macv, String hoten, Date ngayvaolam, String username, String password) {
        this.manv = manv;
        this.maQuan = maQuan;
        this.macv = macv;
        Hoten = hoten;
        Ngayvaolam = ngayvaolam;
        this.username = username;
        this.password = password;
    }

    public Nhanvien() {
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getMaQuan() {
        return maQuan;
    }

    public void setMaQuan(String maQuan) {
        this.maQuan = maQuan;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public Date getNgayvaolam() {
        return Ngayvaolam;
    }

    public void setNgayvaolam(Date ngayvaolam) {
        Ngayvaolam = ngayvaolam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ObservableList<Nhanvien> HienThiNhanVien(){
        ObservableList<Nhanvien> listNhanvien = null;
        String sql_query_nhanvienList = "Select * from nhanvien order by manv";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        try{
            listNhanvien = FXCollections.observableArrayList();
            Connection conn= DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_nhanvienList);
            while(rs.next()){
                listNhanvien.add(new Nhanvien(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getString(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNhanvien;
    }

    public static ObservableList<Nhanvien> loadNhanVienByCV(String macv){
        ObservableList<Nhanvien> listNhanvien = null;
        String sql_query_nhanvienList = "Select * from nhanvien where macv='"+macv+"' order by manv";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        try{
            listNhanvien = FXCollections.observableArrayList();
            Connection conn= DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_nhanvienList);
            while(rs.next()){
                listNhanvien.add(new Nhanvien(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getString(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNhanvien;
    }

    public static void themNhanVien( String maquan,String macv, String tennv, String username, String password){
        String sql_insert_nhanvien = "{call THEMNHANVIEN(?,?,?,to_date(?, 'DD-MM-YYYY'),?,?)}";

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //c.add(Calendar.DATE, 60);
        date=c.getTime();
        System.out.println(dateFormat.format(date));

            try {
                Connection conn = DbConnection.getConnectionAdmin();
                callableStatement = conn.prepareCall(sql_insert_nhanvien);
                callableStatement.setString(1, maquan);
                callableStatement.setString(2, macv);
                callableStatement.setString(3, tennv);
                callableStatement.setString(4, dateFormat.format(date));
                callableStatement.setString(5, username);
                callableStatement.setString(6, password);

                callableStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();

        }
    }

    public static void xoaNhanVien(String manv){
        String sql_delete_nhanvien = "{call XOANHANVIEN(?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement =conn.prepareCall(sql_delete_nhanvien);
            callableStatement.setString(1,manv);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void suaNhanVien(String manv, String maquan, String macv, String tennv, String username, String password){
        String sql_update_nhanvien = "{call SUANHANVIEN(?,?,?,?,?,?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_update_nhanvien);
            callableStatement.setString(1,manv);
            callableStatement.setString(2,maquan);
            callableStatement.setString(3,macv);
            callableStatement.setString(4,tennv);
            callableStatement.setString(5,username);
            callableStatement.setString(6,password);

            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void timNhanVien(TextField TimNV, FilteredList filter, TableView tableNV){
        TimNV.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super Nhanvien>) nv->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }else if(nv.getHoten().toUpperCase().contains(newValue.toUpperCase())){
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableNV.comparatorProperty());
        tableNV.setItems(sort);
    }

    public static void timNhanvienGiaoHangTheoQuan(TextField MaQuan, FilteredList filter, TableView tableNV){
        MaQuan.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super Nhanvien>) nv->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }else if(nv.getMaQuan().toUpperCase().contains(newValue.toUpperCase())){
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableNV.comparatorProperty());
        tableNV.setItems(sort);
    }
}

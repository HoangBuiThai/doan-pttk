package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.List;
import java.util.function.Predicate;


public class KhachHang {

    private String makh;
    private String maQuan;
    private String hoten;
    private String CMND;
    private String diachi;
    private String sdt;
    private String tenbao;

    public KhachHang() {

    }

    public KhachHang(String makh, String maQuan, String hoten, String CMND, String diachi, String sdt) {
        this.makh = makh;
        this.maQuan = maQuan;
        this.hoten = hoten;
        this.CMND = CMND;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public KhachHang(String hoten, String diachi, String tenbao) {
        this.hoten = hoten;
        this.diachi = diachi;
        this.tenbao = tenbao;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getMaQuan() {
        return maQuan;
    }

    public void setMaQuan(String maQuan) {
        this.maQuan = maQuan;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenbao() {
        return tenbao;
    }

    public void setTenbao(String tenbao) {
        this.tenbao = tenbao;
    }

    public static ObservableList<KhachHang> HienThiKhachHang(){
        ObservableList<KhachHang> khachHangList=null;

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();;

        String sql_query_khList = "Select * from khachhang order by makh";
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            khachHangList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_khList);
            while(rs.next()){
                khachHangList.add(new KhachHang(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachHangList;

    }

    public static KhachHang layThongtinKHbyMa(String makh){
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
       //PreparedStatement pst;

        String sql_query_kh = "select * from KHACHHANG where makh='"+makh+"'";
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            //pst = conn.prepareStatement(sql_query_kh);
            ResultSet rs = conn.createStatement().executeQuery(sql_query_kh);
            while (rs.next()){
                KhachHang kh = new KhachHang(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void themKhachHang(String maquan,String hoten,String cmnd, String diachi,String sdt){
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();;
        CallableStatement callableStatement;

        String sql_insert_kh = "{call THEMKHACHHANG(?,?,?,?,?)}";
        try {
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_insert_kh);
            callableStatement.setString(1, maquan);
            callableStatement.setString(2, hoten);
            callableStatement.setString(3, cmnd);
            callableStatement.setString(4, diachi);
            callableStatement.setString(5, sdt);

            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void suaKhachHang(String makh, String hoten, String cmnd, String diachi, String sdt, String maquan){
        //Database connection
         ConnectionClass DbConnection = new ConnectionClass();
         CallableStatement callableStatement;

        String sql_update_kh ="{call SUAKHACHHANG(?,?,?,?,?,?)}";
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_update_kh);

            callableStatement.setString(1,makh);
            callableStatement.setString(2,hoten);
            callableStatement.setString(3,cmnd);
            callableStatement.setString(4,diachi);
            callableStatement.setString(5,sdt);
            callableStatement.setString(6,maquan);

            callableStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void timKhachHang(TextField TimKH, FilteredList filter, TableView tableKH){
        TimKH.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super KhachHang>) kh->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }else if(kh.getHoten().contains(newValue)){
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableKH.comparatorProperty());
        tableKH.setItems(sort);
    }

    public static ObservableList<KhachHang> layDiaChiGiaoHang(String maquan) {

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        ObservableList<KhachHang>listShipperTable=null;
        String sql_query_shipperTable = "{call giaohang_table(?,?,?,?,?)}";
        try {
            listShipperTable = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_query_shipperTable);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setString(2, maquan);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);

            callableStatement.executeQuery();

            ResultSet rs = (ResultSet) callableStatement.getObject(1);
            while (rs.next()) {
                listShipperTable.add(new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listShipperTable;
    }

}



package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import oracle.jdbc.OracleTypes;

import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;



public class CTHD {

    private String mahd;
    private String maap;
    private Date ngayketthuc;
    private int dongia;
    private String tenap;
    private int soluong;

    public String getTenap() {
        return tenap;
    }

    public void setTenap(String tenap) {
        this.tenap = tenap;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public CTHD(String maap, String tenap, int soluong) {
        this.maap = maap;
        this.tenap = tenap;
        this.soluong = soluong;
    }

    public CTHD(String mahd, String maap, Date ngayketthuc, int dongia) {
        this.mahd = mahd;
        this.maap = maap;
        this.ngayketthuc = ngayketthuc;
        this.dongia = dongia;
    }

    public CTHD() {
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getMaap() {
        return maap;
    }

    public void setMaap(String maap) {
        this.maap = maap;
    }

    public Date getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(Date ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public static ObservableList<CTHD> HienThiCTHD(String mahd){
        String sql_query_cthd = "Select * from cthd where mahd='"+mahd+"'";
        ObservableList<CTHD> list_cthd = null;
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        try{
            Connection conn = DbConnection.getConnectionAdmin();
            list_cthd = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_cthd);
            while(rs.next()){
                list_cthd.add(new CTHD(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list_cthd;
    }

    public static void themCTHD(Integer time, String mahd, String maap, String gia){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, (time*30));
        date=c.getTime();
        System.out.println(dateFormat.format(date));

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        String sql_insert_cthd = "{call themCTHD(?,?,to_date(?, 'DD-MM-YYYY'),?)}";


        try {
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_insert_cthd);
            callableStatement.setString(1, mahd);
            callableStatement.setString(2, maap);
            callableStatement.setString(3, dateFormat.format(date));
            callableStatement.setInt(4, Integer.parseInt(gia) * (time));
            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xoaCTHD(String mahd, String maap){
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        String sql_delete_cthd = "{call xoaCTHD(?,?)}";

        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_delete_cthd);
            callableStatement.setString(1,mahd);
            callableStatement.setString(2,maap);
            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<CTHD> DanhSachKiemKho() throws SQLException {
        //Database Connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        ObservableList<CTHD> list_kiemkho = null;

        String sql_query_dskho = "{call kiemkho_table(?,?,?,?)}";

        try {
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_query_dskho);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.INTEGER);

            callableStatement.executeQuery();
            list_kiemkho = FXCollections.observableArrayList();
            ResultSet rs = (ResultSet) callableStatement.getObject(1);
            while (rs.next()) {
                list_kiemkho.add(new CTHD(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list_kiemkho;
    }
}

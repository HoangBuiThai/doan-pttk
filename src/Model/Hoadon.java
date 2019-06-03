package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Hoadon {
    private String mahd;
    private String makh;
    private String manv;
    private Date ngaylap;
    private int tongtien;
    private int thanhtoan;


    public Hoadon(String mahd, String makh, String manv, Date ngaylap, int tongtien,int thanhtoan) {
        this.mahd = mahd;
        this.makh = makh;
        this.manv = manv;
        this.ngaylap = ngaylap;
        this.tongtien = tongtien;
        this.thanhtoan = thanhtoan;
    }

    public Hoadon() {
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public Date getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(Date ngaylap) {
        this.ngaylap = ngaylap;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(int thanhtoan) {
        this.thanhtoan = thanhtoan;
    }

    public static ObservableList<Hoadon> HienThiHoaDon(String makh){
        ObservableList<Hoadon> list_hd = null;
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        String sql_query_hd = "Select * from hoadon where makh='"+makh+"'";
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_hd);
            list_hd = FXCollections.observableArrayList();

            while(rs.next()) {
                list_hd.add(new Hoadon( rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),rs.getInt(5),rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_hd;
    }

    public static String lapHD(String makh, String manv){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //c.add(Calendar.DATE, 60);
        date=c.getTime();
        System.out.println(dateFormat.format(date));

        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        Connection conn = DbConnection.getConnectionAdmin();
        CallableStatement callableStatement;

        String sql_insert_hd ="{call THEMHOADON(?,?,to_date(?, 'DD-MM-YYYY'))}";
        try{

            callableStatement = conn.prepareCall(sql_insert_hd);

            callableStatement.setString(1,makh);
            callableStatement.setString(2,manv);
            callableStatement.setString(3,dateFormat.format(date));

            callableStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        String mahd ="";
        String sql_query_mahd = "Select mahd from (Select mahd from hoadon where makh='"+makh+"' order by mahd DESC) where rownum=1";
        try {
            ResultSet rs =conn.createStatement().executeQuery(sql_query_mahd);
            while(rs.next()){
                mahd=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mahd;
    }

    public static void inHD(String manv,String tennv,String makh,String mahd,int tongtien){
        try{
            //Sửa 2 đường dẫn này trung với đường dẫn hiện tại của máy (Lưu ý đừng sửa chữ mahd trong url_report_output)!!! <3 GOODLUCK
            String url_report_input = "C:\\Users\\BùiTháiHoàng\\Desktop\\doan-javafx\\src\\Report\\HD.jrxml";
            String url_report_output = "C:\\Users\\BùiTháiHoàng\\Desktop\\doan-javafx\\src\\Report\\"+mahd+".pdf";

            KhachHang kh = KhachHang.layThongtinKHbyMa(makh);

            JasperReport jasperReport = JasperCompileManager.compileReport(url_report_input);


            ConnectionClass DbConnection = new ConnectionClass();
            Connection conn = DbConnection.getConnectionAdmin();

            Map<String,Object> parameters = new HashMap<String,Object>();
            parameters.put("v_mahd",mahd);
            parameters.put("v_manv",manv);
            parameters.put("v_tennv",tennv);
            parameters.put("v_makh",kh.getMakh());
            parameters.put("v_tenkh",kh.getHoten());
            parameters.put("v_diachi",kh.getDiachi());
            parameters.put("v_sdt",kh.getSdt());
            parameters.put("v_tongtien",String.valueOf(tongtien));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,conn);

            JasperExportManager.exportReportToPdfFile(jasperPrint,url_report_output);
            JasperViewer.viewReport(jasperPrint,false);
            System.out.println("hello");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void thanhtoan(String mahd){
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        Connection conn = DbConnection.getConnectionAdmin();
        CallableStatement callableStatement;

        String sql_update_hd = "{call thanhtoanhd(?)}";
        try {
            callableStatement = conn.prepareCall(sql_update_hd);
            callableStatement.setString(1,mahd);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

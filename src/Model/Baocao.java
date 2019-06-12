package Model;

import DbConnection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Baocao {
    private Date ngaybaocao;
    private String noidung;
    private String manv;

    public Baocao(Date ngaybaocao, String noidung, String manv) {
        this.ngaybaocao = ngaybaocao;
        this.noidung = noidung;
        this.manv = manv;
    }

    public Baocao() {
    }

    public Date getNgaybaocao() {
        return ngaybaocao;
    }

    public void setNgaybaocao(Date ngaybaocao) {
        this.ngaybaocao = ngaybaocao;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public static void themBaocao(String noidung, String manv){
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

        String sql_insert_baocao = "{call thembaocao(to_date(?, 'DD-MM-YYYY'),?,?)}";
        try{
            callableStatement = conn.prepareCall(sql_insert_baocao);
            callableStatement.setString(1,dateFormat.format(date));
            callableStatement.setString(2,noidung);
            callableStatement.setString(3,manv);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Baocao> HienThiBaoCao(){
        ObservableList<Baocao> listBC = null;

        //Database Connection
        ConnectionClass DbConnection = new ConnectionClass();

        String sql_query_baocao = "Select * from baocao order by ngaybaocao desc";

        try{
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_baocao);
            listBC = FXCollections.observableArrayList();
            while(rs.next()){
                listBC.add(new Baocao(rs.getDate(1),rs.getString(2),rs.getString(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBC;
    }
}

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

public class NXB {
    private String manxb;
    private String tennxb;

    public NXB(String manxb, String tennxb) {
        this.manxb = manxb;
        this.tennxb = tennxb;
    }

    public NXB() {
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getTennxb() {
        return tennxb;
    }

    public void setTennxb(String tennxb) {
        this.tennxb = tennxb;
    }

    public static ObservableList<NXB> HienThiNXB(){
        ObservableList<NXB> listNXB = null;

        String sql_query_NXBList = "select * from NHAXUATBAN where isDelete=0";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();

        try{
            listNXB = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            ResultSet rs = conn.createStatement().executeQuery(sql_query_NXBList);
            while(rs.next()){
                listNXB.add(new NXB(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNXB;
    }



    public static void themNXB(String manxb, String tennxb){
        String sql_insert_NXB = "{call THEMNXB(?,?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_insert_NXB);
            callableStatement.setString(1,manxb);
            callableStatement.setString(2,tennxb);

            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void suaNXB(String manxb,String tennxb){
        String sql_update_nxb = "{call SUANXB(?,?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;

        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_update_nxb);
            callableStatement.setString(1,manxb);
            callableStatement.setString(2,tennxb);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xoaNXB(String manxb){
        String sql_delete_nxb = "{call XOANXB(?)}";
        //Database connection
        ConnectionClass DbConnection = new ConnectionClass();
        CallableStatement callableStatement;
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_delete_nxb);
            callableStatement.setString(1,manxb);
            callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void timNXB(TextField TimNXB, FilteredList filter, TableView tableNXB){
        TimNXB.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super NXB>) ap->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }else if(ap.getTennxb().toUpperCase().contains(newValue.toUpperCase())){
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableNXB.comparatorProperty());
        tableNXB.setItems(sort);
    }
}

package Controller;

import DbConnection.ConnectionClass;
import Model.KhachHang;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class shipperController implements Initializable {

    @FXML
    private Label manv_label;

    @FXML
    private Label tennv_label;

    @FXML
    private Label tenquan_label;

    @FXML
    private TableView<KhachHang> table_Giaohang;

    @FXML
    private TableColumn<KhachHang,String> tenkh_gh_column;

    @FXML
    private TableColumn<KhachHang,String> diachi_gh_column;

    @FXML
    private TableColumn<KhachHang,String> tenap_gh_column;

   // private ObservableList<ShipperTable> listShipperTable;

    private ObservableList<KhachHang> listGiaoHang;

    //Database connection
    private ConnectionClass DbConnection = new ConnectionClass();;
    private PreparedStatement pst;
    private CallableStatement callableStatement;

    public void setDetail(String manv,String tennv,String maquan){
        manv_label.setText(manv);
        tennv_label.setText(tennv);

        String sql_query_quan = "Select *from quanhuyen where maquan=?";
        try{
            Connection conn = DbConnection.getConnectionAdmin();
            pst = conn.prepareStatement(sql_query_quan);
            pst.setString(1,maquan);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                tenquan_label.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

      /*  String sql_query_shipperTable = "{call giaohang_table(?,?,?,?,?)}";
        try{
            listShipperTable = FXCollections.observableArrayList();
            Connection conn = DbConnection.getConnectionAdmin();
            callableStatement = conn.prepareCall(sql_query_shipperTable);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setString(2,maquan);
            callableStatement.registerOutParameter(3,Types.VARCHAR);
            callableStatement.registerOutParameter(4,Types.VARCHAR);
            callableStatement.registerOutParameter(5,Types.VARCHAR);

            callableStatement.executeQuery();

            ResultSet rs = (ResultSet)callableStatement.getObject(1);
            while(rs.next()){
                listShipperTable.add(new ShipperTable(rs.getString(1),rs.getString(2),rs.getString(3)));
            }*/
      listGiaoHang = KhachHang.layDiaChiGiaoHang(maquan);

            tenkh_gh_column.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("hoten"));
            diachi_gh_column.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("diachi"));
            tenap_gh_column.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("tenbao"));

            table_Giaohang.setItems(listGiaoHang);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void dangxuat(ActionEvent event){
        try {
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../ui/loginScene.fxml"));
            Parent loginScene = loader.load();
            Scene scene = new Scene(loginScene);
            stage.setTitle("Đăng nhập");
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

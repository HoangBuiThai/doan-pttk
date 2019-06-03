package Controller;

import DbConnection.ConnectionClass;
import Model.Anpham;
import Model.CTHD;
import Model.Hoadon;
import Model.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class hoadonController implements Initializable {


    @FXML
    private Label maap_label;

    @FXML
    private Label gia_label;

    @FXML
    private Label mahd_label;

    @FXML
    private Label makh_label;

    @FXML
    private Label tenkh_label;

    @FXML
    private Label tennv_label;

    @FXML
    private Label manv_label;

    @FXML
    private Label error_label;

    @FXML
    private Label error_label1;

    @FXML
    private TableView<Hoadon> table_Hoadon;

    @FXML
    private TableColumn<Hoadon,String> mahd_hd_column;

    @FXML
    private TableColumn<Hoadon,String> makh_hd_column;

    @FXML
    private TableColumn<Hoadon,String> manv_hd_column;

    @FXML
    private TableColumn<Hoadon,Integer> tongtien_hd_column;

    @FXML
    private TableColumn<Hoadon, Date> ngaylap_hd_column;

    @FXML
    private TableColumn<Hoadon,Integer> thanhtoan_hd_column;

    @FXML
    private TableView<Anpham> table_Anpham;

    @FXML
    private TableColumn<Anpham,String> maap_ap_column;

    @FXML
    private TableColumn<Anpham,String> tenap_ap_column;

    @FXML
    private TableColumn<Anpham,Integer> gia_ap_column;

    @FXML
    private TableColumn<Anpham,String> manxb_ap_column;

    @FXML
    private TableView<CTHD> table_CTHD;

    @FXML
    private TableColumn<CTHD,String> mahd_cthd_column;

    @FXML
    private TableColumn<CTHD, String> maap_cthd_column;

    @FXML
    private TableColumn<CTHD,Date> ngayketthuc_cthd_column;

    @FXML
    private TableColumn<CTHD,Integer>  gia_cthd_column;


    @FXML
    private AnchorPane cthd_scene;

    @FXML
    private AnchorPane hd_scene;

    @FXML
    private ComboBox<Integer> timeCombobox ;
    private ObservableList<Integer> time=FXCollections.observableArrayList(1,2,3,4);

    private ObservableList<Hoadon> list_hd;

    private ObservableList<Anpham> list_anpham;

    private  ObservableList<CTHD>  list_cthd ;




    public void setDetail(String tenkh,String makh,String tennv, String manv){
        makh_label.setText(makh);
        tenkh_label.setText(tenkh);
        tennv_label.setText(tennv);
        manv_label.setText(manv);

        this.loadHD();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cthd_scene.setVisible(false);
        timeCombobox.setItems(time);
        hd_scene.setVisible(true);
        this.loadHD();
    }

    //Trở về màn hình Quản lý khách hàng
    public void trangchu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../ui/employeeScene.fxml"));
        Parent employeeScene = loader.load();
        Scene scene = new Scene(employeeScene);
        employeeController eController = loader.getController();
        eController.setDetail(tennv_label.getText(),manv_label.getText());
        stage.setTitle("Quản lý khách hàng");
        stage.setScene(scene);
    }

    //Trở về màn hình Quản lý hóa đơn
    public void back(ActionEvent event){
        cthd_scene.setVisible(false);
        hd_scene.setVisible(true);
    }

    public void lapHD(ActionEvent event){
       mahd_label.setText(Hoadon.lapHD(makh_label.getText(),manv_label.getText()));
       cthd_scene.setVisible(true);
       hd_scene.setVisible(false);
       this.loadCTHD(mahd_label.getText());
       this.loadAnpham();
        this.loadHD();
    }

    public void inHD(ActionEvent event){

        Hoadon hd = table_Hoadon.getSelectionModel().getSelectedItem();
        if(hd!=null) {
            if(hd.getTongtien()!=0) {
                Hoadon.inHD(manv_label.getText(), tennv_label.getText(), makh_label.getText(), hd.getMahd(), hd.getTongtien());
            }else{
                error_label.setText("Đơn hàng chưa hoàn tất");
            }
        }else{
            error_label.setText("<<--- Vui lòng chọn Hóa đơn bên bảng");
        }
    }

    public void thanhtoan(ActionEvent event){
        if(list_cthd.size()!=0) {
            Hoadon.thanhtoan(mahd_label.getText());

            int TongTien = 0;

            for(int i=0;i<list_cthd.size();i++){
                TongTien+=list_cthd.get(i).getDongia();
            }

            Hoadon.inHD(manv_label.getText(), tennv_label.getText(), makh_label.getText(), mahd_label.getText(), TongTien);
            cthd_scene.setVisible(false);
            hd_scene.setVisible(true);
            this.loadHD();
        }else{
            error_label1.setText("Chua co sp");
        }
    }

    //Load màn hình Chi tiết hóa đơn
    public void XemCTHD(ActionEvent event){
        Hoadon selected = table_Hoadon.getSelectionModel().getSelectedItem();

        if(selected!=null) {
            if(selected.getThanhtoan()==0) {
                mahd_label.setText(selected.getMahd());
                hd_scene.setVisible(false);
                cthd_scene.setVisible(true);
                timeCombobox.setItems(time);

                this.loadAnpham();
                this.loadCTHD(mahd_label.getText());
            }else{
                error_label.setText("Đơn hàng đã chốt");
            }
        }else{
            error_label.setText("<<--- Vui lòng chọn Hóa đơn bên hảng");
        }
    }

    public void getDetailBao(MouseEvent event){

        Anpham selected = table_Anpham.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            maap_label.setText(selected.getMaap());
            gia_label.setText(String.valueOf(selected.getGiatien()));
        }
    }

    public void themCTHD(ActionEvent event){
        CTHD.themCTHD(timeCombobox.getValue(),mahd_label.getText(),maap_label.getText(),gia_label.getText());
        this.loadCTHD(mahd_label.getText());
        this.loadHD();
        this.load();

    }

    public void xoaCTHD(ActionEvent event){
        CTHD selected = table_CTHD.getSelectionModel().getSelectedItem();
        CTHD.xoaCTHD(selected.getMahd(),selected.getMaap());
        this.loadCTHD(mahd_label.getText());
        this.loadHD();
    }

    public void loadCTHD(String mahd){

        list_cthd = CTHD.HienThiCTHD(mahd);

        mahd_cthd_column.setCellValueFactory(new PropertyValueFactory<CTHD,String>("mahd"));
        maap_cthd_column.setCellValueFactory(new PropertyValueFactory<CTHD,String>("maap"));
        ngayketthuc_cthd_column.setCellValueFactory(new PropertyValueFactory<CTHD,Date>("ngayketthuc"));
        gia_cthd_column.setCellValueFactory(new PropertyValueFactory<CTHD,Integer>("dongia"));
        table_CTHD.setItems(list_cthd);
    }

    public void loadHD(){

        list_hd = Hoadon.HienThiHoaDon(makh_label.getText());

        mahd_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,String>("mahd"));
        makh_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,String>("makh"));
        manv_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,String>("manv"));
        ngaylap_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,Date>("ngaylap"));
        tongtien_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,Integer>("tongtien"));
        thanhtoan_hd_column.setCellValueFactory(new PropertyValueFactory<Hoadon,Integer>("thanhtoan"));

        table_Hoadon.setItems(list_hd);
    }

    public void loadAnpham(){

        list_anpham = Anpham.HienThiAnPham();

        maap_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("maap"));
        manxb_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("manxb"));
        tenap_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("tenap"));
        gia_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,Integer>("giatien"));
        table_Anpham.setItems(list_anpham);

    }

    public void load(){
        maap_label.setText("");
        gia_label.setText("");
        error_label.setText("");
        error_label1.setText("");

    }
}

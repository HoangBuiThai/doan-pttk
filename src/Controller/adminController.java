package Controller;


import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    //Scene
    @FXML
    private AnchorPane qlnv_Scene;

    @FXML
    private AnchorPane qlap_Scene;

    @FXML
    private AnchorPane bc_Scene;

    ///Tab Quản lý khách hàng
    @FXML
    private Label manv_label;

    @FXML
    private TextField tennv_text;

    @FXML
    private TextField username_text;

    @FXML
    private TextField password_text;

    @FXML
    private TableView<Nhanvien> table_QLNV;

    @FXML
    private TableColumn<Nhanvien,String> manv_qlnv_column;

    @FXML
    private TableColumn<Nhanvien,String> maquan_qlnv_column;

    @FXML
    private TableColumn<Nhanvien,String> macv_qlnv_column;

    @FXML
    private TableColumn<Nhanvien,String> tennv_qlnv_column;

    @FXML
    private TableColumn<Nhanvien, Date> ngayvaolam_qlnv_column;

    @FXML
    private TableColumn<Nhanvien,String> username_qlnv_column;

    @FXML
    private TableColumn<Nhanvien,String> password_qlnv_column;

    @FXML
    private ComboBox<String> maquan_Combobox;

    @FXML
    private ComboBox<String> macv_Combobox;

    //Tab Quản lý Ấn phẩm - Nhà xuất bản
    @FXML
    private Label maap_label;

    @FXML
    private TextField manxb_text;

    @FXML
    private TextField tenap_text;

    @FXML
    private TextField tennxb_text;

    @FXML
    private TextField giaap_text;

    @FXML
    private TextField TimNV;

    @FXML
    private TextField TimAP;

    @FXML
    private TextField TimNXB;

    @FXML
    private FilteredList filterNXB;

    @FXML
    private FilteredList filterAP;

    @FXML
    private FilteredList filterNV;



    //Tab báo cáo
    @FXML
    private Label ngay_label;

    @FXML
    private TextArea noidung_textare;

    @FXML
    private TableView<Baocao> table_BC;

    @FXML
    private TableColumn<Baocao,Date> ngaybaocao_bc_column;

    @FXML
    private TableColumn<Baocao,String> noidung_bc_column;

    @FXML
    private TableColumn<Baocao,String> manv_bc_column;

    //Table Quản lý ấn phẩm
    @FXML
    private TableView<Anpham> table_Anpham;

    @FXML
    private TableColumn<Anpham,String> maap_ap_column;

    @FXML
    private TableColumn<Anpham,String> manxb_ap_column;

    @FXML
    private TableColumn<Anpham,String> tenap_ap_column;

    @FXML
    private TableColumn<Anpham,Integer> giaap_ap_column;

    //Table Quản lý NXB
    @FXML
    private TableView<NXB> table_NXB;

    @FXML
    private TableColumn<NXB,String> manxb_nxb_column;

    @FXML
    private TableColumn<NXB,String> tennxb_nxb_column;

    @FXML
    private ComboBox<String> manxb_Combobox;


    private ObservableList<String> listQuanHuyen;
    private ObservableList<String> listCongviec;
    private ObservableList<Nhanvien> listNhanvien;
    private ObservableList<Anpham> listAnpham;
    private ObservableList<NXB> listNXB;
    private ObservableList<Baocao> listBC;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadQuan_Combobox();
        this.loadCV_Combobox();
        this.loadNhanVien();
        qlap_Scene.setVisible(false);
        qlnv_Scene.setVisible(true);
        bc_Scene.setVisible(false);
    }

    //Quản lý nhân viên


    public void chitiet_Nhanvien(MouseEvent event){
        Nhanvien selected = table_QLNV.getSelectionModel().getSelectedItem();
        manv_label.setText(selected.getManv());
        username_text.setText(selected.getUsername());
        password_text.setText(selected.getPassword());
        tennv_text.setText(selected.getHoten());
        macv_Combobox.getSelectionModel().select(selected.getMacv());
        maquan_Combobox.getSelectionModel().select(selected.getMaQuan());
    }

    public void themNhanVien(ActionEvent event){
        if(manv_label.getText().isEmpty()){
            Nhanvien.themNhanVien(maquan_Combobox.getValue(),macv_Combobox.getValue(),tennv_text.getText(),username_text.getText(),password_text.getText());
        }
        this.load();
        this.loadNhanVien();
    }

    public void xoaNhanVien(ActionEvent event){
        Nhanvien.xoaNhanVien(manv_label.getText());
        this.loadNhanVien();
    }

    public void suaNhanVien(ActionEvent event){
        Nhanvien.suaNhanVien(manv_label.getText(),maquan_Combobox.getValue(),macv_Combobox.getValue(),tennv_text.getText(),username_text.getText(),password_text.getText());
        this.load();
        this.loadNhanVien();
    }

    public void timNhanvien(KeyEvent event){
        Nhanvien.timNhanVien(TimNV,filterNV,table_QLNV);
    }

    public void reset(ActionEvent event){
        this.load();
    }

    //MỞ màn hình quản lý nhân viên
    public void manhinhQLNV(ActionEvent event){
        qlap_Scene.setVisible(false);
        qlnv_Scene.setVisible(true);
        bc_Scene.setVisible(false);
        this.loadCV_Combobox();
        this.loadQuan_Combobox();
        this.loadNhanVien();
    }

    //Mở màn hình quản lý ấn phẩm - Nhà Xuất bản
    public void manhinhQLAP(ActionEvent event){
        qlnv_Scene.setVisible(false);
        qlap_Scene.setVisible(true);
        bc_Scene.setVisible(false);
        this.loadAnpham();
        this.loadNXB_Combobox();
        this.loadNXB();
    }

    //Mở màn hình quản lý báo cáo
    public void manhinhBC(ActionEvent event){
        bc_Scene.setVisible(true);
        qlnv_Scene.setVisible(false);
        qlap_Scene.setVisible(false);
        this.loadBaoCao();
    }

    //Xem báo cáo
    public void chitiet_Baocao(MouseEvent event){
        Baocao selected = table_BC.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            ngay_label.setText(String.valueOf(selected.getNgaybaocao()));
            noidung_textare.setText(selected.getNoidung());
        }

    }

    //Quản lý Ấn phẩm
    public void chitiet_Anpham(MouseEvent event){
        Anpham selected = table_Anpham.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            maap_label.setText(selected.getMaap());
            tenap_text.setText(selected.getTenap());
            giaap_text.setText(String.valueOf(selected.getGiatien()));
            manxb_Combobox.getSelectionModel().select(selected.getManxb());
        }
    }

    public void themAnpham(ActionEvent event){

        Anpham.themAnPham(manxb_Combobox.getValue(),tenap_text.getText(),giaap_text.getText());
        this.loadAnpham();
    }

    public void xoaAnpham(ActionEvent event){

       Anpham.xoaAnPham(maap_label.getText());
       this.loadAnpham();
    }

    public void suaAnpham(ActionEvent event){

        Anpham.suaAnPham(maap_label.getText(),manxb_Combobox.getValue(),tenap_text.getText(),giaap_text.getText());
        this.loadAnpham();
    }

    public void timAnpham(KeyEvent event){
        Anpham.timAnpham(TimAP,filterAP,table_Anpham);
    }


    //Quản lý Nhà xuất bản
    public void chitiet_NXB(MouseEvent event){
        NXB selected = table_NXB.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            manxb_text.setText(selected.getManxb());
            tennxb_text.setText(selected.getTennxb());
        }
    }

    public void themNXB(ActionEvent event){
         NXB.themNXB(manxb_text.getText(),tennxb_text.getText());
         this.loadNXB();
         this.loadAnpham();
         this.loadNXB_Combobox();
    }

    public void suaNXB(ActionEvent event){

       NXB.suaNXB(manxb_text.getText(),tennxb_text.getText());
       this.loadNXB();
    }

    public void xoaNXB(ActionEvent event){
        NXB.xoaNXB(manxb_text.getText());
        this.loadNXB();
    }

    public void timNXB(KeyEvent event){
        NXB.timNXB(TimNXB,filterNXB,table_NXB);
    }

    //đăng xuất
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

    //method load
    public void loadBaoCao(){
        listBC = Baocao.HienThiBaoCao();
        ngaybaocao_bc_column.setCellValueFactory(new PropertyValueFactory<Baocao,Date>("ngaybaocao"));
        noidung_bc_column.setCellValueFactory(new PropertyValueFactory<Baocao,String>("noidung"));
        manv_bc_column.setCellValueFactory(new PropertyValueFactory<Baocao,String>("manv"));

        table_BC.setItems(listBC);
    }

    public void loadNhanVien(){
        manv_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("manv"));
        maquan_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("maQuan"));
        macv_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("macv"));
        tennv_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("Hoten"));
        ngayvaolam_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,Date>("ngayvaolam"));
        username_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("username"));
        password_qlnv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("password"));

        listNhanvien = Nhanvien.HienThiNhanVien();
        table_QLNV.setItems(listNhanvien);
        filterNV = new FilteredList(listNhanvien, e->true);
        this.load();

    }

    public void loadAnpham(){
        listAnpham = Anpham.HienThiAnPham();
        maap_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("maap"));
        manxb_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("manxb"));
        tenap_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,String>("tenap"));
        giaap_ap_column.setCellValueFactory(new PropertyValueFactory<Anpham,Integer>("giatien"));

        table_Anpham.setItems(listAnpham);
        filterAP = new FilteredList(listAnpham, e->true);
        this.load();

    }

    public void loadNXB(){

        listNXB = NXB.HienThiNXB();
        manxb_nxb_column.setCellValueFactory(new PropertyValueFactory<NXB,String>("manxb"));
        tennxb_nxb_column.setCellValueFactory(new PropertyValueFactory<NXB,String>("tennxb"));

        table_NXB.setItems(listNXB);
        filterNXB= new FilteredList(listNXB,e->true);
        this.load();
        this.loadNXB_Combobox();
    }

    public void loadQuan_Combobox(){
        listQuanHuyen = QuanHuyen.loadMaQuan();
        maquan_Combobox.setItems(listQuanHuyen);
    }

    public void loadCV_Combobox(){

        listCongviec = Chucvu.loadMaCV();
        macv_Combobox.setItems(listCongviec);
    }

    public void loadNXB_Combobox(){
        ObservableList<String> list =FXCollections.observableArrayList();
        listNXB=NXB.HienThiNXB();
        for(int i=0;i<listNXB.size();i++){
            list.add(listNXB.get(i).getManxb());
        }
        manxb_Combobox.setItems(list);
    }

    public void load(){
        manv_label.setText("");
        tennv_text.setText("");
        username_text.setText("");
        password_text.setText("");
        maquan_Combobox.getSelectionModel().select("");
        macv_Combobox.getSelectionModel().select("");
        manxb_text.setText("");
        maap_label.setText("");
        tenap_text.setText("");
        tennxb_text.setText("");
        giaap_text.setText("");
        manxb_Combobox.getSelectionModel().select("");

    }
}

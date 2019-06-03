package Controller;

import Model.CTHD;
import Model.KhachHang;
import Model.Nhanvien;
import Model.QuanHuyen;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class managerController implements Initializable {

    @FXML
    private Label manv_label;

    @FXML
    private Label tennv_label;

    @FXML
    private Label username_label;

    @FXML
    private Label password_label;

    @FXML
    private Label macv_label;

    @FXML
    private TableView<CTHD> tableKiemKho;

    @FXML
    private TableColumn<CTHD,String> tenap_kiemkho_column;

    @FXML
    private TableColumn<CTHD,String> maap_kiemkho_column;

    @FXML
    private TableColumn<CTHD,Integer> sl_kiemkho_column;

    @FXML
    private TableView<Nhanvien> tableNhanVien;

    @FXML
    private TableColumn<Nhanvien,String> manv_nv_column;

    @FXML
    private TableColumn<Nhanvien,String> maquan_nv_column;

    @FXML
    private  TableColumn<Nhanvien,String> macv_nv_column;

    @FXML
    private TableColumn<Nhanvien,String> tennv_nv_column;

    @FXML
    private TableColumn<Nhanvien, Date> ngayvl_nv_column;

    @FXML
    private TableColumn<Nhanvien,String> username_nv_column;

    @FXML
    private TableColumn<Nhanvien,String> password_nv_column;

    @FXML
    private AnchorPane sceneQLPP;

    @FXML
    private AnchorPane sceneQLKK;

    @FXML
    private ComboBox<String> quanCombobox;


    private ObservableList<CTHD> listDonHang;
    private ObservableList<String> listQuanHuyen;
    private ObservableList<Nhanvien> listNhanVienGH;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadDSKho();
        this.loadQuan();
        sceneQLPP.setVisible(false);
        sceneQLKK.setVisible(true);
    }

    public void manhinhQLPP(ActionEvent event){
        sceneQLPP.setVisible(true);
        sceneQLKK.setVisible(false);
        this.loadNhanvien();
    }

    public void manhinhQLKK(ActionEvent event){
        sceneQLPP.setVisible(false);
        sceneQLKK.setVisible(true);
    }

    public void laychitietThongtinNV(MouseEvent event){
        Nhanvien selected = tableNhanVien.getSelectionModel().getSelectedItem();
        manv_label.setText(selected.getManv());
        tennv_label.setText(selected.getHoten());
        username_label.setText(selected.getUsername());
        password_label.setText(selected.getPassword());
        macv_label.setText(selected.getMacv());
        quanCombobox.getSelectionModel().select(selected.getMaQuan());
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

    public void capnhat(ActionEvent event){
        Nhanvien.suaNhanVien(manv_label.getText(),quanCombobox.getValue(),macv_label.getText(),tennv_label.getText(),username_label.getText(),password_label.getText());
    }

    public void loadDSKho(){
        try {
            listDonHang = CTHD.DanhSachKiemKho();
            tenap_kiemkho_column.setCellValueFactory(new PropertyValueFactory<CTHD,String>("tenap"));
            maap_kiemkho_column.setCellValueFactory(new PropertyValueFactory<CTHD,String>("maap"));
            sl_kiemkho_column.setCellValueFactory(new PropertyValueFactory<CTHD,Integer>("soluong"));
            tableKiemKho.setItems(listDonHang);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadQuan(){

        listQuanHuyen = QuanHuyen.loadMaQuan();
        quanCombobox.setItems(listQuanHuyen);
    }

    public void loadNhanvien(){
        listNhanVienGH = Nhanvien.loadNhanVienByCV("GH");
        manv_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("manv"));
        maquan_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("maQuan"));
        tennv_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("Hoten"));
        macv_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("macv"));
        ngayvl_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,Date>("Ngayvaolam"));
        username_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("username"));
        password_nv_column.setCellValueFactory(new PropertyValueFactory<Nhanvien,String>("password"));

        tableNhanVien.setItems(listNhanVienGH);
    }

    public void load(){
        macv_label.setText("");
        manv_label.setText("");
        tennv_label.setText("");
        this.loadQuan();

    }
}

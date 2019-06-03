package Controller;

import DbConnection.ConnectionClass;
import Model.KhachHang;
import Model.QuanHuyen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class employeeController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label manvLabel;

    @FXML
    private Label error_label;

    @FXML
    private TableView<KhachHang> tableKH;


    @FXML
    private TableColumn<KhachHang,String> mkhColumn;

    @FXML
    private TableColumn<KhachHang,String> hotenColumn;

    @FXML
    private TableColumn<KhachHang,String> cmndColumn;

    @FXML
    private TableColumn<KhachHang,String> sdtColumn;

    @FXML
    private TableColumn<KhachHang,String> diachiColumn;

    @FXML
    private TableColumn<KhachHang,String> quanColumn;

    @FXML
    private TextField TimKH;

    @FXML
    private Label mkhText;

    @FXML
    private TextField hotenText;

    @FXML
    private TextField cmndText;

    @FXML
    private TextField sdtText;

    @FXML
    private TextField diachiText;


    @FXML
    private ComboBox<String> quanCombobox;

    private ObservableList<String> listQuanHuyen;
    private ObservableList<KhachHang> khachHangList;
    private FilteredList filter;


    public void setDetail(String name,String manv){
        usernameLabel.setText(name);
        manvLabel.setText(manv);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            this.loadKhachHang();
            this.loadQuan();

    }

    @FXML
    public void getDetailCustomer(MouseEvent e){
        KhachHang selected = tableKH.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            mkhText.setText(selected.getMakh());
            hotenText.setText(selected.getHoten());
            cmndText.setText(selected.getCMND());
            sdtText.setText(selected.getSdt());
            diachiText.setText(selected.getDiachi());
            quanCombobox.getSelectionModel().select(selected.getMaQuan());
        }
    }

    @FXML
    public void ThemKhachHang(ActionEvent event) throws SQLException {
        if(mkhText.getText().isEmpty() ) {
            if(!hotenText.getText().isEmpty() && !diachiText.getText().isEmpty() && !sdtText.getText().isEmpty()) {
                KhachHang.themKhachHang(quanCombobox.getValue(), hotenText.getText(), cmndText.getText(), diachiText.getText(), sdtText.getText());
                this.loadKhachHang();
                this.load();
            }else{
                error_label.setText("Vui lòng nhập đầy đủ thông tin");
            }
        }else{
            error_label.setText("Khách hàng đã tồn tại");
        }
    }

    @FXML
    public void reset(ActionEvent event){
        this.load();
    }



    @FXML
    public void SuaKhachHang(ActionEvent event){
        if(!mkhText.getText().isEmpty()) {
            KhachHang.suaKhachHang(mkhText.getText(), hotenText.getText(), cmndText.getText(), diachiText.getText(), sdtText.getText(), quanCombobox.getValue());
            this.loadKhachHang();
            this.load();
        }else{
            error_label.setText("Khách hàng chưa tồn tại");
        }
    }

    @FXML
    public void XemHoaDon(ActionEvent event){
        if(!mkhText.getText().isEmpty()) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../ui/hoadonScene.fxml"));
                Parent hoadonScene = loader.load();
                Scene scene = new Scene(hoadonScene);
                hoadonController hdcontroller = loader.getController();
                hdcontroller.setDetail(hotenText.getText(), mkhText.getText(), usernameLabel.getText(), manvLabel.getText());
                stage.setTitle("Hóa đơn");
                stage.setScene(scene);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void TimKhachHang(KeyEvent event) throws IOException{
       KhachHang.timKhachHang(TimKH,filter,tableKH);
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

    public void load(){
        mkhText.setText("");
        diachiText.setText("");
        cmndText.setText("");
        hotenText.setText("");
        sdtText.setText("");
        error_label.setText("");
        quanCombobox.getSelectionModel().select("");
    }

    public void loadQuan(){

        listQuanHuyen = QuanHuyen.loadMaQuan();
        quanCombobox.setItems(listQuanHuyen);
    }

    public void loadKhachHang(){

        khachHangList = KhachHang.HienThiKhachHang();

        mkhColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("makh"));
        hotenColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("hoten"));
        cmndColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("CMND"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("sdt"));
        diachiColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("diachi"));
        quanColumn.setCellValueFactory(new PropertyValueFactory<KhachHang,String>("maQuan"));

        tableKH.setItems(khachHangList);
        filter = new FilteredList(khachHangList, e->true);

    }
}






